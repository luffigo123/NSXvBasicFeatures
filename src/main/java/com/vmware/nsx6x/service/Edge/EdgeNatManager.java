package com.vmware.nsx6x.service.Edge;

import java.util.HashMap;

import com.vmware.nsx6x.model.EdgeNAT.Nat;
import com.vmware.nsx6x.utils.Config;
import com.vmware.nsx6x.utils.DefaultEnvironment;
import com.vmware.nsx6x.utils.HttpReq;
import com.vmware.nsx6x.utils.TestData;
import com.vmware.nsx6x.utils.XmlFileOp;



public class EdgeNatManager{
	private String vsmIP;
	private HttpReq httpReq;
//	private HeaderReq headerReq;
	
	public EdgeManger edgeMgr;
	public String edgeName;
	
	public String uplinkIP = DefaultEnvironment.UplinkIP;
	public String internalIPRange = "192.168.1.2-192.168.1.10";
	
	private String filepath = Config.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	private String xmlPath_NAT = filepath + "/RestCallXML/NAT.xml";
	
	
	public EdgeNatManager(){
		vsmIP = DefaultEnvironment.VSMIP;
		httpReq = HttpReq.getInstance();
//		headerReq = HeaderReq.getInstance();
		
		edgeMgr = new EdgeManger();
		edgeName = edgeMgr.edgeName;
		
	}
	
	//get default dnat rule instance
	public Nat getDefaultDNatInsatnce(){
		Nat dnat = new Nat("dnat","0",uplinkIP,internalIPRange,"true","true",TestData.NativeString,"any","any","any");
		return dnat;
	}
	
	//get default snat rule instance
	public Nat getDefaultSNatInsatnce(){
		Nat snat = new Nat("snat","1",internalIPRange,uplinkIP,"true","true",TestData.NativeString,"any","any","any");
		return snat;
	}
	/**
	 * query NAT Rules information for Edge
	 * @param edgeName
	 * @return
	 */
	public String queryNatInfo(String edgeName){
		String edgeId = edgeMgr.getEdgeIDbyName(edgeName);
		//GET https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/nat/config
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/nat/config";
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

	//send post request to append NAT rules
	private void postNATRule(String edgeName, String xmlContents, HashMap<String,String> header){
		String edgeId = edgeMgr.getEdgeIDbyName(edgeName);
		//POST https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/nat/config/rules
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/nat/config/rules";
//System.out.println(url);
//
//		StringReader sr = new StringReader(xmlContents);
//		String functionName = "Edge NAT";
//		String requestParameters = "";
//		try {
//			headerReq.postData(sr, url, requestParameters, new StringWriter(), functionName, header);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		this.httpReq.postRequestWithHeader(xmlContents, url, header);
	}
	
	/**
	 * append NAT Rules
	 * @param edgeName
	 * @param nat
	 */
	public void appendNATRules(String edgeName, Nat nat){
		HashMap<String,String> header = new HashMap<String,String>();
		header.put("Content-Type", "application/xml");
		
//		String xmlFilePath = "RestCallXML\\NAT.xml";
		String [] xmlKeys = {"action","vnic","originalAddress","translatedAddress","loggingEnabled","enabled","description","protocol","translatedPort","originalPort"};
		String [] xmlValues = {nat.getAction(),nat.getVnic(),nat.getOriginalAddress(),nat.getTranslatedAddress(),nat.getLoggingEnabled(),nat.getEnabled(),
				nat.getDescription(),nat.getProtocol(),nat.getTranslatedPort(),nat.getOriginalPort()};
		String xmlContents = XmlFileOp.generateXMLWithFile(xmlKeys, xmlValues, xmlPath_NAT);
		
		this.postNATRule(edgeName, xmlContents, header);
	}
	
	/**
	 * delete specific NAT Rule
	 * @param edgeName
	 * @param action
	 */
	public void deleteNATRule(String edgeName,String originalAddress){
		String edgeId = edgeMgr.getEdgeIDbyName(edgeName);
		String ruleId = this.getNATRuleID(edgeName, originalAddress);
		//DELETE https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/nat/config/rules/ruleID
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/nat/config/rules/" + ruleId;
System.out.println(url);
		try {
			httpReq.delRequest(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * get specific NAT Rule id
	 * @param edgeName
	 * @param originalAddress
	 * @return
	 */
	public String getNATRuleID(String edgeName, String originalAddress){
 		String id = "";
		String queryInfo = this.queryNatInfo(edgeName);
		id = XmlFileOp.findNearestBeforeTagValue3(queryInfo, "ruleId", "originalAddress", originalAddress);
		return id;
	}
	
	/**
	 * check whether the NAT Rule exist
	 * @param edgeName
	 * @param originalAddress
	 * @return
	 */
	public boolean isNATRuleExist(String edgeName, String originalAddress){
		boolean flag = false;
		String queryInfo = this.queryNatInfo(edgeName);
		if(queryInfo.contains(originalAddress)){
			String targetTag = "originalAddress";
			flag = XmlFileOp.checkGivenStringEqualTagValue(queryInfo, targetTag, originalAddress);
		}
		return flag;
	}
	
}
