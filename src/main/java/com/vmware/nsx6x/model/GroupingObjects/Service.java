package com.vmware.nsx6x.model.GroupingObjects;

public class Service {
	protected String name;
	protected String desc;
	protected String inheritanceAllowed;
	protected String applicationProtocol;
	protected String value;
	

	/**
	 * 
	 */
	public Service() {
		super();
	}


	/**
	 * @param name
	 * @param desc
	 * @param inheritanceAllowed
	 * @param applicationProtocol
	 * @param value
	 */
	public Service(String name, String desc, String inheritanceAllowed,
			String applicationProtocol, String value) {
		super();
		this.name = name;
		this.desc = desc;
		this.inheritanceAllowed = inheritanceAllowed;
		this.applicationProtocol = applicationProtocol;
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
	 * @return the inheritanceAllowed
	 */
	public String getInheritanceAllowed() {
		return inheritanceAllowed;
	}


	/**
	 * @param inheritanceAllowed the inheritanceAllowed to set
	 */
	public void setInheritanceAllowed(String inheritanceAllowed) {
		this.inheritanceAllowed = inheritanceAllowed;
	}


	/**
	 * @return the applicationProtocol
	 */
	public String getApplicationProtocol() {
		return applicationProtocol;
	}


	/**
	 * @param applicationProtocol the applicationProtocol to set
	 */
	public void setApplicationProtocol(String applicationProtocol) {
		this.applicationProtocol = applicationProtocol;
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
