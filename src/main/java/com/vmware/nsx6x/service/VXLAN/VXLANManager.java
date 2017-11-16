package com.vmware.nsx6x.service.VXLAN;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.vmware.nsx6x.common.EnvPrep;
import com.vmware.nsx6x.model.VXLAN.Cluster;
import com.vmware.nsx6x.model.VXLAN.ClusterNest;
import com.vmware.nsx6x.model.VXLAN.Clusters;
import com.vmware.nsx6x.model.VXLAN.MultiCast;
import com.vmware.nsx6x.model.VXLAN.NWFabricFeatureConfig;
import com.vmware.nsx6x.model.VXLAN.ResourceConfig;
import com.vmware.nsx6x.model.VXLAN.SegmentIPPool;
import com.vmware.nsx6x.model.VXLAN.TransportZone;
import com.vmware.nsx6x.service.Installation.UniversalSyncRoleManager;
import com.vmware.nsx6x.utils.Config;
import com.vmware.nsx6x.utils.DefaultEnvironment;
import com.vmware.nsx6x.utils.Dom4jXmlUtils;
import com.vmware.nsx6x.utils.HttpReq;
import com.vmware.nsx6x.utils.JAXBUtils;
import com.vmware.nsx6x.utils.JAXBUtils.CollectionWrapper;
import com.vmware.nsx6x.utils.Log4jInstance;
import com.vmware.nsx6x.utils.TestData;
import com.vmware.nsx6x.utils.VC;
import com.vmware.nsx6x.utils.XmlFileOp;

public class VXLANManager {
//	private Config cfg;
	private VC vc;
	private HttpReq httpReq;
	private static Logger log;
	private String vsmIP;
	private JAXBUtils jAXBUtils;
	
	private String filepath = Config.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	private String configVXLAN_xmlPath = filepath + "/RestCallXML/ConfigVXLAN.xml";
	
	private String vxlan_URL;
	private String segmentIPool_URL;
	private String multicast_URL;
	private String transportZone_URL;

	public String cluster01_Name = DefaultEnvironment.Cluster1;
	public String cluster02_Name = DefaultEnvironment.Cluster2;
	public String cluster01_Moid;
	public String cluster02_Moid;
	public String dvSwitchName = DefaultEnvironment.dvSwitchName;
	public String dvSwitchMoid;

	public String vlanId = "0";
	public String mtu = "1600";
	public String teaming = "FAILOVER_ORDER";
	
	public String segmentIP_Begin = "5000";
	public String segmentIP_End = "65535";
	public String multiCaseBegin= "239.0.0.1";
	public String multiCaseEnd= "239.1.1.1";
	
	public String universal_segmentIP_Begin = "70000";
	public String universal_segmentIP_End = "655350";
	public String universal_multiCaseBegin= "239.7.7.1";
	public String universal_multiCaseEnd= "239.9.9.1";
	
	public String transportZoneName;
	public String universalTransportZoneName;
	
	public String segmentIPPoolName;
	public String universalSegmentIPPoolName;
	
	public String multicastName;
	public String universalMulticastName;
	
	private UniversalSyncRoleManager universalSyncRoleManager;
	
	public VXLANManager(){
		vc = VC.getInstance();
		httpReq = HttpReq.getInstance();
		log = Log4jInstance.getLoggerInstance();
		vsmIP = DefaultEnvironment.VSMIP;
//		cfg = Config.getInstance();
		
		vxlan_URL = "https://" + vsmIP + "/api/2.0/nwfabric/configure";
		segmentIPool_URL = "https://" + vsmIP + "/api/2.0/vdn/config/segments";
		multicast_URL = "https://" + vsmIP + "/api/2.0/vdn/config/multicasts";
		transportZone_URL = "https://" + vsmIP + "/api/2.0/vdn/scopes";
		
		transportZoneName = TestData.NativeString;
		universalTransportZoneName = TestData.NativeString + TestData.NativeString.substring(0,1);
		
		segmentIPPoolName = TestData.NativeString;
		universalSegmentIPPoolName = TestData.NativeString + TestData.NativeString.substring(0,1);
		
		multicastName = TestData.NativeString;
		universalMulticastName = TestData.NativeString.substring(0,1) + TestData.NativeString;
							
		log.info("Init all resource's Moid!");
		this.initMoid(vc);
		
		log.info("Assign manager role!");
		universalSyncRoleManager = new UniversalSyncRoleManager();
		universalSyncRoleManager.assignManagerRole_AsPrimary();
	}
	
