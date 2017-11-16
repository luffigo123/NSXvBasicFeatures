package com.vmware.nsx6x.service.Edge;

import java.util.HashMap;

import com.vmware.nsx6x.model.EdgeSSLVPN.IPPool;
import com.vmware.nsx6x.model.EdgeSSLVPN.InstallationPackage;
import com.vmware.nsx6x.model.EdgeSSLVPN.PrivateNetwork;
import com.vmware.nsx6x.model.EdgeSSLVPN.User;
import com.vmware.nsx6x.utils.Config;
import com.vmware.nsx6x.utils.DefaultEnvironment;
import com.vmware.nsx6x.utils.Dom4jXmlUtils;
import com.vmware.nsx6x.utils.HttpReq;
import com.vmware.nsx6x.utils.TestData;
import com.vmware.nsx6x.utils.XmlFileOp;

public class SSLVPNManager{
	
	private String vsmIP;
	private HttpReq httpReq;
//	private HeaderReq headerReq;
	
	public EdgeManger edgeMgr;
	public String edgeName;
	
	public String ipRange = "192.168.3.2-192.168.3.10";
	public String privateNetwork = "192.168.1.0/24";
	public String defaultInstallationPackageName = "SSLVPNInstallationPackage001" + TestData.NativeString;
//	public String hostName = DefaultEnvironment.UplinkIP;
	public String hostName = DefaultEnvironment.edgeGatewayUplinkIPAddress;
	public String userId = "User001" + TestData.NativeString;
	public String userPassword = "!QAZ2wsx";
	
	public String status = "";
//	public String ipAddress = DefaultEnvironment.UplinkIP;
	public String ipAddress = DefaultEnvironment.edgeGatewayUplinkIPAddress;
	
	private String filepath = Config.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	private String xmlPath_ServerSetting = filepath + "/RestCallXML/ServerSetting.xml";
	private String xmlPath_SSLVPNIPPool = filepath + "/RestCallXML/SSLVPNIPPool.xml";
	private String xmlPath_SSLVPNPrivateNetwork = filepath + "/RestCallXML/SSLVPNPrivateNetwork.xml";
	private String xmlPath_SSLVPNInstallationPackage = filepath + "/RestCallXML/SSLVPNInstallationPackage.xml";
	private String xmlPath_SSLVPNUser = filepath + "/RestCallXML/SSLVPNUser.xml";
	private String xmlPath_SSLVPNAuthentication = filepath + "/RestCallXML/SSLVPNAuthentication.xml";
	
	
	public SSLVPNManager(){
		vsmIP = DefaultEnvironment.VSMIP;
		httpReq = HttpReq.getInstance();
//		headerReq = HeaderReq.getInstance();
		
		edgeMgr = new EdgeManger();
		edgeName = edgeMgr.edgeName;
	}
	
	//return the default IPPool instance
	public IPPool getIPPoolInstance(){
		IPPool ippool = new IPPool(ipRange, "255.255.255.0", "192.168.3.1", "true", TestData.NativeString, "1.1.1.1", "2.2.2.2", "eng.vmware.com", "3.3.3.3");
		return ippool;
	}
	
	//get the default privateNetwork instance
	public PrivateNetwork getDefaultPrivateNetworkInstance(){
		PrivateNetwork pn = new PrivateNetwork("192.168.1.0/24","true","20-40","true",TestData.NativeString);
		return pn;
	}
	
	//get the default InstallationPackage instance
	public InstallationPackage getDefaultInstallationPackageInstance(){
		InstallationPackage iPack = new InstallationPackage(defaultInstallationPackageName,hostName,"443","true","Description");
		return iPack;
	}
	
	//get the default User instance
	public User getDefaultUserInstance(){
		User user = new User(userId,userPassword,TestData.NativeString,TestData.NativeString,TestData.NativeString,"false","true","false");
		return user;
	}
	
	/**
	 * enable or disable SSL VPN
	 * @param edgeName
	 * @param trueOrfalse
	 * @throws Exception
	 */
	public void enableDisableSSLVPN(String edgeName, String trueOrfalse){
		String edgeId = edgeMgr.getEdgeIDbyName(edgeName);
		//POST https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/sslvpn/config/?enableService=true|False
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/sslvpn/config/?enableService=" + trueOrfalse;
//System.out.println(url);
//		try {
//			httpReq.postRequest2(url, new StringReader(""));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		this.httpReq.postRequest("", url);
	}
	
