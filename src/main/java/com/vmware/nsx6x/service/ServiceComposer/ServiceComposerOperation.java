package com.vmware.nsx6x.service.ServiceComposer;


import com.vmware.nsx6x.utils.Config;
import com.vmware.nsx6x.utils.DefaultEnvironment;
import com.vmware.nsx6x.utils.Dom4jXmlUtils;
import com.vmware.nsx6x.utils.HttpReq;
import com.vmware.nsx6x.utils.TestConstants;
import com.vmware.nsx6x.utils.TestData;
import com.vmware.nsx6x.utils.XmlFileOp;

public class ServiceComposerOperation {

	private HttpReq httpReq;
	private String vsmIP;
	
	private String filepath = Config.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	private String xmlFilePath_SecurityPolicy = filepath + "/RestCallXML/SecurityPolicy.xml";
	private String xmlFilePath_ChildSecurityPolicy = filepath + "/RestCallXML/CreateChildSecurityPolicy.xml";
	

	public String securityPolicyName = "SecurityPolicy001" + TestData.NativeString;
	public String securityGroupsName = "SecurityGroup001" + TestData.NativeString;
	public String securityPolicyWeight = "7300";
	public String childSecurityPolicyWeight = "8300";
	
	
	public ServiceComposerOperation(){
		httpReq = HttpReq.getInstance();
		vsmIP = DefaultEnvironment.VSMIP;
	}

	
	//send post request of add Security Policy
	private void postSecurityPolicy(String xmlContents){
		//POST https://<nsxmgr-ip>/api/2.0/services/policy/securitypolicy
		String url = "https://" + vsmIP + "/api/2.0/services/policy/securitypolicy";
		System.out.println(url);
		httpReq.postRequest(xmlContents,url);
	}
	
	/**
	 * create the Security Policy
	 * @param securityPolicyName
	 * @param securityPolicyDesc
	 */
	public void createSecurityPolicy (String securityPolicyName, String securityPolicyDesc){
		
//		String xmlFilePath = "RestCallXML\\SecurityPolicy.xml";
		String xmlContents = "";
		String nodeHierarchic1 = "//securityPolicy/name";
		String nodeHierarchic2 = "//securityPolicy/description";
		xmlContents = XmlFileOp.readXMLContens(xmlFilePath_SecurityPolicy);
		xmlContents = Dom4jXmlUtils.modifySpecificNodeValue(xmlContents, nodeHierarchic1, 1, securityPolicyName);
		xmlContents = Dom4jXmlUtils.modifySpecificNodeValue(xmlContents, nodeHierarchic2, 1, securityPolicyDesc);
		this.postSecurityPolicy(xmlContents);

	}
	
