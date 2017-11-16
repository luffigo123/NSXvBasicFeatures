package com.vmware.nsx6x.service.GroupingObjects;

import java.util.ArrayList;

import com.vmware.nsx6x.model.GroupingObjects.DynamicMember;
import com.vmware.nsx6x.model.GroupingObjects.GOSecurityGroup;
import com.vmware.nsx6x.utils.Config;
import com.vmware.nsx6x.utils.DefaultEnvironment;
import com.vmware.nsx6x.utils.HttpReq;
import com.vmware.nsx6x.utils.TestData;
import com.vmware.nsx6x.utils.VC;
import com.vmware.nsx6x.utils.XmlFileOp;

public class SecurityGroupManager {
	public HttpReq httpReq;
	private String vsmIP;
	
	public String defaultDesc = "DefaultDes" + TestData.NativeString;
	public String securityGroupsName = "SecurityGroup001" + TestData.NativeString;
	
	private String filepath = Config.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	private String xmlPath = filepath + "/RestCallXML/SecurityGroup.xml";
	
	public SecurityGroupManager() {
		httpReq = HttpReq.getInstance();
		vsmIP = DefaultEnvironment.VSMIP;
	}


	/**
	 * generate a default Security Group instance
	 * @return
	 */
	public GOSecurityGroup getSecurityGroupInstance()
	{
//		GroupingObjectsBase gob = new GroupingObjectsBase();
//		GroupingObjectManager groupObjectMgr = new GroupingObjectManager();
		VC vc = VC.getInstance();
		
		String clusterName = Config.getInstance().ConfigMap.get("Cluster1");
		String networkName = Config.getInstance().ConfigMap.get("Internal01");
		
		String clusterMoid = "";
		String internalNetworkMoid = "";
		try {
			clusterMoid = vc.getClusterMOID(clusterName);
			internalNetworkMoid = vc.getNetworkMoIdByName(networkName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String securityGroupName = securityGroupsName;
		String desc = defaultDesc;
		
		String obejctID1 = clusterMoid;
		String obejctID2 = internalNetworkMoid;
		
		DynamicMember dynamicMember = new DynamicMember("OR", "OR", "VM.NAME", "contains", "vm");
		DynamicMember dynamicMember2 = new DynamicMember("OR", "OR", "VM.NAME", "contains", "vm");
		
		ArrayList<String> includeList = new ArrayList<String>();
		ArrayList<String> excludeList = new ArrayList<String>();
		ArrayList<DynamicMember> dynamicMembersList = new ArrayList<DynamicMember>();
		includeList.add(obejctID1);
		excludeList.add(obejctID2);
		dynamicMembersList.add(dynamicMember);
		dynamicMembersList.add(dynamicMember2);
		
		GOSecurityGroup securityGroup = new GOSecurityGroup(securityGroupName, desc, "false", includeList, excludeList, dynamicMembersList);
		
		return securityGroup;
	}
	
	
 	/**
 	 * Query all Security Groups info
 	 * @return
 	 */
 	public String queryAllSecurityGroupsInfo()
 	{
 		//GET https://<nsxmgr-ip>/api/2.0/services/securitygroup/scope/<scopeID>
 		String url = "https://" + vsmIP + "/api/2.0/services/securitygroup/scope/globalroot-0" ;
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
 	 * Add Security Group
 	 * @param xmlContents
 	 */
 	public void addSecurityGroup(String xmlContents)
 	{
 		//POST https://<nsxmgr-ip>/api/2.0/services/securitygroup//bulk/<scopeID>
 		String url = "https://" + vsmIP + "/api/2.0/services/securitygroup/bulk/globalroot-0";
		System.out.println(url);
		httpReq.postRequest(xmlContents,url);

 	}
 	
 	/**
 	 * get specific security group id
 	 * @param specificSecurtyGroupName
 	 * @return
 	 */
 	public String getSpecificSecurityGroupID(String specificSecurityGroupName)
 	{
 		String queryInfo = queryAllSecurityGroupsInfo();
 		String sgID = XmlFileOp.findNearestBeforeTagValue3(queryInfo, "objectId", "name", specificSecurityGroupName);
 		return sgID;
 	}
 	

	
 	/**
 	 * Query specific security group
 	 * @param securitygroup_id
 	 * @return
 	 */
 	public String querySpecificSecurityGroupInfo(String securitygroup_id)
 	{
 		//GET https://<nsxmgr-ip>/api/2.0/services/securitygroup/<securityGroupID>
 		String url = "https://" + vsmIP + "/api/2.0/services/securitygroup/" + securitygroup_id;
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
 	 * modify specific security group
 	 * @param securitygroup_id
 	 * @param xmlContents
 	 */
 	public void modifySpecificSecurityGroup(String securitygroup_id, String xmlContents)
 	{
 		//String securitygroup_id = getSpecificSecurityGroupID(specificSecurityGroupName);
 		//PUT https://<nsxmgr-ip>/api/2.0/services/securitygroup/bulk/<securitygroup-id>
 		String url = "https://" + vsmIP + "/api/2.0/services/securitygroup/bulk/" + securitygroup_id;
		System.out.println(url);
		httpReq.putRequest(xmlContents, url);

 	}
 	 	
 	/**
 	 * Delete specific security group
 	 * @param specificSecurityGroupName
 	 */
 	public void deleteSpecificSecurityGroup(String specificSecurityGroupName)
 	{
 		String securitygroup_id = getSpecificSecurityGroupID(specificSecurityGroupName);
 		//DELETE https://<nsxmgr-ip>/api/2.0/services/securitygroup/<securitygroup-id>
		String url = "https://" + vsmIP + "/api/2.0/services/securitygroup/" + securitygroup_id;
		System.out.println(url);
		httpReq.delRequest(url);

 	}
 	
	/**
	 * Check target SecurityGroup whether exist
	 * @param securityGroupsName
	 * @return
	 */
	public boolean isSecurityGroupExist(String securityGroupsName){
		boolean flag = false;	
		String querySecurityGroupsInfo = this.queryAllSecurityGroupsInfo();
		String targetTag = "name";
		flag = XmlFileOp.checkGivenStringEqualTagValue(querySecurityGroupsInfo, targetTag, securityGroupsName);
		return flag;
	}
 	
 	
 	/**
	 * Add Security Group
	 * @param securityGroup
	 */
	public void addSecurityGroup(GOSecurityGroup securityGroup)
	{
		String includeMemberString = "<member><objectId></objectId></member>";
		String excludeMemberString = "<excludeMember><objectId>network-12</objectId></excludeMember>";
		String dynamicMemberString = "<dynamicSet><operator></operator><dynamicCriteria><operator></operator><key></key><criteria></criteria><value></value></dynamicCriteria></dynamicSet>";
		String endTag = "securitygroup";
		String xmlContents = null;
		
		if(securityGroup != null)
		{
			//1. step: add value to the xmlcontents
			String [] xmlKeys1 = {"name","description","inheritanceAllowed"};
			String [] xmlValues1 = {securityGroup.getName(),securityGroup.getDescription(),securityGroup.getInheritanceAllowed()};								
//			String xmlPath = "RestCallXML\\SecurityGroup.xml";							

			xmlContents = XmlFileOp.generateXMLWithFile(xmlKeys1, xmlValues1, xmlPath);
			
			//2. step: append member to the xmlcontents
			ArrayList<String> includeMemberList = securityGroup.getIncludeMember();
			String appendString_include = "";
			String [] xmlKeys2 = {"objectId"};
			String [] xmlValues2 = {""};
			if(includeMemberList.size() >= 1)
			{
				for(int i = 0; i< includeMemberList.size() ; i++)
				{
					String objectID = includeMemberList.get(i);
					xmlValues2[0] = objectID;
					//String tempString = XmlFileOp.generateXMLContentsWithValues(xmlKeys2, xmlValues2, includeMemberString);
					String tempString = XmlFileOp.generateXMLWithContents(xmlKeys2, xmlValues2, includeMemberString);
					appendString_include = appendString_include + tempString;
				}
			}	
			xmlContents = XmlFileOp.appendToEnd(appendString_include, xmlContents, endTag);
			
			//3. step: append exclude member to the xmlcontents
			ArrayList<String> excludeMemberList = securityGroup.getExcludeMember();
			String appendString_exclude = "";
			String [] xmlKeys3 = {"objectId"};	
			if(excludeMemberList.size() >= 1)
			{
				for(int i = 0; i< excludeMemberList.size() ; i++)
				{
					String objectID = excludeMemberList.get(i);
					String [] xmlValues3 = {objectID};
					//String tempString = XmlFileOp.generateXMLContentsWithValues(xmlKeys3, xmlValues3, excludeMemberString);
					String tempString = XmlFileOp.generateXMLWithContents(xmlKeys3, xmlValues3, excludeMemberString);
					appendString_exclude = appendString_exclude + tempString;
				}
				
			}	
			xmlContents = XmlFileOp.appendToEnd(appendString_exclude, xmlContents, endTag);
			
			
			//4. step: append dynamic member to the xmlcontents
			ArrayList<DynamicMember> dynamicMembers = securityGroup.getDynamicMember();
			String appendString_dynamic = "";
			String [] xmlKeys4 = {"operator","dynamicCriteria_operator","key","criteria","value"};
			if (dynamicMembers.size() >=1) 
			{
				for(int i=0; i< dynamicMembers.size(); i++)
				{
					DynamicMember dynamicMember = dynamicMembers.get(i);
					String [] xmlValues4 = {dynamicMember.getOperator(),dynamicMember.getDynamicCriteria_operator(),dynamicMember.getKey(),dynamicMember.getCriteria(),dynamicMember.getValue()};
					//String tempString = XmlFileOp.generateXMLContentsWithValues(xmlKeys4, xmlValues4, dynamicMemberString);
					String tempString = XmlFileOp.generateXMLWithContents(xmlKeys4, xmlValues4, dynamicMemberString);
					appendString_dynamic = appendString_dynamic + tempString;
				}
				appendString_dynamic = "<dynamicMemberDefinition>" + appendString_dynamic + "</dynamicMemberDefinition>";
			}
			xmlContents = XmlFileOp.appendToEnd(appendString_dynamic, xmlContents, endTag);	
		}
		
		System.out.println(xmlContents);
		addSecurityGroup(xmlContents);
		
	}
	
	/**
	 * Modify the specific security group
	 * @param specificSecurityGroupName
	 * @param securityGroup
	 */
	public void editSecurityGroup(String specificSecurityGroupName,GOSecurityGroup securityGroup)
	{
		String firstPartString = null;
		String includeMemberString = "<member><objectId>domain-c7</objectId></member>";;
		String excludeMemberString = "<excludeMember><objectId>network-12</objectId></excludeMember>";
		String dynamicMemberString = "<dynamicSet><operator>OR</operator><dynamicCriteria><operator>OR</operator><key>VM.NAME</key><criteria>contains</criteria><value>vm</value><isValid>true</isValid></dynamicCriteria></dynamicSet>";;
		String endTag = "securitygroup";
		String xmlContents = null;
		String finalContents = null;
		String securitygroup_id = null;
		
		if(specificSecurityGroupName != null )
		{
			//pre-condition
			//1: query the specific security group info and generate the first part of xmlcontents
			securitygroup_id = getSpecificSecurityGroupID(specificSecurityGroupName);
			xmlContents = querySpecificSecurityGroupInfo(securitygroup_id);
			
			String tempS = "</inheritanceAllowed>";
			String temps2 = "</securitygroup>";
			int index = xmlContents.indexOf(tempS) + tempS.length();
			firstPartString = xmlContents.substring(0, index) + temps2;
			finalContents = firstPartString;
			//System.out.println(finalContents);
			
			if(securityGroup != null)
			{
				//1. step: input values to the xmlContents
				String [] xmlKeys1 = {"name","description","inheritanceAllowed"};
				String [] xmlValues1 = {securityGroup.getName(),securityGroup.getDescription(),securityGroup.getInheritanceAllowed()};													
				//finalContents = XmlFileOp.generateXMLContentsWithValues(xmlKeys1, xmlValues1, finalContents);
				finalContents = XmlFileOp.generateXMLWithContents(xmlKeys1, xmlValues1, finalContents);
				
				//2. step: append member to the contents
				ArrayList<String> includeMemberList = securityGroup.getIncludeMember();
				String appendString_include = "";
				String [] xmlKeys2 = {"objectId"};
				String [] xmlValues2 = {""};
				if(includeMemberList.size() >= 1)
				{
					for(int i = 0; i< includeMemberList.size() ; i++)
					{
						String objectID = includeMemberList.get(i);
						xmlValues2[0] = objectID;
						//String tempString = XmlFileOp.generateXMLContentsWithValues(xmlKeys2, xmlValues2, includeMemberString);
						String tempString = XmlFileOp.generateXMLWithContents(xmlKeys2, xmlValues2, includeMemberString);
						appendString_include = appendString_include + tempString;
					}
				}	
				finalContents = XmlFileOp.appendToEnd(appendString_include, finalContents, endTag);
				
				//3. step: append exclude member to the contents
				ArrayList<String> excludeMemberList = securityGroup.getExcludeMember();
				String appendString_exclude = "";
				String [] xmlKeys3 = {"objectId"};	
				if(excludeMemberList.size() >= 1)
				{
					for(int i = 0; i< excludeMemberList.size() ; i++)
					{
						String objectID = excludeMemberList.get(i);
						String [] xmlValues3 = {objectID};
						//String tempString = XmlFileOp.generateXMLContentsWithValues(xmlKeys3, xmlValues3, excludeMemberString);
						String tempString = XmlFileOp.generateXMLWithContents(xmlKeys3, xmlValues3, excludeMemberString);
						appendString_exclude = appendString_exclude + tempString;
					}
					
				}	
				finalContents = XmlFileOp.appendToEnd(appendString_exclude, finalContents, endTag);
				
				//4. step: append dynamic to the contents
				ArrayList<DynamicMember> dynamicMembers = securityGroup.getDynamicMember();
				String appendString_dynamic = "";
				String [] xmlKeys4 = {"operator","dynamicCriteria_operator","key","criteria","value"};
				if (dynamicMembers.size() >=1) 
				{
					for(int i=0; i< dynamicMembers.size(); i++)
					{
						DynamicMember dynamicMember = dynamicMembers.get(i);
						String [] xmlValues4 = {dynamicMember.getOperator(),dynamicMember.getDynamicCriteria_operator(),dynamicMember.getKey(),dynamicMember.getCriteria(),dynamicMember.getValue()};
						//String tempString = XmlFileOp.generateXMLContentsWithValues(xmlKeys4, xmlValues4, dynamicMemberString);
						String tempString = XmlFileOp.generateXMLWithContents(xmlKeys4, xmlValues4, dynamicMemberString);
						appendString_dynamic = appendString_dynamic + tempString;
					}
					appendString_dynamic = "<dynamicMemberDefinition>" + appendString_dynamic + "</dynamicMemberDefinition>";
				}	
				finalContents = XmlFileOp.appendToEnd(appendString_dynamic, finalContents, endTag);
			}
		}
		//System.out.println(finalContents);
		modifySpecificSecurityGroup(securitygroup_id, finalContents);
	}
	
	/**
	 * Set up default Security Group environment
	 * @throws Exception
	 */
	public void setDefaultSecurityGroupEnv(){
		if(!isSecurityGroupExist(securityGroupsName)){
			GOSecurityGroup securityGroup = getSecurityGroupInstance();
			addSecurityGroup(securityGroup);
		}
	}
	
	/**
	 * clean up default Security Group environment
	 * @throws Exception
	 */
	public void cleanDefaultSecurityGroupEnv(){
		if(isSecurityGroupExist(securityGroupsName)){
			deleteSpecificSecurityGroup(securityGroupsName);
		}
	}
	
	
}
