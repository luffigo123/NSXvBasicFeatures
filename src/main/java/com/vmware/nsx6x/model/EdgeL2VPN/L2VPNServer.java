package com.vmware.nsx6x.model.EdgeL2VPN;

import java.util.ArrayList;

public class L2VPNServer {
	private String listenerIp;
	private String listenerPort;
	private String encryptionAlgorithm;
	private String serverCertificate;
	private ArrayList<PeerSite> peerSiteList;
	
	public L2VPNServer() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * constructor 
	 * @param listenerIp 			<!-- Required. IP of external interface on which L2VPN service to listen on -->
	 * @param listenerPort			<!-- optional. 443 by default. Port on which L2VPN service to listen on -->
	 * @param encryptionAlgorithm	<!-- Mandatory. Supported ciphers are ,"AES128-GCM-SHA256" and "NULL-MD5"-->
	 * @param serverCertificate		<!-- Optional. If not specified server will use its default(selfsigned) certificate-->
	 * @param peerSiteList			PeerStie object
	 */
	public L2VPNServer(String listenerIp, String listenerPort,
			String encryptionAlgorithm, String serverCertificate,
			ArrayList<PeerSite> peerSiteList) {
		super();
		this.listenerIp = listenerIp;
		this.listenerPort = listenerPort;
		this.encryptionAlgorithm = encryptionAlgorithm;
		this.serverCertificate = serverCertificate;
		this.peerSiteList = peerSiteList;
	}
	
	public String getListenerIp() {
		return listenerIp;
	}
	public void setListenerIp(String listenerIp) {
		this.listenerIp = listenerIp;
	}
	public String getListenerPort() {
		return listenerPort;
	}
	public void setListenerPort(String listenerPort) {
		this.listenerPort = listenerPort;
	}
	public String getEncryptionAlgorithm() {
		return encryptionAlgorithm;
	}
	public void setEncryptionAlgorithm(String encryptionAlgorithm) {
		this.encryptionAlgorithm = encryptionAlgorithm;
	}
	public String getServerCertificate() {
		return serverCertificate;
	}
	public void setServerCertificate(String serverCertificate) {
		this.serverCertificate = serverCertificate;
	}
	public ArrayList<PeerSite> getPeerSiteList() {
		return peerSiteList;
	}
	public void setPeerSiteList(ArrayList<PeerSite> peerSiteList) {
		this.peerSiteList = peerSiteList;
	}
}
