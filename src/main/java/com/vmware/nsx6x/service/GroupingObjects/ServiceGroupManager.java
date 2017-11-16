package com.vmware.nsx6x.service.GroupingObjects;

import com.vmware.nsx6x.model.GroupingObjects.ServiceGroup;
import com.vmware.nsx6x.utils.Config;
import com.vmware.nsx6x.utils.DefaultEnvironment;
import com.vmware.nsx6x.utils.HttpReq;
import com.vmware.nsx6x.utils.TestData;
import com.vmware.nsx6x.utils.XmlFileOp;

public class ServiceGroupManager {
	
	private HttpReq httpReq;
	private String vsmIP;
	public String serviceGroupName = "ServiceGroup001" + TestData.NativeString;
	public String defaultDesc = "DefaultDes" + TestData.NativeString;
	
	private String filepath = Config.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	private String xmlPath = filepath + "/RestCallXML/ServiceGroup.xml";
	
	public ServiceGroupManager() {
		httpReq = HttpReq.getInstance();
		vsmIP = DefaultEnvironment.VSMIP;
	}

	/**
	 * query all service groups info
	 * @return
	 */
	public String queryAllServiceGroupsInfo()
	{
		//GET https://<nsxmgr-ip>/api/2.0/services/applicationgroup/<scope-moref>
		String url = "https://" + vsmIP + "/api/2.0/services/applicationgroup/scope/globalroot-0" ;
		System.out.println(url);
		String result = "";
		try {
			result = httpReq.getRequest(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Add Service Group
	 * @param xmlContents 
	 */
	public void addServiceGroup(String xmlContents)
	{
		//POST https://<nsxmgr-ip>/api/2.0/services/applicationgroup/<scope-moref>
		String url = "https://" + vsmIP + "/api/2.0/services/applicationgroup/globalroot-0";
		System.out.println(url);
		httpReq.postRequest(xmlContents,url);

	}
	
	/**
	 * modify specific ServiceGroup
	 * @param applicationgroup_id
	 * @param xmlContents
	 */
	public void modifyServiceGroup(String serviceGroupName, String xmlContents)
	{	
		String applicationgroup_id = getSpecificServiceGroupID(serviceGroupName);
		//PUT https://<nsxmgr-ip>/api/2.0/services/applicationgroup/<applicationgroup-id>
		String url = "https://" + vsmIP + "/api/2.0/services/applicationgroup/" + applicationgroup_id;
		httpReq.putRequest(xmlContents,url);

	}
	
	public void editServiceGroup(String orgName, String newName, String newDesc)
	{
		String  member_objectId = "application-315";
		String inheritanceAllowed = "true";
		ServiceGroup sGroup = new ServiceGroup(newName, newDesc, inheritanceAllowed, member_objectId);
		
		//array of xml keys
		String [] xmlKeys = {"name","inheritanceAllowed","description","member_objectId"};
						
		//array of xml values
		String [] xmlValues = {sGroup.getName(),sGroup.getInheritanceAllowed(),sGroup.getDesc(),sGroup.getMember_objectId()};
						
		//CpnfigSSO.xml path
		//String xmlPath = "RestCallXML\\ServiceGroup.xml";
						
		//Get the final contens of xml
		String serviceGroupID = getSpecificServiceGroupID(orgName);
		String xmlContents = querySpecificServiceGroupInfo(serviceGroupID);
		String resultContents = XmlFileOp.generateXMLWithContents(xmlKeys, xmlValues, xmlContents);

		modifyServiceGroup(orgName, resultContents);
	}
	
	/**
	 * get specific service group id
	 * @param specificServiceGroupName
	 * @return
	 */
	public String getSpecificServiceGroupID(String specificServiceGroupName)
	{
		String serviceGroupsInfo = queryAllServiceGroupsInfo();
		String serviceGroupID = XmlFileOp.findNearestBeforeTagValue3(serviceGroupsInfo, "objectId", "name", specificServiceGroupName);
		return serviceGroupID;
	}
	
	/**
	 * query specific Service Group info
	 * @param serviceGroupID
	 * @return
	 */
	public String querySpecificServiceGroupInfo(String serviceGroupID){
		//GET https://<nsxmgr-ip>/api/2.0/services/applicationgroup/<applicationgroup-id>
		String url = "https://" + vsmIP + "/api/2.0/services/applicationgroup/" +  serviceGroupID;
		System.out.println(url);	
		String result = "";
		try {
			result = httpReq.getRequest(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Delete specific service group
	 * @param specificServiceGroupName 
	 */
	public void deleteSpecificServiceGroup(String specificServiceGroupName)
	{
		String applicationgroup_id = getSpecificServiceGroupID(specificServiceGroupName);
		//DELETE https://<nsxmgr-ip>/api/2.0/services/applicationgroup/<applicationgroup-id>?force=<true|false>
		String url = "https://" + vsmIP + "/api/2.0/services/applicationgroup/" + applicationgroup_id + "?force=false";
		httpReq.delRequest(url);

	}
	
	/**
	 * Check the target ServiceGroup whether exist
	 * @param serviceGroupName
	 * @return
	 */
	public boolean isServiceGroupExist(String serviceGroupName)
	{
		boolean flag = false;
		
		String queryServiceGroupInfo = this.queryAllServiceGroupsInfo();
		String targetTag = "name";
		flag = XmlFileOp.checkGivenStringEqualTagValue(queryServiceGroupInfo, targetTag, serviceGroupName);
		return flag;
	}
		
	/**
	 * Add Service Group
	 * @param name
	 * @param value
	 * @param desc
	 * @param member_objectId
	 */
 	public void addServiceGroup(String name, String desc)
	{
		String  member_objectId = "application-278";
		String inheritanceAllowed = "true";
		ServiceGroup sGroup = new ServiceGroup(name, desc, inheritanceAllowed, member_objectId);
		
		//array of xml keys
		String [] xmlKeys = {"name","inheritanceAllowed","description","member_objectId"};
						
		//array of xml values
		String [] xmlValues = {sGroup.getName(),sGroup.getInheritanceAllowed(),sGroup.getDesc(),sGroup.getMember_objectId()};
						
		//CpnfigSSO.xml path
//		String xmlPath = "RestCallXML\\ServiceGroup.xml";
						
		//Get the final contens of xml
		String xmlContents = XmlFileOp.generateXMLWithFile(xmlKeys, xmlValues, xmlPath);
	
		addServiceGroup(xmlContents);
		
	}
 	
	/**
	 * Set up default Service Group environment
	 * @throws Exception
	 */
	public void setDefaultServiceGroupEnv(){	
		if(!isServiceGroupExist(serviceGroupName)){		
			addServiceGroup(serviceGroupName, serviceGroupName);
		}
	}
	
	/**
	 * Clean up default Service Group environment
	 * @throws Exception
	 */
	public void cleanDefaultServiceGroupEnv(){
		if(isServiceGroupExist(serviceGroupName)){		
			deleteSpecificServiceGroup(serviceGroupName);
		}
	}
	
}
