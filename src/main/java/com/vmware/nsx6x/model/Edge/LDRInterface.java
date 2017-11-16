package com.vmware.nsx6x.model.Edge;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class LDRInterface {	
	private String name;
	private String type;
	private String connectedToId;
	private ArrayList<AddressGroup> addressGroups;
	private String mtu;
	private String isConnected;
		
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
	
	@XmlElement(name = "connectedToId")
	public String getConnectedToId() {
		return connectedToId;
	}
	public void setConnectedToId(String connectedToId) {
		this.connectedToId = connectedToId;
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
	/**
	 * @param name
	 * @param type
	 * @param connectedToId
	 * @param addressGroups
	 * @param mtu
	 * @param isConnected
	 */
	public LDRInterface(String name, String type, String connectedToId, ArrayList<AddressGroup> addressGroups,
			String mtu, String isConnected) {
		super();
		this.name = name;
		this.type = type;
		this.connectedToId = connectedToId;
		this.addressGroups = addressGroups;
		this.mtu = mtu;
		this.isConnected = isConnected;
	}
	/**
	 * 
	 */
	public LDRInterface() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "LDRInterface [name=" + name + ", type=" + type + ", connectedToId=" + connectedToId + ", addressGroups="
				+ addressGroups + ", mtu=" + mtu + ", isConnected=" + isConnected + "]";
	}
	
	
}

/*<interfaces>
<interface>
    <addressGroups>
        <addressGroup>
            <primaryAddress>3.3.3.3</primaryAddress>
            <subnetMask>255.255.255.0</subnetMask>
            <subnetPrefixLength>24</subnetPrefixLength>
        </addressGroup>
    </addressGroups>           
    <isConnected>true</isConnected>
    <mtu>1500</mtu>
    <name>Internal01简中</name>
    <connectedToId>virtualwire-2</connectedToId>
    <type>internal</type>
</interface>       
</interfaces>
*/