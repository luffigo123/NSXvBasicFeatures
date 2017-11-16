package com.vmware.nsx6x.service.Controller;

import org.apache.log4j.Logger;

import com.vmware.nsx6x.model.Controller.Controller;
import com.vmware.nsx6x.service.GroupingObjects.IPPoolManager;
import com.vmware.nsx6x.utils.DefaultEnvironment;
import com.vmware.nsx6x.utils.HttpReq;
import com.vmware.nsx6x.utils.JAXBUtils;
import com.vmware.nsx6x.utils.JAXBUtils.CollectionWrapper;
import com.vmware.nsx6x.utils.Log4jInstance;
import com.vmware.nsx6x.utils.TestData;
import com.vmware.nsx6x.utils.VC;
import com.vmware.nsx6x.utils.XmlFileOp;

public class ControllerManager {
	private VC vc;
	private HttpReq httpReq;
	private static Logger log;
	private String vsmIP;
	private JAXBUtils jAXBUtils;
	
	public String nativeString = TestData.NativeString;
	
	public String defaultController001_Name;
	public String defaultController002_Name;
	public String defaultController003_Name;
	public String defaultDesc;
	
	private String controller_URL;
	
	public String cluster01_Name;
	public String cluster01_Moid;
	public String dvPortGroupName;
	public String dvPortGroupMoid;
	public String dataStoreName;
	public String dataStoreMoid;
	public String hostIPAddress;
	public String hostMoid;
	
	public IPPoolManager ipPoolMgr;

	public ControllerManager(){
		vc = VC.getInstance();
		httpReq = HttpReq.getInstance();
		log = Log4jInstance.getLoggerInstance();
		vsmIP = DefaultEnvironment.VSMIP;

		controller_URL = "https://" + vsmIP + "/api/2.0/vdn/controller";
	
		log.info("Init all parameters' value!");
		this.initParametersValue();
		
		log.info("Init all resource's Moid!");
		this.initMoid(vc);
		
		ipPoolMgr = new IPPoolManager();
	}
	
	public void initMoid(VC vc){
		cluster01_Moid = vc.getClusterMOID(cluster01_Name);
		dvPortGroupMoid = vc.getNetworkMoIdByName(dvPortGroupName);
		dataStoreMoid = vc.getDatastoreMOID(hostIPAddress, dataStoreName);
		hostMoid = vc.getHostMOID(hostIPAddress);
	}
	
	public void initParametersValue() {
		defaultController001_Name = this.nativeString + this.nativeString.substring(0, 1);
		defaultController002_Name = this.nativeString + this.nativeString.substring(0, 1) + this.nativeString.substring(0, 1);
		defaultController003_Name = this.nativeString + this.nativeString.substring(0, 1) + this.nativeString.substring(0, 1) + this.nativeString.substring(0, 1);
		defaultDesc = this.nativeString;
		cluster01_Name = DefaultEnvironment.Cluster1;
		dvPortGroupName = DefaultEnvironment.dvPortGroupName;
		dataStoreName = DefaultEnvironment.DataStore1;
		hostIPAddress = DefaultEnvironment.Host1InDvSwitch;
	}
	
	private String postController(String xmlContents){
		log.info("The URL is " + this.controller_URL);
		return httpReq.postRequest(xmlContents,controller_URL);
	}
	
