package com.vmware.nsx6x.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.FilePartSource;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;

/**
 * This class encapsulates the functionality to interact with the
 * Racetrack web service.
 *
 * @author Sean Li
 */

public class Racetrack {

	private String RaceTrackServer = "racetrack.eng.vmware.com";
	private String testSetID = null;
	private String testCaseID = null;
	private String testCaseResult = null;
	private List<String> TESTTYPES = TestConstants.TESTTYPES;
	private List<String> LANGUAGES = TestConstants.LANGUAGES;
	private List<String> RESULTTYPES = TestConstants.RESULTTYPES;
	private List<String> VERIFYRESULTS = TestConstants.VERIFYRESULTS;
	private LocalLog logger = LocalLog.getInstance(null);
	
	private static final String encoding = "UTF-8";
	/**
	 * Returns a new test set ID on success or null on failure.
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
	public String testSetBegin(String BuildID, String User, String Product, String Description, String HostOS, 
			String ServerBuildID, String Branch, String BuildType, String TestType, String Language) throws IOException
	{
		String result = "";
		if (Language != null && !this.LANGUAGES.contains(Language))
		{
			logger.log("testSetBegin: Specified language is invalid - " + Language);
			return null;
		}
		if (TestType != null && !this.TESTTYPES.contains(TestType))
		{
			logger.log("testSetBegin: Specified test type is invalid - " + TestType);
			return null;
		}
		NameValuePair[] params = new NameValuePair[10];
		params[0] = new NameValuePair("BuildID", BuildID);
		params[1] = new NameValuePair("User", User);
		params[2] = new NameValuePair("Product", Product);
		params[3] = new NameValuePair("Description", Description);
		params[4] = new NameValuePair("HostOS", HostOS);
		params[5] = new NameValuePair("ServerBuildID", ServerBuildID);
		params[6] = new NameValuePair("Branch", Branch);
		params[7] = new NameValuePair("BuildType", BuildType);
		params[8] = new NameValuePair("TestType", TestType);
		params[9] = new NameValuePair("Language", Language);
		result = post("TestSetBegin.php", params);
		this.testSetID = result;
		return result;
	}
	/**
	 * testSetEnd - End the test set

      Param          Required?   		Description
      testSetID             No          The test set/run that is being completed.
	 * @return
	 * @throws IOException 
	 */
	public String testSetEnd() throws IOException
	{
		String result = "";
		NameValuePair[] params = new NameValuePair[1];
		params[0] = new NameValuePair("ID", this.testSetID);
		result = post("TestSetEnd.php", params);
		return result;
	}
	
