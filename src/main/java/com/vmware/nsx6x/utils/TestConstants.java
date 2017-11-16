package com.vmware.nsx6x.utils;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class TestConstants {
	public static final String FAIL = "FAIL";
	public static final String PASS = "PASS";
	public static final String FALSE = "FALSE";
	public static final String TRUE = "TRUE";
	public static final List<String> TESTTYPES = Arrays.asList("BATS","Smoke","Regression", "DBT", "Unit", "Performance");
	public static final List<String> LANGUAGES = Arrays.asList("English","Japanese",
			"French", "Italian", "German", "Spanish", "Portuguese", "Chinese", "Korean");
	public static final List<String> RESULTTYPES = Arrays.asList("PASS","FAIL",
			"RUNNING", "CONFIG", "SCRIPT", "PRODUCT", "RERUNPASS", "UNSUPPORTED");
	public static final List<String> VERIFYRESULTS = Arrays.asList("TRUE","FALSE");
//	public static final String PATH_CURRENTDIR = System.getProperty("user.dir");
//	public static final String PATH_RestCallXML = PATH_CURRENTDIR + "\\RestCallXML";
	
//	public static final String PATH_CURRENTDIR = Config.class.getProtectionDomain().getCodeSource().getLocation().getPath();
//	public static final String PATH_RestCallXML = PATH_CURRENTDIR + "/RestCallXML/";
//	public static final String PATH_Congif = PATH_CURRENTDIR + File.separator + "config";
	
	public static final String PATH_CURRENTDIR = System.getProperty("user.dir");

	public static final String PATH_Congif = PATH_CURRENTDIR + File.separator + "\\src\\main\\resources\\Config.cfg";
	public static final String PATH_RestCallXML = PATH_CURRENTDIR + File.separator + "\\src\\main\\resources\\RestCallXML\\";
	
	public static final String defaultDesc = "DefaultDes" + TestData.NativeString;

}