	public String queryContollersInfo(){
		log.info("The URL is " + this.controller_URL);
		String result = "";
		try {
			result = httpReq.getRequest(controller_URL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public String getSpecificControllerId(String controllerName){	
		String queryInfo = "";
		try {
			queryInfo =this.queryContollersInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
//		String id = XmlFileOp.findNearestBeforeTagValue3(queryInfo, "id", "name", controllerName);
		String id =  XmlFileOp.findNearestAfterTagValue(queryInfo, "name", controllerName, "id");
		return id;
	}
	
	private void deleteController(String controllerName) {
		String controllerId = this.getSpecificControllerId(controllerName);
		String url = this.controller_URL + "/" + controllerId + "?forceRemoval=true";
		log.info("The URL is " + url);
		httpReq.delRequest(url);
	}	
	
	private String getControllerStatus(String jobId) {
		String url = this.controller_URL + "/progress/" + jobId;
		String queryInfo = this.httpReq.getRequest(url);
		String status = XmlFileOp.getValueBySpecificTag(queryInfo, "status");
		return status;
	}
	
	public boolean isControllerDeploySuccessfully(String jobId) {
		int count = 1;
		boolean result = false;
		String status = this.getControllerStatus(jobId);
		while(!status.equalsIgnoreCase("Success")) {
			try {
				Thread.sleep(60000);
				count ++;
				status = this.getControllerStatus(jobId);
			}catch(Exception e) {
				e.printStackTrace();
			}
			if(count > 30) {
				return false;
			}
		}
		result = true;
		return result;
	}
	
	public boolean isControllerExist(String controllerName){
		boolean flag = false;
		String queryInfo = "";
		try {
			queryInfo = this.queryContollersInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String targetTag = "name";
		flag = XmlFileOp.checkGivenStringEqualTagValue(queryInfo, targetTag, controllerName);
		return flag;
	}
	
	public String addController(Controller controller){
		jAXBUtils = new JAXBUtils(Controller.class, CollectionWrapper.class);
		String xmlContents = this.jAXBUtils.objToXml(controller, "UTF-8");
		return this.postController(xmlContents);
	}
	
	public void deleteSpecificController(String controllerName){
		if(this.isControllerExist(controllerName)){
			this.deleteController(controllerName);
		}	
	}
	
	public String getControllerIPPoolId() {
		this.ipPoolMgr.setupIPPool_ForController();
		String controllerIPPoolName = ipPoolMgr.ipPoolName_ForController;
		return this.ipPoolMgr.getSpecificIPPoolId(controllerIPPoolName);
	}
	
	public Controller getDefaultController001() {
		Controller controller = new Controller(this.defaultController001_Name, this.defaultDesc, this.getControllerIPPoolId(), 
				this.cluster01_Moid, this.hostMoid, this.dataStoreMoid, this.dvPortGroupMoid, "!QAZ2wsx3edc","");
		return controller;
	}
	
	public Controller getDefaultController002() {
		Controller controller = new Controller(this.defaultController002_Name, this.defaultDesc, this.getControllerIPPoolId(), 
				this.cluster01_Moid, this.hostMoid, this.dataStoreMoid, this.dvPortGroupMoid, "!QAZ2wsx3edc","");
		return controller;
	}
	
	public Controller getDefaultController003() {
		Controller controller = new Controller(this.defaultController003_Name, this.defaultDesc, this.getControllerIPPoolId(), 
				this.cluster01_Moid, this.hostMoid, this.dataStoreMoid, this.dvPortGroupMoid, "!QAZ2wsx3edc","");
		return controller;
	}
	
	public void setupDefaultContrller001(){
		if(!this.isControllerExist(this.defaultController001_Name)){
			String jobId = this.addController(getDefaultController001());
			boolean isControllerDeploySuccessfully = this.isControllerDeploySuccessfully(jobId);
			
			if(isControllerDeploySuccessfully) {
				log.info("Add controller successfully!");
			}else {
				log.error("Falied to deploy controller!");
			}
			try {
				Thread.sleep(60000);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setupDefaultContrller002(){
		if(!this.isControllerExist(this.defaultController002_Name)){
			String jobId = this.addController(getDefaultController002());
			boolean isControllerDeploySuccessfully = this.isControllerDeploySuccessfully(jobId);	
			if(isControllerDeploySuccessfully) {
				log.info("Add controller successfully!");
			}else {
				log.error("Falied to deploy controller!");
			}
			try {
				Thread.sleep(60000);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setupDefaultContrller003(){
		if(!this.isControllerExist(this.defaultController003_Name)){
			String jobId = this.addController(getDefaultController003());
			boolean isControllerDeploySuccessfully = this.isControllerDeploySuccessfully(jobId);
			if(isControllerDeploySuccessfully) {
				log.info("Add controller successfully!");
			}else {
				log.error("Falied to deploy controller!");
			}
//			try {
//				Thread.sleep(60000);
//			}catch(Exception e) {
//				e.printStackTrace();
//			}
		}
	}
	
	public void cleanupDefaultController001(){
		if(this.isControllerExist(this.defaultController001_Name)){
			this.deleteSpecificController(defaultController001_Name);
		}
	}
	
	public void cleanupDefaultController002(){
		if(this.isControllerExist(this.defaultController002_Name)){
			this.deleteSpecificController(defaultController002_Name);
		}
	}
	
	public void cleanupDefaultController003(){
		if(this.isControllerExist(this.defaultController003_Name)){
			this.deleteSpecificController(defaultController003_Name);
		}
	}
	
	public void setupThreeControllers() {
		this.setupDefaultContrller001();
		this.setupDefaultContrller002();
		this.setupDefaultContrller003();
	}
	
	public void cleanThreeControllers() {
		this.cleanupDefaultController001();
		this.cleanupDefaultController002();
		this.cleanupDefaultController003();
	}
	
	
}