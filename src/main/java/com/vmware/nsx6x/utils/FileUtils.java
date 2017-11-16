package com.vmware.nsx6x.utils;

import java.io.File;

public class FileUtils {
	public static Boolean chekFileOrString (String filePath) {
		Boolean isFile;
		File f = new File(filePath);
		if(f.exists()) {
			isFile = true;
		}
		else 
			isFile = false;
		
		return isFile;
	}
}
