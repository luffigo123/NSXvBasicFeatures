package com.vmware.nsx6x.service.GroupingObjects;

import java.io.StringReader;

import com.vmware.nsx6x.model.GroupingObjects.Service;
import com.vmware.nsx6x.utils.Config;
import com.vmware.nsx6x.utils.DefaultEnvironment;
import com.vmware.nsx6x.utils.HttpReq;
import com.vmware.nsx6x.utils.TestData;
import com.vmware.nsx6x.utils.XmlFileOp;

public class ServiceManager {
	
	public HttpReq httpReq;
	private String vsmIP;
	public String serviceName = "Service001" +TestData.NativeString;
	public String orgServiceValue = "7000-7001";
	public String newServiceValue = "8000-8003";
	public String defaultDesc = "DefaultDes" + TestData.NativeString;
	
	private String filepath = Config.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	private String xmlPath = filepath + "/RestCallXML/GOService.xml";
	
	
	public ServiceManager(){
		httpReq = HttpReq.getInstance();
		vsmIP = DefaultEnvironment.VSMIP;
	}
	
	
	/**
	 * Query all Services info
	 * @return 
	 */
	public String queryAllServiceInfo(){
		//GET https://<nsxmgr-ip>/api/2.0/services/application/scope/<application-id>
		String url = "https://" + vsmIP + "/api/2.0/services/application/scope/globalroot-0" ;
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
	 * Add Service
	 * @param xmlContents
	 */
	public void addService(String xmlContents)
	{
		//POST https://<nsxmgr-ip>/api/2.0/services/application/<moref>
		String url = "https://" + vsmIP + "/api/2.0/services/application/globalroot-0";
		httpReq.postRequest(xmlContents,url);

	}
	
	/**
	 * get specific service id
	 * @param specificServiceName
	 * @return
	 */
	public String getSpecificServiceID(String specificServiceName)
	{
		String serviceInfos = queryAllServiceInfo();
		String serviceID = XmlFileOp.findNearestBeforeTagValue3(serviceInfos, "objectId", "name", specificServiceName);
		return serviceID;	
	}
	
	/**
	 * Query specific service info
	 * @param specificServiceName
	 * @return
	 */
	public String querySpecificServiceInfo(String specificServiceName)
	{
		String application_id = getSpecificServiceID(specificServiceName);
		
		//GET https://<nsxmgr-ip>/api/2.0/services/application/<application-id>
		String url = "https://" + vsmIP + "/api/2.0/services/application/" + application_id;
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
	 * modify the specific service
	 * @param specificServiceName
	 * @param xmlContents 
	 */
	public void modifySpecificService(String specificServiceName,String xmlContents)
	{
		String application_id = getSpecificServiceID(specificServiceName);
		
		//PUT https://<nsxmgr-ip>/api/2.0/services/application/<application-id>
		String url = "https://" + vsmIP + "/api/2.0/services/application/" + application_id;
		System.out.println(url);
		httpReq.putRequest(xmlContents,url);

		
	}
	
	/**
	 * delete specific service
	 * @param specificServiceName 
	 */
 	public void deleteSpecificService(String specificServiceName)
	{
		String application_id = getSpecificServiceID(specificServiceName);
		//DELETE https://<nsxmgr-ip>/api/2.0/services/application/<application-id>?force=<true|false>
		//DELETE https://<nsxmgr-ip>/api/2.0/services/application/<application-id>
		String url = "https://" + vsmIP + "/api/2.0/services/application/" + application_id;
		System.out.println(url);
		httpReq.delRequest(url);

	}
	
	/**
	 * Check target service whether exist
	 * @param serviceName
	 * @return
	 */
	public boolean isServiceExist(String serviceName){
		boolean flag = false;	
		String queryServicesInfo = this.queryAllServiceInfo();
		String targetTag = "name";
		flag = XmlFileOp.checkGivenStringEqualTagValue(queryServicesInfo, targetTag, serviceName);
		return flag;
	}

	/**
	 * Add service
	 * @param service
	 */
	public void addService(String serviceName,String desc,String value)
	{
		String applicationProtocol = "TCP";
		Service service = new Service(serviceName, desc, "true", applicationProtocol, value);
		
		//array of xml keys
		String [] xmlKeys = {"name","desc","inheritanceAllowed","applicationProtocol","value"};
								
		//array of xml values
		String [] xmlValues = {service.getName(),service.getDesc(),service.getInheritanceAllowed(),service.getApplicationProtocol(),service.getValue()};
								
		//CpnfigSSO.xml path
//		String xmlPath = "RestCallXML\\GOService.xml";
								
		//Get the final contens of xml
		String xmlContents = XmlFileOp.getXMLContensWithValues(xmlKeys, xmlValues, xmlPath);
		
		addService(xmlContents);
	}
	
	/**
	 * Modify specific service
	 * @param orgServiceName
	 * @param newServicename
	 * @param newDesc
	 * @param newValue
	 */
	public void editSpecificService(String orgServiceName,String newServicename,String newDesc,String newValue)
	{
		//Check the default Service which you want edit whether exist
		if(this.isServiceExist(orgServiceName)){
			String orgXMLContents = this.querySpecificServiceInfo(orgServiceName);
			
			Service service = new Service();
			
			if(newServicename != null){
				service.setName(newServicename);
			}else{
				service.setName(orgServiceName + "1");
			}
			if(newValue != null){
				service.setValue(newValue);
			}else{
				String value = XmlFileOp.getValueBySpecificTag(orgXMLContents, "value");
				service.setValue(value);
			}
			if(newDesc != null){
				service.setDesc(newDesc);
			}else{
				service.setDesc(XmlFileOp.getValueBySpecificTag(orgXMLContents, "description"));
			}
			service.setInheritanceAllowed(XmlFileOp.getValueBySpecificTag(orgXMLContents, "inheritanceAllowed"));
			service.setApplicationProtocol(XmlFileOp.getValueBySpecificTag(orgXMLContents, "applicationProtocol"));
			
			//array of xml keys
			String [] xmlKeys = {"name","desc","inheritanceAllowed","applicationProtocol","value"};
											
			//array of xml values
			String [] xmlValues = {service.getName(),service.getDesc(),service.getInheritanceAllowed(),service.getApplicationProtocol(),service.getValue()};
										
			//CpnfigSSO.xml path
			String queryContents = this.querySpecificServiceInfo(orgServiceName);
											
			//Get the final contens of xml
			String xmlContents = XmlFileOp.generateXMLWithContents(xmlKeys, xmlValues, queryContents);

			this.modifySpecificService(orgServiceName, xmlContents);
		}
			
	}
	
	
	/**
	 * Set up default Service environment
	 * @throws Exception
	 */
	public void setDefaultServiceEnv(){
		if(!isServiceExist(serviceName)){
			addService(serviceName, defaultDesc, orgServiceValue);
		}
	}
	
	/**
	 * clean up default Service environment
	 * @throws Exception
	 */
	public void cleanDefaultServiceEnv(){
		if(isServiceExist(serviceName)){
			deleteSpecificService(serviceName);
		}
	}
}
