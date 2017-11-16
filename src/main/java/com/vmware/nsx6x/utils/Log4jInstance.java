package com.vmware.nsx6x.utils;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class Log4jInstance {
	private static Logger logger = null;

	public static Logger getLoggerInstance(){
		logger = Logger.getRootLogger();
//		DOMConfigurator.configure("Log\\log4j.xml");
		String log4jXml = Config.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "/log4j.xml";
		DOMConfigurator.configure(log4jXml);
		return logger;
	}
}
