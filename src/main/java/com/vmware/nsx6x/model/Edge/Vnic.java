package com.vmware.nsx6x.model.Edge;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class Vnic {
	private String index;
	private String name;
	private String type;
	private String portgroupId;
	private ArrayList<AddressGroup> addressGroups;
	private String mtu;
	private String isConnected;
	private String enableProxyArp;
	private String enableSendRedirects;
	
	@XmlElement(name = "index")
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	
	@XmlElement(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@XmlElement(name = "type")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@XmlElement(name = "portgroupId")
	public String getPortgroupId() {
		return portgroupId;
	}
	public void setPortgroupId(String portgroupId) {
		this.portgroupId = portgroupId;
	}
	
	@XmlElementWrapper(name = "addressGroups")
	@XmlElement(name = "addressGroup")
	public ArrayList<AddressGroup> getAddressGroups() {
		return addressGroups;
	}
	public void setAddressGroups(ArrayList<AddressGroup> addressGroups) {
		this.addressGroups = addressGroups;
	}
	
	@XmlElement(name = "mtu")
	public String getMtu() {
		return mtu;
	}
	public void setMtu(String mtu) {
		this.mtu = mtu;
	}
	
	@XmlElement(name = "isConnected")
	public String getIsConnected() {
		return isConnected;
	}
	public void setIsConnected(String isConnected) {
		this.isConnected = isConnected;
	}
	
	@XmlElement(name = "enableProxyArp")
	public String getEnableProxyArp() {
		return enableProxyArp;
	}
	public void setEnableProxyArp(String enableProxyArp) {
		this.enableProxyArp = enableProxyArp;
	}
	
	@XmlElement(name = "enableSendRedirects")
	public String getEnableSendRedirects() {
		return enableSendRedirects;
	}
	public void setEnableSendRedirects(String enableSendRedirects) {
		this.enableSendRedirects = enableSendRedirects;
	}
	
	/**
	 * 
	 * @param index
	 * @param name
	 * @param type
	 * @param portgroupId
	 * @param addressGroups
	 * @param mtu
	 * @param isConnected
	 * @param enableProxyArp
	 * @param enableSendRedirects
	 */
	public Vnic(String index, String name, String type, String portgroupId,
			ArrayList<AddressGroup> addressGroups, String mtu,
			String isConnected, String enableProxyArp,
			String enableSendRedirects) {
		super();
		this.index = index;
		this.name = name;
		this.type = type;
		this.portgroupId = portgroupId;
		this.addressGroups = addressGroups;
		this.mtu = mtu;
		this.isConnected = isConnected;
		this.enableProxyArp = enableProxyArp;
		this.enableSendRedirects = enableSendRedirects;
	}
	
	public Vnic() {
		super();
	}
	
}
