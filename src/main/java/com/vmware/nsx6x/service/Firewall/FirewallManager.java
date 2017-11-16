package com.vmware.nsx6x.service.Firewall;

import java.util.ArrayList;
import java.util.HashMap;

import com.vmware.nsx6x.model.Firewall.Firewall;
import com.vmware.nsx6x.model.Firewall.Member;
import com.vmware.nsx6x.utils.Config;
import com.vmware.nsx6x.utils.DefaultEnvironment;
import com.vmware.nsx6x.utils.HttpReq;
import com.vmware.nsx6x.utils.TestData;
import com.vmware.nsx6x.utils.VC;
import com.vmware.nsx6x.utils.XmlFileOp;

/**
 * 
 * @author Fei
 *
 */
public class FirewallManager {
	public HttpReq httpReq;
//	private HeaderReq headerReq;
	private String vsmIP;
	
	
	private String filepath = Config.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	private String xmlPath_Section = filepath + "/RestCallXML/FirewallSection.xml";
	private String xmlPath_Firewall = filepath + "/RestCallXML/FirewallRule.xml";
	private String xmlFilePath_FirewallConfig = filepath + "/RestCallXML/FirewallConfiguration.xml";
	
	public String sectionName = "Section001" + TestData.NativeString;
	public String sectionLayer3 = "layer3sections";
	public String sectionLayer2 = "layer2sections";
	public String firewallRuleName = "Firewall001" + TestData.NativeString;
	public String saveConfigurationName = "SaveConfiguration001" + TestData.NativeString;
	
	public FirewallManager(){
		httpReq = HttpReq.getInstance();
//		headerReq = HeaderReq.getInstance();
		vsmIP = DefaultEnvironment.VSMIP;
	}
	
	
	/**
	 * get the Source and Destination members
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Member> getSourecDestMemberList() throws Exception
	{
		VC vc = VC.getInstance();
		ArrayList<Member> arrayList = new ArrayList<Member>();
	
//		String datacenterName = Config.getInstance().ConfigMap.get("DatacenterName");
//		String clusterName = Config.getInstance().ConfigMap.get("Cluster1");
//		String legacyPortGroupName = Config.getInstance().ConfigMap.get("Internal01");
//		String dvPortGroupName = Config.getInstance().ConfigMap.get("dvPortGroupName");
//		String vmName = Config.getInstance().ConfigMap.get("VM1");
		
		String datacenterName = DefaultEnvironment.DatacenterName;
		String clusterName = DefaultEnvironment.Cluster1;
		String legacyPortGroupName = DefaultEnvironment.Internal01;
		String dvPortGroupName = DefaultEnvironment.dvPortGroupName;
		String vmName = Config.getInstance().ConfigMap.get("VM1");
		
		String datacenterMoid = vc.getDataCenterMOID(datacenterName);
		String clusterMoid = vc.getClusterMOID(clusterName);
		String legacyPortGrouMoid = vc.getNetworkMoIdByName(legacyPortGroupName);
		String dvPortGroupMoid = vc.getNetworkMoIdByName(dvPortGroupName);
		String vmMoid = vc.getVirtualMachineMoIdByName(vmName);
		
		Member member = new Member("Ipv4Address", "192.168.1.1");
		Member member2 = new Member("Datacenter", datacenterMoid);
		Member member3 = new Member("ClusterComputeResource", clusterMoid);
		Member member4 = new Member("Network", legacyPortGrouMoid);
		Member member5 = new Member("DistributedVirtualPortgroup", dvPortGroupMoid);
		Member member6 = new Member("VirtualMachine", vmMoid);

		arrayList.add(member);
		arrayList.add(member2);
		arrayList.add(member3);
		arrayList.add(member4);
		arrayList.add(member5);
		arrayList.add(member6);
		
		return arrayList;
	}
	
	/**
	 * Get the Service members
	 * @return
	 */
	public ArrayList<Member> getServiceMemberList()
	{
		ArrayList<Member> arrayList = new ArrayList<Member>();
		
		Member member = new Member("Application", "application-278");
		Member member2 = new Member("ApplicationGroup", "applicationgroup-1");

		arrayList.add(member);
		arrayList.add(member2);

		return arrayList;
	}
	
	/**
	 * Get the AppliedTo Members list
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Member> getAppliedToMemberList() throws Exception
	{
		VC vc = VC.getInstance();
		ArrayList<Member> arrayList = new ArrayList<Member>();
	
		String datacenterName = Config.getInstance().ConfigMap.get("DatacenterName");
		String clusterName = Config.getInstance().ConfigMap.get("Cluster1");
		String legacyPortGroupName = Config.getInstance().ConfigMap.get("Internal01");
		String dvPortGroupName = Config.getInstance().ConfigMap.get("dvPortGroupName");
		String vmName = Config.getInstance().ConfigMap.get("VM1");
		String hostName = Config.getInstance().ConfigMap.get("Host1InDvSwitch");
		
		String datacenterMoid = vc.getDataCenterMOID(datacenterName);
		String clusterMoid = vc.getClusterMOID(clusterName);
		String legacyPortGrouMoid = vc.getNetworkMoIdByName(legacyPortGroupName);
		String dvPortGroupMoid = vc.getNetworkMoIdByName(dvPortGroupName);
		String vmMoid = vc.getVirtualMachineMoIdByName(vmName);
		String hostMoid = vc.getHostMOID(hostName);
		
		Member member = new Member("HostSystem", hostMoid);
		Member member2 = new Member("Datacenter", datacenterMoid);
		Member member3 = new Member("ClusterComputeResource", clusterMoid);
		Member member4 = new Member("Network", legacyPortGrouMoid);
		Member member5 = new Member("DistributedVirtualPortgroup", dvPortGroupMoid);
		Member member6 = new Member("VirtualMachine", vmMoid);

		arrayList.add(member);
		arrayList.add(member2);
		arrayList.add(member3);
		arrayList.add(member4);
		arrayList.add(member5);
		arrayList.add(member6);
		
		return arrayList;
	}
	
	
	/**
	 * Init firewall instance
	 * @return
	 * @throws Exception
	 */
 	public Firewall getFirewallInstance() throws Exception
	{
 		String name = this.firewallRuleName;
		String action = "allow";
		String notes = name;
		
		
		//Define appliedToList info  - Type: Datacenter | DISTRIBUTED_FIREWALL
		Member appliedTo = new Member("DISTRIBUTED_FIREWALL", "DISTRIBUTED_FIREWALL");
		ArrayList<Member> appliedToList = new ArrayList<Member>();
		appliedToList.add(appliedTo);
		
		//Define sources info	    - Type: Datacenter | ClusterComputeResource | Ipv4Address | IPSet
		Member source = new Member("Ipv4Address", "192.168.1.1");
		ArrayList<Member> sourcesList = new ArrayList<Member>();
		sourcesList.add(source);
		
		//Define destinations info   - Type: Datacenter | ClusterComputeResource | Ipv4Address | IPSet
		Member destination = new Member("Ipv4Address", "192.168.1.2");
		ArrayList<Member> destinationsList = new ArrayList<Member>();
		destinationsList.add(destination);
		
		//Define services info   - Type: Application | ApplicationGroup
		String applicationValue = "application-278";
		Member service = new Member("Application", applicationValue);
		ArrayList<Member> servicesList = new ArrayList<Member>();
		servicesList.add(service);
		
		Firewall firewall = new Firewall(name, action, notes, appliedToList, sourcesList, destinationsList, servicesList);
		
		return firewall;
	}
	
