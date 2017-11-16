package com.vmware.nsx6x.model.VXLAN;

import javax.xml.bind.annotation.XmlElement;

public class ClusterNest {
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
	public ClusterNest(String objectId) {
		super();
		this.objectId = objectId;
	}

	public ClusterNest() {
		super();
	}
}
