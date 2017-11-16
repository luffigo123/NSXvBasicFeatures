package com.vmware.nsx6x.service.Edge;

import java.util.ArrayList;
import java.util.HashMap;

import com.vmware.nsx6x.model.EdgeL2VPN.L2VPN;
import com.vmware.nsx6x.model.EdgeL2VPN.L2VPNClient;
import com.vmware.nsx6x.model.EdgeL2VPN.L2VPNServer;
import com.vmware.nsx6x.model.EdgeL2VPN.PeerSite;
import com.vmware.nsx6x.utils.Config;
import com.vmware.nsx6x.utils.DefaultEnvironment;
import com.vmware.nsx6x.utils.HttpReq;
import com.vmware.nsx6x.utils.XmlFileOp;

public class EdgeL2VPNManager{
	private String vsmIP;
	private HttpReq httpReq;
//	private HeaderReq headerReq;
	
	public EdgeManger edgeMgr;
	public String edgeName;
	
	private String uplinkIP = DefaultEnvironment.UplinkIP;
	
	private String filepath = Config.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	private String xmlPath_L2VPNServer = filepath + "/RestCallXML/L2VPNServer.xml";
	private String xmlPath_L2VPNClient = filepath + "/RestCallXML/L2VPNClient.xml";
	
	
	public EdgeL2VPNManager(){
		vsmIP = DefaultEnvironment.VSMIP;
		httpReq = HttpReq.getInstance();
//		headerReq = HeaderReq.getInstance();
		
		edgeMgr = new EdgeManger();
		edgeName = edgeMgr.edgeName;
		
	}
	
