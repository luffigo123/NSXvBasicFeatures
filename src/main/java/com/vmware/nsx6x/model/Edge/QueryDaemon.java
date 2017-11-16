package com.vmware.nsx6x.model.Edge;

import javax.xml.bind.annotation.XmlElement;

public class QueryDaemon {
	private String enabled;
	private String port;
	
	@XmlElement(name = "enabled")
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	
	@XmlElement(name = "port")
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	
	/**
	 * 
	 * @param enabled
	 * @param port
	 */
	public QueryDaemon(String enabled, String port) {
		super();
		this.enabled = enabled;
		this.port = port;
	}
	
	public QueryDaemon() {
		super();
	}
	
}