	/**
	 * query firewall configuration
	 * @return
	 * @throws Exception 
	 */
	public String queryFirewallConfig() throws Exception
	{
		//GET https://<nsxmgr-ip>/api/4.0/firewall/globalroot-0/config
		String url = "https://" + vsmIP + "/api/4.0/firewall/globalroot-0/config";
		System.out.println(url);
		return httpReq.getRequest(url);
	}
	
	
	/**
	 * Get specific firewall ruleID
	 * @param firewallName
	 * @return
	 * @throws Exception 
	 */
	public String getSpecificFirewallRuleID(String firewallName) throws Exception
	{
		String firewallInfo = queryFirewallConfig();
		String temp = XmlFileOp.getStringsBeforeSpecificTag(firewallInfo, "rule", "name", firewallName);
	    String result = XmlFileOp.getSpecificPropertyValueInTagString(temp, "rule", "id");
	    return result;
	}
	
	/**
	 * Query specific firewall rule info
	 * @param layer
	 * @param sectionID
	 * @param firewallName
	 * @return
	 * @throws Exception 
	 */
	public String querySpecificFirewallRuleInfo(String sectionLayer,String sectionID,String firewallRuleName) throws Exception
	{
		String ruleID = getSpecificFirewallRuleID(firewallRuleName);
		//GET https://<nsxmgr-ip>/api/4.0/firewall/globalroot-0/config/layer3sections|layer2sections/<sectionId>/rules/<ruleId>
		String url = "https://" + vsmIP + "/api/4.0/firewall/globalroot-0/config/" + sectionLayer + "/" + sectionID + "/rules/" + ruleID;
System.out.println(url);
		return httpReq.getRequest(url);
	}
	
	/**
	 * 
	 * @param layer 			layer3sections | layer2sections
	 * @param sectionID
	 * @return
	 * @throws Exception 
	 */
	public String querySpecificSectionInfo(String layer,String sectionName) throws Exception
	{
		String sectionID = getSpecificSectionID(sectionName);
		//GET https://<nsxmgr-ip>/api/4.0/firewall/globalroot-0/config/layer3sections|layer2sections/<sectionId> |<sectionName>
		String url = "https://" + vsmIP + "/api/4.0/firewall/globalroot-0/config/" +  layer + "/" + sectionID ;
System.out.println(url);		
		return httpReq.getRequest(url);	
	}
	
	/**
	 * Add Firewall Section
	 * @param xmlContents 
	 * @param layer     	layer3sections | layer2sections
	 * @throws Exception 
	 */
	public void addFirewallSection(String xmlContents, String layer) throws Exception
	{
		//POST https://<nsxmgr-ip>/api/4.0/firewall/globalroot-0/config/layer3sections|layer2sections
		String url = "https://" + vsmIP + "/api/4.0/firewall/globalroot-0/config/" + layer;
		System.out.println(url);
		httpReq.postRequest(xmlContents,url);
	}
	
	/**
	 * Add Section
	 * @param sectionName
	 * @param layer			layer3sections | layer2sections
	 * @author Fei
	 * @throws Exception 
	 */
	public void addSection(String sectionName,String sectionLayer) throws Exception
	{											
		//FierwallSection.xml path
//String xmlPath = "RestCallXML\\FirewallSection.xml";	
		
		//Get the final contents of xml
		String xmlContents = XmlFileOp.readXMLContens(xmlPath_Section);
		xmlContents = XmlFileOp.modifyTagPropertyValue(xmlContents, "section", "name", sectionName );
		//Add FirewallSection
		addFirewallSection(xmlContents, sectionLayer);
	}
	
	/**
	 * Get specific sectionID
	 * @param orginalSectionName
	 * @return
	 * @throws Exception 
	 */
	public String getSpecificSectionID(String sectionName) throws Exception
	{
		String firewallInfo = queryFirewallConfig();
		String tag = "section";
		String relateProperty = "name";
		String relatePropertyValue = sectionName;
		String targetProperty = "id";
		String specificSectionID = XmlFileOp.findSpecificTagPropertyValue(firewallInfo, tag, relateProperty, relatePropertyValue, targetProperty);
System.out.println(specificSectionID);
		
		return specificSectionID;
	}
	
	
	/**
	 * Modify specific Firewall Section
	 * @param xmlContents
	 * @param layer
	 * @param sectionID
	 * @param header
	 * @throws Exception
	 */
	public void modifySpecificSection(String xmlContents,String sectionLayer,String sectionID, HashMap<String, String> header) throws Exception
	{
		//PUT https://<nsxmgr-ip>/api/4.0/firewall/globalroot-0/config/layer3sections|layer2sections/<sectionId> |<sectionName>
		String url = "https://" + vsmIP + "/api/4.0/firewall/globalroot-0/config/" + sectionLayer + "/" + sectionID;
//
//		StringReader sr = new StringReader(xmlContents);
//		String functionName = "FirewallSection";
//		String requestParameters = "";
//	
//		headerReq.putData2(sr, url, requestParameters, new StringWriter(), functionName, header);
		this.httpReq.putRequestWithHeader(xmlContents, url, header);
	}
	
	
	/**
	 * Edit Specific FirewallSection
	 * @param layer
	 * @param orginalSectionName
	 * @param newSectionName
	 * @throws Exception
	 * @author Fei 
	 */
	public void editFirewallSection(String sectionLayer, String orginalSectionName, String newSectionName) throws Exception
	{
		//define headers parameters
		HashMap<String, String> header = new HashMap<String, String>();
		String generationNumber ="";

		//define section parameters
		String tag = "section";
		String relateProperty = "name";
		String relatePropertyValue = orginalSectionName;
		String targetProperty = "id";
		String xmlContents = "";
		
		//query section info
		String sectionInfo = querySpecificSectionInfo(sectionLayer, orginalSectionName);
		//get section ID
		String specificSectionID = XmlFileOp.findSpecificTagPropertyValue(sectionInfo, tag, relateProperty, relatePropertyValue, targetProperty);
		//Get generationNumber and timestamp of section
		generationNumber = XmlFileOp.findSpecificTagPropertyValue(sectionInfo, tag, relateProperty, relatePropertyValue, "generationNumber");
		
		//generate section xml contents
		xmlContents = XmlFileOp.modifyTagPropertyValue(sectionInfo, tag, relateProperty, newSectionName);

		header.put("Content-Type", "application/xml");
		header.put("If-Match", generationNumber);
		
		modifySpecificSection(xmlContents, sectionLayer, specificSectionID, header);
		
	}
	
