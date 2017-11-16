package com.vmware.nsx6x.service.FlowMonitoring;

import com.vmware.nsx6x.model.FlowMonitoring.Collector;
import com.vmware.nsx6x.utils.Config;
import com.vmware.nsx6x.utils.DefaultEnvironment;
import com.vmware.nsx6x.utils.HttpReq;
import com.vmware.nsx6x.utils.XmlFileOp;


public class FlowMonitoringManager {
	public HttpReq httpReq;
	private String vsmIP;
	public String ipAddressIPFix = "192.168.1.1";
	public String portIPFix = "5430";
	
	private String filepath = Config.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	private String IPFix_xmlPath = filepath + "/RestCallXML/IPFix.xml";
	
	
	public FlowMonitoringManager(){
		httpReq = HttpReq.getInstance();
		vsmIP = DefaultEnvironment.VSMIP;
	}
	
	
	/**
	 * get a Collector instance
	 * @return
	 */
	public Collector getIPCollectorInsatnce()
	{
		Collector c = new Collector("192.168.1.1","5555");
		return c;
	}
	
	/**
	 * query the IPFix Configuration
	 * @return
	 * @throws Exception
	 */
	public String queryIPFixConfig() throws Exception
	{
		//GET https://<nsxmgr-ip>/api/4.0/firewall/{contextId}/config/ipfix
		String url = "https://" + vsmIP + "/api/4.0/firewall/globalroot-0/config/ipfix";
System.out.println(url);
		return httpReq.getRequest(url);
	}
	
	/**
	 * configure the IPFix
	 * @param xmlContents
	 * @throws Exception
	 */
	public void modifyIPFixConfig(String xmlContents) throws Exception
	{
		//PUT https://<nsxmgr-ip>/api/4.0/firewall/{contextId}/config/ipfix
		String url = "https://" + vsmIP + "/api/4.0/firewall/globalroot-0/config/ipfix";
System.out.println(url);
		httpReq.putRequest(xmlContents,url);
	}
	
	/**
	 * delete teh IPFix
	 * @throws Exception
	 */
	public void deleteIPFixConfig() throws Exception
	{
		//DELETE https://<nsxmgr-ip>/api/4.0/firewall/{contextId}/config/ipfix
		String url = "https://" + vsmIP + "/api/4.0/firewall/globalroot-0/config/ipfix";
		httpReq.delRequest(url);
	}
	
	/**
	 * Configure IPFix info
	 * @param ipfixEnabled				true | false
	 * @param observationDomainId		please input numeral
	 * @param flowTimeout				1-60
	 * @throws Exception
	 */
	public void configIPFix(String ipfixEnabled,String observationDomainId,String flowTimeout) throws Exception
	{		
 		//array of xml keys
 		String [] xmlKeys = {"ipfixEnabled","observationDomainId","flowTimeout"};				
 		//array of xml values
 		String [] xmlValues = {ipfixEnabled,observationDomainId,flowTimeout};				
 		//generate xmlContents
// 		String xmlFilePath = "RestCallXML\\IPFix.xml";
 		
 		String xmlContents = XmlFileOp.generateXMLWithFile(xmlKeys, xmlValues, IPFix_xmlPath);
	
		modifyIPFixConfig(xmlContents);
	}
	
	/**
	 * add IP Collector
	 * @param ipCollector
	 * @throws Exception
	 */
	public void addIPCollector(Collector ipCollector) throws Exception
	{
		String ipCollectorString = "<collector><ip></ip><port></port></collector>";
		//array of xml keys
 		String [] xmlKeys = {"ip","port"};				
 		//array of xml values
 		String [] xmlValues = {ipCollector.getIp(),ipCollector.getPort()};
 		
 		String endTag = "ipfixConfiguration";
 		String appendString = XmlFileOp.generateXMLWithContents(xmlKeys, xmlValues, ipCollectorString);
		String xmlContents = queryIPFixConfig();
		xmlContents = XmlFileOp.appendToEnd(appendString, xmlContents, endTag);
		modifyIPFixConfig(xmlContents);
	}
	
