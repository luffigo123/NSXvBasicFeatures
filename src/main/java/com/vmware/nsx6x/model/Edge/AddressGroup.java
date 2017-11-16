package com.vmware.nsx6x.model.Edge;

import javax.xml.bind.annotation.XmlElement;

public class AddressGroup {
	private String primaryAddress;
	private String subnetMask;
	private String subnetPrefixLength;
	
	@XmlElement(name = "primaryAddress")
	public String getPrimaryAddress() {
		return primaryAddress;
	}
	public void setPrimaryAddress(String primaryAddress) {
		this.primaryAddress = primaryAddress;
	}
	
	@XmlElement(name = "subnetMask")
	public String getSubnetMask() {
		return subnetMask;
	}
	public void setSubnetMask(String subnetMask) {
		this.subnetMask = subnetMask;
	}
	
	@XmlElement(name = "subnetPrefixLength")
	public String getSubnetPrefixLength() {
		return subnetPrefixLength;
	}
	public void setSubnetPrefixLength(String subnetPrefixLength) {
		this.subnetPrefixLength = subnetPrefixLength;
	}
	
	/**
	 * 
	 * @param primaryAddress
	 * @param subnetMask
	 * @param subnetPrefixLength
	 */
	public AddressGroup(String primaryAddress, String subnetMask,
			String subnetPrefixLength) {
		super();
		this.primaryAddress = primaryAddress;
		this.subnetMask = subnetMask;
		this.subnetPrefixLength = subnetPrefixLength;
	}
	
	public AddressGroup() {
		super();
	}
	
}
