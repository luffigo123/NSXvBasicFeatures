package com.vmware.nsx6x.service.Edge;

import java.util.ArrayList;
import java.util.HashMap;

import com.vmware.nsx6x.model.EdgeFirewall.EdgeFirewallRule;
import com.vmware.nsx6x.model.EdgeFirewall.SourceDestination;
import com.vmware.nsx6x.utils.Config;
import com.vmware.nsx6x.utils.DefaultEnvironment;
import com.vmware.nsx6x.utils.Dom4jXmlUtils;
import com.vmware.nsx6x.utils.HttpReq;
import com.vmware.nsx6x.utils.TestData;
import com.vmware.nsx6x.utils.XmlFileOp;

public class EdgeFirewallManager{
	public String vsmIP;
	public HttpReq httpReq;
//	public HeaderReq headerReq;

	public EdgeManger edgeMgr;
	public String edgeName;

	private String filepath = Config.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	private String xmlPath_EdgeFirewallRule = filepath + "/RestCallXML/EdgeFirewallRule.xml";
	
	public String edgeFirewallRuleName = "EdgeFirewallRull001" + TestData.NativeString;
	
	public EdgeFirewallManager(){
		vsmIP = DefaultEnvironment.VSMIP;
		httpReq = HttpReq.getInstance();
//		headerReq = HeaderReq.getInstance();
		
		edgeMgr = new EdgeManger();
		edgeName = edgeMgr.edgeName;
	}

