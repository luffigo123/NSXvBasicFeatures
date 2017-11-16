package com.vmware.nsx6x.model.Edge;

import javax.xml.bind.annotation.XmlElement;

public class AutoConfiguration {
	private String enabled;
	private String rulePriority;
	
	@XmlElement(name = "enabled")
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	
	@XmlElement(name = "rulePriority")
	public String getRulePriority() {
		return rulePriority;
	}
	public void setRulePriority(String rulePriority) {
		this.rulePriority = rulePriority;
	}
	
	
	/**
	 * 
	 * @param enabled
	 * @param rulePriority
	 */
	public AutoConfiguration(String enabled, String rulePriority) {
		super();
		this.enabled = enabled;
		this.rulePriority = rulePriority;
	}
	
	
	public AutoConfiguration() {
		super();

	}
	
}
