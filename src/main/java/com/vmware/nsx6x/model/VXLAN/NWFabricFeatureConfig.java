package com.vmware.nsx6x.model.VXLAN;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name = "nwFabricFeatureConfig")
@XmlSeeAlso({})
public class NWFabricFeatureConfig {
	private ResourceConfig resourceConfig;

	@XmlElement(name = "resourceConfig")
	public ResourceConfig getResourceConfig() {
		return resourceConfig;
	}
	public void setResourceConfig(ResourceConfig resourceConfig) {
		this.resourceConfig = resourceConfig;
	}
	
	/**
	 * 
	 * @param resourceConfig
	 */
	public NWFabricFeatureConfig(ResourceConfig resourceConfig) {
		super();
		this.resourceConfig = resourceConfig;
	}

	public NWFabricFeatureConfig() {
		super();

	}
	
}
