package com.vmware.nsx6x.service.Edge;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;

import com.vmware.nsx6x.model.Edge.AddressGroup;
import com.vmware.nsx6x.model.Edge.Appliance;
import com.vmware.nsx6x.model.Edge.Appliances;
import com.vmware.nsx6x.model.Edge.AutoConfiguration;
import com.vmware.nsx6x.model.Edge.CliSetting;
import com.vmware.nsx6x.model.Edge.Edge;
import com.vmware.nsx6x.model.Edge.InterfaceType;
import com.vmware.nsx6x.model.Edge.LDRInterface;
import com.vmware.nsx6x.model.Edge.ManegementInterface;
import com.vmware.nsx6x.model.Edge.QueryDaemon;
import com.vmware.nsx6x.model.Edge.Vnic;
import com.vmware.nsx6x.model.Edge.VnicBean;
import com.vmware.nsx6x.service.LogicalSwitch.LogicalSwitchService;
import com.vmware.nsx6x.service.VXLAN.VXLANManager;
import com.vmware.nsx6x.utils.Config;
import com.vmware.nsx6x.utils.DefaultEnvironment;
import com.vmware.nsx6x.utils.Dom4jXmlUtils;
import com.vmware.nsx6x.utils.HttpReq;
import com.vmware.nsx6x.utils.JAXBUtils;
import com.vmware.nsx6x.utils.JAXBUtils.CollectionWrapper;
import com.vmware.nsx6x.utils.Log4jInstance;
import com.vmware.nsx6x.utils.TestData;
import com.vmware.nsx6x.utils.VC;
import com.vmware.nsx6x.utils.VCUtils;
import com.vmware.nsx6x.utils.XmlFileOp;


public class EdgeManger {
	String vsmIP;
	public HttpReq httpReq;
	public JAXBUtils jAXBUtils;
	
	public VCUtils vcUtils;
	public VC vc;
	
	public String edgeName = TestData.NativeString;
	public String logicalRouterName = TestData.NativeString + TestData.NativeString.substring(0, 1);
	public String universalLogicalRouterName = TestData.NativeString.substring(0, 1) + TestData.NativeString + TestData.NativeString.substring(0, 1);
	
	public String internalName = TestData.NativeString;
	public String uplinkName = TestData.NativeString + TestData.NativeString.substring(0, 1);	
	
	public String CSRName = TestData.NativeString + TestData.NativeString.substring(0, 1) + TestData.NativeString.substring(0, 1);
	
	public String trunkName = "trunk";
	public String dvPortGroupName = DefaultEnvironment.dvPortGroupName;
	public String trunkVnicIndex = "8";
	
	private String filepath = Config.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	private String xmlPath_HA = filepath + "/RestCallXML/HighAvailability.xml";
	
	private String xmlFilePathUP = filepath + "/RestCallXML/vnicUp.xml";
	private String xmlFilePathIn = filepath + "/RestCallXML/vnicIn.xml";
	
	private String xmlFilePath_CSR = filepath + "/RestCallXML/CSR.xml";
	private String xmlPath_Trunk = filepath + "/RestCallXML/Trunk.xml";
	
	private String xmlPath_Edge = filepath + "/RestCallXML/Edge.xml";
	
	
	public VXLANManager vxlanManager;
	public LogicalSwitchService lgMgr;
	
	private static Logger log = Log4jInstance.getLoggerInstance();
	
	public EdgeManger(){
		httpReq = HttpReq.getInstance();
		vsmIP = DefaultEnvironment.VSMIP;

		vxlanManager = new VXLANManager();
		lgMgr = new LogicalSwitchService();
	}
	
	public String queryEdgesInfo()
	{
		//GET https://<nsxmgr-ip>/api/4.0/edges/
		String url = "https://" + vsmIP + "/api/4.0/edges/";
		System.out.println(url);
		return httpReq.getRequest(url);
	}
	
	/**
	 * Get this edge's info
	 * @throws Exception 
	 */
	public String retrieveEdgeDetails(String edgeID) throws Exception
	{
		//GET https://<nsxmgr-ip>/api/4.0/edges/<edgeId>
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeID;
		System.out.println(url);
		return httpReq.getRequest(url);
	}
	
