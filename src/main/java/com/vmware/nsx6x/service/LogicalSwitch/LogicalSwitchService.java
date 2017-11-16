package com.vmware.nsx6x.service.LogicalSwitch;

import org.apache.log4j.Logger;

import com.vmware.nsx6x.model.LogicalSwitch.LogicalSwitch;
import com.vmware.nsx6x.model.LogicalSwitch.LogicalSwitchForCreate;
import com.vmware.nsx6x.model.LogicalSwitch.LogicalSwitchForEdit;
import com.vmware.nsx6x.service.VXLAN.VXLANManager;
import com.vmware.nsx6x.utils.DefaultEnvironment;
import com.vmware.nsx6x.utils.HttpReq;
import com.vmware.nsx6x.utils.JAXBUtils;
import com.vmware.nsx6x.utils.JAXBUtils.CollectionWrapper;
import com.vmware.nsx6x.utils.Log4jInstance;
import com.vmware.nsx6x.utils.TestData;
import com.vmware.nsx6x.utils.XmlFileOp;

public class LogicalSwitchService {
	private HttpReq httpReq;
	private static Logger log;
	private String vsmIP;
	private JAXBUtils jAXBUtils;

	private String logicalSwitch_URL;
	private String logicaSwitch_UnderSpecificTransportZone_URL;
//	private String logicaSwitch_UnderSpecificUniversalTransportZone_URL;
	
	public VXLANManager vxlanMrg;
	
	public String defaultLogicalSwitchName001;
	public String defaultLogicalSwitchName002;
	
	public String universalLogicalSwitchName;
	public String universalLogicalSwitchName002;
	
	public LogicalSwitchService(){

		httpReq = HttpReq.getInstance();
		log = Log4jInstance.getLoggerInstance();
		vsmIP = DefaultEnvironment.VSMIP;
		vxlanMrg = new VXLANManager();
		logicalSwitch_URL = "https://" + vsmIP + "/api/2.0/vdn/virtualwires";
		
		logicaSwitch_UnderSpecificTransportZone_URL = "https://" + vsmIP + "/api/2.0/vdn/scopes/";
		
//		defaultLogicalSwitchName001 = "logicalSwitch001" + TestData.NativeString;
//		defaultLogicalSwitchName002 = "logicalSwitch002" + TestData.NativeString;
//		
//		universalLogicalSwitchName = "Universal_LS" + TestData.NativeString;
//		universalLogicalSwitchName002 = "Universal_LS_002" + TestData.NativeString;
		
		defaultLogicalSwitchName001 = TestData.NativeString + TestData.NativeString.substring(0,1);
		defaultLogicalSwitchName002 = TestData.NativeString + TestData.NativeString.substring(0,1)+ TestData.NativeString.substring(0,1);
		
		universalLogicalSwitchName = TestData.NativeString.substring(0,1) + TestData.NativeString;
		universalLogicalSwitchName002 = TestData.NativeString.substring(0,1) + TestData.NativeString.substring(0,1) + TestData.NativeString;
		
//		this.initLogicalSwitch_UnderSpecificTransportZone_URL();
//		this.initUniversalLogicalSwitch_UnderSpecificUniversalTransportZone_Env();
	}
	
//	public void initLogicalSwitch_UnderSpecificTransportZone_URL() {
//		vxlanMrg.setDefaultSegmentIPPool();
//		vxlanMrg.setDefaultMulticast();
//		vxlanMrg.setDefaultTransportZone();
//		String transportZoneName = vxlanMrg.transportZoneName;
//		String transportZoneId = vxlanMrg.getSpecificTransportZoneId(transportZoneName);
//		///2.0/vdn/scopes/{scopeId}/virtualwires
//		logicaSwitch_UnderSpecificTransportZone_URL = "https://" + vsmIP + "/api/2.0/vdn/scopes/" + transportZoneId + "/virtualwires";
//	}
//	
//	public void initUniversalLogicalSwitch_UnderSpecificUniversalTransportZone_Env() {
//		vxlanMrg.setDefault_UniversalSegmentIPPool();
//		vxlanMrg.setDefault_UniversalMulticast();
//		vxlanMrg.setDefault_UniversalTransportZone();
//		String universalTransportZoneName = vxlanMrg.universalTransportZoneName;
//		String universalTransportZoneId = vxlanMrg.getSpecificTransportZoneId(universalTransportZoneName);
//		///2.0/vdn/scopes/{scopeId}/virtualwires
//		logicaSwitch_UnderSpecificUniversalTransportZone_URL = "https://" + vsmIP + "/api/2.0/vdn/scopes/" + universalTransportZoneId + "/virtualwires";
//
//	}
//	
	public String getSpecificTransportZoneId(){
		String transportZoneId = "";
		vxlanMrg.setDefaultTransportZone();
		String transportZoneName = vxlanMrg.transportZoneName;
		transportZoneId = vxlanMrg.getSpecificTransportZoneId(transportZoneName);
		return transportZoneId;
	}
	
