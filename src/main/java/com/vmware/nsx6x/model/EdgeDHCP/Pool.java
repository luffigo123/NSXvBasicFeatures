package com.vmware.nsx6x.model.EdgeDHCP;

public class Pool {
	private String ipRange;
	private String defaultGateway;
	private String subnetMask;
	private String domainName;
	private String primaryNameServer;
	private String secondaryNameServer;
	private String leaseTime;


	
	public Pool() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param ipRange
	 * @param defaultGateway
	 * @param subnetMask
	 * @param domainName
	 * @param primaryNameServer
	 * @param secondaryNameServer
	 * @param leaseTime
	 */
	public Pool(String ipRange, String defaultGateway, String subnetMask,
			String domainName, String primaryNameServer,
			String secondaryNameServer, String leaseTime) {
		super();
		this.ipRange = ipRange;
		this.defaultGateway = defaultGateway;
		this.subnetMask = subnetMask;
		this.domainName = domainName;
		this.primaryNameServer = primaryNameServer;
		this.secondaryNameServer = secondaryNameServer;
		this.leaseTime = leaseTime;
	}
	
	
	/**
	 * 
	 * @param ipRange
	 * @param defaultGateway
	 * @param domainName
	 * @param primaryNameServer
	 * @param secondaryNameServer
	 * @param leaseTime
	 */
	public Pool(String ipRange, String defaultGateway, String domainName,
			String primaryNameServer, String secondaryNameServer,
			String leaseTime) {
		super();
		this.ipRange = ipRange;
		this.defaultGateway = defaultGateway;
		this.domainName = domainName;
		this.primaryNameServer = primaryNameServer;
		this.secondaryNameServer = secondaryNameServer;
		this.leaseTime = leaseTime;
	}
	
	public String getIpRange() {
		return ipRange;
	}
	public void setIpRange(String ipRange) {
		this.ipRange = ipRange;
	}
	public String getDefaultGateway() {
		return defaultGateway;
	}
	public void setDefaultGateway(String defaultGateway) {
		this.defaultGateway = defaultGateway;
	}
	public String getSubnetMask() {
		return subnetMask;
	}
	public void setSubnetMask(String subnetMask) {
		this.subnetMask = subnetMask;
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