	public void initMoid(VC vc){
		cluster01_Moid = vc.getClusterMOID(cluster01_Name);
		cluster02_Moid = vc.getClusterMOID(cluster02_Name);
		dvSwitchMoid = vc.getdvSwitchMoIdByName(dvSwitchName);
	}
	
//*************************************************** VXLAN Begin ******************************************************	
	/**
	 * 
	 * @param xmlContent
	 */
	private void postNetworkVirtualizationComponents(String xmlContent) {
		String url = this.vxlan_URL;
		httpReq.postRequest(xmlContent,url);
	}
	
	public String queryStatusOfSpecificResource(String clusterMoid){
		String endPoint = "https://" + vsmIP + "/api/2.0/nwfabric/status?resource=" + clusterMoid;
		log.info("Get endpoint of resource status : " + endPoint);
		String queryInfo = "";
		try {
			queryInfo = httpReq.getRequest(endPoint);
			log.info("Query informations of resource status successfully! The info : " + queryInfo);
		} catch (Exception e) {
			log.error("Failed to query specifci resource status : " + e.getMessage());
			e.printStackTrace();
		}
		return queryInfo;
	}
	
	/**
	 * 
	 * @param vib
	 */
	public void installNetworkVirtualizationComponents(NWFabricFeatureConfig vib){
		jAXBUtils = new JAXBUtils(NWFabricFeatureConfig.class, CollectionWrapper.class);
		String xmlContent = jAXBUtils.objToXml(vib, "UTF-8");
		this.postNetworkVirtualizationComponents(xmlContent);
	}
	