	/**
	 * Delete specific section
	 * @param layer
	 * @param sectionID
	 * @throws Exception 
	 */
	public void deleteSpecificSection(String sectionLayer,String sectionID) throws Exception
	{
		//DELETE https://<nsxmgr-ip>/api/4.0/firewall/globalroot-0/config/layer3sections|layer2sections/<sectionId> |<sectionName>
		String url = "https://" + vsmIP + "/api/4.0/firewall/globalroot-0/config/" + sectionLayer + "/" + sectionID;
		httpReq.delRequest(url);
	}
	
	
	/**
	 * Delete specific FirewallSection
	 * @param sectionLayer
	 * @param sectionName
	 * @throws Exception 
	 */
	public void deleteSection(String sectionLayer, String sectionName) throws Exception
	{
		String sectionID = getSpecificSectionID(sectionName);
		deleteSpecificSection(sectionLayer, sectionID);
	}
	
	/**
	 * Check specific section whether exist
	 * @param domainName
	 * @return
	 * @throws Exception
	 */
	public boolean isSectionExist(String sectionName) throws Exception
	{
		boolean flag = false;	
		String firewallConfigInfo = queryFirewallConfig();
		
		String target = "name=" + '"' + sectionName + '"';
		System.out.println(target);

		if (firewallConfigInfo.contains(target)){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * Add Firewall rule
	 * @param layerSection - layer3sections|layer2sections
	 * @param sectionID
	 * @throws Exception 
	 */
 	public void addFirewallRule(String xmlContents, String sectionLayer,String sectionID, HashMap<String, String> header) throws Exception
	{
 		//POST https://<nsxmgr-ip>/api/4.0/firewall/globalroot-0/config/layer3sections|layer2sections/<sectionId>/rules
		String url = "https://" + vsmIP + "/api/4.0/firewall/globalroot-0/config/" + sectionLayer + "/" + sectionID + "/rules";
//System.out.println(url);
//		String requestParameters = "";
//		//String result= null;
//		String functionName="Firewall Rule";
//		StringReader sr = new StringReader(xmlContents);
//
//		headerReq.postData(sr, url, requestParameters, new StringWriter(), functionName, header);
		
		this.httpReq.postRequestWithHeader(xmlContents, url, header);
	}
 	
 	/**
 	 * Add Default FirewallRule on Specific Section - you just can indicate rule's name
 	 * @param firewallRuleName
 	 * @param sectionLayer
 	 * @param sectionName
 	 * @throws Exception
 	 */
 	public void addDefaultFirewallRuleOnSpecificSection(String firewallRuleName, String sectionLayer, String sectionName) throws Exception
 	{
 		HashMap<String, String> header = new HashMap<String, String>();
 		
 		//define section parameters
 		String tag = "section";
 		String relateProperty = "name";
 		String relatePropertyValue = sectionName;
 		String xmlContents = "";
 				
 		//query section info
 		String sectionInfo = querySpecificSectionInfo(sectionLayer, sectionName);
 		//get section ID
 		String sectionID = getSpecificSectionID(sectionName);
 		//Get generationNumber and timestamp of section
 		String generationNumber = XmlFileOp.findSpecificTagPropertyValue(sectionInfo, tag, relateProperty, relatePropertyValue, "generationNumber");
 		//Put header info in to HashMap
 		header.put("Content-Type", "application/xml");
 		header.put("If-Match", generationNumber);
 		
 		//array of xml keys
 		String [] xmlKeys = {"name"};				
 		//array of xml values
 		String [] xmlValues = {firewallRuleName};				
 		//FirewallRule.xml path
// String xmlFilePath = "RestCallXML\\FirewallRule.xml";		;
 		//Get the final contens of xml
 		xmlContents = XmlFileOp.generateXMLWithFile(xmlKeys, xmlValues, xmlPath_Firewall);
 		
 		addFirewallRule(xmlContents, sectionLayer, sectionID, header);
 	}
 	
 	
 	/**
 	 * Add FirewallRule on specific section - you can define rule's properties
 	 * @param firewall
 	 * @param sectionLayer
 	 * @param sectionName
 	 * @throws Exception
 	 */
 	public void addFirewallRuleOnSpecificSection(Firewall firewall, String sectionLayer, String sectionName) throws Exception
 	{
 		HashMap<String, String> header = new HashMap<String, String>();
 		
 		//define section parameters
 		String tag = "section";
 		String relateProperty = "name";
 		String relatePropertyValue = sectionName;
 				
 		//query section info
 		String sectionInfo = querySpecificSectionInfo(sectionLayer, sectionName);
 		//get section ID
 		String sectionID = getSpecificSectionID(sectionName);
 		//Get generationNumber and timestamp of section
 		String generationNumber = XmlFileOp.findSpecificTagPropertyValue(sectionInfo, tag, relateProperty, relatePropertyValue, "generationNumber");
 		//Put header info in to HashMap
 		header.put("Content-Type", "application/xml");
 		header.put("If-Match", generationNumber);
 		
 		//Generate xmlContents
 		String appliedToListString = "<appliedTo><value></value><type></type></appliedTo>";
		String sourcesListString = "<source><value></value><type></type></source>";
		String destinationListString = "<destination><value></value><type></type></destination>";
		String servicesListString = "<service><value></value><type></type></service>";
		
		String endTag = "rule";
		String xmlContents = null;
		
		if(firewall != null)
		{
			//1. step: add default value to the xmlcontents
			String [] xmlKeys1 = {"name","action","notes"};
			String [] xmlValues1 = {firewall.getName(),firewall.getAction(),firewall.getNotes()};								
//String xmlPath = "RestCallXML\\FirewallRule.xml";							
			xmlContents = XmlFileOp.generateXMLWithFile(xmlKeys1, xmlValues1, xmlPath_Firewall);
			
			//2. step: append appliedToList to the xmlcontents
			ArrayList<Member> appliedToList = firewall.getAppliedToList();
			String appendString_ApplyTo = "";
			String [] xmlKeysAppend = {"value","type"};
			String [] xmlValuesAppend = {"",""};
			if(appliedToList.size() >= 1)
			{
				for(int i = 0; i< appliedToList.size() ; i++)
				{
					Member ma = appliedToList.get(i);
					xmlValuesAppend[0] = ma.getValue();
					xmlValuesAppend[1] = ma.getType();
					//generate appliedToList contents
					String tempString = XmlFileOp.generateXMLWithContents(xmlKeysAppend, xmlValuesAppend, appliedToListString);
					appendString_ApplyTo = appendString_ApplyTo + tempString;
				}
				appendString_ApplyTo = "<appliedToList>" + appendString_ApplyTo + "</appliedToList>";
			}	
			xmlContents = XmlFileOp.appendToEnd(appendString_ApplyTo, xmlContents, endTag);
			
			//3. step: append sources to the xmlcontents
			ArrayList<Member> sourcesList = firewall.getSources();
			String appendString_Sources = "";
			String [] xmlKeysSource = {"value","type"};
			String [] xmlValuesSource = {"",""};
			if(sourcesList.size() >= 1)
			{
				for(int i = 0; i< sourcesList.size() ; i++)
				{
					Member msource = sourcesList.get(i);
					xmlValuesSource[0] = msource.getValue();
					xmlValuesSource[1] = msource.getType();
					
					//generate sourcesList contents
					String tempString = XmlFileOp.generateXMLWithContents(xmlKeysSource, xmlValuesSource, sourcesListString);
					appendString_Sources = appendString_Sources + tempString;
				}
				appendString_Sources = "<sources excluded=\"false\">" + appendString_Sources + "</sources>";
			}	
			xmlContents = XmlFileOp.appendToEnd(appendString_Sources, xmlContents, endTag);
			
			
			//4. step: append destinations to the xmlcontents
			ArrayList<Member> destinationsList = firewall.getDestinations();
			String appendString_Destination = "";
			String [] xmlKeysDestination = {"value","type"};
			String [] xmlValuesDestination = {"",""};
			if(destinationsList.size() >= 1)
			{
				for(int i = 0; i< destinationsList.size() ; i++)
				{
					Member md = destinationsList.get(i);
					xmlValuesDestination[0] = md.getValue();
					xmlValuesDestination[1] = md.getType();
					
					//generate sourcesList contents
					String tempString = XmlFileOp.generateXMLWithContents(xmlKeysDestination, xmlValuesDestination, destinationListString);
					appendString_Destination = appendString_Destination + tempString;
				}
				appendString_Destination = "<destinations excluded=\"false\">" + appendString_Destination + "</destinations>";
			}	
			xmlContents = XmlFileOp.appendToEnd(appendString_Destination, xmlContents, endTag);
			
			
			//5. step: append services to the xmlcontents
			ArrayList<Member> servicesList = firewall.getServices();
			String appendString_Services = "";
			String [] xmlKeysService = {"value","type"};
			String [] xmlValuesService = {"",""};
			if(servicesList.size() >= 1)
			{
				for(int i = 0; i< servicesList.size() ; i++)
				{
					Member mservice = servicesList.get(i);
					xmlValuesService[0] = mservice.getValue();
					xmlValuesService[1] = mservice.getType();
					
					//generate sourcesList contents
					String tempString = XmlFileOp.generateXMLWithContents(xmlKeysService, xmlValuesService, servicesListString);
					appendString_Services = appendString_Services + tempString;
				}
				appendString_Services = "<services>" + appendString_Services + "</services>";
			}	
			xmlContents = XmlFileOp.appendToEnd(appendString_Services, xmlContents, endTag);
		}
		addFirewallRule(xmlContents, sectionLayer, sectionID, header);
 	}
 	
	/**
	 * Delete Firewall Rule
	 * @param sectionLayer
	 * @param sectionID
	 * @param ruleID
	 * @return
	 * @throws Exception
	 */
	public void deleteFirewallRule(String sectionLayer,String sectionID, String ruleID,HashMap<String, String> header) throws Exception
	{
		//DELETE https://<nsxmgr-ip>/api/4.0/firewall/globalroot-0/config/layer2sections|layer3sections/<sectionId>/rules/<ruleId>
		String url = "https://" + vsmIP + "/api/4.0/firewall/globalroot-0/config/" + sectionLayer + "/" + sectionID + "/rules/" + ruleID;
System.out.println(url);
//		//return httpReq.delRequest(url);
//		StringReader sr = new StringReader("");
//		String requestParameters = "";
//		String functionName = "deleteData";
//		return headerReq.deleteRequest(sr, url, requestParameters, new StringWriter(), functionName, header);
		this.httpReq.delRequestWithHeader(url, header);
	}
	
	/**
	 * Delete specific FirewallRule on specific section
	 * @param sectionLayer
	 * @param sectionID
	 * @param firewallName
	 * @throws Exception
	 * Don't work
	 */
	public void deleteFirewallRuleOnSpecificSection(String sectionLayer,String sectionName,String firewallName) throws Exception
	{
		String ruleID = getSpecificFirewallRuleID(firewallName);
		
 		HashMap<String, String> header = new HashMap<String, String>();
 		
 		//define section parameters
 		String tag = "section";
 		String relateProperty = "name";
 		String relatePropertyValue = sectionName;
				
 		//query section info
 		String sectionInfo = querySpecificSectionInfo(sectionLayer, sectionName);
 		//get section ID
 		String sectionID = getSpecificSectionID(sectionName);
 		//Get generationNumber and timestamp of section
 		String generationNumber = XmlFileOp.findSpecificTagPropertyValue(sectionInfo, tag, relateProperty, relatePropertyValue, "generationNumber");
 		//Put header info in to HashMap
 		header.put("If-Match", generationNumber);
		
		deleteFirewallRule(sectionLayer, sectionID, ruleID, header);
	}
	
	
	/**
	 * Modify the specific Firewall Rule
	 * @param sectionLayer
	 * @param sectionID
	 * @param ruleID
	 * @param xmlContents
	 * @param header
	 * @throws Exception
	 */
	public void modifyFirewallRule(String sectionLayer, String sectionID, String ruleID, String xmlContents, HashMap<String, String> header) throws Exception
	{
		//PUT https://<nsxmgr-ip>/api/4.0/firewall/globalroot-0/config/layer3sections|layer3sections/<sectionId>/rules/<ruleId>
		String url = "https://" + vsmIP + "/api/4.0/firewall/globalroot-0/config/" + sectionLayer + "/" + sectionID + "/rules/" + ruleID;
//System.out.println(url);
//		StringReader sr = new StringReader(xmlContents);
//		String functionName = "FirewallRule";
//		String requestParameters = "";
//			
//		//headerReq.putData(sr, url, requestParameters, new StringWriter(), functionName, header);
//		headerReq.putData2(sr, url, requestParameters, new StringWriter(), functionName, header);
		this.httpReq.putRequestWithHeader(xmlContents, url, header);
	}
	
	/**
	 * Edit firewall Rule name | Action - "allow","reject","deny"| Notes
	 * @param sectionName
	 * @param sectionLayer
	 * @param orgFirewallRuleName
	 * @param newFirewallRuleName
	 * @throws Exception
	 * @author Fei
	 */
	public void editFirewallRullNameActionNotes(String sectionName, String sectionLayer, String orgFirewallRuleName, Firewall firewall) throws Exception
	{
		Firewall fw = new Firewall();
	
		String sectionID = getSpecificSectionID(sectionName);
		String ruleID = getSpecificFirewallRuleID(orgFirewallRuleName);
		
		String specificSectionInfo = querySpecificSectionInfo(sectionLayer, sectionName);
		String specificRuleInfo = querySpecificFirewallRuleInfo(sectionLayer, sectionID, orgFirewallRuleName);
		
		String orgAction = XmlFileOp.getValueBySpecificTag(specificRuleInfo, "action");
		String orgNotes = XmlFileOp.getValueBySpecificTag(specificRuleInfo, "notes");
		
		if(firewall != null){
			String newName = firewall.getName();
			String newAction = firewall.getAction();
			String newNotes = firewall.getNotes();
			
			if(newName != null){
				fw.setName(newName);
			}else{
				fw.setName(orgFirewallRuleName);
			}
			if(newAction != null && ("allow".equals(newAction) || "reject".equals(newAction) || "deny".equals(newAction))){
				fw.setAction(newAction);
			}else{
				fw.setAction(orgAction);
			}
			if(newNotes != null){
				fw.setNotes(newNotes);
			}else{
				fw.setNotes(orgNotes);
			}
		}
		
		HashMap<String, String> header = new HashMap<String, String>();
 		
 		//Get generationNumber and timestamp of section
 		String generationNumber = XmlFileOp.findSpecificTagPropertyValue(specificSectionInfo, "section", "name", sectionName, "generationNumber");
 		//Put header info in to HashMap
 		header.put("Content-Type", "application/xml");
 		header.put("If-Match", generationNumber);
		
 		//array of xml keys
 		String [] xmlKeys = {"name", "action", "notes"};				
 		//array of xml values
 		String [] xmlValues = {fw.getName(),fw.getAction(),fw.getNotes()};
 		
 		//generate xmlContents
		String xmlContents = XmlFileOp.generateXMLWithContents(xmlKeys, xmlValues, specificRuleInfo);
	
		modifyFirewallRule(sectionLayer, sectionID, ruleID, xmlContents, header);
	}
	
	public void editFirewallRullName(String sectionName, String sectionLayer, String orgFirewallRuleName, String newFirewallRuleName) throws Exception
	{
		String sectionID = getSpecificSectionID(sectionName);
		String ruleID = getSpecificFirewallRuleID(orgFirewallRuleName);
		
		String specificSectionInfo = querySpecificSectionInfo(sectionLayer, sectionName);
		String specificRuleInfo = querySpecificFirewallRuleInfo(sectionLayer, sectionID, orgFirewallRuleName);
		
		HashMap<String, String> header = new HashMap<String, String>();
 		
 		//define section parameters
 		String tag = "section";
 		String relateProperty = "name";
 		String relatePropertyValue = sectionName;
 				
 		//Get generationNumber and timestamp of section
 		String generationNumber = XmlFileOp.findSpecificTagPropertyValue(specificSectionInfo, tag, relateProperty, relatePropertyValue, "generationNumber");
 		//Put header info in to HashMap
 		header.put("Content-Type", "application/xml");
 		header.put("If-Match", generationNumber);
		
 		//array of xml keys
 		String [] xmlKeys = {"name"};				
 		//array of xml values
 		String [] xmlValues = {newFirewallRuleName};				
 		//generate xmlContents
		String xmlContents = XmlFileOp.generateXMLWithContents(xmlKeys, xmlValues, specificRuleInfo);
	
		modifyFirewallRule(sectionLayer, sectionID, ruleID, xmlContents, header);
	}
	
	/**
	 * Enable disable FirewallRule or Log
	 * @param sectionName
	 * @param sectionLayer
	 * @param orgFirewallRuleName
	 * @param ruleOrLog					logged | disabled
	 * @param status					 true | false
	 * @throws Exception
	 *  need check
	 */
	public void enableDisableRuleOrLog(String sectionName, String sectionLayer, String orgFirewallRuleName, String ruleOrLog, String status) throws Exception
	{
		String sectionID = getSpecificSectionID(sectionName);
		String ruleID = getSpecificFirewallRuleID(orgFirewallRuleName);
		
		String specificSectionInfo = querySpecificSectionInfo(sectionLayer, sectionName);
		String specificRuleInfo = querySpecificFirewallRuleInfo(sectionLayer, sectionID, orgFirewallRuleName);
		
		HashMap<String, String> header = new HashMap<String, String>();
 		
 		//define section parameters
 		String tag = "section";
 		String relateProperty = "name";
 		String relatePropertyValue = sectionName;
 				
 		//Get generationNumber and timestamp of section
 		String generationNumber = XmlFileOp.findSpecificTagPropertyValue(specificSectionInfo, tag, relateProperty, relatePropertyValue, "generationNumber");
 		//Put header info in to HashMap
 		header.put("Content-Type", "application/xml");
 		header.put("If-Match", generationNumber);
 		String xmlContents = specificRuleInfo;
 		String tempStatus = "";
 		
 		//check status value
 		if("true".equalsIgnoreCase(status.trim()) || "false".equalsIgnoreCase(status.trim())){
 			tempStatus = status;
 		}else{
 			tempStatus = "false";
 		}
 		
 		//modify the rule's status or log's status
 		if("logged".equals(ruleOrLog.toLowerCase().trim())){
 			xmlContents = XmlFileOp.modifyTagPropertyValue(xmlContents, "rule", "logged", tempStatus);
 		}
 		else if("disabled".equalsIgnoreCase(ruleOrLog.toLowerCase().trim())){
 			xmlContents = XmlFileOp.modifyTagPropertyValue(xmlContents, "rule", "disabled", tempStatus);
 		}
 		else{
 			System.out.println("Please indicate which part do you want edit!");
 		}
 		modifyFirewallRule(sectionLayer, sectionID, ruleID, xmlContents, header); 		
	}
	
	/**
	 * Add firewallRule member - include "apllyTo","source","destination","service"
	 * @param sectionName
	 * @param sectionLayer
	 * @param firewallRuleName
	 * @param memberType 		 aplly | source | destination | service
	 * @param member
	 * @throws Exception
	 */
	public void addMemberOnFirewallRule(String sectionName, String sectionLayer,String firewallRuleName,String memberType, Member member) throws Exception
	{
		String sectionID = getSpecificSectionID(sectionName);
		String ruleID = getSpecificFirewallRuleID(firewallRuleName);
		String specificSectionInfo = querySpecificSectionInfo(sectionLayer, sectionName);
		String specificRuleInfo = querySpecificFirewallRuleInfo(sectionLayer, sectionID, firewallRuleName);
		
		HashMap<String, String> header = new HashMap<String, String>();
 					
 		//Get generationNumber and timestamp of section
 		String generationNumber = XmlFileOp.findSpecificTagPropertyValue(specificSectionInfo, "section", "name", sectionName, "generationNumber");
 		//Put header info in to HashMap
 		header.put("Content-Type", "application/xml");
 		header.put("If-Match", generationNumber);
 		
 		String xmlContents = specificRuleInfo;
 		
 		//Generate xmlContents
		String appendString = "";
		String startTag = "direction";
		// step: append appliedToList to the xmlcontents
		String [] xmlKeysAppend = {"value","type"};
		String [] xmlValuesAppend = {"",""};
		xmlValuesAppend[0] = member.getValue();
		xmlValuesAppend[1] = member.getType();
				
		memberType = memberType.trim().toLowerCase();
		switch(memberType)
		{
			case "aplly":
				if(xmlContents.contains("<appliedToList>")){
					String endTag = "appliedToList";
					appendString = "<appliedTo><value></value><type></type></appliedTo>";
					//generate appliedToList contents
					appendString = XmlFileOp.generateXMLWithContents(xmlKeysAppend, xmlValuesAppend, appendString);
					xmlContents = XmlFileOp.appendToEnd(appendString, xmlContents, endTag);
				}else{
					appendString = "<appliedToList><appliedTo><value></value><type></type></appliedTo></appliedToList>";
					//generate appliedToList contents
					appendString = XmlFileOp.generateXMLWithContents(xmlKeysAppend, xmlValuesAppend, appendString);
					xmlContents = XmlFileOp.insertStringBeforeStartTag(appendString, xmlContents, startTag);
				}
				break;
			case "source":
				if(xmlContents.contains("<sources excluded=\"false\">")){
					String endTag = "sources";
					appendString = "<source><value></value><type></type></source>";
					//generate appliedToList contents
					appendString = XmlFileOp.generateXMLWithContents(xmlKeysAppend, xmlValuesAppend, appendString);
					xmlContents = XmlFileOp.appendToEnd(appendString, xmlContents, endTag);
				}else{
					appendString = "<sources excluded=\"false\"><source><value></value><type></type></source></sources>";
					//generate appliedToList contents
					appendString = XmlFileOp.generateXMLWithContents(xmlKeysAppend, xmlValuesAppend, appendString);
					xmlContents = XmlFileOp.insertStringBeforeStartTag(appendString, xmlContents, startTag);
				}
				break;
			case "destination":
				if(xmlContents.contains("<destinations excluded=\"false\">")){
					String endTag = "destinations";
					appendString = "<destination><value></value><type></type></destination>";
					//generate appliedToList contents
					appendString = XmlFileOp.generateXMLWithContents(xmlKeysAppend, xmlValuesAppend, appendString);
					xmlContents = XmlFileOp.appendToEnd(appendString, xmlContents, endTag);
				}else{
					appendString = "<destinations excluded=\"false\"><destination><value></value><type></type></destination></destinations>";
					//generate appliedToList contents
					appendString = XmlFileOp.generateXMLWithContents(xmlKeysAppend, xmlValuesAppend, appendString);
					xmlContents = XmlFileOp.insertStringBeforeStartTag(appendString, xmlContents, startTag);
				}
				break;
			case "service":
				if(xmlContents.contains("<services>")){
					String endTag = "services";
					appendString = "<service><value></value><type></type></service>";
					//generate appliedToList contents
					appendString = XmlFileOp.generateXMLWithContents(xmlKeysAppend, xmlValuesAppend, appendString);
					xmlContents = XmlFileOp.appendToEnd(appendString, xmlContents, endTag);
				}else{
					appendString = "<services><service><value></value><type></type></service></services>";
					//generate appliedToList contents
					appendString = XmlFileOp.generateXMLWithContents(xmlKeysAppend, xmlValuesAppend, appendString);
					xmlContents = XmlFileOp.insertStringBeforeStartTag(appendString, xmlContents, startTag);
				}
				break;
			default:
				break;
		}	
		
		modifyFirewallRule(sectionLayer, sectionID, ruleID, xmlContents, header);	
	}
	
	/**
	 * Edit member information - include "apllyTo","source","destination","service"
	 * @param sectionName
	 * @param sectionLayer
	 * @param firewallRuleName
	 * @param memberType	   apllyto | source | destination | service
	 * @param member
	 * @throws Exception
	 */
	public void editMemberOnFirewallRule(String sectionName, String sectionLayer,String firewallRuleName,String memberType, Member member) throws Exception
	{
		String sectionID = getSpecificSectionID(sectionName);
		String ruleID = getSpecificFirewallRuleID(firewallRuleName);
		String specificSectionInfo = querySpecificSectionInfo(sectionLayer, sectionName);
		String specificRuleInfo = querySpecificFirewallRuleInfo(sectionLayer, sectionID, firewallRuleName);
		
		HashMap<String, String> header = new HashMap<String, String>();
 					
 		//Get generationNumber and timestamp of section
 		String generationNumber = XmlFileOp.findSpecificTagPropertyValue(specificSectionInfo, "section", "name", sectionName, "generationNumber");
 		//Put header info in to HashMap
 		header.put("Content-Type", "application/xml");
 		header.put("If-Match", generationNumber);
 		
 		String xmlContents = specificRuleInfo;
 		
 		//Get member info
		String [] xmlKeysAppend = {"",""};
		String [] xmlValuesAppend = {"",""};
		xmlValuesAppend[0] = member.getValue();
		xmlValuesAppend[1] = member.getType();
				
		memberType = memberType.trim().toLowerCase();
		switch(memberType)
		{
			case "apllyto":
				if(xmlContents.contains("<appliedToList>")){
					xmlKeysAppend[0] = "appliedTo_value";
					xmlKeysAppend[1] = "appliedTo_type";
					//generate appliedToList contents
					xmlContents = XmlFileOp.generateXMLWithContents(xmlKeysAppend, xmlValuesAppend, xmlContents);
				}else{
					System.out.println("Please ensure the ApplyTo Member exist");
				}
				break;
			case "source":
				if(xmlContents.contains("<source>")){
					xmlKeysAppend[0] = "source_value";
					xmlKeysAppend[1] = "source_type";
					//generate appliedToList contents
					xmlContents = XmlFileOp.generateXMLWithContents(xmlKeysAppend, xmlValuesAppend, xmlContents);
				}else{
					System.out.println("Please ensure the Source Member exist");
				}
				break;
			case "destination":
				if(xmlContents.contains("<destination>")){
					xmlKeysAppend[0] = "destination_value";
					xmlKeysAppend[1] = "destination_type";
					//generate appliedToList contents
					xmlContents = XmlFileOp.generateXMLWithContents(xmlKeysAppend, xmlValuesAppend, xmlContents);
				}else{
					System.out.println("Please ensure the Destination Member exist");
				}
				break;
			case "service":
				if(xmlContents.contains("<service>")){
					xmlKeysAppend[0] = "service_value";
					xmlKeysAppend[1] = "service_type";
					//generate appliedToList contents
					xmlContents = XmlFileOp.generateXMLWithContents(xmlKeysAppend, xmlValuesAppend, xmlContents);
				}else{
					System.out.println("Please ensure the Service Member exist");
				}
				break;
			default:
				break;
		}		
		modifyFirewallRule(sectionLayer, sectionID, ruleID, xmlContents, header);
	}
	
	/**
	 * check target whether exist
	 * @param sectionLayer
	 * @param sectionID
	 * @param firewallRuleName
	 * @return
	 * @throws Exception
	 */
	public boolean isFirewallRuleExist(String sectionLayer,String sectionID, String firewallRuleName) throws Exception
	{
		boolean flag = false;	
		//String firewallRuleInfo = querySpecificFirewallRuleInfo(sectionLayer, sectionID, firewallRuleName);
		String firewallRuleInfo = queryFirewallConfig();
		String targetTag = "name";
		flag = XmlFileOp.checkGivenStringEqualTagValue(firewallRuleInfo, targetTag, firewallRuleName);
		return flag;
	}
	
	/**
	 * check member whether exist
	 * @param sectionLayer
	 * @param sectionID
	 * @param firewallRuleName
	 * @return
	 * @throws Exception
	 */
	public boolean isFirewallRuleMemberExist(String sectionLayer,String sectionID, String firewallRuleName,String memberValue) throws Exception
	{
		boolean flag = false;	
		String firewallRuleInfo = querySpecificFirewallRuleInfo(sectionLayer, sectionID, firewallRuleName);
		String targetTag = "value";
		flag = XmlFileOp.checkGivenStringEqualTagValue(firewallRuleInfo, targetTag, memberValue);
		return flag;
	}
	
	/**
	 * check target whether exist
	 * @param sectionLayer
	 * @param sectionID
	 * @param firewallRuleName
	 * @return
	 * @throws Exception
	 */
	public boolean isFirewallNotesExist(String sectionLayer,String sectionID, String firewallRuleName, String notes) throws Exception
	{
		boolean flag = false;	
		String firewallRuleInfo = querySpecificFirewallRuleInfo(sectionLayer, sectionID, firewallRuleName);
		//String firewallRuleInfo = queryFirewallConfig();
		String targetTag = "notes";
		flag = XmlFileOp.checkGivenStringEqualTagValue(firewallRuleInfo, targetTag, notes);
		return flag;
	}
	
	/**
	 * Add a Virtual Machine to the exclusion list, in the NSX Manager->Manager->Exclusion List
	 * @param vmMoId
	 * @throws Exception
	 */
	public void addVM2ExclusionList(String vmMoId) throws Exception {
		String endPoint = "https://" + vsmIP + "/api/2.1/app/excludelist/" + vmMoId;
		//HttpReq.getInstance().putRequest(endPoint, "Blank.xml");
		HttpReq.getInstance().putRequest(endPoint, "\r"); //Need to send a newline char in request body, "" or null can't work at all (use blank xml could work, too).
	}
	
	/**
	 * Del a Virtual Machine from the Exclusion list,  in the NSX Manager->Manager->Exclusion List
	 * @param vmMoId
	 * @return null if success
	 * @throws Exception
	 */
	public void delVMFromExclusionList(String vmMoId) throws Exception {
//		String results;
		String url = "https://" + vsmIP + "/api/2.1/app/excludelist/" + vmMoId;
//		results = HttpReq.getInstance().delRequest(endPoint);
		this.httpReq.delRequest(url);
//		return results; 
	}
	
	/**
	 * query all Save Configuration info
	 * @return
	 * @throws Exception
	 */
	public String queryAllSaveConfigurationInfo() throws Exception
	{
		//GET https://<nsxmgr-ip>/api/4.0/firewall/globalroot-0/drafts/
		String endPoint = "https://" + vsmIP + "/api/4.0/firewall/globalroot-0/drafts";
System.out.println(endPoint);
		return httpReq.getRequest(endPoint);
	}
	
	/**
	 * query specific Save Configuration Info
	 * @return
	 * @throws Exception
	 */
	public String querySpecificSaveConfigurationInfo(String draftName) throws Exception
	{
		String draftID = getSpecificDraftID(draftName);
		//GET https://<nsxmgr-ip>/api/4.0/firewall/globalroot-0/drafts/<draftID>
		String endPoint = "https://" + vsmIP + "/api/4.0/firewall/globalroot-0/drafts/" + draftID;
System.out.println(endPoint);
		return httpReq.getRequest(endPoint);
	}
	
	/**
	 * get specific Save Configuration id
	 * @param draftName
	 * @return
	 * @throws Exception
	 */
	public String getSpecificDraftID(String draftName) throws Exception
	{
		String queryAllDraftInfo = queryAllSaveConfigurationInfo();
		String draftID = XmlFileOp.findSpecificTagPropertyValue(queryAllDraftInfo, "firewallDraft", "name", draftName, "id");
		return draftID;
	}
	
	
	/**
	 * post data for save configuration
	 * @param xmlContents
	 * @return
	 * @throws Exception
	 */
	public void postSaveConfiguration(String xmlContents) throws Exception
	{
		//POST https://<nsxmgr-ip>/api/4.0/firewall/globalroot-0/drafts
		String url = "https://" + vsmIP + "/api/4.0/firewall/globalroot-0/drafts";
System.out.println(url);
		httpReq.postRequest(xmlContents,url);
	}
	
	/**
	 * 
	 * @param draftName
	 * @param desc
	 * @param preserve    true | false
	 * @throws Exception
	 */
	public void saveConfiguration(String draftName,String desc,String preserve) throws Exception
	{
 		//array of xml keys
 		String [] xmlKeys = {"description", "preserve"};				
 		//array of xml values
 		String [] xmlValues = {desc,preserve};	
		//FierwallSection.xml path
//String xmlFilePath_config = "RestCallXML\\FirewallConfiguration.xml";
 		//generate xmlContents
		String xmlContents = XmlFileOp.generateXMLWithFile(xmlKeys, xmlValues, xmlFilePath_FirewallConfig);
		xmlContents =XmlFileOp.modifyTagPropertyValue(xmlContents, "firewallDraft", "name", draftName);
		
		String temp = getAllFirewallInfos();
		xmlContents = XmlFileOp.appendToEnd(temp, xmlContents, "config");
						
		postSaveConfiguration(xmlContents);
	}
	
	/**
	 * Get the specific contents of firewall's Info
	 * @return
	 * @throws Exception
	 */
	public String getAllFirewallInfos() throws Exception
	{
		StringBuffer sb = new StringBuffer();
		String xmlContents = queryFirewallConfig();
		String tag1 = "layer3Sections";
		String tag2 = "layer2Sections";
		String tag3 = "layer3RedirectSections";
		sb.append(XmlFileOp.getTagContents(tag1, xmlContents));
		sb.append(XmlFileOp.getTagContents(tag2, xmlContents));
		sb.append(XmlFileOp.getTagContents(tag3, xmlContents));
		return sb.toString();
	}
	
	/**
	 * modify the specific Save Configuration
	 * @param draftID
	 * @param xmlContents
	 * @throws Exception
	 */
	public void modifySaveConfiguration(String draftName,String xmlContents) throws Exception
	{
		String draftID = getSpecificDraftID(draftName);
		//PUT https://<nsxmgr-ip>/api/4.0/firewall/globalroot-0/drafts/<draftID>
		String endPoint = "https://" + vsmIP + "/api/4.0/firewall/globalroot-0/drafts/" + draftID;
System.out.println(endPoint);
		//StringReader sr = new StringReader(xmlContents);
		//httpReq.putRequest2(endPoint, sr);
		httpReq.putRequest(xmlContents,endPoint);
	}
	
	/**
	 * edit Save Configuration
	 * @param orgDraftName
	 * @param newDraftName
	 * @param newDesc
	 * @param newPreserve
	 * @throws Exception
	 */
	public void editSaveConfiguration(String orgDraftName, String newDraftName,String newDesc,String newPreserve) throws Exception
	{
 		//array of xml keys
 		String [] xmlKeys = {"description", "preserve"};				
 		//array of xml values
 		String [] xmlValues = {newDesc,newPreserve};	
 		String xmlContents = querySpecificSaveConfigurationInfo(orgDraftName);
 		xmlContents =XmlFileOp.modifyTagPropertyValue(xmlContents, "firewallDraft", "name", newDraftName);
 		//generate xmlContents
 		xmlContents = XmlFileOp.generateXMLWithContents(xmlKeys, xmlValues, xmlContents);
		
		modifySaveConfiguration(orgDraftName, xmlContents);
	}
	
	/**
	 * export Save Configuration
	 * @param draftName
	 * @throws Exception
	 */
	public String exportSaveConfiguration(String draftName) throws Exception
	{
		String draftID = getSpecificDraftID(draftName);
		//GET https://<nsxmgr-ip>/api/4.0/firewall/globalroot-0/drafts/<draftID>/action/export
		String endPoint = "https://" + vsmIP + "/api/4.0/firewall/globalroot-0/drafts/" + draftID + "/action/export";
System.out.println(endPoint);
		return httpReq.getRequest(endPoint);
	}
	
	/**
	 * import Save Configuration
	 * @param draftName
	 * @throws Exception
	 * DON'T WORK
	 */
	public void importSaveConfiguration(String draftName) throws Exception
	{
		String draftID = getSpecificDraftID(draftName);
		//POST https://<nsxmgr-ip>/api/4.0/firewall/globalroot-0/drafts/<draftID>/action/import
		String endPoint = "https://" + vsmIP + "/api/4.0/firewall/globalroot-0/drafts/" + draftID + "/action/import";
System.out.println(endPoint);
		String contents = exportSaveConfiguration(draftName);
		httpReq.postRequest(endPoint, contents);
	}
	
	/**
	 * delete save configuration
	 * @param draftName
	 * @throws Exception
	 */
	public void deleteSaveConfiguration(String draftName) throws Exception
	{
		String draftID = getSpecificDraftID(draftName);
		//DELETE https://<nsxmgr-ip>/api/4.0/firewall/globalroot-0/drafts/<draftID>
		String endPoint = "https://" + vsmIP + "/api/4.0/firewall/globalroot-0/drafts/" + draftID;
System.out.println(endPoint);
		httpReq.delRequest(endPoint);
	}
	
	
	/**
	 * check target Save Configuration whether exist
	 * @param draftName
	 * @return
	 * @throws Exception
	 */
	public boolean isSaveConfigurationExist(String draftName) throws Exception
	{
		boolean flag = false;	
		String queryInfo = queryAllSaveConfigurationInfo();
		
		String target = "name=" + '"' + draftName + '"';
		System.out.println(target);

		if (queryInfo.contains(target)){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * Set up default Section environment
	 * @throws Exception
	 */
	public void setDefaultSectionEnv(String sectionLayer) throws Exception{

		if(!this.isSectionExist(sectionName)){
			this.addSection(sectionName, sectionLayer);
		}	
	}
	
	/**
	 * clean up default section environment
	 * @throws Exception
	 */
	public void cleanDefaultSectionEnv(String sectionLayer) throws Exception{

		if(this.isSectionExist(sectionName)){
			this.deleteSection(sectionLayer, sectionName);
		}	
	}
	
	/**
	 * Set up default Firewall Rule environment
	 * @throws Exception
	 */
	public void  setDefaultFirewallRuleEnv(String sectionLayer) throws Exception {

		String sectionID = this.getSpecificSectionID(sectionName);

		if(!this.isFirewallRuleExist(sectionLayer, sectionID, firewallRuleName)){
			this.addDefaultFirewallRuleOnSpecificSection(firewallRuleName, sectionLayer, sectionName);
		}
	}
	
	/**
	 * Clean up default Firewall Rule environment
	 * @throws Exception
	 */
	public void  cleanDefaultFirewallRuleEnv(String sectionLayer) throws Exception {
		String sectionID = this.getSpecificSectionID(sectionName);
		if(this.isFirewallRuleExist(sectionLayer, sectionID, firewallRuleName)){
			this.deleteFirewallRuleOnSpecificSection(sectionLayer, sectionName, firewallRuleName);
		}
	}
	
	/**
	 * Set up Firewall rule's member
	 * @param memberType
	 * @param member
	 * @throws Exception
	 */
	public void setDefaultMemberEnv(String memberType, Member member,String sectionLayer) throws Exception
	{
		this.addMemberOnFirewallRule(sectionName, sectionLayer, firewallRuleName, memberType, member);
		
	}
	
	/**
	 * set up default Save Configuration 
	 * @throws Exception
	 */
	public void setDefaultSaveConfigEnv() throws Exception {
		String draftName = saveConfigurationName;
		
		if(!this.isSaveConfigurationExist(draftName)){
			this.saveConfiguration(draftName, draftName, "false");
		}
	}
	
	/**
	 * clean up default Save Configuration
	 * @throws Exception
	 */
	public void cleanDefaultSaveConfiEnv() throws Exception{

		String draftName = saveConfigurationName;
		if(this.isSaveConfigurationExist(draftName)){
			this.deleteSaveConfiguration(draftName);
		}
	}
	
}
