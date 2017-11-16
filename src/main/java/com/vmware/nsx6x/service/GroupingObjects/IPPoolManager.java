package com.vmware.nsx6x.service.GroupingObjects;

import java.util.ArrayList;

import com.vmware.nsx6x.model.GroupingObjects.IPPool;
import com.vmware.nsx6x.model.GroupingObjects.IPRangeDto;
import com.vmware.nsx6x.utils.DefaultEnvironment;
import com.vmware.nsx6x.utils.HttpReq;
import com.vmware.nsx6x.utils.JAXBUtils;
import com.vmware.nsx6x.utils.JAXBUtils.CollectionWrapper;
import com.vmware.nsx6x.utils.TestData;
import com.vmware.nsx6x.utils.XmlFileOp;

public class IPPoolManager {
	public HttpReq httpReq;
	private String vsmIP;
	
	public String nativeString = TestData.NativeString;
	
//	public String defaultIPPoolName = TestData.NativeString;
//	public String defaultDesc = "DefaultDes" + TestData.NativeString;
	public String defaultIPPoolName;
	public String defaultDesc;

	public String ipPoolName_ForController;

	public JAXBUtils jAXBUtils;
	
	public IPPoolManager() {
		httpReq = HttpReq.getInstance();
		vsmIP = DefaultEnvironment.VSMIP;
		jAXBUtils = new JAXBUtils(IPPool.class, CollectionWrapper.class);
		
		defaultIPPoolName = this.nativeString;
		defaultDesc = this.nativeString;
		ipPoolName_ForController = this.nativeString + this.nativeString.substring(0,2);
		
	}

	public void addIPPool(String xmlContents)
	{
		//POST https://NSX-Manager-IP-Address/api/2.0/services/ipam/pools/scope/scopeId
		String url = "https://" + vsmIP + "/api/2.0/services/ipam/pools/scope/globalroot-0" ;
		System.out.println(url);
		httpReq.postRequest(xmlContents,url);

	}
	
