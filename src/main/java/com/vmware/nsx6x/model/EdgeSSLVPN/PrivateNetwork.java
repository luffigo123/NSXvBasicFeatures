package com.vmware.nsx6x.model.EdgeSSLVPN;

public class PrivateNetwork {
	private String network;
	private String enabled;
	private String ports;
	private String optimize;
	private String description;
	
	public PrivateNetwork() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PrivateNetwork(String network, String enabled, String ports,
			String optimize, String description) {
		super();
		this.network = network;
		this.enabled = enabled;
		this.ports = ports;
		this.optimize = optimize;
		this.description = description;
	}
	public String getNetwork() {
		return network;
	}
	public void setNetwork(String network) {
		this.network = network;
	}
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	public String getPorts() {
		return ports;
	}
	public void setPorts(String ports) {
		this.ports = ports;
	}
	public String getOptimize() {
		return optimize;
	}
	public void setOptimize(String optimize) {
		this.optimize = optimize;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