	public String getSpecificUniversalTransportZoneId(){
		vxlanMrg.setDefault_UniversalTransportZone();
		String universalTransportZoneName = vxlanMrg.universalTransportZoneName;
		String universalTransportZoneId = vxlanMrg.getSpecificTransportZoneId(universalTransportZoneName);
		return universalTransportZoneId;
	}
	
	/**
	 * 
	 * @param contents
	 * @return
	 */
	private String postLogicalSwitch(String contents){
		String url = this.logicaSwitch_UnderSpecificTransportZone_URL + this.getSpecificTransportZoneId() + "/virtualwires";
		return this.httpReq.postRequest(contents, url);
	}
	
	private String postUniversalLogicalSwitch(String contents){
		
//		String url = this.logicaSwitch_UnderSpecificUniversalTransportZone_URL + "?isUniversal=true";
		String url = this.logicaSwitch_UnderSpecificTransportZone_URL + this.getSpecificUniversalTransportZoneId() + "/virtualwires?isUniversal=true";
		return this.httpReq.postRequest(contents, url);
	}
	
	/**
	 * Add a logical Switch under the specific Transport Zone
	 * @param logicalSwitch
	 * @return
	 */
	public String addLogicalSwitch(LogicalSwitch logicalSwitch){
		jAXBUtils = new JAXBUtils(LogicalSwitchForCreate.class, CollectionWrapper.class);
		String contents = jAXBUtils.objToXml(logicalSwitch, "UTF-8");
		return this.postLogicalSwitch(contents);
	}
	
	public String addUniversalLogicalSwitch(LogicalSwitch logicalSwitch){
		jAXBUtils = new JAXBUtils(LogicalSwitchForCreate.class, CollectionWrapper.class);
		String contents = jAXBUtils.objToXml(logicalSwitch, "UTF-8");
		return this.postUniversalLogicalSwitch(contents);
	}
	
	
	public String getLogicalSwitchesInfo_UnderSpecificTransportZone() {
		return this.httpReq.getRequest(this.logicalSwitch_URL);
	}
	
	
	/**
	 * 
	 * @return
	 */
	public String getAllLogicalSwitchedInfo(){
		String url = this.logicalSwitch_URL;
		return this.httpReq.getRequest(url);
	}
	
	
	/**
	 * 
	 * @param logicalSwitchName
	 * @return
	 */
	public String getSpecificLogicalSwitchInfo(String logicalSwitchName){
		String id = this.getSpecificLogicalSwitchId(logicalSwitchName);
		String url = this.logicalSwitch_URL + "/" + id;
		return this.httpReq.getRequest(url);
	}

	/**
	 * 
	 * @param logicalSwitchName
	 * @return
	 */
	public String getSpecificLogicalSwitchId(String logicalSwitchName) {
		String queryInfo =this.getLogicalSwitchesInfo_UnderSpecificTransportZone();
		String id = XmlFileOp.findNearestBeforeTagValue3(queryInfo, "objectId", "name", logicalSwitchName);
		return id;
	}
	
	private void putLogicalSwitch(String contents, String orgLogicalSwitchName){
		String id = this.getSpecificLogicalSwitchId(orgLogicalSwitchName);
		String url = this.logicalSwitch_URL + "/" + id; 
		this.httpReq.putRequest(contents, url);
	}
	
	/**
	 * Edit the specific logical Switch
	 * @param logicalSwitchForCreate
	 * @param orgLogicalSwitchName
	 */
	public void editLogicalSwitch(LogicalSwitch logicalSwitch, String orgLogicalSwitchName){
		jAXBUtils = new JAXBUtils(LogicalSwitchForEdit.class, CollectionWrapper.class);
		String contents = jAXBUtils.objToXml(logicalSwitch, "UTF-8");
		this.putLogicalSwitch(contents, orgLogicalSwitchName);
	}
	
	public void deleteSpecificLogicalSwitch(String logicalSwitchName) {
		String id = this.getSpecificLogicalSwitchId(logicalSwitchName);
		String url = this.logicalSwitch_URL + "/" + id;
		if(this.isExist(logicalSwitchName)) {
			this.httpReq.delRequest(url);
		}	
	}
	