	//get default firewall rule instance
	public EdgeFirewallRule getDefaultFirewallInstance(){
		EdgeFirewallRule efr = new EdgeFirewallRule(edgeFirewallRuleName, "true", TestData.NativeString, "accept", "true", "true" , "in",null,null,null);
		return efr;
	}
	
	
	/**
	 * query pre firewall rules info
	 * @param edgeName
	 * @return
	 */
	public String queryPreRulesConfigInfo(String edgeName){
		String edgeId = edgeMgr.getEdgeIDbyName(edgeName);
		//GET https://<nsxmgr-ip>/api/4.0/edges/edgeId/firewall/config
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/firewall/config";
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
	 * get specific firewall ruleId
	 * @param edgeName
	 * @param firewallName
	 * @return
	 */
	public String getSpecificRuleId(String edgeName,String firewallRuleName){
 		String poolID = "";
		String queryInfo = this.queryPreRulesConfigInfo(edgeName);
		poolID = XmlFileOp.findNearestBeforeTagValue3(queryInfo, "id", "name", firewallRuleName);
		return poolID;
	}
	
	/**
	 * query specific firewall rule info
	 * @param edgeName
	 * @param firewallName
	 * @return
	 */
	public String querySpecificFirewallInfo(String edgeName,String firewallRuleName){
		String edgeId = edgeMgr.getEdgeIDbyName(edgeName);
		String ruleId = this.getSpecificRuleId(edgeName, firewallRuleName);
		//GET https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/firewall/config/rules/<ruleId>
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/firewall/config/rules/" + ruleId;
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
	
	//send post request to append firewall rule
	private void postFirewallRulls(String edgeName, String xmlContents, HashMap<String,String> header){
		String edgeId = edgeMgr.getEdgeIDbyName(edgeName);
		//POST https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/firewall/config/rules
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/firewall/config/rules";
//System.out.println(url);
//
//		StringReader sr = new StringReader(xmlContents);
//		String functionName = "Edge Firewall Rule";
//		String requestParameters = "";
//		try {
//			headerReq.postData(sr, url, requestParameters, new StringWriter(), functionName, header);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		this.httpReq.postRequestWithHeader(xmlContents, url, header);
	}
	
	/**
	 * append edge firewall rule
	 * @param edgeName
	 * @param efr
	 */
	public void addFirewallRule(String edgeName, EdgeFirewallRule efr){
		HashMap<String,String> header = new HashMap<String,String>();
		header.put("Content-Type", "application/xml");
		
//String xmlFilePath = "RestCallXML\\EdgeFirewallRule.xml";
		String [] xmlKeys = {"name","matchTranslated","direction","action","enabled","loggingEnabled","description"};
		String [] xmlValues = {efr.getName(),efr.getMatchTranslated(),efr.getDirection(),efr.getAction(),efr.getEnabled(),efr.getLoggingEnabled(),efr.getDesc()};
		String xmlContents = XmlFileOp.generateXMLWithFile(xmlKeys, xmlValues, xmlPath_EdgeFirewallRule);
		
		SourceDestination source = efr.getSource();
		SourceDestination detination = efr.getDestination();
		ArrayList<String> appList = efr.getApplicationList();
		
		//if source is not null, generate the source String
		if(source != null){
			String ipAddressString = "";
			String obString = "";
			String vnicString = "";
			if(source.getIpAddress().size() > 0){
				for(int i=0 ; i< source.getIpAddress().size(); i++){
					ipAddressString = ipAddressString + "<ipAddress>" + source.getIpAddress().get(i) + "</ipAddress>";
				}
			}
			if(source.getGroupingObjectId().size() > 0){
				for(int j=0 ; j< source.getGroupingObjectId().size(); j++){
					obString = obString + "<groupingObjectId>" + source.getGroupingObjectId().get(j) + "</groupingObjectId>";
				}
			}
			if(source.getVnicGroupId().size() > 0){
				for(int k=0 ; k< source.getVnicGroupId().size(); k++){
					vnicString = vnicString + "<vnicGroupId>" + source.getVnicGroupId().get(k) + "</vnicGroupId>";
				}
			}
			String sourceString = "<source>" + ipAddressString + obString + vnicString + "</source>";
			xmlContents = XmlFileOp.appendToEnd(sourceString, xmlContents, "firewallRule");
		}
		//if destination is not null, generate the destination String
		if(detination != null){
			String ipAddressString = "";
			String obString = "";
			String vnicString = "";
			if(detination.getIpAddress().size() > 0){
				for(int i=0 ; i< detination.getIpAddress().size(); i++){
					ipAddressString = ipAddressString + "<ipAddress>" + detination.getIpAddress().get(i) + "</ipAddress>";
				}
			}
			if(detination.getGroupingObjectId().size() > 0){
				for(int j=0 ; j< detination.getGroupingObjectId().size(); j++){
					obString = obString + "<groupingObjectId>" + detination.getGroupingObjectId().get(j) + "</groupingObjectId>";
				}
			}
			if(detination.getVnicGroupId().size() > 0){
				for(int k=0 ; k< detination.getVnicGroupId().size(); k++){
					vnicString = vnicString + "<vnicGroupId>" + detination.getVnicGroupId().get(k) + "</vnicGroupId>";
				}
			}
			String destinationString = "<destination>" + ipAddressString + obString + vnicString + "</destination>";
			xmlContents = XmlFileOp.appendToEnd(destinationString, xmlContents, "firewallRule");
		}
		//if application list is not null, generate the application String
		if(appList != null){
			String appString = "";
			if(appList.size() > 0){
				for(int i=0 ; i< appList.size(); i++){
					appString = appString + "<applicationId>" + appList.get(i) + "</applicationId>";
				}
				appString = "<application>" + appString + "</application>";
				xmlContents = XmlFileOp.appendToEnd(appString, xmlContents, "firewallRule");
			}
		}
		
		this.postFirewallRulls(edgeName, xmlContents, header);
	}
	
	/**
	 * check whether firewall rule exist
	 * @param edgeName
	 * @param firewallName
	 * @return
	 */
	public boolean isFirewallRuleExist(String edgeName, String firewallRuleName){
		boolean flag = false;
		String queryInfo = this.queryPreRulesConfigInfo(edgeName);
		if(queryInfo.contains(firewallRuleName)){
			String targetTag = "name";
			flag = XmlFileOp.checkGivenStringEqualTagValue(queryInfo, targetTag, firewallRuleName);
		}
		return flag;
	}
	
	/**
	 * delete specific firewall rule
	 * @param edgeName
	 * @param firewallName
	 */
	public void deleteSpecificRule(String edgeName,String firewallRuleName){
		String edgeId = edgeMgr.getEdgeIDbyName(edgeName);
		String ruleId = this.getSpecificRuleId(edgeName, firewallRuleName);
		//DELETE https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/firewall/config/rules/<ruleId>
		
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/firewall/config/rules/" + ruleId;
System.out.println(url);
		try {
			httpReq.delRequest(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//send put request to modify the specific firewall rule
	private void putFirewallRule(String edgeName, String firewallRuleName,String xmlContents, HashMap<String,String> header){

		String edgeId = edgeMgr.getEdgeIDbyName(edgeName);
		String ruleId = this.getSpecificRuleId(edgeName, firewallRuleName);
		//PUT https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/firewall/config/rules/<ruleId>
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/firewall/config/rules/" + ruleId;
//System.out.println(url);
//
//		StringReader sr = new StringReader(xmlContents);
//		String functionName = "Edge Firewall";
//		String requestParameters = "";
//		try {
//			headerReq.putData2(sr, url, requestParameters, new StringWriter(), functionName, header);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		this.httpReq.putRequestWithHeader(xmlContents, url, header);
	}
	
	/**
	 * modify the specific firewall rule
	 * @param edgeName
	 * @param firewallName
	 * @param efr
	 */
	public void modifyFirewallRule(String edgeName, String firewallRuleName, EdgeFirewallRule efr){
		HashMap<String,String> header = new HashMap<String,String>();
		header.put("Content-Type", "application/xml");
		
//String xmlFilePath = "RestCallXML\\EdgeFirewallRule.xml";
		String [] xmlKeys = {"name","matchTranslated","direction","action","enabled","loggingEnabled","description"};
		String [] xmlValues = {efr.getName(),efr.getMatchTranslated(),efr.getDirection(),efr.getAction(),efr.getEnabled(),efr.getLoggingEnabled(),efr.getDesc()};
		String xmlContents = XmlFileOp.generateXMLWithFile(xmlKeys, xmlValues, xmlPath_EdgeFirewallRule);
		xmlContents = xmlContents.replace("<firewallRules>", "");
		xmlContents = xmlContents.replace("</firewallRules>", "");
		SourceDestination source = efr.getSource();
		SourceDestination detination = efr.getDestination();
		
		//if source is not null, generate the source String
		if(source != null){
			String ipAddressString = "";
			String obString = "";
			String vnicString = "";
			if(source.getIpAddress().size() > 0){
				for(int i=0 ; i< source.getIpAddress().size(); i++){
					ipAddressString = ipAddressString + "<ipAddress>" + source.getIpAddress().get(i) + "</ipAddress>";
				}
			}
			if(source.getGroupingObjectId().size() > 0){
				for(int j=0 ; j< source.getGroupingObjectId().size(); j++){
					obString = obString + "<groupingObjectId>" + source.getGroupingObjectId().get(j) + "</groupingObjectId>";
				}
			}
			if(source.getVnicGroupId().size() > 0){
				for(int k=0 ; k< source.getVnicGroupId().size(); k++){
					vnicString = vnicString + "<vnicGroupId>" + source.getVnicGroupId().get(k) + "</vnicGroupId>";
				}
			}
			String sourceString = "<source>" + ipAddressString + obString + vnicString + "</source>";
			xmlContents = XmlFileOp.appendToEnd(sourceString, xmlContents, "firewallRule");
		}
		//if destination is not null, generate the destination String
		if(detination != null){
			String ipAddressString = "";
			String obString = "";
			String vnicString = "";
			if(detination.getIpAddress().size() > 0){
				for(int i=0 ; i< detination.getIpAddress().size(); i++){
					ipAddressString = ipAddressString + "<ipAddress>" + detination.getIpAddress().get(i) + "</ipAddress>";
				}
			}
			if(detination.getGroupingObjectId().size() > 0){
				for(int j=0 ; j< detination.getGroupingObjectId().size(); j++){
					obString = obString + "<groupingObjectId>" + detination.getGroupingObjectId().get(j) + "</groupingObjectId>";
				}
			}
			if(detination.getVnicGroupId().size() > 0){
				for(int k=0 ; k< detination.getVnicGroupId().size(); k++){
					vnicString = vnicString + "<vnicGroupId>" + detination.getVnicGroupId().get(k) + "</vnicGroupId>";
				}
			}
			String destinationString = "<destination>" + ipAddressString + obString + vnicString + "</destination>";
			xmlContents = XmlFileOp.appendToEnd(destinationString, xmlContents, "firewallRule");
		}

		this.putFirewallRule(edgeName, firewallRuleName, xmlContents, header);
	}
	
	/**
	 * check whether firewallrule enabled
	 * @param edgeName
	 * @param firewallRuleName
	 * @return
	 */
	public boolean isFirewallEnabled(String edgeName,String firewallRuleName){
		boolean flag = false;
		String queryInfo = this.querySpecificFirewallInfo(edgeName, firewallRuleName);
		String nodeHierarchic_status = "//firewallRule/enabled";
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
	 * setup default FirewallRule environment
	 */
	public void setDefaultFirewallRuleEnv(){
		if(!this.isFirewallRuleExist(edgeName, edgeFirewallRuleName)){
			EdgeFirewallRule edgeFirewallRule = this.getDefaultFirewallInstance();
			this.addFirewallRule(edgeName, edgeFirewallRule);
		}
	}
	
	/**
	 * clean up default firewall rule environment
	 */
	public void cleanDefaultFirewallRuleEnv(){
		if(this.isFirewallRuleExist(edgeName, edgeFirewallRuleName)){
			this.deleteSpecificRule(edgeName, edgeFirewallRuleName);
		}
	}
	
}
