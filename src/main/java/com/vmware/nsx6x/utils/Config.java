package com.vmware.nsx6x.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

public class Config {
	private static Config config = null;
	public  HashMap<String, String> ConfigMap = new HashMap<String, String>();
//	private String configFile = System.getProperty("user.dir") + "\\src\\main\\resources\\Config.cfg";
	
	String filepath = Config.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	private String configFile = filepath + "/Config.cfg";
	
	private Config()
	{
		readConfigFile();
	}

	public static synchronized Config getInstance()
	{
		if (config == null){
			config = new Config();
		}
		return config;
	}
	
	private Boolean readConfigFile()
	{
		this.ConfigMap = new HashMap<String, String>();

		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(configFile), "UTF-8"));
			String s = null;
			try {
				while ((s = br.readLine()) != null)
				{
					if(s.length()==0)
						continue;
					String[] sTemp = s.split("=");
					String value = null;
					if (sTemp.length > 1)
					{
						if(!sTemp[1].trim().isEmpty())
							value = sTemp[1].trim();
					}
					this.ConfigMap.put(sTemp[0].trim(), value);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
//	public Boolean bInit = readConfigFile();
//	public String VSMIP = this.ConfigMap.get("VSMIPAddress");
//	public String VSMUserName = this.ConfigMap.get("VSMUserName");
//	public String VSMPassword = this.ConfigMap.get("VSMPassword");
//	public String VCIP = this.ConfigMap.get("VCIP");
//	public String VCUserName = this.ConfigMap.get("VCUserName");
//	public String VCPassword = this.ConfigMap.get("VCPassword");
	
}
