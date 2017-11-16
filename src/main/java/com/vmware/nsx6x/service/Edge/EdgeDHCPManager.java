package com.vmware.nsx6x.service.Edge;

import java.util.ArrayList;
import java.util.HashMap;

import com.vmware.nsx6x.model.EdgeDHCP.DHCPRelay;
import com.vmware.nsx6x.model.EdgeDHCP.Pool;
import com.vmware.nsx6x.model.EdgeDHCP.StaticBinding;
import com.vmware.nsx6x.service.GroupingObjects.IPSetManager;
import com.vmware.nsx6x.utils.Config;
import com.vmware.nsx6x.utils.DefaultEnvironment;
import com.vmware.nsx6x.utils.Dom4jXmlUtils;
import com.vmware.nsx6x.utils.HttpReq;
import com.vmware.nsx6x.utils.TestData;
import com.vmware.nsx6x.utils.VC;
import com.vmware.nsx6x.utils.XmlFileOp;

public class EdgeDHCPManager {
	private String vsmIP;
	private HttpReq httpReq;
//	private HeaderReq headerReq;
	private VC vc = null;
	
	public EdgeManger edgeMgr;
	public IPSetManager ipSetMgr;
	
	public String ipRange = "192.168.1.2-192.168.1.10";
	public String defaultGateway = "192.168.1.1";
	public String vm2Name = DefaultEnvironment.VM2;
	public String hostName = "StaticBinding001";
	public String ipAddress = "192.168.1.20";
	
	public String edgeName;

	private String filepath = Config.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	private String xmlPath_DHCP_Pool = filepath + "/RestCallXML/DHCPPool.xml";
	private String xmlPath_DHCP = filepath + "/RestCallXML/DHCP.xml";
	private String xmlPath_StaticBinding = filepath + "/RestCallXML/StaticBinding.xml";
	private String xmlPath_DHCPRelay = filepath + "/RestCallXML/DHCPRelay.xml";

	

	public EdgeDHCPManager(){
		vsmIP = DefaultEnvironment.VSMIP;
		httpReq = HttpReq.getInstance();
//		headerReq = HeaderReq.getInstance();
		
		edgeMgr = new EdgeManger();
		edgeName = edgeMgr.edgeName;
		
	}
	
	
	/**
	 * get default pool instance
	 * @return
	 */
	public Pool getDefaultPoolInstance(){
		Pool pool = new Pool(ipRange,defaultGateway,"255.255.255.0","eng.vmware.com","192.168.5.1","192.168.6.1","1200");
//		Pool pool = new Pool(ipRange,defaultGateway,"eng.vmware.com","192.168.5.1","192.168.6.1","1200");
		return pool;
	}
	

