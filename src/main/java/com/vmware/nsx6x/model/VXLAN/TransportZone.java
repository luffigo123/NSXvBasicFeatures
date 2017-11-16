package com.vmware.nsx6x.model.VXLAN;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name = "vdnScope")
@XmlSeeAlso({})
public class TransportZone {
	private String objectId;
	private String name;
	private String description;
	private Clusters clusters;
	private String controlPlaneMode;
	private String isUniversal;
	
	@XmlElement(name = "objectId")
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
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
	
	@XmlElement(name = "clusters")
	public Clusters getClusters() {
		return clusters;
	}
	public void setClusters(Clusters clusters) {
		this.clusters = clusters;
	}
	@XmlElement(name = "controlPlaneMode")
	public String getControlPlaneMode() {
		return controlPlaneMode;
	}
	public void setControlPlaneMode(String controlPlaneMode) {
		this.controlPlaneMode = controlPlaneMode;
	}
	
	@XmlElement(name = "isUniversal")
	public String getIsUniversal() {
		return isUniversal;
	}
	public void setIsUniversal(String isUniversal) {
		this.isUniversal = isUniversal;
	}
	public TransportZone() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 * @param objectId
	 * @param name
	 * @param description
	 * @param clusters
	 * @param controlPlaneMode
	 */
	public TransportZone(String objectId, String name, String description,
			Clusters clusters, String controlPlaneMode) {
		super();
		this.objectId = objectId;
		this.name = name;
		this.description = description;
		this.clusters = clusters;
		this.controlPlaneMode = controlPlaneMode;
	}
	
	/**
	 * 
	 * @param name
	 * @param description
	 * @param clusters
	 * @param controlPlaneMode
	 */
	public TransportZone(String name, String description, Clusters clusters,
			String controlPlaneMode) {
		super();
		this.name = name;
		this.description = description;
		this.clusters = clusters;
		this.controlPlaneMode = controlPlaneMode;
	}
	
	/**
	 * 
	 * @param name
	 * @param description
	 * @param clusters
	 * @param controlPlaneMode
	 * @param isUniversal
	 */
	public TransportZone(String name, String description, Clusters clusters, String controlPlaneMode,
			String isUniversal) {
		super();
		this.name = name;
		this.description = description;
		this.clusters = clusters;
		this.controlPlaneMode = controlPlaneMode;
		this.isUniversal = isUniversal;
	}
	
	
	

	
	
	
}
