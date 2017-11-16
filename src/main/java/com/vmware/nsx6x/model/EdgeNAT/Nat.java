package com.vmware.nsx6x.model.EdgeNAT;

public class Nat {
	private String action;
	private String vnic;
	private String originalAddress;
	private String translatedAddress;
	private String loggingEnabled;
	private String enabled;
	private String description;
	private String protocol;
	private String translatedPort;
	private String originalPort;
	
	public Nat() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Nat(String action, String vnic, String originalAddress,
			String translatedAddress, String loggingEnabled, String enabled,
			String description, String protocol, String translatedPort,
			String originalPort) {
		super();
		this.action = action;
		this.vnic = vnic;
		this.originalAddress = originalAddress;
		this.translatedAddress = translatedAddress;
		this.loggingEnabled = loggingEnabled;
		this.enabled = enabled;
		this.description = description;
		this.protocol = protocol;
		this.translatedPort = translatedPort;
		this.originalPort = originalPort;
	}
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getVnic() {
		return vnic;
	}
	public void setVnic(String vnic) {
		this.vnic = vnic;
	}
	public String getOriginalAddress() {
		return originalAddress;
	}
	public void setOriginalAddress(String originalAddress) {
		this.originalAddress = originalAddress;
	}
	public String getTranslatedAddress() {
		return translatedAddress;
	}
	public void setTranslatedAddress(String translatedAddress) {
		this.translatedAddress = translatedAddress;
	}
	public String getLoggingEnabled() {
		return loggingEnabled;
	}
	public void setLoggingEnabled(String loggingEnabled) {
		this.loggingEnabled = loggingEnabled;
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
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getTranslatedPort() {
		return translatedPort;
	}
	public void setTranslatedPort(String translatedPort) {
		this.translatedPort = translatedPort;
	}
	public String getOriginalPort() {
		return originalPort;
	}
	public void setOriginalPort(String originalPort) {
		this.originalPort = originalPort;
	}
}
