package com.vmware.nsx6x.model.GroupingObjects;

import javax.xml.bind.annotation.XmlElement;

public class IPRangeDto {
	private String startAddress;

	@XmlElement(name = "startAddress")
	public String getStartAddress() {
		return startAddress;
	}

	public void setStartAddress(String startAddress) {
		this.startAddress = startAddress;
	}

	private String endAddress;
	
	@XmlElement(name = "endAddress")
	public String getEndAddress() {
		return endAddress;
	}

	public void setEndAddress(String endAddress) {
		this.endAddress = endAddress;
	}
	/**
	 * 
	 * @param startAddress
	 * @param endAddress
	 */
	public IPRangeDto(String startAddress, String endAddress) {
		super();
		this.startAddress = startAddress;
		this.endAddress = endAddress;
	}

	public IPRangeDto() {
		super();
	}
	
}