	/**
	 * query SSL VPN details
	 * @return
	 * @throws Exception
	 */
	public String querySSLVPNDetails(String edgeName){
		String edgeId = edgeMgr.getEdgeIDbyName(edgeName);
		//GET https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/sslvpn/config/
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/sslvpn/config";
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
	 *          
	 * @return true | false
	 * @throws Exception
	 */
	public String getSSLVPNStatus(String edgeName){
		String status = "";
		String queryInfo = "";
		String nodeHierarchic = "//sslvpnConfig/enabled";
		try {
			queryInfo = this.querySSLVPNDetails(edgeName);
			status = Dom4jXmlUtils.getSpecificNodeValueByString(queryInfo, nodeHierarchic, 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}
	
	
	private void applyServerSetting(String edgeName,String xmlContents){
		String edgeId = edgeMgr.getEdgeIDbyName(edgeName);
		//PUT https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/sslvpn/config/server/
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/sslvpn/config/server/";
System.out.println(url);
		try {
			httpReq.putRequest(xmlContents,url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Configure the Server Setting
	 * @param ipAddress
	 */
	public void configureServerSetting(String edgeName,String ipAddress){
		//array of xml keys
		String [] xmlKeys = {"ipAddress"};
		//array of xml values
		String [] xmlValues = {ipAddress};
//String xmlFilePath = "RestCallXML\\ServerSetting.xml";
		String xmlContents = "";
		xmlContents = XmlFileOp.generateXMLWithFile(xmlKeys, xmlValues, xmlPath_ServerSetting);
		this.applyServerSetting(edgeName,xmlContents);
	}
	
	/**
	 * query ServerSetting info
	 * @return
	 */
	public String queryServerSeetingInfo(String edgeName){
		String edgeId = edgeMgr.getEdgeIDbyName(edgeName);
		//GET https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/sslvpn/config/server/
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/sslvpn/config/server/";
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
	 * get ServerSetting ipAddress 
	 * @return
	 */
	public String getServerSettingipAddress(String edgeName){
		String ipAddress = "";
		String tag = "<ipAddress>";
		String queryInfo = this.queryServerSeetingInfo(edgeName);
		String nodeHierarchic = "//serverSettings/serverAddresses/ipAddress";
		if(queryInfo.contains(tag)){
			try {
				ipAddress = Dom4jXmlUtils.getSpecificNodeValueByString(queryInfo, nodeHierarchic, 1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			ipAddress = "";
		}
		
		return ipAddress;
	}
	
	/**
	 * check serverSettingipAddress whether equal UPlinkIP, if yes return true
	 * @param ipAddress   UPlinkIP
	 * @return
	 */
	public boolean isReadyServerSetting(String edgeName,String ipAddress){
		String serverSettingipAddress =  this.getServerSettingipAddress(edgeName);
		if(serverSettingipAddress.equalsIgnoreCase(ipAddress)){
			return true;
		}else{
			return false;
		}
	}
	
	
	private void creatIPPool(String edgeName, String xmlContents, HashMap<String,String> header){
		String edgeId = edgeMgr.getEdgeIDbyName(edgeName);
		//POST https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/sslvpn/config/client/networkextension/ippools/
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/sslvpn/config/client/networkextension/ippools/";
//System.out.println(url);
//
//		StringReader sr = new StringReader(xmlContents);
//		String functionName = "Edge SSLVPN";
//		String requestParameters = "";
//		
//		try {
//			headerReq.postData(sr, url, requestParameters, new StringWriter(), functionName, header);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		this.httpReq.postRequestWithHeader(xmlContents, url, header);
	}
	
	/**
	 * add IPPool
	 * @param ippool
	 */
	public void addIPPool(String edgeName, IPPool ippool){
		HashMap<String,String> header = new HashMap<String,String>();
		header.put("Content-Type", "application/xml");
		
		//array of xml keys
		String [] xmlKeys = {"ipRange","netmask","gateway","enabled","description","primaryDns","secondaryDns","dnsSuffix","winsServer"};
		//array of xml values
		String [] xmlValues = {ippool.getIpRange(),ippool.getNetmask(),ippool.getGateway(),ippool.getEnabled(),ippool.getDescription(),
				ippool.getPrimaryDns(),ippool.getSecondaryDns(),ippool.getDnsSuffix(),ippool.getWinsServer()};
		
//String xmlFilePath = "RestCallXML\\SSLVPNIPPool.xml";
		String xmlContents = "";
		xmlContents = XmlFileOp.generateXMLWithFile(xmlKeys, xmlValues, xmlPath_SSLVPNIPPool);
		this.creatIPPool(edgeName, xmlContents,header);
	}
	
	
	public String queryAllIPPoolsInfo(String edgeName){
		String edgeId = edgeMgr.getEdgeIDbyName(edgeName);
		//GET https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/sslvpn/config/client/networkextension/ippools/
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/sslvpn/config/client/networkextension/ippools/";
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
	 * get specific IPPoolID
	 * @param ipRange
	 * @return
	 */
	public String getIPPoolId(String edgeName, String ipRange){
		String tag1= "objectId";
		String tag2 = "ipRange";
		String queryInfo = this.queryAllIPPoolsInfo(edgeName);
		//return XmlFileOp.findNearestBeforeTagValue2(queryInfo, tag1, tag2, ipRange, 256);
		return XmlFileOp.findNearestBeforeTagValue3(queryInfo, tag1, tag2, ipRange);
	}
	
	/**
	 * check IPPool whether exist
	 * @param ipRange
	 * @return
	 */
	public boolean isIPPoolExist(String edgeName,String ipRange){
		boolean flag = false;
		String queryIPsetInfo = this.queryAllIPPoolsInfo(edgeName);
		String targetTag = "ipRange";
		flag = XmlFileOp.checkGivenStringEqualTagValue(queryIPsetInfo, targetTag, ipRange);
		return flag;
	}
	
	/**
	 * delete specific IPPool
	 * @param ipRange
	 */
	public void deleteSpecificIPPool(String edgeName,String ipRange){
		String edgeId = edgeMgr.getEdgeIDbyName(edgeName);
		String ippoolID = this.getIPPoolId(edgeName,ipRange);
		//DELETE https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/sslvpn/config/client/networkextension/ippools/ippoolID
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/sslvpn/config/client/networkextension/ippools/" + ippoolID;
System.out.println(url);
		try {
			httpReq.delRequest(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * delete all IPPools
	 */
	public void deleteAllIPPolls(String edgeName){
		String edgeId = edgeMgr.getEdgeIDbyName(edgeName);
		//DELETE https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/sslvpn/config/client/networkextension/ippools/
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/sslvpn/config/client/networkextension/ippools/";
System.out.println(url);
		try {
			httpReq.delRequest(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//send put request of IPPool
	private void putIPPool(String edgeName, String xmlContents, String ipRange){
		String edgeId = edgeMgr.getEdgeIDbyName(edgeName);
		String ippoolID = this.getIPPoolId(edgeName, ipRange);
		//PUT https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/sslvpn/config/client/networkextension/ippools/ippoolID
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/sslvpn/config/client/networkextension/ippools/" + ippoolID;
System.out.println(url);
		try {
			httpReq.putRequest(xmlContents,url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * edit IPPool
	 * @param edgeName
	 * @param ippool
	 */
	public void editIPPool(String edgeName, IPPool ippool, String orgIPRange){
		//array of xml keys
		String [] xmlKeys = {"ipRange","netmask","gateway","enabled","description","primaryDns","secondaryDns","dnsSuffix","winsServer"};
		//array of xml values
		String [] xmlValues = {ippool.getIpRange(),ippool.getNetmask(),ippool.getGateway(),ippool.getEnabled(),ippool.getDescription(),
				ippool.getPrimaryDns(),ippool.getSecondaryDns(),ippool.getDnsSuffix(),ippool.getWinsServer()};
				
//String xmlFilePath = "RestCallXML\\SSLVPNIPPool.xml";
		String xmlContents = "";
		xmlContents = XmlFileOp.generateXMLWithFile(xmlKeys, xmlValues, xmlPath_SSLVPNIPPool);
		
		this.putIPPool(edgeName, xmlContents,orgIPRange);
	}
	
	
	/**
	 * get all private networks info
	 * @return
	 */
	public String queryAllPrivateNetworkInfo(String edgeName){
		String edgeId = edgeMgr.getEdgeIDbyName(edgeName);
		//GET https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/sslvpn/config/client/networkextension/privatenetworks/
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/sslvpn/config/client/networkextension/privatenetworks/";
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
	
	
	//send post request of add private network 
	private void postPrivateNetwork(String edgeName, String xmlContents){
		String edgeId = edgeMgr.getEdgeIDbyName(edgeName);
		//POST https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/sslvpn/config/client/networkextension/privatenetworks/
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/sslvpn/config/client/networkextension/privatenetworks/";
System.out.println(url);
		try {
			httpReq.postRequest(xmlContents,url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * add Private Network
	 * @param edgeName
	 * @param pn
	 */
	public void addPrivateNetwork(String edgeName,PrivateNetwork pn){
		//array of xml keys
		String [] xmlKeys = {"network","enabled","description","ports","optimize"};
		//array of xml values
		String [] xmlValues = {pn.getNetwork(),pn.getEnabled(),pn.getDescription(),pn.getPorts(),pn.getOptimize()};
				
//String xmlFilePath = "RestCallXML\\SSLVPNPrivateNetwork.xml";
		String xmlContents = "";
		xmlContents = XmlFileOp.generateXMLWithFile(xmlKeys, xmlValues, xmlPath_SSLVPNPrivateNetwork);
		this.postPrivateNetwork(edgeName, xmlContents);
	}
	
	
	/**
	 * check IPPool whether exist
	 * @param ipRange
	 * @return
	 */
	public boolean isPrivateNetworkExist(String edgeName,String network){
		boolean flag = false;
		String queryIPsetInfo = this.queryAllPrivateNetworkInfo(edgeName);
		String targetTag = "network";
		flag = XmlFileOp.checkGivenStringEqualTagValue(queryIPsetInfo, targetTag, network);
		return flag;
	}
	
	/**
	 * get specific private network id
	 * @param edgeName
	 * @param network
	 * @return
	 */
	public String getPrivateNetworkId(String edgeName, String network){
	 	
 		String privateNetworkID = "";
		String queryInfo = this.queryAllPrivateNetworkInfo(edgeName);
		String nodeHierarchic = "//privateNetworks/privateNetwork/objectId";
		try {
			privateNetworkID = Dom4jXmlUtils.getSpecificNodeValueByString(queryInfo, nodeHierarchic, 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return privateNetworkID;
	}
	
	/**
	 * delete specific private network
	 * @param edgeName
	 * @param network
	 */
	public void deleteSpecificPrivateNetwork(String edgeName, String network){
		String edgeId = edgeMgr.getEdgeIDbyName(edgeName);
		String privatenetworkID = this.getPrivateNetworkId(edgeName, network);
		//DELETE https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/sslvpn/config/client/networkextension/privatenetworks/privatenetworkID
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/sslvpn/config/client/networkextension/privatenetworks/" + privatenetworkID;
System.out.println(url);
		try {
			httpReq.delRequest(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//send put request of private network
	private void putPrivateNetwork(String edgeName, String xmlContents,String network){
		String edgeId = edgeMgr.getEdgeIDbyName(edgeName);
		String privateNetworkID = this.getPrivateNetworkId(edgeName, network);
		//PUT https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/sslvpn/config/client/networkextension/privatenetworks/privateNetworkID
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/sslvpn/config/client/networkextension/privatenetworks/" + privateNetworkID;
System.out.println(url);
		try {
			httpReq.putRequest(xmlContents,url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * edit private network
	 * @param edgeName
	 * @param pn
	 * @param network
	 */
	public void editPrivateNetwork(String edgeName,PrivateNetwork pn ,String network){
		//array of xml keys
		String [] xmlKeys = {"network","enabled","description","ports","optimize"};
		//array of xml values
		String [] xmlValues = {pn.getNetwork(),pn.getEnabled(),pn.getDescription(),pn.getPorts(),pn.getOptimize()};
				
//String xmlFilePath = "RestCallXML\\SSLVPNPrivateNetwork.xml";
		String xmlContents = "";
		xmlContents = XmlFileOp.generateXMLWithFile(xmlKeys, xmlValues, xmlPath_SSLVPNPrivateNetwork);
		this.putPrivateNetwork(edgeName, xmlContents, network);
	}
	
	
	/**
	 * query all Installation Package info
	 * @param edgeName
	 * @return
	 */
	public String queryAllInstallationPackageInfo(String edgeName){
		String edgeId = edgeMgr.getEdgeIDbyName(edgeName);
		//GET https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/sslvpn/config/client/networkextension/installpackages/
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/sslvpn/config/client/networkextension/installpackages/";
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
	 * query the specific installation package info
	 * @param edgeName
	 * @param installationPackageName
	 * @return
	 */
	public String querySpecificInstallationPackageInfo(String edgeName, String installationPackageName){
		String edgeId = edgeMgr.getEdgeIDbyName(edgeName);
		String ID = this.getSpecificInstallationPackageID(edgeName, installationPackageName);
		//GET https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/sslvpn/config/client/networkextension/installpackages/ID
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/sslvpn/config/client/networkextension/installpackages/" + ID;
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
	
	//send post request of add installation package
	private String postInstallationPackage(String edgeName,String xmlContents){
		String result = "";
		String edgeId = edgeMgr.getEdgeIDbyName(edgeName);
		//POST https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/sslvpn/config/client/networkextension/installpackages/
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/sslvpn/config/client/networkextension/installpackages/";
System.out.println(url);
		try {
			result = httpReq.postRequest(xmlContents,url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * add installation package
	 * @param edgeName
	 * @param iPack
	 */
	public void addInstallationPackage(String edgeName, InstallationPackage iPack){
		//array of xml keys
		String [] xmlKeys = {"profileName","hostName","port","enabled","description"};
		//array of xml values
		String [] xmlValues = {iPack.getProfileName(),iPack.getHostName(),iPack.getPort(),iPack.getEnabled(),iPack.getDescription()};
				
//String xmlFilePath = "RestCallXML\\SSLVPNInstallationPackage.xml";
		String xmlContents = "";
		xmlContents = XmlFileOp.generateXMLWithFile(xmlKeys, xmlValues, xmlPath_SSLVPNInstallationPackage);
		
		this.postInstallationPackage(edgeName, xmlContents);
	}
	
	/**
	 * get specific installation package ID
	 * @param edgeName
	 * @return
	 */
	public String getSpecificInstallationPackageID(String edgeName,String installationPackageName){
		String tag1= "objectId";
		String tag2 = "profileName";
		String queryInfo = this.queryAllInstallationPackageInfo(edgeName);
		return XmlFileOp.findNearestBeforeTagValue3(queryInfo, tag1, tag2, installationPackageName);
	}
	
	
	/**
	 * check InstallationPackage whether exist
	 * @param ipRange
	 * @return
	 */
	public boolean isInstallationPackageExist(String edgeName,String installationPackagename){
		boolean flag = false;
		String queryInfo = this.queryAllInstallationPackageInfo(edgeName);
		String targetTag = "profileName";
		flag = XmlFileOp.checkGivenStringEqualTagValue(queryInfo, targetTag, installationPackagename);
		return flag;
	}
	
	/**
	 * delete specific InstallationPackage
	 * @param edgeName
	 * @param installationPackageName
	 */
	public void deleteSpecificInstallationPackage(String edgeName, String installationPackageName){
		String edgeId = edgeMgr.getEdgeIDbyName(edgeName);
		String iPackID = this.getSpecificInstallationPackageID(edgeName, installationPackageName);
		//DELETE https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/sslvpn/config/client/networkextension/installpackages/ID
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/sslvpn/config/client/networkextension/installpackages/" + iPackID;
System.out.println(url);
		try {
			httpReq.delRequest(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//send put request to modify the installation package
	private void putInstallationPackage(String edgeName,String installationPackageName,String xmlContents){
		String edgeId = edgeMgr.getEdgeIDbyName(edgeName);
		String iPackID = this.getSpecificInstallationPackageID(edgeName, installationPackageName);
		//PUT https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/sslvpn/config/client/networkextension/installpackages/ID
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/sslvpn/config/client/networkextension/installpackages/" + iPackID;
System.out.println(url);
		try {
			httpReq.putRequest(xmlContents,url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * edit the installation package
	 * @param edgeName
	 * @param installationPackageName
	 * @param iPack
	 */
	public void editInstallationPackage(String edgeName, String installationPackageName,InstallationPackage iPack){
		
		String xmlContents = this.querySpecificInstallationPackageInfo(edgeName, installationPackageName);
		
//		String nodeHierarchic_profileName = "//clientInstallPackage/profileName";
//		String nodeHierarchic_hostName= "//clientInstallPackage/gatewayList/gateway/hostName";
		String nodeHierarchic_enabled = "//clientInstallPackage/enabled";
		String nodeHierarchic_description = "//clientInstallPackage/description";
		//			 xmlContents = XmlFileOp.modifySpecificNodeValue(xmlContents, nodeHierarchic_profileName, 1, iPack.getProfileName());
		//			 xmlContents = XmlFileOp.modifySpecificNodeValue(xmlContents, nodeHierarchic_hostName, 1, iPack.getHostName());
					 xmlContents = Dom4jXmlUtils.modifySpecificNodeValue(xmlContents, nodeHierarchic_enabled, 1, iPack.getEnabled());
					 xmlContents = Dom4jXmlUtils.modifySpecificNodeValue(xmlContents, nodeHierarchic_description, 1, iPack.getDescription());
		
		this.putInstallationPackage(edgeName,installationPackageName,xmlContents);
	}
	
	//send post request to add user
	private String postUser(String edgeName,String xmlContents){
		String result = "";
		String edgeId = edgeMgr.getEdgeIDbyName(edgeName);
		//POST https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/sslvpn/config/auth/localserver/users/
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/sslvpn/config/auth/localserver/users/";
System.out.println(url);
		try {
			result = httpReq.postRequest(xmlContents,url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Add user
	 * @param edgeName
	 * @param user
	 */
	public void addUser(String edgeName,User user){
		//array of xml keys
		String [] xmlKeys = {"userId","password","description","firstName","lastName","disableUserAccount","passwordNeverExpires","changePasswordOnNextLogin"};
		//array of xml values
		String [] xmlValues = {user.getUserId(),user.getPassword(),user.getDescription(),user.getFirstName(),user.getLastName(),user.getDisableUserAccount(),user.getPasswordNeverExpires(),user.getChangePasswordOnNextLogin()};
				
//		String xmlFilePath = "RestCallXML\\SSLVPNUser.xml";
		String xmlContents = "";
		xmlContents = XmlFileOp.generateXMLWithFile(xmlKeys, xmlValues, xmlPath_SSLVPNUser);
		this.postUser(edgeName, xmlContents);
	}
	
	/**
	 * query all users' informations
	 * @param edgeName
	 * @return
	 */
	public String queryAllUserInfo(String edgeName){
		String edgeId = edgeMgr.getEdgeIDbyName(edgeName);
		//GET https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/sslvpn/config/auth/localserver/users
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/sslvpn/config/auth/localserver/users";
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
	 * get specific user objectId
	 * @param edgeName
	 * @param network
	 * @return
	 */
	public String getUserObjectID(String edgeName, String userId){
 		String userObjectID = "";
		String queryInfo = this.queryAllUserInfo(edgeName);
		userObjectID = XmlFileOp.findNearestBeforeTagValue3(queryInfo, "objectId", "userId", userId);
		return userObjectID;
	}
	
	/**
	 * query specific user info
	 * @param edgeName
	 * @param userId
	 * @return
	 */
	public String querySpecificUserInfo(String edgeName,String userId){
		String edgeId = edgeMgr.getEdgeIDbyName(edgeName);
		String userObjectID = this.getUserObjectID(edgeName, userId);
		//GET https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/sslvpn/config/auth/localserver/users/userID
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/sslvpn/config/auth/localserver/users/" + userObjectID;
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
	 * check InstallationPackage whether exist
	 * @param ipRange
	 * @return
	 */
	public boolean isUserExist(String edgeName,String userId){
		boolean flag = false;
		String queryInfo = this.queryAllUserInfo(edgeName);
		String targetTag = "userId";
		flag = XmlFileOp.checkGivenStringEqualTagValue(queryInfo, targetTag, userId);
		return flag;
	}
	
	/**
	 * delete specific User
	 * @param edgeName
	 * @param userId
	 */
	public void deleteSpecificUser(String edgeName, String userId){
		String edgeId = edgeMgr.getEdgeIDbyName(edgeName);
		String userObjectID = this.getUserObjectID(edgeName, userId);
		//DELETE https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/sslvpn/config/auth/localserver/users/userID
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/sslvpn/config/auth/localserver/users/" + userObjectID;
System.out.println(url);
		try {
			httpReq.delRequest(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//send put request to modify the User
	private void putUser(String edgeName,String userId,String xmlContents, HashMap<String,String> header){
		String edgeId = edgeMgr.getEdgeIDbyName(edgeName);
		String userObjectID = this.getUserObjectID(edgeName, userId);
		//PUT https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/sslvpn/config/auth/localserver/users/userID
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/sslvpn/config/auth/localserver/users/" + userObjectID;
//System.out.println(url);
//
//		StringReader sr = new StringReader(xmlContents);
//		String functionName = "Edge SSLVPN";
//		String requestParameters = "";
//		try {
//			headerReq.putData2(sr, url, requestParameters, new StringWriter(), functionName, header);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		this.httpReq.putRequestWithHeader(xmlContents, url, header);
	}
	
	/**
	 * edit User
	 * @param edgeName
	 * @param userId
	 * @param user
	 */
	public void editUser(String edgeName, String userId,User user){	
		HashMap<String,String> header = new HashMap<String,String>();
		header.put("Content-Type", "application/xml");
		
//String xmlFilePath = "RestCallXML\\SSLVPNUser.xml";
		String xmlContents = "";
		xmlContents = XmlFileOp.readXMLContens(xmlPath_SSLVPNUser);
		
		//String xmlContents = this.querySpecificUserInfo(edgeName, userId);
		String nodeHierarchic_userId = "//user/userId";
		String nodeHierarchic_password= "//user/password";
		String nodeHierarchic_firstName = "//user/firstName";
		String nodeHierarchic_lastName = "//user/lastName";
		String nodeHierarchic_desc = "//user/description";
		String nodeHierarchic_enabledisable = "//user/disableUserAccount";
		xmlContents = Dom4jXmlUtils.modifySpecificNodeValue(xmlContents, nodeHierarchic_userId, 1, userId);
		 xmlContents = Dom4jXmlUtils.modifySpecificNodeValue(xmlContents, nodeHierarchic_password, 1, user.getPassword());
		 xmlContents = Dom4jXmlUtils.modifySpecificNodeValue(xmlContents, nodeHierarchic_firstName, 1, user.getFirstName());
		 xmlContents = Dom4jXmlUtils.modifySpecificNodeValue(xmlContents, nodeHierarchic_lastName, 1, user.getLastName());
		 xmlContents = Dom4jXmlUtils.modifySpecificNodeValue(xmlContents, nodeHierarchic_desc, 1, user.getDescription());
		 xmlContents = Dom4jXmlUtils.modifySpecificNodeValue(xmlContents, nodeHierarchic_enabledisable, 1, user.getDisableUserAccount());		
		this.putUser(edgeName, userId, xmlContents,header);
	}
	
	/**
	 * query authentication info
	 * @param edgeName
	 * @return
	 */
	public String queryAuthenticationInfo(String edgeName){	
		String edgeId = edgeMgr.getEdgeIDbyName(edgeName);
		//GET https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/sslvpn/config/auth/settings/
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/sslvpn/config/auth/settings/";
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
	
	//send put request to add local authentication
	private void putLocalAuthentication(String edgeName,String xmlContents){
		String edgeId = edgeMgr.getEdgeIDbyName(edgeName);
		//PUT https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/sslvpn/config/auth/settings/
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/sslvpn/config/auth/settings/";
System.out.println(url);
		try {
			httpReq.putRequest(xmlContents,url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * add local authentication
	 * @param edgeName
	 */
	public void addLocalAuth(String edgeName){
//String xmlFilePath = "RestCallXML\\SSLVPNAuthentication.xml";
		String xmlContents = "";
		xmlContents = XmlFileOp.readXMLContens(xmlPath_SSLVPNAuthentication);
		this.putLocalAuthentication(edgeName, xmlContents);
	}
	
	
	/**
	 * enable the SSL VPN 
	 */
	public void enableSSLVPN(){
		//SSLVPNManager this = new SSLVPNManager();
		status = this.getSSLVPNStatus(edgeName);
		if(status.equalsIgnoreCase("false")){
			this.enableDisableSSLVPN(edgeName, "true");
		}
	}
	
	/**
	 * Disable the SSL VPN
	 */
	public void disableSSLVPN(){
		//SSLVPNManager this = new SSLVPNManager();
		status = this.getSSLVPNStatus(edgeName);
		if(status.equalsIgnoreCase("true")){
			this.enableDisableSSLVPN(edgeName,"false");
		}
	}
	
	/**
	 * configure Server Setting
	 */
	public void setServerSettingEnv(){
		//SSLVPNManager this = new SSLVPNManager();
		if(!this.isReadyServerSetting(edgeName,ipAddress)){
			this.configureServerSetting(edgeName,ipAddress);
		}
	}
	
	/**
	 * setup default IPPool environment
	 */
	public void setDefaulIPPoolENV(){
		//SSLVPNManager this = new SSLVPNManager();
		String ipRange = this.ipRange;
		if(!this.isIPPoolExist(edgeName, ipRange)){
			IPPool ippool = this.getIPPoolInstance();
			this.addIPPool(edgeName, ippool);
		}
	}
	
	/**
	 * clean up default IPPool environment
	 */
	public void cleanDefualtIPPoolENV(){
		//SSLVPNManager this = new SSLVPNManager();
		String ipRange = this.ipRange;
		if(this.isIPPoolExist(edgeName, ipRange)){
			this.deleteSpecificIPPool(edgeName, ipRange);
		}
	}
	
	/**
	 * check status true or false
	 * @param status
	 * @return
	 */
	public boolean checkSSLVPNStatus(String status){
		if("true".equalsIgnoreCase(status)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * setup default private network environment
	 */
	public void setDefaultPrivateNetworkENV(){
		String network = this.privateNetwork;
		if(!this.isPrivateNetworkExist(edgeName, network)){
			PrivateNetwork pn = this.getDefaultPrivateNetworkInstance();
			this.addPrivateNetwork(edgeName, pn);
		}
	}
	
	/**
	 * clean up the default private network environment
	 */
	public void cleanupDefaultPrivateNetworkENV(){
		String network = this.privateNetwork;
		if(this.isPrivateNetworkExist(edgeName, network)){
			this.deleteSpecificPrivateNetwork(edgeName, network);
		}
	}
	
	/**
	 * setup default installation package environment
	 */
	public void setDefaultInstallationPackageEnv(){
		String installationPackageName = this.defaultInstallationPackageName;
		if(!this.isInstallationPackageExist(edgeName, installationPackageName)){
			InstallationPackage iPack = this.getDefaultInstallationPackageInstance();
			this.addInstallationPackage(edgeName, iPack);
		}
	}
	
	/**
	 * clean up the default installation package environment
	 */
	public void cleanDefaultInstallationPackageEnv(){
		String installationPackageName = this.defaultInstallationPackageName;
		if(this.isInstallationPackageExist(edgeName, installationPackageName)){
			this.deleteSpecificInstallationPackage(edgeName, installationPackageName);
		}
	}

	/**
	 * setup default User environment
	 */
	public void setDefaultUserEnv(){
		String userId = this.userId;
		if(!this.isUserExist(edgeName, userId)){
			User user = this.getDefaultUserInstance();
			this.addUser(edgeName, user);
		}
	}
	
	/**
	 * clean up default User environment
	 */
	public void cleanDefaultUserEnv(){
		String userId = this.userId;
		if(this.isUserExist(edgeName, userId)){
			this.deleteSpecificUser(edgeName, userId);
		}
	}
	
	/**
	 * set up default authentication - local environment
	 */
	public void setDefaultAuthEnv(){
		this.addLocalAuth(edgeName);
	}
	
}