	/**
	 * Query All the Security Policies and return the XML string
	 * @return the XML string
	 */
	public String queryAllSecurityPolicies (){
		String returnStr = "";
		String ep = "https://" + vsmIP + "/api/2.0/services/policy/securitypolicy/all";
		try {
			returnStr = httpReq.getRequest(ep);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(returnStr!=null) {
			System.out.println("Success to Query the Security Policies");
		}
		else
			System.out.println("Fail to Query the Security Policies");
		return returnStr;
	}
	
	/**
	 * Find the specified Security Policy's ID by querying all the Security Policies 
	 * @param SPOName
	 * @return the SPO id
	 * @throws Exception
	 */
	public String getSecurityPolicyId(String SPOName) {
		String id = "";
		try {
			String allPoliciesStr = queryAllSecurityPolicies();
			//id = XmlFileOp.findNearestBeforeTagValue(allPoliciesStr, "objectId", "name", SPOName);
			id = XmlFileOp.findNearestBeforeTagValue3(allPoliciesStr, "objectId", "name", SPOName);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return id;
	}
		
	
	/**
	 * Query the specified Security Policy by ID
	 * @param SecurityPolicyId
	 * @return the Security Policy details in xml format
	 */
	public String querySecurityPolicyDetails (String securityPolicyId) {
		String details = "";
		String ep = "https://" + vsmIP + "/api/2.0/services/policy/securitypolicy/" + securityPolicyId;
		try {
			details = httpReq.getRequest(ep);
		}catch (Exception e) {
			System.out.println(e.toString());
		}
		return details;
	}
	
	/**
	 * Check if the Security Policy Exist
	 * @param SecurityPolicyId
	 * @return true if exist
	 * @throws Exception
	 */
	public Boolean chkSecurityPolicyExist (String SecurityPolicyId) {
		Boolean isExist = false;
		if(querySecurityPolicyDetails(SecurityPolicyId)!=null) {
			isExist = true;
		}
		return isExist;
	}
	
	/**
	 * check if the security Policy Exist
	 * @param securityPolicyName
	 * @return
	 * @throws Exception
	 */
	public boolean isSecurityPolicyExist(String securityPolicyName)
	{
		boolean flag = false;
		
		String queryIPsetInfo = this.queryAllSecurityPolicies();
		String targetTag = "name";
		flag = XmlFileOp.checkGivenStringEqualTagValue(queryIPsetInfo, targetTag, securityPolicyName);
		return flag;	
	}
	
	
	/**
	 * Edit the Security Policy with new name, description and weight
	 * @param SecurityPolicyId
	 * @throws Exception
	 */
	public Boolean modifySecurityPolicy (String securityPolicyName, String newName, String newDescription, String newWeight) {
		Boolean isPassed = false;
		String SecurityPolicyId = this.getSecurityPolicyId(securityPolicyName);
		String SPDetails = querySecurityPolicyDetails(SecurityPolicyId);
		String xmlKeys[] = {"securityPolicy^name#1@"+newName, "securityPolicy^description#1@"+newDescription, "securityPolicy^precedence#1@"+newWeight};
		String ep = "https://" + vsmIP + "/api/2.0/services/policy/securitypolicy/" + SecurityPolicyId;
		String SPNewReqBody = XmlFileOp.fillValuesIntoXmlContents(xmlKeys, SPDetails);
		try {
			httpReq.putRequest(SPNewReqBody,ep);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String id = getSecurityPolicyId(newName);
		if (id!=null) {
			isPassed = true;
		}
		return isPassed;
	}
	
	//send put request to Edit a Security Policy
	private void putSecurityPolicy(String securityPolicyId, String xmlContents){
		//https://<nsxmgr-ip>/api/2.0/services/policy/securitypolicy/ObjectID
		String url = "https://" + vsmIP + "/api/2.0/services/policy/securitypolicy/" + securityPolicyId;
System.out.println(url);
		try {
			httpReq.putRequest(xmlContents,url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * edit security policy
	 * @param orgSecurityPolicyName
	 * @param newSecurityPolicyName
	 * @param newSPDesc
	 */
	public void editSecurityPolicy(String orgSecurityPolicyName, String newSecurityPolicyName, String newSPDesc, String newSPWeight){
		String securityPolicyId = this.getSecurityPolicyId(orgSecurityPolicyName);
		String xmlContents = this.querySecurityPolicyDetails(securityPolicyId);
		
		String nodeHierarchic_Name = "//securityPolicy/name";
		String nodeHierarchic_Desc = "//securityPolicy/description";
		String nodeHierarchic_Weight = "//securityPolicy/precedence";
		xmlContents = Dom4jXmlUtils.modifySpecificNodeValue(xmlContents, nodeHierarchic_Name, 1, newSecurityPolicyName);
		 xmlContents = Dom4jXmlUtils.modifySpecificNodeValue(xmlContents, nodeHierarchic_Desc, 1, newSPDesc);
		 xmlContents = Dom4jXmlUtils.modifySpecificNodeValue(xmlContents, nodeHierarchic_Weight, 1, newSPWeight);
		
		this.putSecurityPolicy(securityPolicyId, xmlContents);
	}
	
	/**
	 * edit security policy firewall rule
	 * @param orgSecurityPolicyName
	 * @param newSecurityPolicyName
	 * @param newSPDesc
	 */
	public void editSecurityPolicyFirewall(String orgSecurityPolicyName, String newSPFirewallName){
		String securityPolicyId = this.getSecurityPolicyId(orgSecurityPolicyName);
		String xmlContents = this.querySecurityPolicyDetails(securityPolicyId);
		
		String nodeHierarchic_Firewallname= "//securityPolicy/actionsByCategory/action/name";
		xmlContents = Dom4jXmlUtils.modifySpecificNodeValue(xmlContents, nodeHierarchic_Firewallname, 1, newSPFirewallName);
		
		this.putSecurityPolicy(securityPolicyId, xmlContents);
	}
	
	/**
	 * Check if the Security Policy setting correct as expeted, correct value1 in tag1 in the sPoDetails, valu2 in tag2 in the sPoDetails  
	 * @param sPoDetails
	 * @param tag1
	 * @param value1
	 * @param tag2
	 * @param value2
	 * @return true if correct
	 */
	public Boolean chkSecurityPolicyCorrect (String sPoDetails, String tag1, String value1, String tag2, String value2) {
		Boolean isCorrect;
		isCorrect = XmlFileOp.checkGivenStringEqualTagValue(sPoDetails, tag1, value1) && XmlFileOp.checkGivenStringEqualTagValue(sPoDetails, tag2, value2);
		return isCorrect;
	}
	
	/**
	 * Delete the Security Policy with the specified id
	 * @param rfcm
	 * @param vsmInfo
	 * @param securityGroupName
	 * @return true if success to delete
	 */
	public void delSecurityPolicy (String SecurityPolicyId) {
//		Boolean isDel = false;
//		String returnStr = "";
//		String ep = "https://" + vsmIP + "/api/2.0/services/policy/securitypolicy/" + SecurityPolicyId + "?force=false";
		String epForce = "https://" + vsmIP + "/api/2.0/services/policy/securitypolicy/" + SecurityPolicyId + "?force=true";

//		httpReq.delRequest(ep);
		httpReq.delRequest(epForce);

	}
	
	public void delSecurityPolicyByName (String SecurityPolicyName){
//		Boolean isDel = false;
		String SecurityPolicyId = this.getSecurityPolicyId(SecurityPolicyName);
//		String returnStr = "";
//		String ep = "https://" + vsmIP + "/api/2.0/services/policy/securitypolicy/" + SecurityPolicyId + "?force=false";
		String epForce = "https://" + vsmIP + "/api/2.0/services/policy/securitypolicy/" + SecurityPolicyId + "?force=true";
		httpReq.delRequest(epForce);
	}
	
	/**
	 * Delete the Network Introspection Service from the Security Policy
	 * @return true if success
	 */
	public void delNetIntroSrvInSPO (String SecurityPolicyId){
		String ep = "https://" + vsmIP + "/api/2.0/services/policy/securitypolicy/" + SecurityPolicyId;
		String sPDetails = "";
		try {
			sPDetails = httpReq.getRequest(ep);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String newSPDetails = XmlFileOp.removeSpecialBlockInXMLString("actionsByCategory", "category", "traffic_steering", sPDetails);
		System.out.println(newSPDetails);
		try {
			httpReq.putRequest(newSPDetails,ep);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	
	/**
	 * create child Securit Policy
	 * @param parentSecurityPolicyName
	 * @param childSecurityPolicyName
	 * @param childSecurityPolicyDesc
	 * @param childSecurityPolicyWeight
	 */
	public void createChildSecurityPolicy(String parentSecurityPolicyName,String childSecurityPolicyName, String childSecurityPolicyDesc,String childSecurityPolicyWeight){
		String parentSecurityPolicyID = this.getSecurityPolicyId(parentSecurityPolicyName);
//		String xmlFilePath = "RestCallXML\\CreateChildSecurityPolicy.xml";
		String xmlContents = "";
		String nodeHierarchic1 = "//securityPolicy/name";
		String nodeHierarchic2 = "//securityPolicy/description";
		String nodeHierarchic3 = "//securityPolicy/precedence";
		String nodeHierarchic4 = "//securityPolicy/parent/objectId";
		xmlContents = XmlFileOp.readXMLContens(xmlFilePath_ChildSecurityPolicy);
		 xmlContents = Dom4jXmlUtils.modifySpecificNodeValue(xmlContents, nodeHierarchic1, 1, childSecurityPolicyName);
		 xmlContents = Dom4jXmlUtils.modifySpecificNodeValue(xmlContents, nodeHierarchic2, 1, childSecurityPolicyDesc);
		 xmlContents = Dom4jXmlUtils.modifySpecificNodeValue(xmlContents, nodeHierarchic3, 1, childSecurityPolicyWeight);
		 xmlContents = Dom4jXmlUtils.modifySpecificNodeValue(xmlContents, nodeHierarchic4, 1, parentSecurityPolicyID);
		this.postSecurityPolicy(xmlContents);
	}
	
	/**
	 * Update the Service of Firewall from HTTP/HTTPS to ANY, logged to false, and directory inbound
	 * @return
	 */
	public void editSecurityPolicyFirewallRule (String securityPolicyId) {
		String spDetails = querySecurityPolicyDetails(securityPolicyId);
		String noSrvXml = XmlFileOp.removeBlockInXMLString("applications", spDetails);
		String [] newSettings = {"actionsByCategory^logged#1@false", "actionsByCategory^direction#1@inbound"};
		String ep = "https://" + vsmIP + "/api/2.0/services/policy/securitypolicy/" + securityPolicyId;
		String reqBody = XmlFileOp.fillValuesIntoXmlContents(newSettings, noSrvXml);
		//XmlFileOp.printIndentedXMLString(reqBody);
		try {
			httpReq.putRequest(reqBody,ep);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //return null or exception for pass or fail
	}
	
	
	/**
	 * Export the Security Policy as XML file "EISPSecurityPolicyName"
	 * @param rfcm
	 * @param vsmInfo
	 * @return true if success
	 */
	public Boolean exportSecurityPolicyAsXmlFile (String spId, String prefix, String fileName) {
		Boolean isPassed = false;
		String results = "";
		//String ep = "https://" + vsmIP + "/api/2.0/services/policy/securitypolicy/hierarchy?policyIds=" + spId + "&prefix=" + prefix;
		String ep = "https://" + vsmIP + "/api/2.0/services/policy/securitypolicy/hierarchy?prefix=" + prefix;
		try {
			results = httpReq.getRequest(ep);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("RESULTS: " + results);
		if(!results.isEmpty()) {
			XmlFileOp.writeAsXmlFile(fileName, results);
			isPassed = true;
		}
		return isPassed;
	}
	
	/**
	 * Import the Security Policy from the previous saved configuration of case Export Security Policy
	 * @param rfcm
	 * @param vsmInfo
	 * @return true if success
	 */
	public void importSecurityPolicy (String fileName, String suffix) {
		String ep = "https://" + vsmIP + "/api/2.0/services/policy/securitypolicy/hierarchy?suffix=" + suffix;
		String fulPath = TestConstants.PATH_RestCallXML + "\\log\\" + fileName;
		String reqBody = XmlFileOp.readXMLContens(fulPath);//(xmlFilePath).generateXMLStringCommon(fulPath);
		httpReq.postRequest(reqBody,ep);

	}
	

	
	/**
	 * Apply the Security Policy to the  Security Group
	 * @return true if success
	 */
	public void applySecurityPolicy2SecurityGroup (String spoId, String sgoId, String sgoName, String sgoDesc) {
		String sgBindingStr = XmlFileOp.generateXMLStringCommon("ApplySecurityPolicyToSecurityGroup.xml",
																"objectId", sgoId,
																"name", sgoName,
																"description", sgoDesc);
		String url = "https://" + vsmIP + "/api/2.0/services/policy/securitypolicy/" + spoId;
		String spDetails = querySecurityPolicyDetails(spoId);
		String reqBody = XmlFileOp.insertStringBeforeStartTag(sgBindingStr, spDetails, "actionsByCategory");
		try {
			httpReq.putRequest(reqBody,url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			httpReq.getRequest(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Synchronize Service Composer firewall with Distributed Firewall
	 * @throws Exception
	 */
	public void synchronizeServiceComposerFirewall(){
		String url = "https://" + vsmIP + "/api/2.0/services/policy/serviceprovider/firewall";
		try {
			httpReq.putRequest("SyncServiceComposerFirewall.xml",url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * set default Security Policy ENV
	 * @throws Exception
	 */
	public void setDefaultSecurityPolicyEnv()
	{
		String SPODesc = TestConstants.defaultDesc;
		
		if(!this.isSecurityPolicyExist(securityPolicyName)){
			this.createSecurityPolicy(securityPolicyName, SPODesc);
		}	
	}
	
	/**
	 * Clean up default Security Policy ENV
	 * @throws Exception
	 */
	public void cleanDefaultSecurityPolicyENV()
	{
		String SecurityPolicyId = this.getSecurityPolicyId(securityPolicyName);
		if(this.isSecurityPolicyExist(securityPolicyName)){
			this.delSecurityPolicy(SecurityPolicyId);
		}
	}

	/**
	 * Apply the Security Policy to Security group
	 * @return true if successfully applied
	 * @throws Exception
	 */
	public Boolean applySP2SG(String spoId,String sgoId){
		Boolean isPassed;
		String baseSPODesc = TestConstants.defaultDesc;

		try {
			this.applySecurityPolicy2SecurityGroup(spoId, sgoId, securityPolicyName, baseSPODesc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		isPassed = XmlFileOp.checkGivenStringEqualTagValue(this.querySecurityPolicyDetails(spoId), "objectId",sgoId);
		return isPassed;
	}
	
	/**
	 * Synchronize Service Composer Firewall
	 * @throws Exception
	 */
	public boolean syncServiceCompserFirewall() {
		boolean isPassed = false;
		try {
			this.synchronizeServiceComposerFirewall();
			isPassed = true; //The put operation will be success if no exceptions 
		}catch (Exception e) {
			e.printStackTrace();
		}
		return isPassed;
	}
}
