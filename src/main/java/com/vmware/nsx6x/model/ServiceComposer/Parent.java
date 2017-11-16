package com.vmware.nsx6x.model.ServiceComposer;

import javax.xml.bind.annotation.XmlElement;

public class Parent {
	private String objectId;
	
	@XmlElement(name = "objectId")
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	
	/**
	 * 
	 * @param objectId
	 */
	public Parent(String objectId) {
		super();
		this.objectId = objectId;
	}
	
	public Parent() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
