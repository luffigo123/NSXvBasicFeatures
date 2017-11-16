package com.vmware.nsx6x.model.Edge;

public class VnicBean {	
	public String index;
	public String name;
	public String type;
	public String portgroupId;
	public String primaryAddress;
	public String subnetMask;
	public String subnetPrefixLength;
	
	/**
	 * 
	 * @param index
	 * @param name
	 * @param type
	 * @param portgroupId
	 * @param primaryAddress
	 * @param subnetMask
	 * @param subnetPrefixLength
	 */
	public VnicBean(String index, String name, String type, String portgroupId,
			String primaryAddress, String subnetMask, String subnetPrefixLength) {
		super();
		this.index = index;
		this.name = name;
		this.type = type;
		this.portgroupId = portgroupId;
		this.primaryAddress = primaryAddress;
		this.subnetMask = subnetMask;
		this.subnetPrefixLength = subnetPrefixLength;
	}
	
	

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPortgroupId() {
		return portgroupId;
	}

	public void setPortgroupId(String portgroupId) {
		this.portgroupId = portgroupId;
	}

	public String getPrimaryAddress() {
		return primaryAddress;
	}

	public void setPrimaryAddress(String primaryAddress) {
		this.primaryAddress = primaryAddress;
	}

	public String getSubnetMask() {
		return subnetMask;
	}

	public void setSubnetMask(String subnetMask) {
		this.subnetMask = subnetMask;
	}

	public String getSubnetPrefixLength() {
		return subnetPrefixLength;
	}

	public void setSubnetPrefixLength(String subnetPrefixLength) {
		this.subnetPrefixLength = subnetPrefixLength;
	}
}