	/**
	 * edit the specific IP Collector
	 * @param orgIPCollector
	 * @param newIPCollector
	 * @throws Exception
	 */
	public void editSpecificIPCollector(Collector orgIPCollector,Collector newIPCollector) throws Exception
	{
		String ipCollectorString = "<collector><ip></ip><port></port></collector>";
		//array of xml keys
 		String [] xmlKeys = {"ip","port"};				
 		//array of xml values
 		String [] orgXmlValues = {orgIPCollector.getIp(),orgIPCollector.getPort()};
 		String [] newXmlValues = {newIPCollector.getIp(),newIPCollector.getPort()};
 		
 		String orgIPCString = XmlFileOp.generateXMLWithContents(xmlKeys, orgXmlValues, ipCollectorString);
		String newIPCString = XmlFileOp.generateXMLWithContents(xmlKeys, newXmlValues, ipCollectorString);
		
		String xmlContents = queryIPFixConfig();
		
		if(xmlContents.contains(orgIPCString))
		{
			xmlContents = xmlContents.replace(orgIPCString, newIPCString);
		}else{
			System.out.println("Please ensure the IP Collector exist!");
		}	
		modifyIPFixConfig(xmlContents);	
	}
	
	
	/**
	 * delete the specific IP Collector
	 * @param ipCollector
	 * @throws Exception
	 */
	public void deleteSpecificIPCollector(Collector ipCollector) throws Exception
	{
		String ipCollectorString = "<collector><ip></ip><port></port></collector>";
		//array of xml keys
 		String [] xmlKeys = {"ip","port"};				
 		//array of xml values
 		String [] xmlValues = {ipCollector.getIp(),ipCollector.getPort()};
 		
 		String deleteString = XmlFileOp.generateXMLWithContents(xmlKeys, xmlValues, ipCollectorString);
		String xmlContents = queryIPFixConfig();
		
		if(xmlContents.contains(deleteString))
		{
			xmlContents = xmlContents.replace(deleteString, "");
		}else{
			System.out.println("Please ensure the IP Collector exist!");
		}	
		modifyIPFixConfig(xmlContents);	
	}
	
	/**
	 * check the IPFix Server status whether is true
	 * @return
	 * @throws Exception
	 */
	public boolean checkIPFixStatus() throws Exception
	{
		boolean flag = false;
		String queryInfo = queryIPFixConfig();
		if(queryInfo.contains("true")){
			flag =true;
		}
		return flag;
	}
	
	/**
	 * check the collector whether exist
	 * @param colletor
	 * @return
	 * @throws Exception
	 */
	public boolean isCollectorIPExist(Collector colletor) throws Exception
	{
		boolean flag = false;
		String ipCollectorString = "<collector><ip></ip><port></port></collector>";
		//array of xml keys
 		String [] xmlKeys = {"ip","port"};				
 		//array of xml values
 		String [] xmlValues = {colletor.getIp(),colletor.getPort()};
 		//generate the collector string
 		String collectorString = XmlFileOp.generateXMLWithContents(xmlKeys, xmlValues, ipCollectorString);
 		
 		String queryInfo = queryIPFixConfig();
 		if(queryInfo.contains(collectorString)){
 			flag = true;
 		}
 		return flag;	
	}
	
	/**
	 * Query Excluded Flows info
	 * @return
	 * @throws Exception
	 */
	public String queryExcludedFlowsInfo() throws Exception
	{
		//GET https://<nsxmgr-ip>/api/2.1/flow/config
		String url = "https://" + vsmIP + "/api/2.1/flow/config";
System.out.println(url);
		return httpReq.getRequest(url);
	}
	
	/**
	 * Configure Excluded Flows
	 * @param xmlContents
	 * @return
	 * @throws Exception
	 */
	public void postExcludedFlows(String xmlContents) throws Exception
	{
		//POST https://<nsxmgr-ip>/api/2.1/flow/config
		String url = "https://" + vsmIP + "api/2.1/flow/config";
System.out.println(url);
		httpReq.postRequest(xmlContents,url);
	}
	
	/**
	 * Set up default IPFix environment
	 * @throws Exception
	 */
	public void setDefaultIPFixEnv() throws Exception{
		String ipfixEnabled = "true";
		String observationDomainId = "123";
		String flowTimeout = "5";
		
		if(!checkIPFixStatus()){
			configIPFix(ipfixEnabled, observationDomainId, flowTimeout);
		}	
	}
	/**
	 * Clean Up the default IPFix Evn
	 * @throws Exception
	 */
	public void cleanUpDefaultIPFixEnv() throws Exception{
		if(this.checkIPFixStatus()){
			this.deleteIPFixConfig();
		}	
	}
	
	/**
	 * Set up default collector IPs Env
	 * @throws Exception
	 */
	public void setDefaultCollectorIPs() throws Exception{
		Collector collector = new Collector(ipAddressIPFix, portIPFix);
		if(!this.isCollectorIPExist(collector)){
			this.addIPCollector(collector);
		}
	}
	
	/**
	 * Clean Up the default collector IPs Env
	 * @throws Exception
	 */
	public void cleanUpDefaultCollectorIPsEnv() throws Exception{

		Collector collector = new Collector(ipAddressIPFix, portIPFix);
		if(this.isCollectorIPExist(collector)){
			this.deleteSpecificIPCollector(collector);
		}
	}

}
