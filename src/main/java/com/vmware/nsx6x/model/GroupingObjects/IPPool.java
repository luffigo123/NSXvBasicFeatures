package com.vmware.nsx6x.model.GroupingObjects;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ipamAddressPool")
public class IPPool {
	private String name;
	
	@XmlElement(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	private String prefixLength;
	
	@XmlElement(name = "prefixLength")
	public String getPrefixLength() {
		return prefixLength;
	}
	public void setPrefixLength(String prefixLength) {
		this.prefixLength = prefixLength;
	}

	private String gateway;
	
	@XmlElement(name = "gateway")
	public String getGateway() {
		return gateway;
	}
	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	private String dnsSuffix;
	private String dnsServer1;
	private String dnsServer2;
	private ArrayList<IPRangeDto> ipRanges;

	@XmlElement(name = "dnsSuffix")
	public String getDnsSuffix() {
		return dnsSuffix;
	}
	public void setDnsSuffix(String dnsSuffix) {
		this.dnsSuffix = dnsSuffix;
	}
	
	@XmlElement(name = "dnsServer1")
	public String getDnsServer1() {
		return dnsServer1;
	}
	public void setDnsServer1(String dnsServer1) {
		this.dnsServer1 = dnsServer1;
	}
	
	@XmlElement(name = "dnsServer2")
	public String getDnsServer2() {
		return dnsServer2;
	}
	public void setDnsServer2(String dnsServer2) {
		this.dnsServer2 = dnsServer2;
	}
	
	@XmlElementWrapper(name = "ipRanges")
	@XmlElement(name = "ipRangeDto")
	public ArrayList<IPRangeDto> getIpRanges() {
		return ipRanges;
	}
	public void setIpRanges(ArrayList<IPRangeDto> ipRanges) {
		this.ipRanges = ipRanges;
	}
	
	private String revision;
	
	@XmlElement(name = "revision")
	public String getRevision() {
		return revision;
	}
	public void setRevision(String revision) {
		this.revision = revision;
	}
	
	private String objectId;
	@XmlElement(name = "objectId")
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	
	private String subnetId;
	
	@XmlElement(name = "subnetId")
	public String getSubnetId() {
		return subnetId;
	}
	public void setSubnetId(String subnetId) {
		this.subnetId = subnetId;
	}
	/**
	 * 
	 * @param name
	 * @param prefixLength
	 * @param gateway
	 * @param dnsSuffix
	 * @param dnsServer1
	 * @param dnsServer2
	 * @param ipRanges
	 */
	public IPPool(String name, String prefixLength, String gateway,
			String dnsSuffix, String dnsServer1, String dnsServer2,
			ArrayList<IPRangeDto> ipRanges) {
		super();
		this.name = name;
		this.prefixLength = prefixLength;
		this.gateway = gateway;
		this.dnsSuffix = dnsSuffix;
		this.dnsServer1 = dnsServer1;
		this.dnsServer2 = dnsServer2;
		this.ipRanges = ipRanges;
	}
	
	public IPPool() {
		super();
	}
	
}
