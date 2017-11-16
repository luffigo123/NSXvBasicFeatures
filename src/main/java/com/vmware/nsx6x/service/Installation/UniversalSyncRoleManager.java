package com.vmware.nsx6x.service.Installation;

import org.apache.log4j.Logger;

import com.vmware.nsx6x.utils.DefaultEnvironment;
import com.vmware.nsx6x.utils.HttpReq;
import com.vmware.nsx6x.utils.Log4jInstance;

public class UniversalSyncRoleManager {
	private HttpReq httpReq;
	private static Logger log;
	private String vsmIP;
	

	private String url;
	public UniversalSyncRoleManager(){

		httpReq = HttpReq.getInstance();
		log = Log4jInstance.getLoggerInstance();
		vsmIP = DefaultEnvironment.VSMIP;

		url = "https://" + vsmIP + "/api/2.0/universalsync/configuration/role";

	}
	
	/**
	 * 
	 * @param role  set-as-primary  | set-as-standalone
	 * @return
	 */
	public String assignRoleToManager(String role){
		url = url + "?action=" + role;
		return this.httpReq.postRequest("", url);
	}
	
	public String queryUniveralSyncRole() {
		return this.httpReq.getRequest(url);
	}
	
	public void assignManagerRole_AsPrimary() {
		String queryInfo = this.queryUniveralSyncRole();
		if(!queryInfo.contains("PRIMARY")) {
			this.assignRoleToManager("set-as-primary");
		}	
	}
	
}