	/**
	 * testCaseBegin - Start a new test case

      Param          Required?   Description
      Name           Yes         The name of the test case
      Feature        Yes         The feature that is being tested
      Description    No          A description of this test case
      MachineName    No          The host that the test is running against
      TCMSID         No          A comma-separated Testlink (TCMSID) ID's.
      InputLanguage  No          abbreviation for the language used eg 'EN'
      ResultSetID    No          The test set/run that is being completed. (We will use the testSetID which is created in testSetBegin)
	 * @return
	 * @throws IOException 
	 */
	public String testCaseBegin(String Name, String Feature, String Description, String MachineName, String TCMSID, String InputLanguage) throws IOException
	{
		String result = "";
		if (this.testSetID == null)
		{
			logger.log("testCaseBegin called but there is no active test set.");
			return null;
		}
		this.testCaseID = null;
		NameValuePair[] params = new NameValuePair[7];
		params[0] = new NameValuePair("Name", Name);
		params[1] = new NameValuePair("Feature", Feature);
		params[2] = new NameValuePair("Description", Description);
		params[3] = new NameValuePair("MachineName", MachineName);
		params[4] = new NameValuePair("TCMSID", TCMSID);
		params[5] = new NameValuePair("InputLanguage", InputLanguage);
		params[6] = new NameValuePair("ResultSetID", this.testSetID);
		result = post("TestCaseBegin.php", params);
		if (result == null)
		{
			logger.log("testCaseBegin failed, no testCaseID obtained.");
			return null;
		}
		this.testCaseID = result;
		this.testCaseResult = "PASS";
		return result;
	}
	/** 
	 * testCaseEnd - End a test case
      Param          Required?   Description
      Result         No          The result of the test. Enum of 'PASS',
                                 'FAIL', 'RUNNING','CONFIG','SCRIPT',
                                 'PRODUCT','RERUNPASS', or 'UNSUPPORTED'
	 * @return
	 * @throws IOException 
	 */
	public String testCaseEnd(String Result) throws IOException
	{
		String result = "";
		if (Result == null)
			Result = this.testCaseResult;
		if (!this.RESULTTYPES.contains(Result))
		{
			logger.log("testCaseEnd: Specified test result is invalid. - " + Result);
			return null;
		}
		NameValuePair[] params = new NameValuePair[2];
		params[0] = new NameValuePair("Result", Result);
		params[1] = new NameValuePair("ID", this.testCaseID);
		result = post("TestCaseEnd.php", params);
		this.testCaseID = null;
		this.testCaseResult = null;
		return result;
	}
	public String Comment(String Description) throws IOException
	{
		String result = "";
		if (Description == null || Description.isEmpty())
		{
			logger.log("Racetrack.Comment(): there is no active test case.");
			return null;
		}
		if (this.testCaseID == null)
        {
            logger.log("Racetrack.Comment(): there is no Description.");
            return null;
        }
		NameValuePair[] params = new NameValuePair[2];
		params[0] = new NameValuePair("Description", Description);
		params[1] = new NameValuePair("ResultID", this.testCaseID);
		result = post("TestCaseComment.php", params);
		return result;
	}
	/**
	 * screenshot - upload a screenshot

      Param          Required?   Description
      Description    Yes         The comment
      Screenshot     Yes         The screenshot location including file name and path
	 * @return
	 * @throws IOException 
	 */
	public String uploadScreenshot(String Description, String Screenshot) throws IOException
	{
		String result = "";
		if (this.testCaseID == null)
		{ 	
			logger.log("uploadScreenshot(): uploadScreenshot called but there is no active test case.");
			return null;
		}
		
		File image=new File(Screenshot);
		FilePartSource filePS = new  FilePartSource("fd", image);
		Part[] parts = new Part[3];
		parts[0] = new StringPart("Description", Description);
		parts[1] = new StringPart("ResultID", this.testCaseID);
		parts[2] = new FilePart("Screenshot", filePS, "filename", Screenshot.substring(Screenshot.lastIndexOf("\\") + 1));
		
		
		result = post("TestCaseScreenshot.php", parts);
		return result;
	}
	
	/**
	 * log - upload a log

      Param          Required?   Description
      Description    Yes         The comment
      Log            Yes         The log location including file name and path
	 */
	public String uploadLog(String Description, String log) throws IOException
	{
		String result = "";
		if (this.testCaseID == null)
		{ 	
			logger.log("uploadLog(): uploadLog called but there is no active test case.");
			return null;
		}
		
		
		File logFile=new File(log);
		FilePartSource filePS = new  FilePartSource("fd", logFile);
		
		Part[] parts = new Part[3];
		parts[0] = new StringPart("Description", Description);
		parts[1] = new StringPart("ResultID", this.testCaseID);
		parts[2] = new FilePart("Log", filePS, "filename", log.substring(log.lastIndexOf("\\") + 1));
		
		result = post("TestCaseLog.php", parts);
		return result;
	}
	
