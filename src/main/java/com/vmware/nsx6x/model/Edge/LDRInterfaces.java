package com.vmware.nsx6x.model.Edge;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "interfaces")
public class LDRInterfaces {	
	private ArrayList<LDRInterface> interfaces;

	public ArrayList<LDRInterface> getInterfaces() {
		return interfaces;
	}
	
	@XmlElement(name = "interface")
	public void setInterfaces(ArrayList<LDRInterface> interfaces) {
		this.interfaces = interfaces;
	}

	/**
	 * @param interfaces
	 */
	public LDRInterfaces(ArrayList<LDRInterface> interfaces) {
		super();
		this.interfaces = interfaces;
	}

	/**
	 * 
	 */
	public LDRInterfaces() {		
		super();
		interfaces = new ArrayList<LDRInterface>();
	}

	@Override
	public String toString() {
		return "LDRInterfaces [interfaces=" + interfaces + "]";
	}
	
	public void addInterfaces(LDRInterface ldrInterface)
	{
		interfaces.add(ldrInterface);
	}
	
	public void clearInterfaces()
	{
		interfaces.clear();
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