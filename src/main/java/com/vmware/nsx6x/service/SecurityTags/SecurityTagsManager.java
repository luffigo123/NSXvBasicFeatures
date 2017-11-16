package com.vmware.nsx6x.service.SecurityTags;

import com.vmware.nsx6x.utils.Config;
import com.vmware.nsx6x.utils.DefaultEnvironment;
import com.vmware.nsx6x.utils.HttpReq;
import com.vmware.nsx6x.utils.TestData;
import com.vmware.nsx6x.utils.XmlFileOp;


public class SecurityTagsManager {
	public HttpReq httpReq;
	private String vsmIP;
	
	private String filepath = Config.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	private String xmlPath = filepath + "/RestCallXML/SecurityTag.xml";
	
	public String securityTagName = "SecurityTag001" + TestData.NativeString;
	
	public SecurityTagsManager(){
		httpReq = HttpReq.getInstance();
		vsmIP = DefaultEnvironment.VSMIP;
	}
	
	
 	/**
 	 * Add Security tag
 	 * @param xmlContents
 	 */
 	public void addSecuritytag(String xmlContents) 
 	{
 		//POST https://<nsxmgr-ip>/api/2.0/services/securitytags/tag
 		String url = "https://" + vsmIP + "/api/2.0/services/securitytags/tag";
		System.out.println(url);

		try {
			httpReq.postRequest(xmlContents,url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

 	}
 		
 	/**
 	 * Query All Security tags info
 	 * @return
 	 */
 	public String queryAllSecurityTagsInfo()
 	{
 		//GET https://<nsxmgr-ip>/api/2.0/services/securitytags/tag
 		String url = "https://" + vsmIP + "/api/2.0/services/securitytags/tag";
		System.out.println(url);
		String result = null;
		try {
			result = httpReq.getRequest(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
 	}
 	
 	/**
 	 * Get Security Tag ID
 	 * @param securityTagName
 	 * @return
 	 */
 	public String getSpecificSecurityTagId(String securityTagName)
 	{
 		String info = queryAllSecurityTagsInfo();
 		String securityId = XmlFileOp.findNearestBeforeTagValue3(info, "objectId", "name", securityTagName);
 		return securityId;	
 	}
 	
 	/**
 	 * Apply Security Tag to VM
 	 * @param TagIdentifierString
 	 * @param vmMoid

 	 */
 	public void applyTagToVM(String TagIdentifierString, String vmMoid)
 	{
 		//PUT https://<nsxmgr-ip>/api/2.0/services/securitytags/tag/{TagIdentifierString}/vm/{vmMoid}
 		String url = "https://" + vsmIP + "/api/2.0/services/securitytags/tag/" + TagIdentifierString + "/vm/" + vmMoid;
		System.out.println(url);
//		StringReader sr = new StringReader("");
//		try {
//			httpReq.putRequest2(url, sr);
//		} catch (VSMOperationFailedException | VSMNotReachableException
//				| VSMRequestTimedOutException | VSMUnauthorizedAccessException
//				| VSMAccessForbiddenException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		this.httpReq.putRequest("", url);
 	}
 	
 	
 	/**
 	 * Delete specific Security Tag ID
 	 * @param securityTagName
 	 * @throws Exception 
 	 */
 	public void deleteSecurityTag(String securityTagName) 
 	{
 		String tagIdentifierString = getSpecificSecurityTagId(securityTagName);
 		//DELETE https://<nsxmgr-ip>/api/2.0/services/securitytags/tag/{TagIdentifierString}
 		String url = "https://" + vsmIP + "/api/2.0/services/securitytags/tag/" + tagIdentifierString;
		System.out.println(url);
		try {
			httpReq.delRequest(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	}
 	
	/**
	 * Check target Security Tag whether exist
	 * @param serviceName
	 * @return
	 * @throws Exception 
	 */
	public boolean isSecurityTagExist(String securityTagName) {
		boolean flag = false;	
		String queryServicesInfo = this.queryAllSecurityTagsInfo();
		String targetTag = "name";
		flag = XmlFileOp.checkGivenStringEqualTagValue(queryServicesInfo, targetTag, securityTagName);
		return flag;
	}
 	
	/**
	 * Detach the security tag to vm
	 * @param securityTagName
	 * @param vmMoid
	 * @throws Exception
	 */
	public void detachSecurityTagToVM(String securityTagName, String vmMoid) {
		String securityTagID = getSpecificSecurityTagId(securityTagName);
		//DELETE https://<nsxmgr-ip>/api/2.0/services/securitytags/tag/{TagIdentifierString}/vm/{vmMoid}
		String endPoint = "https://" + vsmIP + "/api/2.0/services/securitytags/tag/" + securityTagID + "/vm/" + vmMoid;
		System.out.println(endPoint);
		try {
			httpReq.delRequest(endPoint);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Add Security Tag
	 * @param name
	 * @param description
	 * @throws Exception 
	 */
	public void addSecurityTag(String name,String description)
	{
		//array of xml keys
		String [] xmlKeys = {"name","description"};								
		//array of xml values
		String [] xmlValues = {name,description};								
		//CpnfigSSO.xml path
//		String xmlPath = "RestCallXML\\SecurityTag.xml";
										
		//Get the final contens of xml
		String xmlContents = XmlFileOp.generateXMLWithFile(xmlKeys, xmlValues, xmlPath);
		addSecuritytag(xmlContents);
	}
	
	/**
	 * apply the security tag to the vm
	 * @param securityTagName
	 * @param vmMoid
	 * @throws Exception 
	 */
	public void assignSecurityTagToVM(String securityTagName, String vmMoid)
	{
		String tagId = null;
		tagId = getSpecificSecurityTagId(securityTagName);
		if(tagId != null && vmMoid != null){
			applyTagToVM(tagId, vmMoid);
		}else {
			System.out.println("The ID is null, pleas ensure it exist!");	
		}		
	}	
	
	
	/**
	 * Set up default Security Tag environment
	 * @throws Exception
	 */
	public void setDefaultSecurityTagEnv(){
		if(!isSecurityTagExist(securityTagName)){
			addSecurityTag(securityTagName, securityTagName);
		}
	}
	
	/**
	 * clean up default Security Tag environment
	 * @throws Exception
	 */
	public void cleanDefaultSecurityTagEnv(){
		if(isSecurityTagExist(securityTagName)){
			deleteSecurityTag(securityTagName);
		}
	}

}
