package com.vmware.nsx6x.utils;

import static org.testng.AssertJUnit.assertEquals;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class Log {
	private static Log log = null;
	private List<String> TESTTYPES = TestConstants.TESTTYPES;
	private List<String> LANGUAGES = TestConstants.LANGUAGES;
	private List<String> RESULTTYPES = TestConstants.RESULTTYPES;
	private List<String> VERIFYRESULTS = TestConstants.VERIFYRESULTS;
	private String testCaseResult = null;
	private String FeatureName = null;
	private String TestCaseName = null;
	private boolean hasException = false;
	private boolean testCaseStarted = false;
	private boolean testSetStarted = false;
	private int ScreenshotNum = 0;
	private int CheckPointNum = 0;
	private String currentDir = System.getProperty("user.dir");
	private String sLogFileName = "TestLog" + new SimpleDateFormat("yyMMddHHmmss").format(new Date()).toString() + DefaultEnvironment.InputLanguage + ".txt";
	private boolean  sLogOnRacetrack = new Boolean(Config.getInstance().ConfigMap.get("LogOnRaceTrack")).booleanValue();
	private Racetrack racetrack = new Racetrack();
	private LocalLog localLogger= null;
	private Config cfg = Config.getInstance();
	/**
	 * Create log file. 
	 * Below is the parameters needed in Config.cfg file
      Param          Required?   Description
      BuildID        Yes         The build number that is being tested
      User           Yes         The user running the test
      Product        Yes         The name of the product under test
      Description    Yes         A description of this test run
      HostOS         Yes         The Host OS
      ServerBuildID  No          The build number of the "server" product
      Branch         No          The branch which generated the build
      BuildType      No          The type of build under test
      TestType       No          default Regression
      Language       No          default English 
	 * @throws IOException
	 */
	public void testSetBegin()
	{
		try {
			localLogger.log("Test Begin!");
			if (cfg.ConfigMap.get("TestType") != null && !TESTTYPES.contains(cfg.ConfigMap.get("TestType")))
			{
				localLogger.log("testSetBegin: Specified test type is invalid - " + cfg.ConfigMap.get("TestType"));
				return;
			}
			if (this.sLogOnRacetrack)
				racetrack.testSetBegin(cfg.ConfigMap.get("BuildID"), 
						cfg.ConfigMap.get("TesterName"), 
						cfg.ConfigMap.get("Product"), 
						cfg.ConfigMap.get("Description"), 
						cfg.ConfigMap.get("HostOS"), 
						cfg.ConfigMap.get("ServerBuildID"), 
						cfg.ConfigMap.get("Branch"), 
						cfg.ConfigMap.get("BuildType"), 
						cfg.ConfigMap.get("TestType"), 
						cfg.ConfigMap.get("Language"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.testSetStarted = true;
	}
	/**
	 * testSetEnd - End the test set
	 */
	public void testSetEnd()
	{
		if (this.testSetStarted) {
			try {
				localLogger.log("Test End!");
				localLogger.EndLog();
				if (this.sLogOnRacetrack && this.testSetStarted)
					racetrack.testSetEnd();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.testCaseStarted = false;
		}
	}
	/**
	 * testCaseBegin - Start a new test case
      Param          Required?   Description
      testCaseId	 Yes		 Against column 'TestIDForAutomation' in excel sheet
      Name           Yes         The name of the test case
      Feature        Yes         The feature that is being tested
      Description    No          A description of this test case
	 */
	public void testCaseBegin(String testCaseId, String Name, String Feature, String Description)
	{
		try {
			this.ScreenshotNum = 0;
			this.CheckPointNum = 0;
			this.FeatureName = Feature;
			this.TestCaseName = Name;
			localLogger.log("********TestCaseBegin********");
			localLogger.log("****ID: " + testCaseId);
			localLogger.log("****Name: " + Name);
			localLogger.log("****Description: " + Description);
			if (this.sLogOnRacetrack && this.testSetStarted)
				racetrack.testCaseBegin(Name, Feature, Description, null, testCaseId,
						cfg.getInstance().ConfigMap.get("InputLanguage"));
			this.testCaseResult = TestConstants.PASS;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.testCaseStarted = true;
	}
	/** 
	 * testCaseEnd - End a test case
      Param          Required?   Description
      Result         No          The result of the test. Enum of 'PASS',
                                 'FAIL', 'RUNNING','CONFIG','SCRIPT',
                                 'PRODUCT','RERUNPASS', or 'UNSUPPORTED'
	 */
	public void testCaseEnd(String Result)
	{
		if (this.testCaseStarted) {
			try {
				if(Result != null)
					this.testCaseResult = Result;
				if (this.hasException) {
					this.testCaseResult = TestConstants.FAIL;
				}
				if (this.CheckPointNum==0) {
					log.log("****Script issue: Test case has no any checkpoint."
							+ "Please check your test script.****");
					this.testCaseResult = TestConstants.FAIL;
				}
				localLogger.log("********TestCaseEnd: " + this.testCaseResult + "************");
				if (this.sLogOnRacetrack && this.testSetStarted) {
					if (this.hasException) {
						racetrack.testCaseEnd(TestConstants.FAIL);
					}
					else {
						racetrack.testCaseEnd(this.testCaseResult);
					}
				}
				//reset all
				this.testCaseResult = null;
				this.FeatureName = null;
				this.TestCaseName = null;
				this.ScreenshotNum = 0;
				this.CheckPointNum = 0;
				this.hasException = false;
				this.testCaseStarted = false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				this.testCaseResult = null;
			}
			this.testCaseStarted = false;
		}
	}

	public void log(String Description)
	{
		try {
			localLogger.log(Description);
			if (this.sLogOnRacetrack && this.testSetStarted)
				racetrack.Comment(Description);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void verifyEqual(String description, Object actual, Object expected){
		verifyEqual(description, actual, expected, null);
	}

	/**
	 * Parameters          		Required?   Description
	 * @param description	Yes         Description of test logic steps
	 * @param actual		Yes         The actual value. (any string)
	 * @param expected		Yes         The expected value. (any string)
	 * @param errorMessage	Yes			Error message on code level
	 */
	public void verifyEqual(String description, Object actual,
			Object expected, String errorMessage)
	{
		this.CheckPointNum++;
		String result = "";  
		try {
			//local verify
			if (description != null) {
				localLogger.log("--Checkpoint: " + description);
			}
			if (actual == null)
			{
				localLogger.log("Error - Actual is null.");
				result = TestConstants.FAIL;
			}
			if (expected == null)
			{
				localLogger.log("Error - Expected is null.");
				result = TestConstants.FAIL;
			}
			if (result != TestConstants.FAIL && actual.equals(expected)){
				result = TestConstants.PASS;
			}
			else
			{
				result = TestConstants.FAIL;
				this.testCaseResult = TestConstants.FAIL;
				this.takeScreenshot();
				if (errorMessage!=null){
					this.log(errorMessage);
				}
			}
			localLogger.log(String.format("----%s : Actual is {%s} and expected is {%s}",
					result, actual, expected));
			//racetrack verify
			if (this.sLogOnRacetrack && this.testSetStarted)
			{	
				System.out.println("upload racetrack "+description);
				racetrack.verify(description, actual, expected, null, errorMessage);// racetrack.verify(Description, Actual, Expected, screenShot); // should be 
				//	the "screenShot" as the last parameter. After the function 
				// TakeScreenshot() implemented, will revert it back.
			}

			//testNG verify
			assertEquals(description, expected, actual);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.log(e.toString());
			this.testCaseResult = TestConstants.FAIL;
		}
		
	}

	public void verifyTrue(String description, boolean actual){
		verifyTrue(description, actual, null);
	}

	public void verifyTrue(String description, boolean actual, String errorMessage){
		verifyEqual(description, actual, true, errorMessage);
	}

	public void verifyFalse(String description, boolean actual){
		verifyFalse(description, actual, null);
	}

	public void verifyFalse(String description, boolean actual, String errorMessage){
		verifyEqual(description, actual, false, errorMessage);
	}

	public void verifyNull(String description, Object actual){
		verifyTrue(description, actual == null, null);
	}

	public void verifyNull(String description, Object actual, String errorMessage){
		verifyTrue(description, actual == null, errorMessage);
	}

	public void verifyNotNull(String description, Object actual){
		verifyNotNull(description, actual, null);
	}

	public void verifyNotNull(String description, Object actual, String errorMessage){
		verifyFalse(description, actual == null, errorMessage);
	}

	/**
	 * log - upload a log

      Param          Required?   Description
      Description    Yes         The comment
	 */
	public void uploadLogToRacetrack(String Description)
	{
		try {
			if (this.sLogOnRacetrack && this.testSetStarted)
				racetrack.uploadLog(Description, this.currentDir + "\\" + this.sLogFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * log - upload a log

      Param          Required?   Description
      Description    Yes         The comment
	 */
	public void uploadScreeshotToRacetrack(String Description, String screenshot)
	{
		try {
			if (this.sLogOnRacetrack && this.testSetStarted)
				racetrack.uploadLog(Description, screenshot);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Take the screenshot
	 */
	private boolean takeScreenshot(String screenshot)
	{
		return true;
	}

	private void takeScreenshot(){
		String screenShot = null;
		screenShot = this.FeatureName + "_" + this.TestCaseName + this.ScreenshotNum;
		this.ScreenshotNum = this.ScreenshotNum + 1;
		this.takeScreenshot(screenShot);
	}
	private Log()
	{
		try {
			String logFile = this.currentDir + "\\" + this.sLogFileName;
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(logFile),"UTF-8");
			out.write(Config.getInstance().ConfigMap.toString().replace("{", "").replace("}", "") + System.getProperty("line.separator"));
			out.flush();
			out.close();
			localLogger = LocalLog.getInstance(logFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static synchronized Log getInstance()
	{

		if (log == null){
			log = new Log();

		}

		return log;
	}
	public void handleException(Throwable exception) {
		try {
			StringWriter sWriter = new StringWriter();
			PrintWriter pWriter = new PrintWriter(sWriter);
			exception.printStackTrace(pWriter);
			this.log(sWriter.toString());
			pWriter.flush();
			pWriter.close();
			sWriter.flush();
			sWriter.close();
			this.hasException = true;
		}
		catch (IOException e) {
			this.log("Print exception stack trace failed.");
			e.printStackTrace();
		}
	}
}
