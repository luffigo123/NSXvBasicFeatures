package com.vmware.nsx6x.service.ActivityMonitoring;

import com.vmware.nsx6x.utils.Config;
import com.vmware.nsx6x.utils.DefaultEnvironment;
import com.vmware.nsx6x.utils.HttpReq;
import com.vmware.nsx6x.utils.XmlFileOp;


public class ActivityMonitoringOperation {

	public HttpReq httpReq;
	private String vsmIP;
	
	private String filepath = Config.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	private String xmlPath = filepath + "/RestCallXML/ReportingDataCollection4VC.xml";
	
	public ActivityMonitoringOperation(){
		httpReq = HttpReq.getInstance();
		vsmIP = DefaultEnvironment.VSMIP;
	}
	
	
	/**
	 * 
	 * @param xmlContents
	 * @return
	 */
	public void enableDisableCollectReportingData(String xmlContents) {
		String url = "https://" + vsmIP + "/api/1.0/eventcontrol/eventcontrol-root/request";
		httpReq.postRequest(xmlContents,url);
	}
	
	
	public void enableDisableCollectingReportData(String status)
	{
		String xmlContents = null;
		
		String [] xmlKeys = {"value"};
		String [] xmlValues = {status};
										
		xmlContents = XmlFileOp.getXMLContensWithValues(xmlKeys, xmlValues, xmlPath);
System.out.println(xmlContents);
		
	this.enableDisableCollectReportingData(xmlContents);
	}
	
	
}
