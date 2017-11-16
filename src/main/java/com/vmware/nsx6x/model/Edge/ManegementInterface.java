package com.vmware.nsx6x.model.Edge;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class ManegementInterface {
	private ArrayList<AddressGroup> addressGroups;
	private String connectedToId;
	
	@XmlElementWrapper(name = "addressGroups")
	@XmlElement(name = "addressGroup")
	public ArrayList<AddressGroup> getAddressGroups() {
		return addressGroups;
	}
	public void setAddressGroups(ArrayList<AddressGroup> addressGroups) {
		this.addressGroups = addressGroups;
	}
	
	@XmlElement(name = "connectedToId")
	public String getConnectedToId() {
		return connectedToId;
	}
	public void setConnectedToId(String connectedToId) {
		this.connectedToId = connectedToId;
	}
	
	public ManegementInterface() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 * @param addressGroups
	 * @param connectedToId
	 */
	public ManegementInterface(ArrayList<AddressGroup> addressGroups, String connectedToId) {
		super();
		this.addressGroups = addressGroups;
		this.connectedToId = connectedToId;
	}
	
}
