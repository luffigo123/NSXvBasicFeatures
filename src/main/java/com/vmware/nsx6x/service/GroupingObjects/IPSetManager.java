package com.vmware.nsx6x.service.GroupingObjects;

import com.vmware.nsx6x.model.GroupingObjects.IPSet;
import com.vmware.nsx6x.utils.Config;
import com.vmware.nsx6x.utils.DefaultEnvironment;
import com.vmware.nsx6x.utils.HttpReq;
import com.vmware.nsx6x.utils.TestData;
import com.vmware.nsx6x.utils.XmlFileOp;

public class IPSetManager {

	public HttpReq httpReq;
	private String vsmIP;
	
	public String defaultDesc = "DefaultDes" + TestData.NativeString;
	public String ipSetName = "IPSet001" + TestData.NativeString;
	public String ipSetValue = "192.168.200.1";
	public String newIPSetValue = "192.168.100.0/24";
	
	private String filepath = Config.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	private String xmlPath = filepath + "/RestCallXML/IPSet.xml";
	
	public IPSetManager() {
		httpReq = HttpReq.getInstance();
		vsmIP = DefaultEnvironment.VSMIP;
	}

	public void addIPSet(String xmlContents)
	{
		//POST https://<nsxmgr-ip>/api/2.0/services/ipset/<scope-moref>
		String url = "https://" + vsmIP + "/api/2.0/services/ipset/globalroot-0" ;
		httpReq.postRequest(xmlContents,url);
	}
	
	/**
	 * Query all IPsets info
	 * @return
	 */
	public String queryIPSetsInfo()
	{
		//GET https://<nsxmgr-ip>/api/2.0/services/ipset/scope/<scope-moref>
		String url = "https://" + vsmIP + "/api/2.0/services/ipset/scope/globalroot-0" ;
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
	 * Get IPSet's ID by IPSet name
	 * @param IPSetName
	 * @return
	 */
	public String getSpecificIPSetID(String IPSetName)
	{	
		String ipSetsInfo = "";
		try {
			ipSetsInfo = queryIPSetsInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String ipSetID = XmlFileOp.findNearestBeforeTagValue3(ipSetsInfo, "objectId", "name", IPSetName);
		return ipSetID;
	}
	
	/**
	 * Query Specific IPSet info
	 * @param IPSetID
	 * @return
	 */
 	public String querySpecificIPSetInfo(String IPSetID)
	{
		//GET https://<nsxmgr-ip>/api/2.0/services/ipset/<ipset-id>
		String url = "https://" + vsmIP + "/api/2.0/services/ipset/" + IPSetID;
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
	 * Modify specific IPSet
	 * @param xmlContents
	 * @param IPSetID
	 */
	public void modifySpecificIPSet(String xmlContents, String IPSetID)
	{
		//PUT https://<nsxmgr-ip>/api/2.0/services/ipset/<ipset-id>
		String url = "https://" + vsmIP + "/api/2.0/services/ipset/" + IPSetID;
		System.out.println(url);
		httpReq.putRequest(xmlContents, url);

	}
	
	/**
	 * Delete specific IPSet
	 * @param IPSetID
	 */
	public void deleteSpecificIPSet(String IPSetID)
	{
		//DELETE https://<nsxmgr-ip>/api/2.0/services/ipset/<ipset-id>?force=<true|false>
		String url = "https://" + vsmIP + "/api/2.0/services/ipset/" + IPSetID + "?force=false";
		System.out.println(url);
		httpReq.delRequest(url);

	}
	
	/**
	 * Check the target IPSet whether exist
	 * @param ipSetName
	 * @return
	 */
	public boolean isIPSetExist(String ipSetName)
	{
		boolean flag = false;
		
		String queryIPsetInfo = "";
		try {
			queryIPsetInfo = this.queryIPSetsInfo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String targetTag = "name";
		flag = XmlFileOp.checkGivenStringEqualTagValue(queryIPsetInfo, targetTag, ipSetName);
		return flag;
	}
	
	/**
	 * Add IPSet
	 * @param name - IPSet name
	 * @param value - eg:192.168.200.1,192.168.200.1/24, 192.168.200.1-192.168.200.24
	 * @param desc
	 * @author Fei
	 */
	public void addIPSet(String name, String value, String desc)
	{
		String xmlContents = null;
		IPSet ipSet = new IPSet(name, desc, value);
		
		//array of xml keys
		String [] xmlKeys = {"name","value","description"};				
		//array of xml values
		String [] xmlValues = {ipSet.getName(),ipSet.getValue(),ipSet.getDesc()};				
		//CpnfigSSO.xml path
//		String xmlPath = "RestCallXML\\IPSet.xml";	
		
		try {
			xmlContents = XmlFileOp.getXMLContensWithValues(xmlKeys, xmlValues, xmlPath);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//Check the IPSet with given name whether exist -- just add IPSet when the given name isn't exist
		try {
			if(!isIPSetExist(name))
			{
				addIPSet(xmlContents);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Modify the specific IPSet.If you want modify the value, please input null. Note: Not Completed
	 * @param orgName  -  the specific IPSet name
	 * @param newName  -  
	 * @param newValue -  eg:192.168.200.1,192.168.200.1/24, 192.168.200.1-192.168.200.24
	 * @param newDesc  - 
	 * @author Fei 
	 */
	public void editSpecificIPSet(String orgName,String newName, String newValue, String newDesc)
	{
		if(isIPSetExist(orgName))
		{
			String ipSetID = getSpecificIPSetID(orgName);
			String orgXMLContents = querySpecificIPSetInfo(ipSetID);
		
			IPSet ipSet = new IPSet();
			
			if(newName != null)
			{
				ipSet.setName(newName);
			}
			else 
			{
				ipSet.setName(orgName + "1");
			}
			
			if(newValue != null)
			{
				ipSet.setValue(newValue);
			}
			else
			{
				String value = XmlFileOp.getValueBySpecificTag(orgXMLContents, "value");
				ipSet.setValue(value);
			}
			
			if(newDesc != null)
			{
				ipSet.setDesc(newDesc);
			}
			else 
			{
				ipSet.setDesc(XmlFileOp.getValueBySpecificTag(orgXMLContents, "description"));
			}
			
			//array of xml keys
			String [] xmlKeys = {"objectId","revision","name","description","value"};
			
			String revision = XmlFileOp.getValueBySpecificTag(orgXMLContents, "revision");
			//array of xml values
			String [] xmlValues = {ipSetID, revision, ipSet.getName(), ipSet.getDesc(),ipSet.getValue()};
		
			//Get the final contens of xml
			String	xmlContents = XmlFileOp.getXMLContensWithValues(xmlKeys, xmlValues, xmlPath);

			modifySpecificIPSet(xmlContents, ipSetID);
		}
	}
	
	/**
	 * Delete Specific IPSet by name
	 * @param ipSetName
	 * @author Fei
	 */
	public void deleteIPSet(String ipSetName)
	{
		if(isIPSetExist(ipSetName))
		{
			String ipSetID = getSpecificIPSetID(ipSetName);
			deleteSpecificIPSet(ipSetID);
		}	
	}
	
	/**
	 * Set up default IPSet  environment
	 * @throws Exception
	 */
	public void setDefaultIPSetEnv(){
		if(!isIPSetExist(ipSetName)){
			addIPSet(ipSetName, ipSetValue, defaultDesc);
		}	
	}

	/**
	 * clean up default IPSet environment
	 * @throws Exception
	 */
	public void cleanDefaultIPSetEnv(){
		if(isIPSetExist(ipSetName)){
			deleteIPSet(ipSetName);
		}
	}

}
