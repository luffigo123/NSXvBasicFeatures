package com.vmware.nsx6x.model.VXLAN;

import javax.xml.bind.annotation.XmlElement;

public class ResourceConfig {
	private String resourceId;

	@XmlElement(name = "resourceId")
	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	/**
	 * 
	 * @param resourceId
	 */
	public ResourceConfig(String resourceId) {
		super();
		this.resourceId = resourceId;
	}

	public ResourceConfig() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
