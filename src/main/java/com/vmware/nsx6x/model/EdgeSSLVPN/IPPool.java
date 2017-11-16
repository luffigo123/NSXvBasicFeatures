package com.vmware.nsx6x.model.EdgeSSLVPN;

public class IPPool {
	private String ipRange;
	private String netmask;
	private String gateway;
	private String enabled;
	private String description;
	private String primaryDns;
	private String secondaryDns;
	private String dnsSuffix;
	private String winsServer;
	
	public IPPool(){
		super();
	}
	
	public IPPool(String ipRange, String netmask, String gateway,
			String enabled, String description, String primaryDns,
			String secondaryDns, String dnsSuffix, String winsServer) {
		super();
		this.ipRange = ipRange;
		this.netmask = netmask;
		this.gateway = gateway;
		this.enabled = enabled;
		this.description = description;
		this.primaryDns = primaryDns;
		this.secondaryDns = secondaryDns;
		this.dnsSuffix = dnsSuffix;
		this.winsServer = winsServer;
	}
	
	public String getIpRange() {
		return ipRange;
	}

	public void setIpRange(String ipRange) {
		this.ipRange = ipRange;
	}

	public String getNetmask() {
		return netmask;
	}

	public void setNetmask(String netmask) {
		this.netmask = netmask;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPrimaryDns() {
		return primaryDns;
	}

	public void setPrimaryDns(String primaryDns) {
		this.primaryDns = primaryDns;
	}

	public String getSecondaryDns() {
		return secondaryDns;
	}

	public void setSecondaryDns(String secondaryDns) {
		this.secondaryDns = secondaryDns;
	}

	public String getDnsSuffix() {
		return dnsSuffix;
	}

	public void setDnsSuffix(String dnsSuffix) {
		this.dnsSuffix = dnsSuffix;
	}

	public String getWinsServer() {
		return winsServer;
	}

	public void setWinsServer(String winsServer) {
		this.winsServer = winsServer;
	}
	
}
