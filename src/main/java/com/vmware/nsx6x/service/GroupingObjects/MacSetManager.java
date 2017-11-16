package com.vmware.nsx6x.service.GroupingObjects;

import com.vmware.nsx6x.model.GroupingObjects.MacSet;
import com.vmware.nsx6x.utils.Config;
import com.vmware.nsx6x.utils.DefaultEnvironment;
import com.vmware.nsx6x.utils.HttpReq;
import com.vmware.nsx6x.utils.TestData;
import com.vmware.nsx6x.utils.XmlFileOp;

public class MacSetManager {
	
	public HttpReq httpReq;
	private String vsmIP;
	
	public String macSetName = "MacSet001" + TestData.NativeString;
	public String orgMacSetValue = "11:22:33:44:55:66";
	public String newMacSetValue = "aa:bb:cc:dd:ee:ff";
	public String defaultDesc = "DefaultDes" + TestData.NativeString;

	private String filepath = Config.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	private String xmlPath = filepath + "/RestCallXML/MacSet.xml";
	
	
	public MacSetManager() {
		httpReq = HttpReq.getInstance();
		vsmIP = DefaultEnvironment.VSMIP;
	}

	/**
	 * add mac set
	 * @param xmlContents
	 */
	public void addMacSet(String xmlContents)
	{
		//POST https://<nsxmgr-ip>/api/2.0/services/macset/<scope-moref>
		String url = "https://" + vsmIP + "/api/2.0/services/macset/globalroot-0";
		System.out.println(url);
		httpReq.postRequest(xmlContents, url);
	
	}
	
	/**
	 * query all MacSets info
	 * @return 
	 */
	public String queryAllMacSetsInfo()
	{
		//GET https://<nsxmgr-ip>/api/2.0/services/macset/<scope-moref>
		String url = "https://" + vsmIP + "/api/2.0/services/macset/scope/globalroot-0" ;
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
	 * get specific macset id
	 * @param specificMacSetname
	 * @return
	 */
	public String getSpecificMacSetID(String specificMacSetname)
	{
		String macSetInfo = queryAllMacSetsInfo();
System.out.println(macSetInfo);
		String macSetID = XmlFileOp.findNearestBeforeTagValue3(macSetInfo, "objectId", "name", specificMacSetname);
		return macSetID;
	}
	
	/**
	 * Query Specific macSet info
	 * @param macSetID
	 * @return 
	 */
	public  String querySpecificMacSetInfo(String macSetID) {
		//GET https://<nsxmgr-ip>/api/2.0/services/macset/<macset-id>
		String url = "https://" + vsmIP + "/api/2.0/services/macset/" + macSetID;
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
	 * Edit specific MacSet
	 * @param xmlContents
	 * @param macSetID
	 */
	public void modifySpecificMacSet(String xmlContents, String macSetID){
		//PUT https://<nsxmgr-ip>/api/2.0/services/MACset/<MACset-id>
		String url = "https://" + vsmIP + "/api/2.0/services/macset/" + macSetID;		
		System.out.println(url);
		httpReq.putRequest(xmlContents, url);

	}
	
	/**
	 * Delete specific macser
	 * @param macSetID 
	 */
	public void deleteSpecificMacSet(String macSetID)
	{
		//DELETE https://<nsxmgr-ip>/api/2.0/services/macset/<macset-id>
		String url = "https://" + vsmIP + "/api/2.0/services/macset/" + macSetID;
		System.out.println(url);
		httpReq.delRequest(url);

	}
	
	/**
	 * Check the target MacSet whether exist
	 * @param macSetName
	 * @return 
	 */
	public boolean isMacSetExist(String macSetName)
	{
		boolean flag = false;
		
		String queryMacSetsInfo = this.queryAllMacSetsInfo();
		String targetTag = "name";
		flag = XmlFileOp.checkGivenStringEqualTagValue(queryMacSetsInfo, targetTag, macSetName);
		return flag;
	}
	
	/**
	 * Add MacSet
	 * @param name
	 * @param value
	 * @param desc 
	 */
	public void addMacSet(String name,String value,String desc)
	{
		MacSet macSet = new MacSet(name, desc, value);
		
		//array of xml keys
		String [] xmlKeys = {"name","value","description"};
						
		//array of xml values
		String [] xmlValues = {macSet.getName(),macSet.getValue(),macSet.getDesc()};
						
		
		//Get the final contens of xml
		String xmlContents = XmlFileOp.generateXMLWithFile(xmlKeys, xmlValues, xmlPath);
				
		addMacSet(xmlContents);
	}
	
	/**
	 * Modify specific macSet
	 * @param orgName
	 * @param newName
	 * @param newValue
	 * @param newDesc 
	 */
	public void editMacSet(String orgName,String newName, String newValue, String newDesc) {
		//Check the default macSet which you want edit whether exist
		if(isMacSetExist(orgName)){
			
			String macSetID = this.getSpecificMacSetID(orgName);
			String orgXMLContents = this.querySpecificMacSetInfo(macSetID);
			
			MacSet macSet = new MacSet();
			
			if(newName != null){
				macSet.setName(newName);
			}else{
				macSet.setName(orgName+"1");
			}
			
			if(newValue != null){
				macSet.setValue(newValue);
			}else{
				String value = XmlFileOp.getValueBySpecificTag(orgXMLContents, "value");
				macSet.setValue(value);
			}
			
			if(newDesc != null){
				macSet.setDesc(newDesc);
			}else{
				macSet.setDesc(XmlFileOp.getValueBySpecificTag(orgXMLContents, "description"));
			}
			
			//array of xml keys
			String [] xmlKeys = {"objectId","revision","name","description","value"};
			String revision = XmlFileOp.getValueBySpecificTag(orgXMLContents, "revision");
			//array of xml values
			String [] xmlValues = {macSetID,revision,macSet.getName(),macSet.getDesc(),macSet.getValue()};
								
			//Get the final contens of xml
			String	xmlContents = XmlFileOp.getXMLContensWithValues(xmlKeys, xmlValues, xmlPath);

			this.modifySpecificMacSet(xmlContents, macSetID);	
		}
	}
	
	/**
	 * Delete specific macset
	 * @param specificMacSetname
	 */
	public void deleteMacSet(String specificMacSetname)
	{
		String macSetID = this.getSpecificMacSetID(specificMacSetname);
		this.deleteSpecificMacSet(macSetID);
	}
	
	
	/**
	 * Set up default macSet environment
	 * @throws Exception
	 */
	public void setDefaultMacSetEnv(){
		
		if(!isMacSetExist(macSetName)){
			addMacSet(macSetName, orgMacSetValue, defaultDesc);
		}
	}
	
	/**
	 * clean up default MacSet environment
	 * @throws Exception
	 */
	public void cleanDefaultMacSetEnv(){
		if(isMacSetExist(macSetName)){
			deleteMacSet(macSetName);
		}
	}
	
}
