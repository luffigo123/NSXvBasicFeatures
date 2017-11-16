package com.vmware.nsx6x.service.Edge;

import java.util.ArrayList;
import java.util.HashMap;

import com.vmware.nsx6x.model.EdgeIPSecVPN.IPSecVPN;
import com.vmware.nsx6x.model.EdgeIPSecVPN.Site;
import com.vmware.nsx6x.utils.Config;
import com.vmware.nsx6x.utils.DefaultEnvironment;
import com.vmware.nsx6x.utils.HttpReq;
import com.vmware.nsx6x.utils.TestData;
import com.vmware.nsx6x.utils.XmlFileOp;

public class IPSecVPNManager{
	private String vsmIP;
	private HttpReq httpReq;
//	private HeaderReq headerReq;
	
	public EdgeManger edgeMgr;
	public String edgeName;
	
	public String localIP = DefaultEnvironment.UplinkIP;
	public String siteName = "Site001" + TestData.NativeString;
	
	private String filepath = Config.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	private String xmlPath_IPSecVPN = filepath + "/RestCallXML/IPSecVPN.xml";
	private String xmlPath_IPSecVPNSite = filepath + "/RestCallXML/IPSecVPNSite.xml";
	
	public IPSecVPNManager(){
		vsmIP = DefaultEnvironment.VSMIP;
		httpReq = HttpReq.getInstance();
//		headerReq = HeaderReq.getInstance();
		
		edgeMgr = new EdgeManger();
		edgeName = edgeMgr.edgeName;
		
	}
	
	//get default IPSec VPN instance
	public IPSecVPN getDefaultIPSecVPNInstance(){
		ArrayList<Site> siteList = new ArrayList<Site>();
		Site site1 = new Site("true",siteName,"aaa",localIP,"bbb","any","aes","true","dh2","1.1.1.0/24","2.2.2.0/24","123","PSK");
		Site site2 = new Site("true",siteName,"ccc",localIP,"ddd","4.4.4.4","aes","true","dh2","3.3.3.0/24","4.4.4.0/24","123","PSK");
		siteList.add(site1);
		siteList.add(site2);
		IPSecVPN ipsecvpn = new IPSecVPN("true", "true", "info", "456", siteList);
		return ipsecvpn;
	}
	
	
	/**
	 * query IPSec VPN info
	 * @param edgeName
	 * @return
	 */
	public String queryIPSecVPNInfo(String edgeName){
		String edgeId = edgeMgr.getEdgeIDbyName(edgeName);
		//GET https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/ipsec/config
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/ipsec/config";
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
	
	//send put request to configure IPSecVPN
	private void putIPSecVPN(String edgeName, String xmlContents, HashMap<String,String> header){
		String edgeId = edgeMgr.getEdgeIDbyName(edgeName);
		//PUT https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/ipsec/config
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/ipsec/config";
//System.out.println(url);

//		StringReader sr = new StringReader(xmlContents);
//		String functionName = "Edge Firewall";
//		String requestParameters = "";
//		try {
//			headerReq.putData2(sr, url, requestParameters, new StringWriter(), functionName, header);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		this.httpReq.putRequestWithHeader(xmlContents, url, header);
	}
	
	/**
	 * configure the IPSec VPN
	 * @param edgeName
	 */
	public void configureIPSecVPN(String edgeName, IPSecVPN ipsecVPN){
		HashMap<String,String> header = new HashMap<String,String>();
		header.put("Content-Type", "application/xml");
		
//String ipSecVPNFilePath = "RestCallXML\\IPSecVPN.xml";
		String [] xmlKeys = {"ipsec_enabled","logging_enable","logLevel","global_psk"};
		String [] xmlValues = {ipsecVPN.getEnabled(),ipsecVPN.getLogging_enable(),ipsecVPN.getLogLevel(),ipsecVPN.getGlobal_psk()};
		String xmlContents = XmlFileOp.generateXMLWithFile(xmlKeys, xmlValues, xmlPath_IPSecVPN);
		
		ArrayList<Site> siteList = ipsecVPN.getSiteList();
		String siteString = "";

		//generate sites string
		if(siteList != null){
//String siteFilePath = "RestCallXML\\IPSecVPNSite.xml";
			String [] sitexmlKeys = {"enabled","name","localId","localIp","peerId","peerIp","encryptionAlgorithm","enablePfs","dhGroup",
					"localSubnets_subnet","peerSubnets_subnet","authenticationMode","psk"};
			for(int i=0 ; i< siteList.size(); i++){
				Site site = siteList.get(i);
				String [] sitexmlValues = {site.getEnabled(),site.getName(),site.getLocalId(),site.getLocalIp(),site.getPeerId(),site.getPeerIp(),site.getEncryptionAlgorithm(),
						site.getEnablePfs(),site.getDhGroup(),site.getLocalSubnet(),site.getPeerSubnet(),site.getAuthenticationMode(),site.getPsk()};
				String temp = XmlFileOp.generateXMLWithFile(sitexmlKeys, sitexmlValues, xmlPath_IPSecVPNSite);
				String pskString = "<psk>" + site.getPsk() + "</psk>";
				//if peerIp equals "any", then remove the <psk>******</psk>
//				if(site.getPeerIp().equalsIgnoreCase("any")){
//					temp = temp.replace(pskString, "");
//				}
				siteString = siteString + temp;
			}
			siteString = "<sites>" + siteString + "</sites>";
		}
		
		xmlContents = XmlFileOp.appendToEnd(siteString, xmlContents, "ipsec");
		
		this.putIPSecVPN(edgeName, xmlContents, header);
	}
	
	/**
	 * delete IPSecVPN
	 * @param edgeName
	 */
	public void deleteIPSecVPN(String edgeName){
		String edgeId = edgeMgr.getEdgeIDbyName(edgeName);
		//DELETE https://<nsxmgr-ip>/api/4.0/edges/<edgeId>/ipsec/config/
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/ipsec/config";
System.out.println(url);
		try {
			httpReq.delRequest(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * check whether site is exist
	 * @param edgeName
	 * @param siteName
	 * @return
	 */
	public boolean isSiteExist(String edgeName, String siteName){
		boolean flag = false;
		String queryInfo = this.queryIPSecVPNInfo(edgeName);
		if(queryInfo.contains(siteName)){
			String targetTag = "name";
			flag = XmlFileOp.checkGivenStringEqualTagValue(queryInfo, targetTag, siteName);
		}
		return flag;
	}
	

	
	/**
	 * setup default IPSecVPN environment
	 */
	public void setDefaultIPSecVPNEnv(){
		if(!this.isSiteExist(edgeName, siteName)){
			IPSecVPN ipSecVPN = this.getDefaultIPSecVPNInstance();
			this.configureIPSecVPN(edgeName, ipSecVPN);
		}
	}
	
	/**
	 * clean up default IPSecVPN rule environment
	 */
	public void cleanDefaultIPSecVPNEnv(){
		if(this.isSiteExist(edgeName, siteName)){
			this.deleteIPSecVPN(edgeName);
		}
	}
}
