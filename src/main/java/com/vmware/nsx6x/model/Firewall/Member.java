package com.vmware.nsx6x.model.Firewall;

public class Member {

	private String type;
	private String value;
	
	/**
	 * 
	 */
	public Member() {
		super();
	}
	/**
	 * @param type 		 Ipv4Address | Datacenter | ClusterComputeResource | Application| ApplicationGroup
	 * @param value
	 * Application - application-278 | application-315
	 * || ApplicationGroup - applicationgroup-9
	 */
	public Member(String type, String value) {
		super();
		this.type = type;
		this.value = value;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
