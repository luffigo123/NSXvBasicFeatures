package com.vmware.nsx6x.utils;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IPAddressUtils {
	/**
	 * Used to varify if the string as a valid IPv4 address.
	 * @param ipv4Address
	 * @return true if valid.
	 */
//	static Boolean checkIfIPv4Address (String ipv4Address) {
//		Boolean isIPv4 = false;
//		
//		Pattern ippattern = Pattern.compile("((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)");
//		if(ipv4Address!="") {
//			Matcher m = ippattern.matcher(ipv4Address);
//			if(m.find()) {
//				isIPv4 = true;
//			}
//		}
//        return isIPv4;
//
//	}
//	
//	/**
//	 * Generate an IP address
//	 * @param first - IP Address first section
//	 * @param second - IP Address second section
//	 * @param third -- IP Address third section
//	 * @return an IP address
//	 */
//	public static String generateIPAddress(String first, String second, String third)
//	{
//		String ipAddress = null;		
//		ipAddress = first + second + third + String.valueOf(RandomUtil.generateRandomNum());	
//		return ipAddress;
//	}
//	
//	/**
//	 *  Generate an ip section
//	 * @param first
//	 * @param second
//	 * @param third
//	 * @return
//	 */
//	public static String generateIPSection(String first, String second, String third)
//	{
//		String ipSection = null;
//		int num1 = RandomUtil.generateRandomNum();
//		int num2 = RandomUtil.generateRandomNumSeed();
//		int min=0;
//		int	max=0;
//		
//		if(num1 > num2)
//		{
//			max = num1;
//			min = num2;
//		}
//		else if (num1 < num2) 
//		{
//			max = num2;
//			min = num1;
//		}
//		else 
//		{
//			if(num1 == 1)
//			{
//				min = num1;
//				max = num1 +10;
//			}
//			else 
//			{
//				min = num1 -1;
//				max = num1;
//			}
//		}
//		
//		String fst = first + "." + second + "." + third + ".";
//		ipSection = fst + String.valueOf(min) + "-" + fst + String.valueOf(max);	
//		return ipSection;
//	}
	
	
	/**
	 * Generate random mac address
	 * @return
	 */
	public static String generateMacAddress()
	{
		String [] ch ={"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};
    	int len = ch.length;
    	int index;
    	String result = new String();
    	Random rd = new Random();
    	for(int i=1;i<=12;i++){
    		index = rd.nextInt(len);
    		if(i%2 == 0 && i != 12 )
    			result = result + ch[index]+ ":";
    		else
    			result = result + ch[index];
    	}
		return result;
	}
	

}