	/**
	 * get VM Nic Binding default instance
	 * @return
	 */
	public StaticBinding getDefaultVMNicBindingInstance(){
		vc = VC.getInstance();
		String vmId = "";
		try {
			vmId = vc.getVirtualMachineMoIdByName(vm2Name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		StaticBinding sb = new StaticBinding("",vmId,"0",hostName,ipAddress,"192.168.1.1","eng.vmware.com","1.1.1.1","2.2.2.2","3600");
		return sb;
	}
	

	/**
	 * get default Mac Binding instance
	 * @return
	 */
	public StaticBinding getDefaultMacBindingInstance(){
		StaticBinding sb = new StaticBinding("11:22:33:44:55:66","1","1",hostName,ipAddress,"192.168.1.1","eng.vmware.com","1.1.1.1","2.2.2.2","3600");
		return sb;
	}
	

	/**
	 * get the default DHCP Relay instance
	 * @return
	 */
	public DHCPRelay getdefaultDHCPRelayInstance(){
		String ipSetName = "IPSet001" + TestData.NativeString;
		String uplinkIPAddress = DefaultEnvironment.UplinkIP;
		String relayServer_IPSetID = "";
//		GroupingObjectManager gom = new GroupingObjectManager();
		ipSetMgr = new IPSetManager();
		try {
			relayServer_IPSetID = ipSetMgr.getSpecificIPSetID(ipSetName);
		} catch (Exception e) {

			e.printStackTrace();
		}
		DHCPRelay dhcpRelay = new DHCPRelay(relayServer_IPSetID,"1.2.3.4","eng.vmware.com","0",uplinkIPAddress);
		return dhcpRelay;
	}
	
	//send put request of DHCP configuration
	private void putDHCP(String edgeName, String xmlContents, HashMap<String,String> header){
		String edgeId = edgeMgr.getEdgeIDbyName(edgeName);
		//PUT https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/dhcp/config
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/dhcp/config";
//System.out.println(url);
//
//		StringReader sr = new StringReader(xmlContents);
//		String functionName = "Edge DHCP";
//		String requestParameters = "";
//		try {
//			headerReq.putData2(sr, url, requestParameters, new StringWriter(), functionName, header);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		this.httpReq.putRequestWithHeader(xmlContents, url, header);
	}
	
	
	/**
	 * configure the DHCP
	 * @param edgeName
	 * @param poolList
	 */
	public void configDHCPPools(String edgeName, ArrayList<Pool> poolList, String dhcpStatus){
		HashMap<String,String> header = new HashMap<String,String>();
		header.put("Content-Type", "application/xml");
		
//String dhxpXMLFilePath = "RestCallXML\\DHCP.xml";
//String poolXMLFilePath = "RestCallXML\\DHCPPool.xml";
		String poolString ="";
		String xmlContents = "";
		//array of xml keys
		String [] xmlKeys = {"ipRange","defaultGateway","subnetMask","domainName","primaryNameServer","secondaryNameServer","leaseTime"};		
		String poolContents = XmlFileOp.readXMLContens(xmlPath_DHCP_Pool);
		String tempString = "";
		Pool pool = null;
		for(int i=0; i< poolList.size(); i++){
			pool = poolList.get(i);
			String [] xmlValues = {pool.getIpRange(),pool.getDefaultGateway(),pool.getSubnetMask(),pool.getDomainName(),pool.getPrimaryNameServer(),pool.getSecondaryNameServer(),
					pool.getLeaseTime()};
			tempString = XmlFileOp.generateXMLWithContents(xmlKeys, xmlValues, poolContents);
			poolString = poolString + tempString;
		}
		poolString = "<ipPools>" + poolString + "</ipPools>";
		xmlContents = XmlFileOp.readXMLContens(xmlPath_DHCP);
		xmlContents = XmlFileOp.appendToEnd(poolString, xmlContents, "dhcp");
		
		String nodeHierarchic_status = "//dhcp/enabled";
		xmlContents = Dom4jXmlUtils.modifySpecificNodeValue(xmlContents, nodeHierarchic_status, 1, dhcpStatus);
		
		this.putDHCP(edgeName, xmlContents,header);
	}
	
	/**
	 * configure DHCP StaticBinding
	 * @param edgeName
	 * @param bindingList
	 */
	public void configDHCPStaticBinding(String edgeName, ArrayList<StaticBinding> bindingList){
		HashMap<String,String> header = new HashMap<String,String>();
		header.put("Content-Type", "application/xml");
		
//String dhcpXMLFilePath = "RestCallXML\\DHCP.xml";
//String bindingXMLFilePath = "RestCallXML\\StaticBinding.xml";
		String bindingString ="";
		String xmlContents = "";
		//array of xml keys
		String [] xmlKeys = {"macAddress","vmId","vnicId","hostname","ipAddress","defaultGateway","domainName","primaryNameServer","secondaryNameServer","leaseTime"};		
		String bindingContents = XmlFileOp.readXMLContens(xmlPath_StaticBinding);
		String tempString = "";
		StaticBinding sb = null;
		if(bindingList.size()>0){
			for(int i=0; i< bindingList.size(); i++){
				sb = bindingList.get(i);
				String [] xmlValues = {sb.getMacAddress(),sb.getVmId(),sb.getVnicId(),sb.getHostname(),sb.getIpAddress(),sb.getDefaultGateway(),sb.getDomainName(),
						sb.getPrimaryNameServer(),sb.getSecondaryNameServer(),sb.getLeaseTime()};
				tempString = XmlFileOp.generateXMLWithContents(xmlKeys, xmlValues, bindingContents);
				bindingString = bindingString + tempString;
			}
			bindingString = "<staticBindings>" + bindingString + "</staticBindings>";
		}
		xmlContents = XmlFileOp.readXMLContens(xmlPath_DHCP);
		xmlContents = XmlFileOp.appendToEnd(bindingString, xmlContents, "dhcp");
		
		this.putDHCP(edgeName, xmlContents,header);
	}
	
	/**
	 * query DHCP information
	 * @param edgeName
	 * @return
	 */
	public String queryDHCPInfo(String edgeName){
		String edgeId = edgeMgr.getEdgeIDbyName(edgeName);
		//GET https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/dhcp/config
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/dhcp/config";
System.out.println(url);
		String queryInfo = "";
		try {
			queryInfo = httpReq.getRequest(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return queryInfo;
	}
	
	/**
	 * check pool whether exist
	 * @param edgeName
	 * @param ipRange
	 * @return
	 */
	public boolean isPoolExist(String edgeName,String ipRange){
		boolean flag = false;
		String queryInfo = this.queryDHCPInfo(edgeName);
		String targetTag = "ipRange";
		flag = XmlFileOp.checkGivenStringEqualTagValue(queryInfo, targetTag, ipRange);
		return flag;
	}
	
	/**
	 * delete DHCP Configuration
	 * @param edgeName
	 */
	public void deleteDHCPConfiguration(String edgeName){
		String edgeId = edgeMgr.getEdgeIDbyName(edgeName);
		//DELETE https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/dhcp/config
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/dhcp/config";
System.out.println(url);
		try {
			httpReq.delRequest(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//send post request to append ippool to dhcp
	private void postIPPool(String edgeName, String xmlContents, HashMap<String,String> header){
		String edgeId = edgeMgr.getEdgeIDbyName(edgeName);
		//POST https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/dhcp/config/ippools
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/dhcp/config/ippools";
//System.out.println(url);
//
//		StringReader sr = new StringReader(xmlContents);
//		String functionName = "Edge DHCP";
//		String requestParameters = "";
//		try {
//			headerReq.postData(sr, url, requestParameters, new StringWriter(), functionName, header);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		this.httpReq.postRequestWithHeader(xmlContents, url, header);
	}
	
	/**
	 * append IPPool to DHCP
	 * @param edgeName
	 * @param pool
	 */
	public void addDHCPPool(String edgeName,Pool pool){
		HashMap<String,String> header = new HashMap<String,String>();
		header.put("Content-Type", "application/xml");
		
//String xmlFilePath = "RestCallXML\\DHCPPool.xml";
		String [] xmlKeys = {"ipRange","defaultGateway","subnetMask","domainName","primaryNameServer","secondaryNameServer","leaseTime"};
		String [] xmlValues = {pool.getIpRange(),pool.getDefaultGateway(),pool.getSubnetMask(),pool.getDomainName(),pool.getPrimaryNameServer(),pool.getSecondaryNameServer(),
				pool.getLeaseTime()};
		String xmlContents = XmlFileOp.generateXMLWithFile(xmlKeys, xmlValues, xmlPath_DHCP_Pool);
		
		this.postIPPool(edgeName, xmlContents, header);
	}
	
	/**
	 * get specific poolID
	 * @param edgeName
	 * @param ipRange
	 * @return
	 */
	public String getPoolID(String edgeName,String ipRange){
 		String poolID = "";
		String queryInfo = this.queryDHCPInfo(edgeName);
		poolID = XmlFileOp.findNearestBeforeTagValue3(queryInfo, "poolId", "ipRange", ipRange);
		return poolID;
	}
	
	/**
	 * delete dhcp pool
	 */
	public void deleteDHCPPool(String edgeName, String ipRange){
		String edgeId = edgeMgr.getEdgeIDbyName(edgeName);
		String poolID = this.getPoolID(edgeName, ipRange);
		//DELETE https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/dhcp/config/ippools/<poolId>
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/dhcp/config/ippools/" + poolID;
System.out.println(url);
		try {
			httpReq.delRequest(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Service is on return true, else return false
	 * @param edgeName
	 * @return
	 */
	public boolean isServiceOn(String edgeName){
		boolean flag = false;
		String queryInfo = this.queryDHCPInfo(edgeName);
		String nodeHierarchic_status = "//dhcp/enabled";
		if(queryInfo.contains("enabled")){
			String enabled = "";
			try {
				enabled = Dom4jXmlUtils.getSpecificNodeValueByString(queryInfo, nodeHierarchic_status, 1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(enabled.equalsIgnoreCase("true")){
				flag = true;
			}
		}
		return flag;
	}
	
	/**
	 * enable and disable logging service
	 */
	public void enableDisableLoggingService(String edgeName,String loggingServiceStatus){
		HashMap<String,String> header = new HashMap<String,String>();
		header.put("Content-Type", "application/xml");
		
//		String xmlFilePath = "RestCallXML\\DHCP.xml";
		String xmlContents = "";
		xmlContents = XmlFileOp.readXMLContens(xmlPath_DHCP);
		
		String nodeHierarchic_loggingStatus = "//dhcp/logging/enable";
		xmlContents = Dom4jXmlUtils.modifySpecificNodeValue(xmlContents, nodeHierarchic_loggingStatus, 1, loggingServiceStatus);
		//xmlContents = XmlFileOp.modifySpecificNodeValue(xmlContents, nodeHierarchic_loggingLevel, 1, loggingLevel);
		this.putDHCP(edgeName, xmlContents,header);
	}
	
	/**
	 * change logging level
	 * @param edgeName
	 * @param loggingLevel
	 */
	public void changeLoggingLevel(String edgeName,String loggingLevel){
		HashMap<String,String> header = new HashMap<String,String>();
		header.put("Content-Type", "application/xml");
		
//String xmlFilePath = "RestCallXML\\DHCP.xml";
		String xmlContents = "";
		xmlContents = XmlFileOp.readXMLContens(xmlPath_DHCP);
		
		String nodeHierarchic_loggingLevel = "//dhcp/logging/logLevel";
		xmlContents = Dom4jXmlUtils.modifySpecificNodeValue(xmlContents, nodeHierarchic_loggingLevel, 1, loggingLevel);
		this.putDHCP(edgeName, xmlContents,header);
	}
	
	/**
	 * Service is on return true, else return false
	 * @param edgeName
	 * @return
	 */
	public boolean isLoggingServiceOn(String edgeName){
		boolean flag = false;
		String queryInfo = this.queryDHCPInfo(edgeName);
		String nodeHierarchic_loggingStatus = "//dhcp/logging/enable";
		if(queryInfo.contains("<enable>")){
			String enable = "";
			try {
				enable = Dom4jXmlUtils.getSpecificNodeValueByString(queryInfo, nodeHierarchic_loggingStatus, 1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(enable.equalsIgnoreCase("true")){
				flag = true;
			}
		}
		return flag;
	}
	
	//send post request to append static binding
	private void postStaticBinding(String edgeName,String xmlContents,HashMap<String,String> header){
		String edgeId = edgeMgr.getEdgeIDbyName(edgeName);
		//POST https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/dhcp/config/bindings
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/dhcp/config/bindings";
//System.out.println(url);
//
//		StringReader sr = new StringReader(xmlContents);
//		String functionName = "Edge DHCP";
//		String requestParameters = "";
//		try {
//			headerReq.postData(sr, url, requestParameters, new StringWriter(), functionName, header);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		this.httpReq.postRequestWithHeader(xmlContents, url, header);
	}
	
	/**
	 * add static binding
	 * @param edgeName
	 * @param sb
	 */
	public void addStaticBinding(String edgeName,StaticBinding sb){
		HashMap<String,String> header = new HashMap<String,String>();
		header.put("Content-Type", "application/xml");
		
//String xmlFilePath = "RestCallXML\\StaticBinding.xml";
		String [] xmlKeys = {"macAddress","vmId","vnicId","hostname","ipAddress","defaultGateway","domainName","primaryNameServer","secondaryNameServer","leaseTime"};
		String [] xmlValues = {sb.getMacAddress(),sb.getVmId(),sb.getVnicId(),sb.getHostname(),sb.getIpAddress(),sb.getDefaultGateway(),sb.getDomainName(),
				sb.getPrimaryNameServer(),sb.getSecondaryNameServer(),sb.getLeaseTime()};
		String xmlContents = XmlFileOp.generateXMLWithFile(xmlKeys, xmlValues, xmlPath_StaticBinding);
		
		this.postStaticBinding(edgeName, xmlContents, header);
	}
	
	/**
	 * check static binding whether exist
	 * @param edgeName
	 * @param ipAddress
	 * @return
	 */
	public boolean isStaticBingExist(String edgeName,String ipAddress){
		boolean flag = false;
		String queryInfo = this.queryDHCPInfo(edgeName);
		String targetTag = "ipAddress";
		flag = XmlFileOp.checkGivenStringEqualTagValue(queryInfo, targetTag, ipAddress);
		return flag;
	}
	
	/**
	 * get static binding id
	 * @param edgeName
	 * @param ipAddress
	 * @return
	 */
	public String getStaticBindingId(String edgeName,String ipAddress){
		String staticBindingId = "";
		String queryInfo = this.queryDHCPInfo(edgeName);
		if(queryInfo.contains("bindingId")){
			staticBindingId = XmlFileOp.findNearestBeforeTagValue3(queryInfo, "bindingId", "ipAddress", ipAddress);
		}
		return staticBindingId;
	}
	
	/**
	 * delete
	 * @param edgeName
	 * @param ipAddress
	 */
	public void deleteStaticBinding(String edgeName, String ipAddress){
		String edgeId = edgeMgr.getEdgeIDbyName(edgeName);
		String bindingId = this.getStaticBindingId(edgeName, ipAddress);
		//DELETE https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/dhcp/config/bindings/<bindingId>
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/dhcp/config/bindings/" + bindingId;
System.out.println(url);
		try {
			httpReq.delRequest(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * query DHCP Relay info
	 * @param edgeName
	 * @return
	 */
	public String queryDHCPRelayInfo(String edgeName){
		String edgeId = edgeMgr.getEdgeIDbyName(edgeName);
		//GET https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/dhcp/config/relay
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/dhcp/config/relay";
System.out.println(url);
		String queryInfo = "";
		try {
			queryInfo = httpReq.getRequest(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return queryInfo;
	}
	
	//send put request to configure DHCP Relay
	private void putDHCPRelay(String edgeName, String xmlContents, HashMap<String,String> header){
		String edgeId = edgeMgr.getEdgeIDbyName(edgeName);
		//PUT https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/dhcp/config/relay
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/dhcp/config/relay";
//System.out.println(url);
//
//		StringReader sr = new StringReader(xmlContents);
//		String functionName = "Edge DHCP Relay";
//		String requestParameters = "";
//		try {
//			headerReq.putData2(sr, url, requestParameters, new StringWriter(), functionName, header);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		this.httpReq.putRequestWithHeader(xmlContents, url, header);
	}
	
	/**
	 * configure DHCP Relay
	 * @param edgeName
	 */
	public void configureDHCPRelay(String edgeName, DHCPRelay dhcpRelay){
		HashMap<String,String> header = new HashMap<String,String>();
		header.put("Content-Type", "application/xml");
		
//String xmlFilePath = "RestCallXML\\DHCPRelay.xml";
		String [] xmlKeys = {"groupingObjectId","ipAddress","fqdn","vnicIndex","giAddress"};
		String [] xmlValues = {dhcpRelay.getRelayServer_IPSetID(),dhcpRelay.getRelayServer_IPAddress(),dhcpRelay.getRelayServer_Dns(),
				dhcpRelay.getRelayAgent_vnicIndex(),dhcpRelay.getRelayAgent_giAddress()};
		String xmlContents = XmlFileOp.generateXMLWithFile(xmlKeys, xmlValues, xmlPath_DHCPRelay);
		
		this.putDHCPRelay(edgeName, xmlContents, header);
	}
	
	/**
	 * delete DHCP Relay
	 * @param edgeName
	 */
	public void deleteDHCPRepay(String edgeName){
		String edgeId = edgeMgr.getEdgeIDbyName(edgeName);
		//DELETE https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/dhcp/config/relay
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/dhcp/config/relay";
System.out.println(url);
		try {
			httpReq.delRequest(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * check whether DHCP is configured
	 * @param edgeName
	 * @param relayAgent_giAddress
	 * @return
	 */
	public boolean isDHCPRelayExist(String edgeName, String relayAgent_giAddress){
		boolean flag = false;
		String queryInfo = this.queryDHCPRelayInfo(edgeName);
		String targetTag = "giAddress";
		flag = XmlFileOp.checkGivenStringEqualTagValue(queryInfo, targetTag, relayAgent_giAddress);
		return flag;
	}
	
	/**
	 * setup default User environment
	 */
	public void setDefaultPoolEnv(){
		if(!this.isPoolExist(edgeName, ipRange)){
			Pool pool = this.getDefaultPoolInstance();
			this.addDHCPPool(edgeName, pool);
		}
	}
	
	/**
	 * clean up default User environment
	 */
	public void cleanDefaultPoolEnv(){
		if(this.isPoolExist(edgeName, ipRange)){
			this.deleteDHCPPool(edgeName, ipRange);
		}
	}
	
	/**
	 * setup default vm nic binding environment
	 */
	public void setDefaultNicBindingEnv(){
		if(!this.isStaticBingExist(edgeName, ipAddress)){
			StaticBinding sb = this.getDefaultVMNicBindingInstance();
			this.addStaticBinding(edgeName, sb);
		}
	}
	
	/**
	 * setup default Mac Binding environment
	 */
	public void setDefaultMacBindingEnv(){
		if(!this.isStaticBingExist(edgeName, ipAddress)){
			StaticBinding sb = this.getDefaultMacBindingInstance();
			this.addStaticBinding(edgeName, sb);
		}
	}
	
	/**
	 * clean up default static binding environment
	 */
	public void cleanDefaultStaticBindingEnv(){
		if(this.isStaticBingExist(edgeName, ipAddress)){
			this.deleteStaticBinding(edgeName, ipAddress);
		}
	}
	
}
