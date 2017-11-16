package com.vmware.nsx6x.common;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vmware.nsx6x.utils.Config;
import com.vmware.nsx6x.utils.DefaultEnvironment;
import com.vmware.nsx6x.utils.Dom4jXmlUtils;
import com.vmware.nsx6x.utils.HttpReq;
import com.vmware.nsx6x.utils.XmlFileOp;

public class EnvPrep {

	private HashMap<String, String> cfg = Config.getInstance().ConfigMap;
	private HttpReq httpReq = HttpReq.getInstance();
	private String vsmIP = DefaultEnvironment.VSMIP;

	
	
	/**
	 * Keep query the job status in specified interval (seconds) until it complete/success/failed
	 * @param jobId
	 * @param seconds
	 * @return the final status string
	 * @throws Exception By Bo Cao on Apr 15, 2015
	 */
	public String queryJobStatus (String jobId, int seconds){
		String jobStatus = "";
		long timeSec = seconds * 1000;
		String endPoint = "https://" + cfg.get("VSMIPAddress") + "/api/2.0/services/taskservice/job/" + jobId;
		
		String resBody = ""; 
		int count = 0;
		
		do {
			try {
				resBody = httpReq.getRequest((endPoint));
				count ++;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jobStatus = XmlFileOp.getSpecificTagValues("status", resBody).toString();
			System.out.println(jobStatus);
			try {
				Thread.sleep(timeSec);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} while (!jobStatus.contains("COMPLETED")&&!jobStatus.contains("SUCCESS")&&!jobStatus.contains("FAILED")&&!(count > 120));
		
		return jobStatus;
		
	}
	
	
	public String queryJobStatus2(String jobId, int seconds) {
		String jobStatus = "";
		long timeSec = seconds * 1000;
		int count = 0;
		do {
			try {
				jobStatus = this.getJobStatus(jobId);
//				Log.info("job status is" + jobStatus + "************************");
				Thread.sleep(timeSec);
				count++;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} while (!jobStatus.contains("COMPLETED")&&!jobStatus.contains("SUCCESS")&&!jobStatus.contains("FAILED")&&!(count > 120));
		
		return jobStatus;
		
	}
	
	
	
	/**
	 * Used to varify if the string as a valid IPv4 address.
	 * @param ipv4Address
	 * @return true if valid.
	 */
	static Boolean checkIfIPv4Address (String ipv4Address) {
		Boolean isIPv4 = false;
		
		Pattern ippattern = Pattern.compile("((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)");
		if(ipv4Address!="") {
			Matcher m = ippattern.matcher(ipv4Address);
			if(m.find()) {
				isIPv4 = true;
			}
		}
        return isIPv4;
	}
	

	
	/**
	 * query job instance info
	 * @param jobID
	 * @return
	 * @author fxiong
	 */
	public String queryJobInstanceInfo(String jobID){
		//GET https://<nsxmgr-ip>/api/2.0/services/taskservice/job/<jobID>
		String endPoint = "https://" + vsmIP + "/api/2.0/services/taskservice/job/" + jobID;
System.out.println(endPoint);
		String queryInfo = "";
		try {
			queryInfo = httpReq.getRequest(endPoint);
System.out.println(queryInfo);			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return queryInfo;
	}
	
	/**
	 * get job instance status
	 * @param jobID
	 * @return
	 * @throws Exception
	 * @author fxiong
	 */
	public String getJobStatus(String jobID) throws Exception{
		String jobStatus = "";
		String queryInfo  = this.queryJobInstanceInfo(jobID);
		String nodeHierarchic = "//jobInstances/jobInstance/status";
		jobStatus = Dom4jXmlUtils.getSpecificNodeValueByString(queryInfo, nodeHierarchic, 1);
		return jobStatus;
	}
}
