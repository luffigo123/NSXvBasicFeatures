package com.vmware.nsx6x.model.EdgeDHCP;

public class DHCPRelay {
	private String relayServer_IPSetID;
	private String relayServer_IPAddress;
	private String relayServer_Dns;
	private String relayAgent_vnicIndex;
	private String relayAgent_giAddress;
	
	
	public DHCPRelay() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public DHCPRelay(String relayServer_IPSetID, String relayServer_IPAddress,
			String relayServer_Dns, String relayAgent_vnicIndex,
			String relayAgent_giAddress) {
		super();
		this.relayServer_IPSetID = relayServer_IPSetID;
		this.relayServer_IPAddress = relayServer_IPAddress;
		this.relayServer_Dns = relayServer_Dns;
		this.relayAgent_vnicIndex = relayAgent_vnicIndex;
		this.relayAgent_giAddress = relayAgent_giAddress;
	}
	public String getRelayServer_IPSetID() {
		return relayServer_IPSetID;
	}
	public void setRelayServer_IPSetID(String relayServer_IPSetID) {
		this.relayServer_IPSetID = relayServer_IPSetID;
	}
	public String getRelayServer_IPAddress() {
		return relayServer_IPAddress;
	}
	public void setRelayServer_IPAddress(String relayServer_IPAddress) {
		this.relayServer_IPAddress = relayServer_IPAddress;
	}
	public String getRelayServer_Dns() {
		return relayServer_Dns;
	}
	public void setRelayServer_Dns(String relayServer_Dns) {
		this.relayServer_Dns = relayServer_Dns;
	}
	public String getRelayAgent_vnicIndex() {
		return relayAgent_vnicIndex;
	}
	public void setRelayAgent_vnicIndex(String relayAgent_vnicIndex) {
		this.relayAgent_vnicIndex = relayAgent_vnicIndex;
	}
	public String getRelayAgent_giAddress() {
		return relayAgent_giAddress;
	}
	public void setRelayAgent_giAddress(String relayAgent_giAddress) {
		this.relayAgent_giAddress = relayAgent_giAddress;
	}
	
	

}