	/**
	 * Get specific edgeID by name
	 * @param edgeName
	 * @return
	 * @throws Exception 
	 */
	public String getEdgeIDbyName(String edgeName)
	{
		String edgesInfo = "";
		try {
			edgesInfo = this.queryEdgesInfo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String tag1 = "objectId";
		String tag2 = "name";
		//String edgeId = XmlFileOp.findNearestBeforeTagValue2(edgesInfo, tag1, tag2, edgeName, 256);
		String edgeId = XmlFileOp.findNearestBeforeTagValue3(edgesInfo, tag1, tag2, edgeName);
		return edgeId;
	}
	
	/**
	 * append edge
	 * @param requestBodyOrFileName
	 * @return
	 * @throws Exception
	 */
	private String appendEdge(String requestBodyOrFileName)
	{
		//POST https://<nsxmgr-ip>/api/4.0/edges/
		String url = "https://" + vsmIP + "/api/4.0/edges/";
		System.out.println(url);
		String result = "";
		try {
			result = httpReq.postRequest(requestBodyOrFileName,url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Add an Edge
	 * @param edge
	 */
	public void addEdge(Edge edge){
		jAXBUtils = new JAXBUtils(Edge.class, CollectionWrapper.class);
		String xmlContent = jAXBUtils.objToXml(edge, "UTF-8");
		this.appendEdge(xmlContent);
	}
	
	private String appendUniversalEdge(String requestBodyOrFileName){
		//POST https://<nsxmgr-ip>/api/4.0/edges?isUniversal=true"
		String url = "https://" + vsmIP + "/api/4.0/edges" + "?isUniversal=true";
		System.out.println(url);
		String result = "";
		try {
			result = httpReq.postRequest(requestBodyOrFileName,url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Add an Edge
	 * @param edge
	 */
	public void addUniversalEdge(Edge edge){
		jAXBUtils = new JAXBUtils(Edge.class, CollectionWrapper.class);
		String xmlContent = jAXBUtils.objToXml(edge, "UTF-8");
		this.appendUniversalEdge(xmlContent);
	}


	/**
	 * Check the target Edge whether exist
	 * @param ipSetName
	 * @return
	 * @throws Exception 
	 */
	public boolean isEdgeExist(String edgeName)
	{
		boolean flag = false;
		String queryInfo = this.queryEdgesInfo();
		String targetTag = "name";
		if(XmlFileOp.checkTagExist(queryInfo, targetTag) & XmlFileOp.checkGivenStringEqualTagValue(queryInfo, targetTag, edgeName)){
			flag =true;
		}
		return flag;
	}

	/**
	 * delete specific edge
	 * @param edgeName
	 * @throws Exception
	 */
	public void deleteEdge(String edgeName){
		String edgeId = this.getEdgeIDbyName(edgeName);
		//DELETE https://<nsxmgr-ip>/api/4.0/edges/{edgeId}
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId;
System.out.println(url);
		httpReq.delRequest(url);
	}
	
	/**
	 * Force sync the edge instance
	 * @param edgeID
	 * @throws Exception 
	 */
	public void forceSyncEdge(String edgeName){
		String edgeID = this.getEdgeIDbyName(edgeName);
		//POST https://<nsxmgr-ip>/api/4.0/edges/{edgeId}?action=forcesync
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeID + "?action=forcesync";
		this.httpReq.postRequest("", url);
	}
	
	/**
	 * get edge status.<!-- {GREY,RED,YELLOW,GREEN}. GREY => unknown status. RED => None of appliance in serving state. YELLOW =>
							Intermittent health check failures.If health check fails for 5 consecutive times for all appliance 
							(2 for HA else 1) then status will turn to RED. GREEN => Good -->
	 * @param edgeName
	 * @return
	 * @throws Exception
	 */
	public String getEdgeStatus(String edgeName){
		String edgeStatus = "";
		String edgeID = this.getEdgeIDbyName(edgeName);
		//GET https://<nsxmgr-ip>/api/4.0/edges/{edgeId}/status
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeID + "/status";
System.out.println(url);
		String info = httpReq.getRequest(url);
		String nodeHierarchic = "//edgeStatus/edgeStatus";
		edgeStatus = Dom4jXmlUtils.getSpecificNodeValueByString(info, nodeHierarchic, 1);
		return edgeStatus;
	}
	
	/**
	 * check edge's status whether is Good
	 * @param edgeName
	 * @return
	 * @throws InterruptedException 
	 * @throws Exception
	 */
	public boolean isEdgeStatusGood(String edgeName) throws InterruptedException{
		boolean flag = false;
 		Thread.sleep(20000);
		String edgeStatus = this.getEdgeStatus(edgeName);
		if("green".equalsIgnoreCase(edgeStatus)){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * Redeploy Edge
	 * @param edgeID
	 * @throws Exception 
	 */
	public void redeployEdge(String edgeName) throws Exception
	{
		String edgeID = this.getEdgeIDbyName(edgeName);
		//POST https://<nsxmgr-ip>/api/4.0/edges/{edgeId}?action=redeploy
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeID + "?action=redeploy";
//System.out.println(url);
//		httpReq.postRequest2(url, new StringReader(""));
		this.httpReq.postRequest("", url);
	}
	
	/**
	 * change edge size
	 * @param edgeName
	 * @param edgeSize
	 * @throws Exception
	 */
	public void changeEdgeSize(String edgeName, String edgeSize) throws Exception{
		String edgeID = this.getEdgeIDbyName(edgeName);
		//POST https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/appliances/?size=compact|large|xlarge
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeID + "/appliances/?size=" + edgeSize;
//System.out.println(url);
//		httpReq.postRequest2(url, new StringReader(""));
		this.httpReq.postRequest("", url);
	}
	
	/**
	 * configure HA
	 * @param edgeName
	 * @param xmlContents
	 * @throws Exception
	 */
	public void configureHA(String edgeName, String xmlContents) throws Exception{
		String edgeID = this.getEdgeIDbyName(edgeName);
		//PUT https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/highavailability/config
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeID + "/highavailability/config";
//System.out.println(url);
//		httpReq.putRequest(url, xmlContents);
		this.httpReq.putRequest(xmlContents, url);
		
	}
	
	/**
	 * enable or disable HA  ---- true is for enable and false for disable
	 * @param edgeName
	 * @param enableOrDisable
	 * @throws Exception
	 */
	public void enableDisableHA(String edgeName, String enableOrDisable) throws Exception{
		//array of xml keys
		String [] xmlKeys = {"enabled"};						
		//array of xml values
		String [] xmlValues = {enableOrDisable};						
		//xmlPath
//String xmlFilePath = "RestCallXML\\HighAvailability.xml";
			
		String xmlContents = XmlFileOp.generateXMLWithFile(xmlKeys, xmlValues, xmlPath_HA);
		this.configureHA(edgeName, xmlContents);
	}
	
	/**
	 * create CSR
	 * @throws Exception
	 */
	public void createCSR(String edgeName, String xmlContents) throws Exception
	{
		String edgeID = this.getEdgeIDbyName(edgeName);
		//POST https://<nsxmgr-ip>/api/2.0/services/truststore/csr/<scopeId>
		String url = "https://" + vsmIP + "/api/2.0/services/truststore/csr/" + edgeID;
System.out.println(url);
		httpReq.postRequest(xmlContents,url);
	}
	
	/**
	 * 
	 * @param name		CSR name
	 * @param edgeName  edge name
	 * @throws Exception
	 */
	public void generateCSR(String csrName, String edgeName) throws Exception{					
		//xmlPath
//String xmlFilePath = "RestCallXML\\CSR.xml";
		String nodeHierarchic = "//csr/subject/attribute/value";
		String xmlContents = Dom4jXmlUtils.modifySpecificNodeValueByFile(xmlFilePath_CSR, nodeHierarchic, 1, csrName);
		this.createCSR(edgeName ,xmlContents);
	}
	
	
	/**
	 * get specific CSR's objectID
	 * @param CSRName
	 * @param edgeName
	 * @return
	 * @throws Exception
	 */
	public String getSpecificCSRObjectID(String CSRName,String edgeName) throws Exception{
		String objectID = "";
		String queryInfo = this.queryCSRsInfo(edgeName);
		//objectID = XmlFileOp.findNearestBeforeTagValue2(queryInfo, "objectId", "name", CSRName, 256);
		objectID = XmlFileOp.findNearestBeforeTagValue3(queryInfo, "objectId", "name", CSRName);
		return objectID;	
	}
	
	/**
	 * delete specific CSR
	 * @param CSRName
	 * @param edgeName
	 * @throws Exception
	 */
	public void deleteSpecificCSR(String CSRName, String edgeName) throws Exception{
		String csrId = this.getSpecificCSRObjectID(CSRName, edgeName);
		//Delete https://<nsxmgr-ip>/api/2.0/services/truststore/csr/<csrId>
		String url = "https://" + vsmIP + "/api/2.0/services/truststore/csr/" + csrId;
System.out.println(url);
		httpReq.delRequest(url);
	}
	
	/**
	 * check whether CSR esixt
	 * @param CSRName
	 * @param edgeName
	 * @return
	 * @throws Exception
	 */
	public boolean isCSRExist(String CSRName, String edgeName) throws Exception{
		boolean flag = false;
		String queryInfo = this.queryCSRsInfo(edgeName);
		String targetTag = "name";
		flag = XmlFileOp.checkGivenStringEqualTagValue(queryInfo, targetTag, CSRName);
		return flag;
	}
	
	/**
	 * query the specific edge's certificates
	 * @param edgeName
	 * @return
	 * @throws Exception
	 */
	public String queryCSRsInfo(String edgeName) throws Exception{
		String edgeID = this.getEdgeIDbyName(edgeName);
		//GET https://<nsxmgr-ip>/api/2.0/services/truststore/csr/scope/<scopeId>
		String url = "https://" + vsmIP + "/api/2.0/services/truststore/csr/scope/" + edgeID;
System.out.println(url);
		return httpReq.getRequest(url);
	}
	
	/**
	 * create Self Signed Certificate
	 * @param edgeName
	 * @param csrName
	 * @param noOfDays
	 * @throws Exception
	 */
	public void createSelfSignedCertificate(String edgeName, String csrName, String noOfDays) throws Exception{
		String csrId = this.getSpecificCSRObjectID(csrName, edgeName);
		//PUT https://<nsxmgr-ip>/api/2.0/services/truststore/csr/<csrId>?noOfDays=<value>
		String url = "https://" + vsmIP + "/api/2.0/services/truststore/csr/" + csrId + "?noOfDays=" + noOfDays ;
//System.out.println(url);
//		httpReq.putRequest2(url, new StringReader(""));
		this.httpReq.putRequest("", url);
	}
	
	public void appendInterface(String edgeName,String xmlContents) throws Exception{
		String edgeId = this.getEdgeIDbyName(edgeName);
		//POST https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/vnics/?action=patch
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/vnics/?action=patch";
System.out.println(url);
		httpReq.postRequest(xmlContents,url);
	}
	
	
	/**
	 * 
	 * @param edgeName
	 * @param vnicBean
	 * @throws Exception
	 */
	public void addInterface(String edgeName, VnicBean vnicBean) throws Exception{
		//array of xml keys
		String [] xmlKeysD = {"index","name","type","portgroupId","primaryAddress","subnetMask","subnetPrefixLength"};
		String [] xmlValuesD = {vnicBean.getIndex(),vnicBean.getName(),vnicBean.getType(),vnicBean.getPortgroupId(),
				vnicBean.getPrimaryAddress(),vnicBean.getSubnetMask(),vnicBean.getSubnetPrefixLength()};
		//xmlPath
//String xmlFilePathUP = "RestCallXML\\vnicUp.xml";
//String xmlFilePathIn = "RestCallXML\\vnicIn.xml";
		String vnicContents = "";
					
		//array of xml values		
		if("internal".equalsIgnoreCase(vnicBean.getType())){
			vnicContents = XmlFileOp.generateXMLWithFile(xmlKeysD, xmlValuesD, xmlFilePathIn);
		}
		else if("uplink".equalsIgnoreCase(vnicBean.getType())){
			vnicContents = XmlFileOp.generateXMLWithFile(xmlKeysD, xmlValuesD, xmlFilePathUP);
		}
		String tempString = "<vnics></vnics>";
		String xmlContents = XmlFileOp.appendToEnd(vnicContents, tempString, "vnics");
		this.appendInterface(edgeName, xmlContents);
	}
	
	/**
	 * add trunk
	 * @param edgeName
	 * @param subInterfaceName
	 * @param portgroupId
	 * @throws DocumentException
	 */
	public void addTrunk(String edgeName, String subInterfaceName, String portgroupId, String vnicIndex) throws DocumentException{
//String xmlFilePath =  "RestCallXML\\Trunk.xml";
		String xmlContents = XmlFileOp.readXMLContens(xmlPath_Trunk);
		
		String nodeHierarchic_subinterfaceName ="//vnics/vnic/subInterfaces/subInterface/name";
		String nodeHierarchic_portgroupId ="//vnics/vnic/portgroupId";
		String nodeHierarchic_vnicIndex ="//vnics/vnic/index";
		xmlContents = Dom4jXmlUtils.modifySpecificNodeValue(xmlContents, nodeHierarchic_subinterfaceName, 1, subInterfaceName);
		xmlContents = Dom4jXmlUtils.modifySpecificNodeValue(xmlContents, nodeHierarchic_portgroupId, 1, portgroupId);
		xmlContents = Dom4jXmlUtils.modifySpecificNodeValue(xmlContents, nodeHierarchic_vnicIndex, 1, vnicIndex);
		
		try {
			this.appendInterface(edgeName, xmlContents);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * get the first trunk subinterface's index
	 * @param edgeName
	 * @return
	 * @throws Exception
	 */
	public String getTrunkSubinterfaceIndex(String edgeName) throws Exception{
		String queryInfo = this.getAllInterfacesInfo(edgeName);
		String nodeHierarchic = "//vnics/vnic/subInterfaces/subInterface/index";
		String index = Dom4jXmlUtils.getSpecificNodeValueByString(queryInfo, nodeHierarchic, 1);
		return index;
	}
	
	/**
	 * check whether trunk is exist
	 * @param edgeName
	 * @param trunkName
	 * @return
	 * @throws Exception
	 */
	public boolean isTrunkExist(String edgeName, String trunkName) throws Exception{
		boolean flag = false;
		String queryInfo = this.getAllInterfacesInfo(edgeName);
		String targetTag = "name";
		if(XmlFileOp.checkTagExist(queryInfo, targetTag) & XmlFileOp.checkGivenStringEqualTagValue(queryInfo, targetTag, trunkName)){
			flag =true;
		}
		return flag;
	}
	
	/**
	 * query all interfaces info
	 * @param edgeName
	 * @return
	 * @throws Exception
	 */
	public String getAllInterfacesInfo(String edgeName) throws Exception{
		//GET https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/vnics/
		String edgeId = this.getEdgeIDbyName(edgeName);
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/vnics/";
System.out.println(url);
		return httpReq.getRequest(url);
	}
	
	/**
	 * delete specific interface
	 * @param edgeName
	 * @param vnicIndex
	 * @throws Exception
	 */
	public void deleteInterface(String edgeName,String vnicIndex) throws Exception{
		//DELETE https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/vnics/<index>
		String edgeId = this.getEdgeIDbyName(edgeName);
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/vnics/" + vnicIndex;
System.out.println(url);
		httpReq.delRequest(url);
	}
	
	/**
	 * check whether the interface exist
	 * @param edgeName
	 * @param interfaceName
	 * @return
	 * @throws Exception
	 */
	public boolean isInterfaceExist(String edgeName, String interfaceName) throws Exception
	{
		boolean flag = false;
		String queryInfo = this.getAllInterfacesInfo(edgeName);
		String targetTag = "name";
		flag = XmlFileOp.checkGivenStringEqualTagValue(queryInfo, targetTag, interfaceName);
		return flag;
	}
	
	
	/**
	 * Set up default edge environment
	 * @throws Exception
	 */
	public void setDefaultEdgeEnv(){
		this.setPreconditionForESG();
		
		if(!this.isEdgeExist(edgeName)){
			VCUtils vcUtils = new VCUtils();
			
			String clusterName = DefaultEnvironment.Cluster1;
			String datastoreName = DefaultEnvironment.DataStore1;
			String hostIP = DefaultEnvironment.Host1InDvSwitch;
			String clusterMoid = vcUtils.getClusterMOID(clusterName);//resourcePoolID
			String datastoreMoid = vcUtils.getDatastoreMOID(hostIP, datastoreName);
			String dataCenterName = DefaultEnvironment.DatacenterName;
			String dataCenterID = vcUtils.getDataCenterMOID(dataCenterName);
			
			Appliance appliance = new Appliance(clusterMoid, datastoreMoid);
			Appliances appliances = new Appliances("compact", appliance, "true");
			
			CliSetting cliSetting = new CliSetting("admin","!QAZ2wsx3edc","false");	
			AutoConfiguration autoConfiguration = new AutoConfiguration("true", "high");
			QueryDaemon queryDaemon = new QueryDaemon("false", "5666");
			
//			//Add vnic info
			String uplink01 = DefaultEnvironment.Uplink01;
			String internal01 = DefaultEnvironment.Internal01;
			String uplinkPortGrounID = vcUtils.getNetworkMoIdByName(uplink01);
			String internalPortGrounID = vcUtils.getNetworkMoIdByName(internal01);
		
			String edgeGatewayUplinkIPAddress=DefaultEnvironment.edgeGatewayUplinkIPAddress;
			String edgeGatewayUplinkSubnetMask=DefaultEnvironment.edgeGatewayUplinkSubnetMask;
			String edgeGatewayUplinkSubnetPrefixLength=DefaultEnvironment.edgeGatewayUplinkSubnetPrefixLength;
			String edgeGatewayInternalIPAddress=DefaultEnvironment.edgeGatewayInternalIPAddress;
			String edgeGatewayInternalSubnetMask=DefaultEnvironment.edgeGatewayInternalSubnetMask;
			String edgeGatewayInternalSubnetPrefixLength=DefaultEnvironment.edgeGatewayInternalSubnetPrefixLength;
			
			AddressGroup addressGroup_Internal01 = new AddressGroup(edgeGatewayInternalIPAddress,edgeGatewayInternalSubnetMask,edgeGatewayInternalSubnetPrefixLength);
			AddressGroup addressGroup_Uplink01 = new AddressGroup(edgeGatewayUplinkIPAddress,edgeGatewayUplinkSubnetMask,edgeGatewayUplinkSubnetPrefixLength);
			ArrayList<AddressGroup> addressGroups_internal = new ArrayList<AddressGroup>();
			ArrayList<AddressGroup> addressGroups_uplink = new ArrayList<AddressGroup>();
			addressGroups_uplink.add(addressGroup_Uplink01);
			addressGroups_internal.add(addressGroup_Internal01);
			Vnic vnic_internal = new Vnic("1", internalName, InterfaceType.interfaceType_internal, internalPortGrounID, addressGroups_internal, "1500","true","false","false");
			Vnic vnic_uplink = new Vnic("0", uplinkName, InterfaceType.interfaceType_uplink, uplinkPortGrounID, addressGroups_uplink, "1500","true","false","false");
			ArrayList<Vnic> vnics = new ArrayList<Vnic>();
			vnics.add(vnic_internal);
			vnics.add(vnic_uplink);
			
			Edge edge = new Edge(dataCenterID, "default", this.edgeName, "default", edgeName, "true", "false", "emergency",vnics,appliances,cliSetting,autoConfiguration, "gatewayServices", queryDaemon);
			
			this.addEdge(edge);
		}	
	}
	
	public void setupDefaultLDR() {
		this.setPreconditionForLDR();

		if(!this.isEdgeExist(this.logicalRouterName)){
			VCUtils vcUtils = new VCUtils();
			
			String clusterName = DefaultEnvironment.Cluster1;
			String datastoreName = DefaultEnvironment.DataStore1;
			String hostIP = DefaultEnvironment.Host1InDvSwitch;
			String clusterMoid = vcUtils.getClusterMOID(clusterName);//resourcePoolID
			String datastoreMoid = vcUtils.getDatastoreMOID(hostIP, datastoreName);
			String dataCenterName = DefaultEnvironment.DatacenterName;
			String dataCenterID = vcUtils.getDataCenterMOID(dataCenterName);
			
			Appliance appliance = new Appliance(clusterMoid, datastoreMoid);
			Appliances appliances = new Appliances("compact", appliance, "true");
			
			CliSetting cliSetting = new CliSetting("admin","!QAZ2wsx3edc","false");
			
			AutoConfiguration autoConfiguration = new AutoConfiguration("true", "high");
			
			QueryDaemon queryDaemon = new QueryDaemon("false", "5666");
			
	
	//		//Add vnic info
			String uplinkName = this.uplinkName;
			String internalName = this.internalName;
			
			String lg001 = this.lgMgr.defaultLogicalSwitchName001;
			String lg002 = this.lgMgr.defaultLogicalSwitchName002;
			
			String lg001Id = this.lgMgr.getSpecificLogicalSwitchId(lg001);
			String lg002Id = this.lgMgr.getSpecificLogicalSwitchId(lg002);
			
			String dvPortgroupName = DefaultEnvironment.dvPortGroupName;
			String dvPortgroupId = vcUtils.getNetworkMoIdByName(dvPortgroupName);
			
			String logicalRouterManagementIPAddress=DefaultEnvironment.logicalRouterManagementIPAddress;
			String logicalRouterManagementSubnetMask=DefaultEnvironment.logicalRouterManagementSubnetMask;
			String logicalRouterManagementSubnetPrefixLength=DefaultEnvironment.logicalRouterManagementSubnetPrefixLength;
			String logicalRouterUplinkIPAddress=DefaultEnvironment.logicalRouterUplinkIPAddress;
			String logicalRouterUplinkSubnetMask=DefaultEnvironment.logicalRouterUplinkSubnetMask;
			String logicalRouterUplinkSubnetPrefixLength=DefaultEnvironment.logicalRouterUplinkSubnetPrefixLength;
			String logicalRouterInternalIPAddress=DefaultEnvironment.logicalRouterInternalIPAddress;
			String logicalRouterInternalSubnetMask=DefaultEnvironment.logicalRouterInternalSubnetMask;
			String logicalRouterInternalSubnetPrefixLength=DefaultEnvironment.logicalRouterInternalSubnetPrefixLength;
			
			AddressGroup addressGroup_Internal01 = new AddressGroup(logicalRouterInternalIPAddress,logicalRouterInternalSubnetMask,logicalRouterInternalSubnetPrefixLength);
			AddressGroup addressGroup_Uplink01 = new AddressGroup(logicalRouterUplinkIPAddress,logicalRouterUplinkSubnetMask,logicalRouterUplinkSubnetPrefixLength);
			AddressGroup addressGroup_mgmtInterface = new AddressGroup(logicalRouterManagementIPAddress,logicalRouterManagementSubnetMask,logicalRouterManagementSubnetPrefixLength);
			
			ArrayList<AddressGroup> addressGroups_internal = new ArrayList<AddressGroup>();
			ArrayList<AddressGroup> addressGroups_uplink = new ArrayList<AddressGroup>();
			ArrayList<AddressGroup> addressGroups_mgmtInterface = new ArrayList<AddressGroup>();
			
			addressGroups_uplink.add(addressGroup_Uplink01);
			addressGroups_internal.add(addressGroup_Internal01);
			addressGroups_mgmtInterface.add(addressGroup_mgmtInterface);
			
//			Vnic vnic_internal = new Vnic("2", internalName, InterfaceType.interfaceType_internal, lg002Id, addressGroups_internal, "1500","true","false","false");
//			Vnic vnic_uplink = new Vnic("1", uplinkName, InterfaceType.interfaceType_uplink, lg001Id, addressGroups_uplink, "1500","true","false","false");
//			
//			ManegementInterface mgmtInterface = new ManegementInterface(addressGroups_mgmtInterface,dvPortgroupId);
//			
//			ArrayList<Vnic> vnics = new ArrayList<Vnic>();
//			vnics.add(vnic_internal);
//			vnics.add(vnic_uplink);
			
			LDRInterface vnic_internal = new LDRInterface(internalName, InterfaceType.interfaceType_internal, lg002Id, addressGroups_internal, "1500","true");
			LDRInterface vnic_uplink = new LDRInterface( uplinkName, InterfaceType.interfaceType_uplink, lg001Id, addressGroups_uplink, "1500","true");
			
			ManegementInterface mgmtInterface = new ManegementInterface(addressGroups_mgmtInterface,dvPortgroupId);
			
			ArrayList<LDRInterface> interfaces = new ArrayList<LDRInterface>();
			interfaces.add(vnic_internal);
			interfaces.add(vnic_uplink);
			
			Edge edge = new Edge(dataCenterID, "default", this.logicalRouterName, "default", "desc", "true", "false", "emergency",interfaces,appliances,cliSetting,autoConfiguration, "distributedRouter",mgmtInterface);
//			Edge edge = new Edge(dataCenterID, "default", this.logicalRouterName, "default", logicalRouterName, 
//					"true", "false", "emergency",interfaces,appliances,cliSetting,autoConfiguration, "distributedRouter", queryDaemon,mgmtInterface);
			this.addEdge(edge);
		}
	}
	
	public void setupDefaultUniversalLogicalRouter() {
		this.setPreconditionForUniversalLDR();
		if(!this.isEdgeExist(this.universalLogicalRouterName)){
			VCUtils vcUtils = new VCUtils();
			
			String clusterName = DefaultEnvironment.Cluster1;
			String datastoreName = DefaultEnvironment.DataStore1;
			String hostIP = DefaultEnvironment.Host1InDvSwitch;
			String clusterMoid = vcUtils.getClusterMOID(clusterName);//resourcePoolID
			String datastoreMoid = vcUtils.getDatastoreMOID(hostIP, datastoreName);
			String dataCenterName = DefaultEnvironment.DatacenterName;
			String dataCenterID = vcUtils.getDataCenterMOID(dataCenterName);
			
			Appliance appliance = new Appliance(clusterMoid, datastoreMoid);
			Appliances appliances = new Appliances("compact", appliance, "true");
			
			CliSetting cliSetting = new CliSetting("admin","!QAZ2wsx3edc","false");
			
			AutoConfiguration autoConfiguration = new AutoConfiguration("true", "high");
			
			QueryDaemon queryDaemon = new QueryDaemon("false", "5666");
			
	
	//		//Add vnic info
			String uplinkName = this.uplinkName;
			String internalName = this.internalName;
			
			String universalLogicalSwitchName = this.lgMgr.universalLogicalSwitchName;
			String universalLogicalSwitchName002 = this.lgMgr.universalLogicalSwitchName002;
			
			String lg001Id = this.lgMgr.getSpecificLogicalSwitchId(universalLogicalSwitchName);
			String lg002Id = this.lgMgr.getSpecificLogicalSwitchId(universalLogicalSwitchName002);
			
			String dvPortgroupName = DefaultEnvironment.dvPortGroupName;
			String dvPortgroupId = vcUtils.getNetworkMoIdByName(dvPortgroupName);
			
			String universalLogicalRouterManagementIPAddress=DefaultEnvironment.universalLogicalRouterManagementIPAddress;
			String universalLogicalRouterManagementSubnetMask=DefaultEnvironment.universalLogicalRouterManagementSubnetMask;
			String universalLogicalRouterManagementSubnetPrefixLength=DefaultEnvironment.universalLogicalRouterManagementSubnetPrefixLength;
			String universalLogicalRouterUplinkIPAddress=DefaultEnvironment.universalLogicalRouterUplinkIPAddress;
			String universalLogicalRouterUplinkSubnetMask=DefaultEnvironment.universalLogicalRouterUplinkSubnetMask;
			String universalLogicalRouterUplinkSubnetPrefixLength=DefaultEnvironment.universalLogicalRouterUplinkSubnetPrefixLength;
			String universalLogicalRouterInternalIPAddress=DefaultEnvironment.universalLogicalRouterInternalIPAddress;
			String universalLogicalRouterInternalSubnetMask=DefaultEnvironment.universalLogicalRouterInternalSubnetMask;
			String universalLogicalRouterInternalSubnetPrefixLength=DefaultEnvironment.universalLogicalRouterInternalSubnetPrefixLength;
			
			AddressGroup addressGroup_Internal01 = new AddressGroup(universalLogicalRouterInternalIPAddress,universalLogicalRouterInternalSubnetMask,universalLogicalRouterInternalSubnetPrefixLength);
			AddressGroup addressGroup_Uplink01 = new AddressGroup(universalLogicalRouterUplinkIPAddress,universalLogicalRouterUplinkSubnetMask,universalLogicalRouterUplinkSubnetPrefixLength);
			AddressGroup addressGroup_mgmtInterface = new AddressGroup(universalLogicalRouterManagementIPAddress,universalLogicalRouterManagementSubnetMask,universalLogicalRouterManagementSubnetPrefixLength);
			
			
			ArrayList<AddressGroup> addressGroups_internal = new ArrayList<AddressGroup>();
			ArrayList<AddressGroup> addressGroups_uplink = new ArrayList<AddressGroup>();
			ArrayList<AddressGroup> addressGroups_mgmtInterface = new ArrayList<AddressGroup>();
			
			addressGroups_uplink.add(addressGroup_Uplink01);
			addressGroups_internal.add(addressGroup_Internal01);
			addressGroups_mgmtInterface.add(addressGroup_mgmtInterface);
			
			LDRInterface vnic_internal = new LDRInterface(internalName, InterfaceType.interfaceType_internal, lg002Id, addressGroups_internal, "1500","true");
			LDRInterface vnic_uplink = new LDRInterface( uplinkName, InterfaceType.interfaceType_uplink, lg001Id, addressGroups_uplink, "1500","true");
			
			ManegementInterface mgmtInterface = new ManegementInterface(addressGroups_mgmtInterface,dvPortgroupId);
			
			ArrayList<LDRInterface> interfaces = new ArrayList<LDRInterface>();
			interfaces.add(vnic_internal);
			interfaces.add(vnic_uplink);
			
			Edge edge = new Edge(dataCenterID, "default", universalLogicalRouterName, "default", "desc", "true", "false", "emergency",interfaces,appliances,cliSetting,autoConfiguration, "distributedRouter",mgmtInterface);

			
//			Vnic vnic_internal = new Vnic("2", internalName, InterfaceType.interfaceType_internal, lg002Id, addressGroups_internal, "1500","true","false","false");
//			Vnic vnic_uplink = new Vnic("1", uplinkName, InterfaceType.interfaceType_uplink, lg001Id, addressGroups_uplink, "1500","true","false","false");
//			
//			ManegementInterface mgmtInterface = new ManegementInterface(addressGroups_mgmtInterface,dvPortgroupId);
//			
//			ArrayList<Vnic> vnics = new ArrayList<Vnic>();
//			vnics.add(vnic_internal);
//			vnics.add(vnic_uplink);
//			
//			Edge edge = new Edge(dataCenterID, "default", universalLogicalRouterName, "default", universalLogicalRouterName, "true", "false", "emergency",
//					vnics,appliances,cliSetting,autoConfiguration, "distributedRouter", queryDaemon,mgmtInterface);
			this.addUniversalEdge(edge);

		}
	}
	
	/**
	 * clean up default Edge environment
	 * @throws Exception
	 */
	public void cleanDefaultEdgetEnv() throws Exception{

		if(this.isEdgeExist(edgeName)){
			this.deleteEdge(edgeName);
			
		}
	}
	
	/**
	 * Set up default trunk environment
	 * @throws Exception
	 */
	public void setDefaultTrunkEnv() throws Exception{
		if(!this.isTrunkExist(edgeName, trunkName)){
			vc = VC.getInstance();
			String subInterfaceName = TestData.NativeString;
			String portgroupId = vc.getNetworkMoIdByName(dvPortGroupName);
			this.addTrunk(edgeName, subInterfaceName, portgroupId, trunkVnicIndex);
		}
	}
	
	/**
	 * clean up default Trunk environment
	 * @throws Exception
	 */
	public void cleanDefaultTrunkEnv() throws Exception{
		if(this.isTrunkExist(edgeName, trunkName)){
			this.deleteInterface(edgeName, trunkVnicIndex);
		}
	}
	
	/**
	 * create default CSR
	 * @throws Exception
	 */
	public void setDefaultCSREnv() throws Exception{

		if(!this.isCSRExist(CSRName, edgeName)){
			this.generateCSR(CSRName, edgeName);
		}
	}
	
	public void cleanDefaultCSREnv() throws Exception{
		if(this.isCSRExist(CSRName, edgeName)){
			this.deleteSpecificCSR(CSRName, edgeName);
		}
	}
	
	/**
	 * Set the pre-condition for ESG 
	 * Precondition :Before to deploy edge service gateway need to install vib first.
	 */
	public void setPreconditionForESG(){
//		this.vxlan.setupDefaultVXLANConfig(this.cluster1);
		this.vxlanManager.setupVIB_Env();
		this.vxlanManager.setup_VXLANConfigure();
	}
	
	public void setPreconditionForLDR() {
		this.vxlanManager.setupVIB_Env();
		this.vxlanManager.setup_VXLANConfigure();
		this.vxlanManager.setDefaultSegmentIPPool();
		this.vxlanManager.setDefaultMulticast();
		this.vxlanManager.setDefaultTransportZone();
		this.lgMgr.setup2DefaultLogicalSwitch();
	}
	
	public void cleanupPreconditionForLDR() {
		this.lgMgr.cleanup2DefaultLogicalSwitch();
		this.vxlanManager.cleanDefaultTransportZone();
		this.vxlanManager.cleanupDefaultMulticast();
		this.vxlanManager.cleanupDefaultSegmnetIPPool();
	}
	
	public void setPreconditionForUniversalLDR() {
		this.vxlanManager.setupVIB_Env();
		this.vxlanManager.setup_VXLANConfigure();
		this.vxlanManager.setDefault_UniversalSegmentIPPool();
		this.vxlanManager.setDefault_UniversalMulticast();
		this.vxlanManager.setDefault_UniversalTransportZone();
		this.lgMgr.setup2Default_UniversalLogicalSwitch();
	}
	
	public void cleanupPreconditionForUniversalLDR() {
		this.lgMgr.cleanup2Default_UniversalLogicalSwitch();
		this.vxlanManager.cleanDefault_UniversalTransportZone();
		this.vxlanManager.cleanupDefault_UniversalMulticast();
		this.vxlanManager.cleanupDefault_UniversalSegmnetIPPool();
	}
	
}
