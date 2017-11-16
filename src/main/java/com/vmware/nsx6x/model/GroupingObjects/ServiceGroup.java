package com.vmware.nsx6x.model.GroupingObjects;

public class ServiceGroup {
	protected String name;
	protected String desc;
	protected String inheritanceAllowed;
	protected String member_objectId;
	

	public ServiceGroup() {
		super();
	}

	/**
	 * @param name
	 * @param desc
	 * @param inheritanceAllowed
	 * @param member_objectId
	 */
	public ServiceGroup(String name, String desc,
			String inheritanceAllowed, String member_objectId) {
		super();
		this.name = name;
		this.desc = desc;
		this.inheritanceAllowed = inheritanceAllowed;
		this.member_objectId = member_objectId;
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
	 * @return the member_objectId
	 */
	public String getMember_objectId() {
		return member_objectId;
	}

	/**
	 * @param member_objectId the member_objectId to set
	 */
	public void setMember_objectId(String member_objectId) {
		this.member_objectId = member_objectId;
	}
}
