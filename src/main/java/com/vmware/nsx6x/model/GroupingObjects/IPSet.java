package com.vmware.nsx6x.model.GroupingObjects;

public class IPSet {
	protected String name;
	protected String desc;
	protected String value;
	
	/**
	 * 
	 */
	public IPSet() {
		super();
	}
	
	/**
	 * @param name
	 * @param desc
	 * @param value
	 */
	public IPSet(String name, String desc, String value) {
		super();
		this.name = name;
		this.desc = desc;
		this.value = value;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}
	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
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
