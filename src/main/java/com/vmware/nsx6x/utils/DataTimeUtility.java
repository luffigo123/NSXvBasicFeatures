package com.vmware.nsx6x.utils;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DataTimeUtility {
	
	/**
	 * Return a string of current time in format: mm-dd-hh-mm
	 * @return
	 */
	public static String getCurrentDateTime() {
		
		Date now = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("MMdd-HHmm");
		String formattedDate = formatter.format(now);
		return formattedDate;
	}
	
	
	/**
	 * Return a string of current time in format: MM-dd-YYYY HH:mm:ss
	 * @return String formated date
	 */
	public static String getCurrentDateTimeFull () {
		Date now = new Date ();
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-YYYY HH:mm:ss");
		String formattedDate = formatter.format(now);
		return formattedDate;
	}

}
