package com.vmware.nsx6x.service.ServiceComposer;

import com.vmware.nsx6x.model.ServiceComposer.SecurityPolicy;
import com.vmware.nsx6x.utils.DefaultEnvironment;
import com.vmware.nsx6x.utils.HttpReq;
import com.vmware.nsx6x.utils.JAXBUtils;
import com.vmware.nsx6x.utils.JAXBUtils.CollectionWrapper;
import com.vmware.nsx6x.utils.XmlFileOp;

public class ServiceComposerOperationV2 {
	private HttpReq httpReq;
	private String vsmIP;
	public JAXBUtils jAXBUtils;
	
	public ServiceComposerOperationV2(){
		httpReq = HttpReq.getInstance();
		vsmIP = DefaultEnvironment.VSMIP;
	}
	
	/**
	 * 
	 * @param xmlContents
	 */
	private void postSecurityPolicy(String xmlContents){
		//POST https://<nsxmgr-ip>/api/2.0/services/policy/securitypolicy
		String url = "https://" + vsmIP + "/api/2.0/services/policy/securitypolicy";
		System.out.println(url);
		httpReq.postRequest(xmlContents,url);
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
	 * 
	 * @param securityPolicyId
	 * @param xmlContents
	 */
	private void putSecurityPolicy(String securityPolicyName, String xmlContents){
		String securityPolicyId = this.getSecurityPolicyId(securityPolicyName);
		//https://<nsxmgr-ip>/api/2.0/services/policy/securitypolicy/ObjectID
		String url = "https://" + vsmIP + "/api/2.0/services/policy/securitypolicy/" + securityPolicyId;
		System.out.println(url);
		try {
			httpReq.putRequest(xmlContents,url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Delete the Security Policy with the specified id
	 * @param rfcm
	 * @param vsmInfo
	 * @param securityGroupName
	 * @return true if success to delete
	 */
	private void delSecurityPolicy (String SecurityPolicyId) {
//		String ep = "https://" + vsmIP + "/api/2.0/services/policy/securitypolicy/" + SecurityPolicyId + "?force=false";
		String epForce = "https://" + vsmIP + "/api/2.0/services/policy/securitypolicy/" + SecurityPolicyId + "?force=true";
		httpReq.delRequest(epForce);

	}
	
	/**
	 * Query the specified Security Policy by Name
	 * @param securityPolicyName
	 * @return the Security Policy details in xml format
	 */
	public String querySpecificSecurityPolicy (String securityPolicyName) {
		String securityPolicyId = this.getSecurityPolicyId(securityPolicyName);
		//GET /2.0/services/policy/securitypolicy/{ID}
		String url = "https://" + vsmIP + "/api/2.0/services/policy/securitypolicy/" + securityPolicyId;
		return httpReq.getRequest(url);
	}
	
	/**
	 * 
	 * @param SecurityPolicyName
	 * @return
	 */
	public String getSecurityPolicyId(String securityPolicyName) {
		String id = "";
		try {
			String allPoliciesStr = queryAllSecurityPolicies();
			id = XmlFileOp.findNearestBeforeTagValue3(allPoliciesStr, "objectId", "name", securityPolicyName);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return id;
	}
	
	/**
	 * 
	 * @param securityPolicyName
	 * @return
	 */
	public SecurityPolicy getSpecificSecurityPolicy(String securityPolicyName){
		String xmlContent = this.querySpecificSecurityPolicy(securityPolicyName);
		SecurityPolicy securityPolicy= jAXBUtils.xmlStringToObj(xmlContent);
		return securityPolicy;
	}
	
	/**
	 * check if the security Policy Exist
	 * @param securityPolicyName
	 * @return
	 * @throws Exception
	 */
	public boolean isExist(String securityPolicyName)
	{
		boolean flag = false;
		
		String queryIPsetInfo = this.queryAllSecurityPolicies();
		String targetTag = "name";
		flag = XmlFileOp.checkGivenStringEqualTagValue(queryIPsetInfo, targetTag, securityPolicyName);
		return flag;	
	}
	
	/**
	 * 
	 * @param servicePolic
	 */
	public void addSecurityPolicy(SecurityPolicy servicePolic){
		jAXBUtils = new JAXBUtils(SecurityPolicy.class, CollectionWrapper.class);
		String xmlContents = jAXBUtils.objToXml(servicePolic, "UTF-8");
		this.postSecurityPolicy(xmlContents);
	}
	
	public void deleteSecurityPolicy (String SecurityPolicyName){
		String SecurityPolicyId = this.getSecurityPolicyId(SecurityPolicyName);
		this.delSecurityPolicy(SecurityPolicyId);
	}
	
	
}