	//get default L2VPN Server instance
	public L2VPNServer getDefaultL2VPNServerInstance(){
		ArrayList<PeerSite> psList = new ArrayList<PeerSite>();
		PeerSite peerSite = new PeerSite("PeerSiteName","Desc","userId","password","8","2.2.2.2","true");
		psList.add(peerSite);
		L2VPNServer server = new L2VPNServer(uplinkIP,"443","AES128-GCM-SHA256","",psList);
		return server;
	}
	
	
	/**
	 * query L2VPN info
	 * @param edgeName
	 * @return
	 */
	public String queryL2VPNInfo(String edgeName){
		String edgeId = edgeMgr.getEdgeIDbyName(edgeName);
		//GET https://<nsxmgr-ip>/api/4.0/edges/{edgeId}/l2vpn/config/
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/l2vpn/config";
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
	
	
	//send put request to configure L2VPN
	private void putL2VPN(String edgeName,String xmlContents, HashMap<String,String> header){
		
		String edgeId = edgeMgr.getEdgeIDbyName(edgeName);
		//PUT https://<nsxmgr-ip>/api/4.0/edges/{edgeId}/l2vpn/config/
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/l2vpn/config";
//System.out.println(url);
//
//		StringReader sr = new StringReader(xmlContents);
//		String functionName = "Edge L2PVN";
//		String requestParameters = "";
//		try {
//			headerReq.putData2(sr, url, requestParameters, new StringWriter(), functionName, header);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		this.httpReq.putRequestWithHeader(xmlContents, url, header);
	}
	
	/**
	 * configure L2VPN
	 * @param edgeName
	 * @param l2vpn
	 */
	public void configureL2VPN(String edgeName, L2VPN l2vpn){
		HashMap<String,String> header = new HashMap<String,String>();
		header.put("Content-Type", "application/xml");
		
		String xmlContents = "";
		
		L2VPNServer server =  l2vpn.getLevpnServer();
		L2VPNClient client = l2vpn.getL2vpnClient();
		
		//generate server's content
		if(server != null){
//			String ipSecVPNFilePath = "RestCallXML\\L2VPNServer.xml";
			String [] xmlKeys = {"enabled","logging_enable","logging_level","listenerIp","listenerPort","encryptionAlgorithm","serverCertificate"};
			String [] xmlValues = {l2vpn.getEnabled(), l2vpn.getLogging_enable(), l2vpn.getLogging_level(),server.getListenerIp(),server.getListenerPort(),server.getEncryptionAlgorithm(),
					server.getServerCertificate()};
			xmlContents = XmlFileOp.generateXMLWithFile(xmlKeys, xmlValues, xmlPath_L2VPNServer);
			
			//check whether have certificate
			if("".equalsIgnoreCase(server.getServerCertificate().trim()) || server.getServerCertificate() == null)
				xmlContents = xmlContents.replace("<serverCertificate></serverCertificate>", "");
			
			//generate PeerSites' content
			String peerSite = "<peerSite><name></name><description></description><l2VpnUser><userId></userId><password></password></l2VpnUser><vnics><index></index></vnics><egressOptimization><gatewayIpAddress></gatewayIpAddress></egressOptimization><enabled></enabled></peerSite>";
			String peerSitesContent = "";
			String [] peerSiteKeys = {"name","description","l2VpnUser_userId","l2VpnUser_password","index","gatewayIpAddress","enabled"};
			for(int i =0 ; i < server.getPeerSiteList().size(); i++){
				PeerSite ps = server.getPeerSiteList().get(i);
				String temp =  "";
				String [] peerSiteValues = {ps.getName(),ps.getDescription(),ps.getUserId(),ps.getPassword(),ps.getVnics(),ps.getGatewayIpAddress(),ps.getEnabled()};
				temp = XmlFileOp.generateXMLWithContents(peerSiteKeys, peerSiteValues, peerSite);
				peerSitesContent = peerSitesContent + temp;
			}

			xmlContents = XmlFileOp.appendToEnd(peerSitesContent, xmlContents, "peerSites");
		}
		
		//generate client's content
		if(client != null){
//			String ipSecVPNFilePath = "RestCallXML\\L2VPNClient.xml";
			String [] xmlKeys = {"enabled","logging_enable","logging_level","serverAddress","serverPort","vnic","gatewayIpAddress","caCertificate","encryptionAlgorithm","l2VpnUser_userId",
					"l2VpnUser_password","proxySetting_type","proxySetting_address","proxySetting_port","proxySetting_userName","proxySetting_password"};
			String [] xmlValues = {l2vpn.getEnabled(), l2vpn.getLogging_enable(), l2vpn.getLogging_level(),client.getServerAddress(),client.getServerPort(),client.getVnic(),client.getGatewayIpAddress(),
					client.getCaCertificate(),client.getEncryptionAlgorithm(),client.getL2VpnUser_userId(),client.getL2VpnUser_password(),client.getProxyType(),client.getProxyAddress()};
			xmlContents = XmlFileOp.generateXMLWithFile(xmlKeys, xmlValues, xmlPath_L2VPNClient);
			
			//check whether have certificate
			if("".equalsIgnoreCase(client.getCaCertificate().trim()) || client.getCaCertificate() == null)
				xmlContents = xmlContents.replace("<caCertificate></caCertificate>", "");
			
		}
		
		this.putL2VPN(edgeName, xmlContents, header);
	}
	
	/**
	 * check Server Mode whether exist
	 * @param edgeName
	 * @param listenerIP
	 * @return
	 */
	public boolean isL2VPNServerExist(String edgeName ,String listenerIP){
		boolean flag = false;
		String queryInfo = this.queryL2VPNInfo(edgeName);
		String targetTag = "listenerIp";
		if(XmlFileOp.checkTagExist(queryInfo, targetTag) && XmlFileOp.checkGivenStringEqualTagValue(queryInfo, targetTag, listenerIP)){
			flag =true;
		}
		return flag;
	}
	
	/**
	 * check Client Mode whether exist
	 * @param edgeName
	 * @param serverAddress
	 * @return
	 */
	public boolean isL2VPNClientExist(String edgeName, String serverAddress){
		boolean flag = false;
		String queryInfo = this.queryL2VPNInfo(edgeName);
		String targetTag = "<serverAddress>";
		if(XmlFileOp.checkTagExist(queryInfo, targetTag) && XmlFileOp.checkGivenStringEqualTagValue(queryInfo, targetTag, serverAddress)){
			flag =true;
		}
		return flag;
	}
	

	
	public void deleteL2VPN(String edgeName){
		String edgeId = edgeMgr.getEdgeIDbyName(edgeName);
		//DELETE https://<nsxmgr-ip>/api/4.0/edges/{edgeId}/l2vpn/config/
		String url = "https://" + vsmIP + "/api/4.0/edges/" + edgeId + "/l2vpn/config";
System.out.println(url);
		try {
			httpReq.delRequest(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
