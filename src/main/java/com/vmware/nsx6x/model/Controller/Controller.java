package com.vmware.nsx6x.model.Controller;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name = "controllerSpec")
@XmlSeeAlso({})
public class Controller {
	private String name;
	private String description;
	private String ipPoolId;
	private String resourcePoolId;
	private String hostId;
	private String datastoreId;
	private String networkId;
	private String password;
	private String deployType;
	
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
	
	@XmlElement(name = "ipPoolId")
	public String getIpPoolId() {
		return ipPoolId;
	}
	public void setIpPoolId(String ipPoolId) {
		this.ipPoolId = ipPoolId;
	}
	
	@XmlElement(name = "resourcePoolId")
	public String getResourcePoolId() {
		return resourcePoolId;
	}
	public void setResourcePoolId(String resourcePoolId) {
		this.resourcePoolId = resourcePoolId;
	}
	
	@XmlElement(name = "hostId")
	public String getHostId() {
		return hostId;
	}
	public void setHostId(String hostId) {
		this.hostId = hostId;
	}
	
	@XmlElement(name = "datastoreId")
	public String getDatastoreId() {
		return datastoreId;
	}
	public void setDatastoreId(String datastoreId) {
		this.datastoreId = datastoreId;
	}
	
	@XmlElement(name = "networkId")
	public String getNetworkId() {
		return networkId;
	}
	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}
	
	@XmlElement(name = "password")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@XmlElement(name = "deployType")
	public String getDeployType() {
		return deployType;
	}
	public void setDeployType(String deployType) {
		this.deployType = deployType;
	}
	public Controller() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param name
	 * @param description
	 * @param ipPoolId
	 * @param resourcePoolId
	 * @param hostId
	 * @param datastoreId
	 * @param networkId
	 * @param password
	 * @param deployType
	 */
	public Controller(String name, String description, String ipPoolId, String resourcePoolId, String hostId,
			String datastoreId, String networkId, String password, String deployType) {
		super();
		this.name = name;
		this.description = description;
		this.ipPoolId = ipPoolId;
		this.resourcePoolId = resourcePoolId;
		this.hostId = hostId;
		this.datastoreId = datastoreId;
		this.networkId = networkId;
		this.password = password;
		this.deployType = deployType;
	}
	
	
}
