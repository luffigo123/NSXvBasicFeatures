package com.vmware.nsx6x.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class LocalLog {
	private static LocalLog localLog = null;
	private String sFileName = null;
	private OutputStreamWriter  fileWriter = null;
	private LocalLog(String FileName)
	{
		this.sFileName = FileName;
		try {
			this.fileWriter = new OutputStreamWriter(new FileOutputStream(FileName, true),"UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static synchronized LocalLog getInstance(String FileName)
	{
		if (FileName == null)
		{
			return localLog;
		}
		localLog = new LocalLog(FileName);
		return localLog;
	}
	public void log(String sLog)
	{
		try {
			this.fileWriter.write(sLog + System.getProperty("line.separator"));
			this.fileWriter.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void EndLog()
	{
		try {
			this.fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
