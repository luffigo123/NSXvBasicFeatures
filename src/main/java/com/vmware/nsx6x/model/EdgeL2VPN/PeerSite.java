package com.vmware.nsx6x.model.EdgeL2VPN;

public class PeerSite {
	private String name;
	private String description;
	private String userId;
	private String password;
	private String vnics;
	private String gatewayIpAddress;
	private String enabled;
	
	public PeerSite() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PeerSite(String name, String description, String userId,
			String password, String vnics, String gatewayIpAddress,
			String enabled) {
		super();
		this.name = name;
		this.description = description;
		this.userId = userId;
		this.password = password;
		this.vnics = vnics;
		this.gatewayIpAddress = gatewayIpAddress;
		this.enabled = enabled;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getVnics() {
		return vnics;
	}
	public void setVnics(String vnics) {
		this.vnics = vnics;
	}
	public String getGatewayIpAddress() {
		return gatewayIpAddress;
	}
	public void setGatewayIpAddress(String gatewayIpAddress) {
		this.gatewayIpAddress = gatewayIpAddress;
	}
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	
}