	/**
	 * Param          		Required?   Description
	 * @param Description	Yes         The comment
	 * @param Actual		Yes         The actual value. (any string)
	 * @param Expected		Yes         The expected value. (any string)
	 * @param Screenshot	No          A screenshot associated with the (failed) verification
	 * @param errorMessage	No			Print error message if verification is false.
	 * @return
	 * @throws IOException 
	 */
	public String verify(String Description, Object Actual, Object Expected, String Screenshot, String errorMessage) throws IOException
	{
		String result = "";
		String verifyResult = "";  
		if (this.testCaseID == null)
		{
			logger.log("verify(): verify called but there is no active test case.");
			return null;
		}
		if (Actual == null)
		{
			logger.log("verify(): Error - Actual is null.");
			verifyResult = TestConstants.FALSE;
			this.testCaseResult = TestConstants.FAIL;
		}
		if (Expected == null)
		{
			logger.log("verify(): Error - Expected is null.");
			verifyResult = TestConstants.FALSE;
			this.testCaseResult = TestConstants.FAIL;
		}
		if (Actual.equals(Expected))
		{
			verifyResult = TestConstants.TRUE;
//			this.testCaseResult = "PASS";//only failed verification should change this variable 
		}
		else 
		{
			verifyResult = TestConstants.FALSE;
			this.testCaseResult = TestConstants.FAIL;
		}
		// this.testCaseResult = Result;
		NameValuePair[] params = new NameValuePair[5];
		params[0] = new NameValuePair("Description", Description);
		params[1] = new NameValuePair("Actual", Actual.toString());
		params[2] = new NameValuePair("Expected", Expected.toString());
		params[3] = new NameValuePair("Result", verifyResult);
		params[4] = new NameValuePair("ResultID", this.testCaseID);
		if (Screenshot == null)
			result = post("TestCaseVerification.php", params);
		else
		{
			File image=new File(Screenshot);
			FilePartSource filePS = new  FilePartSource("fd", image);
			Part[] parts = new Part[6];
			parts[0] = new StringPart("Description", Description);
			parts[1] = new StringPart("Actual", Actual.toString());
			parts[2] = new StringPart("Expected", Expected.toString());
			parts[3] = new StringPart("Result", verifyResult);
			parts[4] = new StringPart("ResultID", this.testCaseID);
			parts[5] = new FilePart("Screenshot", filePS, "filename", Screenshot.substring(Screenshot.lastIndexOf("\\") + 1));

			result = post("TestCaseVerification.php", parts);
		}
		if (verifyResult == TestConstants.FALSE) {
			Comment(errorMessage);
		}
		return result;
	}
	
	/**
	 * Param          		Required?   Description
	 * @param Description	Yes         The comment
	 * @param Actual		Yes         The actual value. (any string)
	 * @param Expected		Yes         The expected value. (any string)
	 * @param Screenshot	No          A screenshot associated with the (failed) verification
	 * @return
	 * @throws IOException 
	 */
	public String verify(String Description, Object Actual, Object Expected, String Screenshot) throws IOException
	{
		return verify(Description, Actual, Expected, Screenshot, null);
	}
	
	public String post(String webServiceMethod, NameValuePair[] params ) throws IOException
	{
		//BufferedReader rd = null;
		String responseContent = null;  
		HttpClient client = new HttpClient();
		PostMethod postRequest = new UTF8PostMethod("http://" + this.RaceTrackServer + "/" + webServiceMethod);

		postRequest.setRequestBody(params);
		
		try {
			client.executeMethod(postRequest);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		responseContent = postRequest.getResponseBodyAsString();  
		postRequest.releaseConnection(); 
		
		return responseContent;
		//InputStream responseStream = postRequest.getResponseBodyAsStream();
		//rd = new BufferedReader(new InputStreamReader(responseStream,  encoding)); 
		
	}
	public String post(String webServiceMethod, Part[] parts) throws IOException
	{
		//BufferedReader rd = null;
		String responseContent = null;  
		HttpClient client = new HttpClient();
		client.getParams().setParameter("http.protocol.content-charset", encoding);
		PostMethod postRequest = new PostMethod("http://" + this.RaceTrackServer + "/" + webServiceMethod);

		//postRequest.setRequestBody(params);
		postRequest.setRequestEntity(new MultipartRequestEntity(parts,postRequest.getParams()));
		client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		try {
			client.executeMethod(postRequest);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		responseContent = postRequest.getResponseBodyAsString();  
		postRequest.releaseConnection(); 
		
		return responseContent;
		//InputStream responseStream = postRequest.getResponseBodyAsStream();
		//rd = new BufferedReader(new InputStreamReader(responseStream,  encoding)); 
		
	}
	public static class UTF8PostMethod extends PostMethod{
	
		public UTF8PostMethod(String url){
			super(url);
		}
		public String getRequestCharSet(){
			return encoding;
		}
	}
}
