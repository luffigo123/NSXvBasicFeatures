package com.vmware.nsx6x.model.Edge;

import javax.xml.bind.annotation.XmlElement;



public class Appliances {
	private String applianceSize;
	private Appliance appliance;
	private String deployAppliances;
	
	@XmlElement(name = "applianceSize")
	public String getApplianceSize() {
		return applianceSize;
	}
	public void setApplianceSize(String applianceSize) {
		this.applianceSize = applianceSize;
	}
	
	@XmlElement(name = "appliance")
	public Appliance getAppliance() {
		return appliance;
	}
	public void setAppliance(Appliance appliance) {
		this.appliance = appliance;
	}
	
	@XmlElement(name = "deployAppliances")
	public String getDeployAppliances() {
		return deployAppliances;
	}
	public void setDeployAppliances(String deployAppliances) {
		this.deployAppliances = deployAppliances;
	}
	
	/**
	 * 
	 * @param applianceSize
	 * @param appliance
	 * @param deployAppliances
	 */
	public Appliances(String applianceSize, Appliance appliance,
			String deployAppliances) {
		super();
		this.applianceSize = applianceSize;
		this.appliance = appliance;
		this.deployAppliances = deployAppliances;
	}
	
	public Appliances() {
		super();

	}
}
