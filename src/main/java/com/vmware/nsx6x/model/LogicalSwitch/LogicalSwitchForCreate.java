package com.vmware.nsx6x.model.LogicalSwitch;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "virtualWireCreateSpec")
public class LogicalSwitchForCreate implements LogicalSwitch{
	private String name;
	private String description;
	private String controlPlaneMode;
	private String guestVlanAllowed;
	private String tenantId;
	
	@XmlElement(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@XmlElement(name = "description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@XmlElement(name = "controlPlaneMode")
	public String getControlPlaneMode() {
		return controlPlaneMode;
	}
	public void setControlPlaneMode(String controlPlaneMode) {
		this.controlPlaneMode = controlPlaneMode;
	}
	
	@XmlElement(name = "guestVlanAllowed")
	public String getGuestVlanAllowed() {
		return guestVlanAllowed;
	}
	public void setGuestVlanAllowed(String guestVlanAllowed) {
		this.guestVlanAllowed = guestVlanAllowed;
	}
	
	@XmlElement(name = "tenantId")
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * 
	 * @param name
	 * @param controlPlaneMode	| UNICAST_MODE | MULTICAST_MODE | HYBRID_MODE
	 * @param guestVlanAllowed		true | false
	 * @param macLearningEnabled	true | false
	 * @param tenantId				virtual wire tenant
	 */
	public LogicalSwitchForCreate(String name, String description, String controlPlaneMode, String guestVlanAllowed,
			String tenantId) {
		super();
		this.name = name;
		this.description = description;
		this.controlPlaneMode = controlPlaneMode;
		this.guestVlanAllowed = guestVlanAllowed;
		this.tenantId = tenantId;
	}
	public LogicalSwitchForCreate() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
