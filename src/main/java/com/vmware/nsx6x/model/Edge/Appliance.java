package com.vmware.nsx6x.model.Edge;

import javax.xml.bind.annotation.XmlElement;

public class Appliance {
	private String resourcePoolId;
	private String datastoreId;
	
	@XmlElement(name = "resourcePoolId")
	public String getResourcePoolId() {
		return resourcePoolId;
	}
	public void setResourcePoolId(String resourcePoolId) {
		this.resourcePoolId = resourcePoolId;
	}
	
	@XmlElement(name = "datastoreId")
	public String getDatastoreId() {
		return datastoreId;
	}
	public void setDatastoreId(String datastoreId) {
		this.datastoreId = datastoreId;
	}
	
	public Appliance() {
		super();
	}
	
	/**
	 * 
	 * @param resourcePoolId
	 * @param datastoreId
	 */
	public Appliance(String resourcePoolId, String datastoreId) {
		super();
		this.resourcePoolId = resourcePoolId;
		this.datastoreId = datastoreId;
	}
	
}
