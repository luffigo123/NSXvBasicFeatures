package com.vmware.nsx6x.utils;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashMap;

public class TestData {
//	private static String TestDataFile = System.getProperty("user.dir") + "\\src\\main\\resources\\TestData_" + Config.getInstance().ConfigMap.get("InputLanguage");
	private static String filepath = Config.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	private static String TestDataFile = filepath +  "/TestData_" + Config.getInstance().ConfigMap.get("InputLanguage");
	
	private static HashMap<String, String> TestDataMap = readDataFile();
	private TestData(){}
	public static final Collection<String> AllTestData = TestDataMap.values();
	public static final String NativeString = readDataFile("NativeString");
	public static final String SuperString = readDataFile("SuperString");
	
	private static String readDataFile(String Key)
	{
		return TestDataMap.get(Key);
	}
	private static HashMap<String, String> readDataFile()
	{
		try {
			HashMap<String, String> TestDataMapTemp = new HashMap<String, String>();
			InputStreamReader isr = new InputStreamReader(new FileInputStream(TestDataFile), "UTF-8");
			BufferedReader bufr = new BufferedReader(isr);
			String line = null;
			while((line = bufr.readLine())!=null)
			{
				if(line.length()==0)
					continue;
				String[] sTemp = line.split("=");
				String value = null;
				if (sTemp.length > 1)
				{
					if(!sTemp[1].trim().isEmpty())
						value = sTemp[1].trim();
				}
				TestDataMapTemp.put(sTemp[0].trim(), value);
				
			}
			bufr.close();			isr.close();
			return TestDataMapTemp;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