	/**
	 * install vib package in cluster
	 */
	public void install_VIB_InCluster(String clusterMoid){
		ResourceConfig resourceConfig = new ResourceConfig(clusterMoid);
		NWFabricFeatureConfig nfc = new NWFabricFeatureConfig(resourceConfig);
		this.installNetworkVirtualizationComponents(nfc);
		try {
			Thread.sleep(90000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Delete the previously installed Network Virtualization components (CAN'T WORK for P77 6.1 NSX API Guide)
	 * @return the results
	 * @throws Exception 
	 */
	private void uninstallNetworkVirtualizationComponents(){
		String url = this.vxlan_URL;
		httpReq.delRequest(url);
	}
	
	/**
	 * 
	 * @param ClusterMoid
	 * @return
	 */
	private boolean configVXLAN(String ClusterMoid) {
		Boolean isPassed = false;
		String url = "https://" + vsmIP + "/api/2.0/nwfabric/configure";
		log.info("Generate xml contents");
		String postBody = XmlFileOp.readXMLContens(configVXLAN_xmlPath);
		String nodeHierarchic_mtu = "//nwFabricFeatureConfig/resourceConfig/configSpec/mtu";
		String nodeHierarchic_objectId_1 = "//nwFabricFeatureConfig/resourceConfig/configSpec/switch/objectId";
		String nodeHierarchic_teaming = "//nwFabricFeatureConfig/resourceConfig/configSpec/teaming";
		String nodeHierarchic_resourceId_1 = "//nwFabricFeatureConfig/resourceConfig/resourceId";
		String nodeHierarchic_objectId_2 = "//nwFabricFeatureConfig/resourceConfig/configSpec/switch/objectId";
		String nodeHierarchic_vlanId = "//nwFabricFeatureConfig/resourceConfig/configSpec/vlanId";
		String nodeHierarchic_resourceId_2 = "//nwFabricFeatureConfig/resourceConfig/resourceId";
		postBody = Dom4jXmlUtils.modifySpecificNodeValue(postBody, nodeHierarchic_mtu, 1, mtu);
		postBody = Dom4jXmlUtils.modifySpecificNodeValue(postBody, nodeHierarchic_objectId_1, 1, dvSwitchMoid);
		postBody = Dom4jXmlUtils.modifySpecificNodeValue(postBody, nodeHierarchic_teaming, 1, teaming);
		postBody = Dom4jXmlUtils.modifySpecificNodeValue(postBody, nodeHierarchic_resourceId_1, 1, dvSwitchMoid);
		postBody = Dom4jXmlUtils.modifySpecificNodeValue(postBody, nodeHierarchic_objectId_2, 2, dvSwitchMoid);
		postBody = Dom4jXmlUtils.modifySpecificNodeValue(postBody, nodeHierarchic_vlanId, 1, vlanId);
		postBody = Dom4jXmlUtils.modifySpecificNodeValue(postBody, nodeHierarchic_resourceId_2, 2, ClusterMoid);
		
		String jobId = "";
		try {
			jobId = httpReq.postRequest(postBody,url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String status = "";
		if(jobId != null && jobId != ""){
			status = new EnvPrep().queryJobStatus2(jobId, 5);
		}
		if(status.contains("COMPLETED")||status.contains("SUCCESS")) {
			isPassed = true;
		}
			
		return isPassed;
	}
	
	/**
	 * Configure VXLAN for all Clusters 
	 */
	public void configVXLAN_InCluster(String clusterMoid){
		this.configVXLAN(clusterMoid);
	}
//*************************************************** VXLAN End ******************************************************
	
	
//*************************************************Segment ID Begin ******************************************************
	private String querySegmentIPPools(){
		String url = this.segmentIPool_URL;
		return this.httpReq.getRequest(url);
	}
	
	public String getSpecificSegmentIPPoolId(String name)
	{	
		String segmentIPPoolsInfo =this.querySegmentIPPools();
		String ipSetID = XmlFileOp.findNearestBeforeTagValue3(segmentIPPoolsInfo, "id", "name", name);
		return ipSetID;
	}
	
	/**
	 * 
	 * @param xmlContent
	 * @return
	 */
	private String postSegmentIPPool(String xmlContent){
		String url = segmentIPool_URL;
		return httpReq.postRequest(xmlContent, url);
	}
	
	private String postUniversalSegmentIPPool(String xmlContent) {
		String url = segmentIPool_URL+"?isUniversal=true";
		return httpReq.postRequest(xmlContent, url);
	}
	
	/**
	 * 
	 * @param segmentIPPool
	 * @return
	 */
	public String addSegmentIPPool(SegmentIPPool segmentIPPool){
		jAXBUtils = new JAXBUtils(SegmentIPPool.class, CollectionWrapper.class);
		String xmlContent = jAXBUtils.objToXml(segmentIPPool, "UTF-8");
		return this.postSegmentIPPool(xmlContent);
	}
	
	public String addUniversalSegmentIPPool(SegmentIPPool segmentIPPool){
		jAXBUtils = new JAXBUtils(SegmentIPPool.class, CollectionWrapper.class);
		String xmlContent = jAXBUtils.objToXml(segmentIPPool, "UTF-8");
		return this.postUniversalSegmentIPPool(xmlContent);
	}
	
	/**
	 * 
	 * @param xmlContent
	 */
	private void putSegmentIPPool(String xmlContent, String segmentId){
		String url = this.segmentIPool_URL + "/"+ segmentId;
		httpReq.putRequest(xmlContent, url);
	}
	
	/**
	 * 
	 * @param segmentIPPool
	 */
	public void editSegmentIPPool(SegmentIPPool segmentIPPool, String segmentId){
		jAXBUtils = new JAXBUtils(SegmentIPPool.class, CollectionWrapper.class);
		String xmlContent = jAXBUtils.objToXml(segmentIPPool, "UTF-8");
		this.putSegmentIPPool(xmlContent, segmentId);
	}
	
	/**
	 * Delete the SegmentIPPool which added before
	 */
	public void deleteSegmentIPPool(String segmentName){
		String segmentId = this.getSpecificSegmentIPPoolId(segmentName);
		String url = this.segmentIPool_URL + "/"+ segmentId;
		httpReq.delRequest(url);
	}
	
	/**
	 * Get default SegmentIPPool
	 * @return
	 */
	public SegmentIPPool getDefaultSegmentIPPool(){
		SegmentIPPool segmentIPPool = new SegmentIPPool(this.segmentIPPoolName,TestData.NativeString,
				this.segmentIP_Begin, this.segmentIP_End);
		return segmentIPPool;
	}
	
	public SegmentIPPool getDefault_UniversalSegmentIPPool(){
		SegmentIPPool segmentIPPool = new SegmentIPPool(this.universalSegmentIPPoolName,TestData.NativeString,
				this.universal_segmentIP_Begin, this.universal_segmentIP_End);
		return segmentIPPool;
	}
	
	private String postMultiCast(String contents){
		return this.httpReq.postRequest(contents, this.multicast_URL);
	}
	
	private String postUniversalMultiCast(String contents){
		String url = this.multicast_URL +"?isUniversal=true";
		return this.httpReq.postRequest(contents, url);
	}
	
	private String queryMultiCaseInfo(){
		String url = this.multicast_URL;
		return this.httpReq.getRequest(url);
	}
	
	public String addMultiCast(MultiCast multiCast){
		jAXBUtils = new JAXBUtils(MultiCast.class, CollectionWrapper.class);
		String xmlContent = jAXBUtils.objToXml(multiCast, "UTF-8");
		return this.postMultiCast(xmlContent);
	}
	
	public String addUniversalMultiCast(MultiCast multiCast){
		jAXBUtils = new JAXBUtils(MultiCast.class, CollectionWrapper.class);
		String xmlContent = jAXBUtils.objToXml(multiCast, "UTF-8");
		return this.postUniversalMultiCast(xmlContent);
	}
	
	public void delteMultiCast(String multiCastName){
		String multiCastId = this.getSpecificMultiCastId(multiCastName);
		String url = this.multicast_URL + "/" + multiCastId;
		this.httpReq.delRequest(url);
	}
	
	public String getSpecificMultiCastId(String multiCastName) {
		String multiCastInfo =this.queryMultiCaseInfo();
		return XmlFileOp.findNearestBeforeTagValue3(multiCastInfo, "id", "name", multiCastName);
	}

	/**
	 * Get default MultiCase
	 * @return
	 */
	public MultiCast getDefaultMultiCast(){
		MultiCast multiCast = new MultiCast(this.multicastName, this.multiCaseBegin, this.multiCaseEnd);
		return multiCast;
	}
	
	public MultiCast getDefault_UniversalMultiCast(){
		MultiCast multiCast = new MultiCast(this.universalMulticastName, 
				this.universal_multiCaseBegin, this.universal_multiCaseEnd);
		return multiCast;
	}
	
	/**
	 * Check the target whether exist
	 * @param ipSetName
	 * @return
	 */
	private boolean isExist(String queryInfo, String targetTag, String targetValue)
	{
		boolean flag = false;
		flag = XmlFileOp.checkGivenStringEqualTagValue(queryInfo, targetTag, targetValue);
		return flag;
	}
	
	/**
	 * check the specific Segment IPPool whether exist
	 * @param targetValue
	 * @return
	 */
	public boolean isSegmentIPPoolExist(String targetValue){
		String queryInfo = this.querySegmentIPPools();
		String targetTag = "name";
		return this.isExist(queryInfo, targetTag, targetValue);
	}
	
	/**
	 * check the specific MultiCast whether exist
	 * @param targetValue
	 * @return
	 */
	public boolean isMustCastExist(String targetValue){
		String queryInfo = this.queryMultiCaseInfo();
		String targetTag = "name";
		return this.isExist(queryInfo, targetTag, targetValue);
	}
	
	public void setDefaultSegmentIPPool() {
		if(!this.isSegmentIPPoolExist(this.segmentIPPoolName)) {
			SegmentIPPool segmentIPPool = this.getDefaultSegmentIPPool();
			this.addSegmentIPPool(segmentIPPool);
		}
	}
	
	public void cleanupDefaultSegmnetIPPool() {
		if(this.isSegmentIPPoolExist(this.segmentIPPoolName)) {
			this.deleteSegmentIPPool(this.segmentIPPoolName);
		}
	}
	
	public void setDefault_UniversalSegmentIPPool() {
		if(!this.isSegmentIPPoolExist(this.universalSegmentIPPoolName)) {
			SegmentIPPool segmentIPPool = this.getDefault_UniversalSegmentIPPool();
			this.addUniversalSegmentIPPool(segmentIPPool);
		}
	}
	
	public void cleanupDefault_UniversalSegmnetIPPool() {
		if(this.isSegmentIPPoolExist(this.universalSegmentIPPoolName)) {
			this.deleteSegmentIPPool(this.universalSegmentIPPoolName);
		}
	}
	
	public void setDefaultMulticast() {
		if(!this.isMustCastExist(this.multicastName)) {
			MultiCast multiCast = this.getDefaultMultiCast();
			this.addMultiCast(multiCast);
		}
	}
	
	public void cleanupDefaultMulticast() {
		if(this.isMustCastExist(multicastName)) {
			this.delteMultiCast(multicastName);
		}
	}
	
	public void setDefault_UniversalMulticast() {
		if(!this.isMustCastExist(this.universalMulticastName)) {
			MultiCast multiCast = this.getDefault_UniversalMultiCast();
			this.addUniversalMultiCast(multiCast);
		}
	}
	
	public void cleanupDefault_UniversalMulticast() {
		if(this.isMustCastExist(universalMulticastName)) {
			this.delteMultiCast(universalMulticastName);
		}
	}
	
	
//*************************************************Segment ID End ******************************************************

//*************************************************Transport Zone Begin ******************************************************	
	private String postTransportZone(String xmlContent){
		String url = this.transportZone_URL;
		return httpReq.postRequest(xmlContent,url);
	}
	
	private String postUniversalTransportZone(String xmlContent){
		String url = this.transportZone_URL + "?isUniversal=true";
		return httpReq.postRequest(xmlContent,url);
	}
	
	public String queryTransportZoneInfo(){
		String url = this.transportZone_URL;
		return httpReq.getRequest(url);
	}
	
	private String getSpecificTransportZoneInfo(String name){
		String id = this.getSpecificTransportZoneId(name);
		String url = this.transportZone_URL + "/" + id;
		return this.httpReq.getRequest(url);
	}

	public String getSpecificTransportZoneId(String name) {
		String queryInfo =this.queryTransportZoneInfo();
		return XmlFileOp.findNearestBeforeTagValue3(queryInfo, "objectId", "name", name);
	}
	
	public void deleteSpecificTransportZone(String name){
		String id = this.getSpecificTransportZoneId(name);
		String url = this.transportZone_URL + "/" + id;
		this.httpReq.delRequest(url);
	}
	
	public String addTransportZone(TransportZone transportZone){
		jAXBUtils = new JAXBUtils(TransportZone.class, CollectionWrapper.class);
		String xmlContent = jAXBUtils.objToXml(transportZone, "UTF-8");
		return this.postTransportZone(xmlContent);
	}
	
	public String addUniversalTransportZone(TransportZone transportZone){
		jAXBUtils = new JAXBUtils(TransportZone.class, CollectionWrapper.class);
		String xmlContent = jAXBUtils.objToXml(transportZone, "UTF-8");
		return this.postUniversalTransportZone(xmlContent);
	}
	
	public boolean isTransportZoneExist(String targetValue){
		String queryInfo = this.queryTransportZoneInfo();
		String targetTag = "name";
		return this.isExist(queryInfo, targetTag, targetValue);
	}
	
	public TransportZone getDefualtTransportZone(){
		ClusterNest clusterNest = new ClusterNest(cluster01_Moid);
		Cluster cluster = new Cluster(clusterNest);
		ArrayList<Cluster> clusterList = new ArrayList<Cluster>();
		clusterList.add(cluster);
		Clusters clusters = new Clusters(clusterList);
		
		String controlPlaneMode = "UNICAST_MODE";
		TransportZone transportZone = new TransportZone(this.transportZoneName,transportZoneName,clusters, controlPlaneMode);
		return transportZone;
	}
	
	public TransportZone getDefualtUniversalTransportZone(){
		ClusterNest clusterNest = new ClusterNest(cluster01_Moid);
		Cluster cluster = new Cluster(clusterNest);
		ArrayList<Cluster> clusterList = new ArrayList<Cluster>();
		clusterList.add(cluster);
		Clusters clusters = new Clusters(clusterList);
		
		String controlPlaneMode = "UNICAST_MODE";
		TransportZone transportZone = new TransportZone(this.universalTransportZoneName,universalTransportZoneName,clusters, controlPlaneMode);
		return transportZone;
	}
	
	/**
	 * Update the attributes of a transport zone.
	 *	For example, you can update the name, description, or control plane mode. You must include the cluster object IDs for
	 *	the transport zone in the request body.
	 */
	private void putTransportZoneAttributes(String name, String contents){
		//PUT /2.0/vdn/scopes/{scopeId}/attributes
		String scopeId = this.getSpecificTransportZoneId(name);
		String url = this.transportZone_URL + "/" + scopeId + "/attributes";
		this.httpReq.putRequest(contents, url);
	}
	
	/**
	 * Update the attributes of a transport zone
	 * @param orgTransportZoneName
	 * @param newTransportZone
	 */
	public void editTransportZoneAttributes(String orgTransportZoneName, TransportZone newTransportZone){
		jAXBUtils = new JAXBUtils(TransportZone.class, CollectionWrapper.class);
		String contents = jAXBUtils.objToXml(newTransportZone, "UTF-8");
		this.putTransportZoneAttributes(orgTransportZoneName, contents);	
	}
	
	/**
	 * Update the specified transport zone.
	 *	You can add a cluster to or delete a cluster from a transport zone.
	 *	You can also repair missing portgroups. For every logical switch created, NSX creates a corresponding portgroup in
	 *	vCenter. If the portgroup is lost for any reason, the logical switch will stop functioning. The repair action recreates any
	 *	missing portgroups.
	 *		
	 *	action type	
	 *  • expand - add a cluster to a transport zone.
	 *	• shrink - remove a cluster from a transport zone.
	 *	• repair - recreate missing distributed port groups.
	 * 
	 * @param actionType    expand |  shrink  |   repair
	 */
	private void putTransportZoneCluster(String actionType, String transportZoneName, String contents){
		//POST /2.0/vdn/scopes/{scopeId}/action
		//https://10.192.133.81/api/2.0/vdn/scopes/vdnscope-17?action=expend
		String id = this.getSpecificTransportZoneId(transportZoneName);
		String url = this.transportZone_URL + "/" + id + "?action=" + actionType;
		this.httpReq.postRequest(contents, url);
	}
	
	/**
	 * 
	 * @param orgTransportZoneName
	 * @param newTransportZone
	 * @param action				expand |  shrink  |   repair
	 *  • expand - add a cluster to a transport zone.
	 *	• shrink - remove a cluster from a transport zone.
	 *	• repair - recreate missing distributed port groups.
	 */
	public void editTransportZoneCluster(String orgTransportZoneName, TransportZone newTransportZone, String action){
		jAXBUtils = new JAXBUtils(TransportZone.class, CollectionWrapper.class);
		String contents = jAXBUtils.objToXml(newTransportZone, "UTF-8");
		this.putTransportZoneCluster(action, orgTransportZoneName, contents);
	}
	
	
	public boolean isClusterExist_InTransportZone(String clusterName){
		String queryInfo = this.queryTransportZoneInfo();
		String targetTag = "name";
		return this.isExist(queryInfo, targetTag, clusterName);
	}
	
	public TransportZone getDefaultTRansportZoneWith2Clusters(){
		String transportZoneName = TestData.NativeString;
		ClusterNest clusterNest01 = new ClusterNest(cluster01_Moid);
		Cluster cluster01 = new Cluster(clusterNest01);
		
		ClusterNest clusterNest02 = new ClusterNest(cluster02_Moid);
		Cluster cluster02 = new Cluster(clusterNest02);
		
		ArrayList<Cluster> clusterList = new ArrayList<Cluster>();
		clusterList.add(cluster01);
		clusterList.add(cluster02);
		
		Clusters clusters = new Clusters(clusterList);
		
		String controlPlaneMode = "UNICAST_MODE";
		TransportZone transportZone = new TransportZone(transportZoneName,transportZoneName,clusters, controlPlaneMode);
		return transportZone;
	}
	
	
	/**
	 * Setup the default transportZone
	 */
	public void setDefaultTransportZone(){
		TransportZone transportZone = this.getDefualtTransportZone();
		String name = transportZone.getName();
		if(!this.isTransportZoneExist(name)){
			this.addTransportZone(transportZone);
		}
	}
	
	/**
	 * Clean the default transportZone
	 */
	public void cleanDefaultTransportZone(){
		if(this.isTransportZoneExist(this.transportZoneName)){
			this.deleteSpecificTransportZone(transportZoneName);
		}
	}
	
	public void setDefault_UniversalTransportZone(){
		TransportZone transportZone = this.getDefualtUniversalTransportZone();
		String name = this.universalTransportZoneName;
		if(!this.isTransportZoneExist(name)){
			this.addUniversalTransportZone(transportZone);
		}
	}
	public void cleanDefault_UniversalTransportZone(){
		if(this.isTransportZoneExist(this.universalTransportZoneName)){
			this.deleteSpecificTransportZone(universalTransportZoneName);
		}
	}
	
	/**
	 * Setup the default transportZone
	 */
	public void setDefaultTransportZoneWith2Clusters(){
		TransportZone transportZone = this.getDefaultTRansportZoneWith2Clusters();
		String name = transportZone.getName();
		if(!this.isTransportZoneExist(name)){
			this.addTransportZone(transportZone);
		}
	}
	
	/**
	 * Clean the default transportZone
	 */
	public void cleanDefaultTransportZoneWith2Clusters(){
		TransportZone transportZone = this.getDefaultTRansportZoneWith2Clusters();
		String name = transportZone.getName();
		if(this.isTransportZoneExist(name)){
			this.deleteSpecificTransportZone(name);
		}
	}
	
//*************************************************Transport Zone End ******************************************************
	
	/**
	 * Check the vib whether be installed
	 * @param clusterMoid
	 * @return
	 */
	public boolean isVibInstalled(String clusterMoid) {
		boolean flag = false;
		String queryInfo = this.queryStatusOfSpecificResource(clusterMoid);
		String tag1 = "featureId";
		String tag2 = "installed";
		String status = XmlFileOp.findNearestAfterTagValue(queryInfo, tag1, "com.vmware.vshield.vsm.nwfabric.hostPrep", tag2);
		if(status.equalsIgnoreCase("true")){
			flag = true;
			log.info("The specifc cluster's " + clusterMoid  + " vib install successfully!");
		}else{
			log.equals("The specifc cluster's " + clusterMoid  + " vib install failed!");
		}
		return flag;
	}
	
	/**
	 * check whether VXLAN is Configured
	 * @param clusterMoid
	 * @return
	 * @throws Exception
	 */
	public boolean isVXLANConfigured(String clusterMoid) {
		boolean flag = false;
		String queryInfo = this.queryStatusOfSpecificResource(clusterMoid);
		String tag1 = "featureId";
		String tag2 = "installed";
		String status = XmlFileOp.findNearestAfterTagValue(queryInfo, tag1, "com.vmware.vshield.vsm.vxlan", tag2);
		if(status.equalsIgnoreCase("true")){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * Install VIB in two clusters
	 */
	public void setupVIB_Env(){
		log.info("Check the vib whether be installed, if not install vib in two clusters!");
		
		boolean flagVIB_01 = this.isVibInstalled(this.cluster01_Moid);
		boolean flagVIB_02 = this.isVibInstalled(this.cluster02_Moid);
		
		if(!flagVIB_01){
			this.install_VIB_InCluster(cluster01_Moid);
		}
		if(!flagVIB_02){
			this.install_VIB_InCluster(cluster02_Moid);
		}	
	}
	
	/**
	 * Configure VXLAN in Clusters
	 */
	public void setup_VXLANConfigure(){
		boolean vxlanFlag_01 = this.isVXLANConfigured(cluster01_Moid);
		boolean vxlanFlag_02 = this.isVXLANConfigured(cluster02_Moid);
		
		if(!vxlanFlag_01){
			this.configVXLAN_InCluster(cluster01_Moid);
		}
		if(!vxlanFlag_02){
			this.configVXLAN_InCluster(cluster02_Moid);
		}
	}
	
	
	
}
