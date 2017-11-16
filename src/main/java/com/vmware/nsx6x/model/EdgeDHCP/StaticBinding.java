package com.vmware.nsx6x.model.EdgeDHCP;

public class StaticBinding {
	private String macAddress;
	private String vmId;
	private String vnicId;
	private String hostname;
	private String ipAddress;
	private String defaultGateway;
	private String domainName;
	private String primaryNameServer;
	private String secondaryNameServer;
	private String leaseTime;
	
	
	public StaticBinding() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param macAddress
	 * @param vmId
	 * @param vnicId
	 * @param hostname
	 * @param ipAddress
	 * @param defaultGateway
	 * @param domainName
	 * @param primaryNameServer
	 * @param secondaryNameServer
	 * @param leaseTime
	 */
	public StaticBinding(String macAddress, String vmId, String vnicId,
			String hostname, String ipAddress, String defaultGateway,
			String domainName, String primaryNameServer,
			String secondaryNameServer, String leaseTime) {
		super();
		this.macAddress = macAddress;
		this.vmId = vmId;
		this.vnicId = vnicId;
		this.hostname = hostname;
		this.ipAddress = ipAddress;
		this.defaultGateway = defaultGateway;
		this.domainName = domainName;
		this.primaryNameServer = primaryNameServer;
		this.secondaryNameServer = secondaryNameServer;
		this.leaseTime = leaseTime;
	}
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	public String getVmId() {
		return vmId;
	}
	public void setVmId(String vmId) {
		this.vmId = vmId;
	}
	public String getVnicId() {
		return vnicId;
	}
	public void setVnicId(String vnicId) {
		this.vnicId = vnicId;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getDefaultGateway() {
		return defaultGateway;
	}
	public void setDefaultGateway(String defaultGateway) {
		this.defaultGateway = defaultGateway;
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	public String getPrimaryNameServer() {
		return primaryNameServer;
	}
	public void setPrimaryNameServer(String primaryNameServer) {
		this.primaryNameServer = primaryNameServer;
	}
	public String getSecondaryNameServer() {
		return secondaryNameServer;
	}
	public void setSecondaryNameServer(String secondaryNameServer) {
		this.secondaryNameServer = secondaryNameServer;
	}
	public String getLeaseTime() {
		return leaseTime;
	}
	public void setLeaseTime(String leaseTime) {
		this.leaseTime = leaseTime;
	}
	
	

}
