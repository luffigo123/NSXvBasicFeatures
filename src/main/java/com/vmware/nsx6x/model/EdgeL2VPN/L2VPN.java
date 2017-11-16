package com.vmware.nsx6x.model.EdgeL2VPN;


public class L2VPN {
	private String enabled;
	private String logging_enable;
	private String logging_level;
	private L2VPNServer levpnServer;
	private L2VPNClient l2vpnClient;
	
	public L2VPN() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param enabled
	 * @param logging_enable
	 * @param logging_level
	 * @param levpnServer
	 * @param l2vpnClient
	 */
	public L2VPN(String enabled, String logging_enable, String logging_level,
			L2VPNServer levpnServer, L2VPNClient l2vpnClient) {
		super();
		this.enabled = enabled;
		this.logging_enable = logging_enable;
		this.logging_level = logging_level;
		this.levpnServer = levpnServer;
		this.l2vpnClient = l2vpnClient;
	}
	
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	public String getLogging_enable() {
		return logging_enable;
	}
	public void setLogging_enable(String logging_enable) {
		this.logging_enable = logging_enable;
	}
	public String getLogging_level() {
		return logging_level;
	}
	public void setLogging_level(String logging_level) {
		this.logging_level = logging_level;
	}
	public L2VPNServer getLevpnServer() {
		return levpnServer;
	}
	public void setLevpnServer(L2VPNServer levpnServer) {
		this.levpnServer = levpnServer;
	}
	public L2VPNClient getL2vpnClient() {
		return l2vpnClient;
	}
	public void setL2vpnClient(L2VPNClient l2vpnClient) {
		this.l2vpnClient = l2vpnClient;
	}
	
}