	/**
	 * Query all IPsets info
	 * @return
	 */
	public String queryIPPoolsInfo()
	{
		//https://NSX-Manager-IP-Address/api/2.0/services/ipam/pools/scope/scopeID
		String url = "https://" + vsmIP + "/api/2.0/services/ipam/pools/scope/globalroot-0" ;
		System.out.println(url);
		String result = "";
		try {
			result = httpReq.getRequest(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Get IPpool's ID by IPPool name
	 * @param IPPoolName
	 * @return
	 */
	public String getSpecificIPPoolId(String IPPoolName)
	{	
		String ipSetsInfo = "";
		try {
			ipSetsInfo =this.queryIPPoolsInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String ipSetID = XmlFileOp.findNearestBeforeTagValue3(ipSetsInfo, "objectId", "name", IPPoolName);
		return ipSetID;
	}
	
	/**
	 * Query Specific IPPool info
	 * @param IPPoolId
	 * @return
	 */
 	public String querySpecificIPSetInfo(String IPPoolId)
	{
		//GET https://NSX-Manager-IP-Address/api/2.0/services/ipam/pools/poolId
		String url = "https://" + vsmIP + "/api/2.0/services/ipam/pools/" + IPPoolId;
		System.out.println(url);
		String result = "";
		try {
			result = httpReq.getRequest(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Modify specific IPPool
	 * @param xmlContents
	 * @param IPSetID
	 */
	public void modifySpecificIPPool(String xmlContents, String IPPoolId)
	{
		//PUT https://NSX-Manager-IP-Address/api/2.0/services/ipam/pools/poolId
		String url = "https://" + vsmIP + "/api/2.0/services/ipam/pools/" + IPPoolId;
		System.out.println(url);
		httpReq.putRequest(xmlContents, url);

	}
	
	/**
	 * Delete specific IPSet
	 * @param IPSetID
	 */
	public void deleteSpecificIPPool(String IPPoolId)
	{
		//DELETE https://NSX-Manager-IP-Address/api/2.0/services/ipam/pools/poolId
		String url = "https://" + vsmIP + "/api/2.0/services/ipam/pools/" + IPPoolId;
		System.out.println(url);
		httpReq.delRequest(url);

	}
	
	/**
	 * Check the target IPSet whether exist
	 * @param ipSetName
	 * @return
	 */
	public boolean isIPPoolExist(String ipPoolName)
	{
		boolean flag = false;
		
		String queryIPsetInfo = "";
		try {
			queryIPsetInfo = this.queryIPPoolsInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String targetTag = "name";
		flag = XmlFileOp.checkGivenStringEqualTagValue(queryIPsetInfo, targetTag, ipPoolName);
		return flag;
	}
	
	/**
	 * Add an IPPool
	 * @param ipool
	 */
	public void addIPPool(IPPool ipPool){
		String xmlContent = this.jAXBUtils.objToXml(ipPool, "UTF-8");
		this.addIPPool(xmlContent);
	}
	

	public void editSpecificIPPool(String orgIPPoolName, IPPool newIPPool)
	{
		IPPool queryIPPool = this.getIPPoolFromQueryXMLContent(orgIPPoolName);
		newIPPool.setRevision(queryIPPool.getRevision());
		newIPPool.setSubnetId(queryIPPool.getSubnetId());
		newIPPool.setObjectId(queryIPPool.getObjectId());
		String xmlContent = this.jAXBUtils.objToXml(newIPPool, "UTF-8");
		String ipPoolId = this.getSpecificIPPoolId(orgIPPoolName);
		this.modifySpecificIPPool(xmlContent, ipPoolId);
	}
	

	public void deleteIPPool(String ipPoolName)
	{
		if(this.isIPPoolExist(ipPoolName)){
			String ipPoolID = this.getSpecificIPPoolId(ipPoolName);
			this.deleteSpecificIPPool(ipPoolID);
		}	
	}
	
	public IPPool getDefaultIPPool(){
		IPRangeDto ipRangDto = new IPRangeDto("192.168.1.2", "192.168.1.5");
		ArrayList<IPRangeDto> ipRanges = new ArrayList<IPRangeDto>();
		ipRanges.add(ipRangDto);
		String prefixLength = "24";
		String gateway = "192.168.1.1";
		String dnsServer1 = "1.1.1.1";
		String dnsServer2 = "2.2.2.2";
		IPPool ipPool  = new IPPool(this.defaultIPPoolName, prefixLength, gateway, this.defaultDesc, dnsServer1, dnsServer2, ipRanges);
		return ipPool;
	}
	
	public IPPool getControllerIPPool() {
		String startIPAddress = DefaultEnvironment.IPPoolStartIPAddress;
		String endIPAddress = DefaultEnvironment.IPPoolEndIPAddress;
		String prefixLength = DefaultEnvironment.IPPoolPrefixLength;
		String gateway = DefaultEnvironment.IPPoolGateway;
		String dnsServer1 = DefaultEnvironment.IPPoolPrimaryDNS;
		String dnsServer2 = DefaultEnvironment.IPPoolSecondaryDNS;
		
		IPRangeDto ipRangDto = new IPRangeDto(startIPAddress, endIPAddress);
		ArrayList<IPRangeDto> ipRanges = new ArrayList<IPRangeDto>();
		ipRanges.add(ipRangDto);

		IPPool ipPool  = new IPPool(this.ipPoolName_ForController, prefixLength, gateway, this.defaultDesc, dnsServer1, dnsServer2, ipRanges);
		return ipPool;
	}
	
	/**
	 * Query Specific IPPool xml contect, and convert to IPPool Object 
	 * @param ipPoolName
	 * @return
	 */
	public IPPool getIPPoolFromQueryXMLContent(String ipPoolName){
		String ipPoolId = this.getSpecificIPPoolId(ipPoolName);
		String xmlContent = this.querySpecificIPSetInfo(ipPoolId);
		IPPool ippool= jAXBUtils.xmlStringToObj(xmlContent);
		return ippool;
	}
	
	public void setupDefaultIPPool(){
		if(!isIPPoolExist(defaultIPPoolName)){
			addIPPool(getDefaultIPPool());
		}
	}
	
	public void cleanupDefaultIPPool(){
		if(isIPPoolExist(defaultIPPoolName)){
			this.deleteIPPool(defaultIPPoolName);
		}
	}
	
	public void setupIPPool_ForController(){
		if(!isIPPoolExist(this.ipPoolName_ForController)){
			addIPPool(this.getControllerIPPool());
		}
	}
	
	public void cleanupIPPool_ForController(){
		if(isIPPoolExist(this.ipPoolName_ForController)){
			this.deleteIPPool(ipPoolName_ForController);
		}
	}
	
	
	
}
