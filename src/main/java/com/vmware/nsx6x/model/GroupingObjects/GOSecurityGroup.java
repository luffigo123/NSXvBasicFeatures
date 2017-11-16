package com.vmware.nsx6x.model.GroupingObjects;

import java.util.ArrayList;



public class GOSecurityGroup {
	protected String name;
	protected String description;
	protected String inheritanceAllowed;
	protected ArrayList<String> includeMember;
	protected ArrayList<String> excludeMember;
	protected ArrayList<DynamicMember> dynamicMember;
	
	/**
	 * 
	 */
	public GOSecurityGroup() {
		super();
	}

	/**
	 * @param name
	 * @param description
	 * @param inheritanceAllowed
	 * @param includeMember			- objectId
	 * @param excludeMember			- objectId
	 * @param dynamicMember
	 */
	public GOSecurityGroup(String name, String description,
			String inheritanceAllowed, ArrayList<String> includeMember,
			ArrayList<String> excludeMember,
			ArrayList<DynamicMember> dynamicMember) {
		super();
		this.name = name;
		this.description = description;
		this.inheritanceAllowed = inheritanceAllowed;
		this.includeMember = includeMember;
		this.excludeMember = excludeMember;
		this.dynamicMember = dynamicMember;
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * @return the includeMember
	 */
	public ArrayList<String> getIncludeMember() {
		return includeMember;
	}

	/**
	 * @param includeMember the includeMember to set
	 */
	public void setIncludeMember(ArrayList<String> includeMember) {
		this.includeMember = includeMember;
	}

	/**
	 * @return the excludeMember
	 */
	public ArrayList<String> getExcludeMember() {
		return excludeMember;
	}

	/**
	 * @param excludeMember the excludeMember to set
	 */
	public void setExcludeMember(ArrayList<String> excludeMember) {
		this.excludeMember = excludeMember;
	}

	/**
	 * @return the dynamicMember
	 */
	public ArrayList<DynamicMember> getDynamicMember() {
		return dynamicMember;
	}

	/**
	 * @param dynamicMember the dynamicMember to set
	 */
	public void setDynamicMember(ArrayList<DynamicMember> dynamicMember) {
		this.dynamicMember = dynamicMember;
	}

}