	/**
	 * Check the target IPSet whether exist
	 * @param ipSetName
	 * @return
	 */
	public boolean isExist(String logicalSwitchName)
	{
		boolean flag = false;
		
		String queryIPsetInfo = "";
		try {
			queryIPsetInfo = this.getAllLogicalSwitchedInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String targetTag = "name";
		flag = XmlFileOp.checkGivenStringEqualTagValue(queryIPsetInfo, targetTag, logicalSwitchName);
		return flag;
	}
	
	public LogicalSwitch getdefaultLogicalSwitch() {
		LogicalSwitch logicalSwitchForCreate = new LogicalSwitchForCreate(defaultLogicalSwitchName001, defaultLogicalSwitchName001,"UNICAST_MODE", "false", "virtual wire tenant");
		return logicalSwitchForCreate;
	}
	
	public LogicalSwitch getdefaultLogicalSwitch002() {
		LogicalSwitch logicalSwitchForCreate = new LogicalSwitchForCreate(defaultLogicalSwitchName002, defaultLogicalSwitchName002,"UNICAST_MODE", "false", "virtual wire tenant");
		return logicalSwitchForCreate;
	}
	
	public LogicalSwitch getdefault_UniversalLogicalSwitch() {
		LogicalSwitch logicalSwitchForCreate = new LogicalSwitchForCreate(this.universalLogicalSwitchName, universalLogicalSwitchName,"UNICAST_MODE", "false", "virtual wire tenant");
		return logicalSwitchForCreate;
	}
	
	public LogicalSwitch getdefault_UniversalLogicalSwitch002() {
		LogicalSwitch logicalSwitchForCreate = new LogicalSwitchForCreate(this.universalLogicalSwitchName002, universalLogicalSwitchName002,"UNICAST_MODE", "false", "virtual wire tenant");
		return logicalSwitchForCreate;
	}
	
	public void setupDefaultLogicalSwitch() {
		this.vxlanMrg.setDefaultSegmentIPPool();
		this.vxlanMrg.setDefaultMulticast();
		this.vxlanMrg.setDefaultTransportZone();
		if(!this.isExist(defaultLogicalSwitchName001)) {
			this.addLogicalSwitch(getdefaultLogicalSwitch());
		}
	}
	
	public void cleanupDefaultLogicalSwitch() {
		if(this.isExist(defaultLogicalSwitchName001)) {
			this.deleteSpecificLogicalSwitch(defaultLogicalSwitchName001);
		}
		this.vxlanMrg.cleanDefaultTransportZone();
		this.vxlanMrg.cleanupDefaultMulticast();
		this.vxlanMrg.cleanupDefaultSegmnetIPPool();
	}
	
	public void setupDefault_UniversalLogicalSwitch() {
		vxlanMrg.setDefault_UniversalSegmentIPPool();
		vxlanMrg.setDefault_UniversalMulticast();
		vxlanMrg.setDefault_UniversalTransportZone();
		if(!this.isExist(universalLogicalSwitchName)) {
			this.addUniversalLogicalSwitch(getdefault_UniversalLogicalSwitch());
		}
	}
	
	public void cleanupDefault_UniversalLogicalSwitch() {
		if(this.isExist(universalLogicalSwitchName)) {
			this.deleteSpecificLogicalSwitch(universalLogicalSwitchName);
		}
		this.vxlanMrg.cleanDefault_UniversalTransportZone();
		this.vxlanMrg.cleanupDefault_UniversalMulticast();
		this.vxlanMrg.cleanupDefault_UniversalSegmnetIPPool();
	}
	
	public void setup2DefaultLogicalSwitch() {
		this.setupDefaultLogicalSwitch();
		if(!this.isExist(defaultLogicalSwitchName002)) {
			this.addLogicalSwitch(getdefaultLogicalSwitch002());
		}
	}
	
	public void cleanup2DefaultLogicalSwitch() {
		this.cleanupDefaultLogicalSwitch();
		if(this.isExist(defaultLogicalSwitchName002)) {
			this.deleteSpecificLogicalSwitch(defaultLogicalSwitchName002);
		}
	}
	
	public void setup2Default_UniversalLogicalSwitch() {
		this.setupDefault_UniversalLogicalSwitch();
		if(!this.isExist(this.universalLogicalSwitchName002)) {
			this.addUniversalLogicalSwitch(this.getdefault_UniversalLogicalSwitch002());
		}
	}
	
	public void cleanup2Default_UniversalLogicalSwitch() {
		this.cleanupDefault_UniversalLogicalSwitch();
		if(this.isExist(universalLogicalSwitchName002)) {
			this.deleteSpecificLogicalSwitch(universalLogicalSwitchName002);
		}
	}
	
	

	public void cleanPrecondition() {
		this.vxlanMrg.cleanDefaultTransportZone();
		this.vxlanMrg.cleanupDefaultMulticast();
		this.vxlanMrg.cleanupDefaultSegmnetIPPool();
		this.vxlanMrg.cleanDefault_UniversalTransportZone();
		this.vxlanMrg.cleanupDefault_UniversalMulticast();
		this.vxlanMrg.cleanupDefault_UniversalSegmnetIPPool();
	}
}
