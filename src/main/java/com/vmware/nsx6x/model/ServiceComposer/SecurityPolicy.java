package com.vmware.nsx6x.model.ServiceComposer;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name = "securityPolicy")
@XmlSeeAlso({})
public class SecurityPolicy {	
	private String name;
	private String description;
	private String inheritanceAllowed;
	private String precedence;
	private Parent parent;
	private ArrayList<ActionsByCategory> actionsByCategory;
	
	@XmlElement(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@XmlElement(name = "description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@XmlElement(name = "inheritanceAllowed")
	public String getInheritanceAllowed() {
		return inheritanceAllowed;
	}
	public void setInheritanceAllowed(String inheritanceAllowed) {
		this.inheritanceAllowed = inheritanceAllowed;
	}
	
	@XmlElement(name = "precedence")
	public String getPrecedence() {
		return precedence;
	}
	public void setPrecedence(String precedence) {
		this.precedence = precedence;
	}
	
	@XmlElement(name = "parent")
	public Parent getParent() {
		return parent;
	}
	public void setParent(Parent parent) {
		this.parent = parent;
	}
	
	@XmlElement(name = "actionsByCategory")
	public ArrayList<ActionsByCategory> getActionsByCategory() {
		return actionsByCategory;
	}
	public void setActionsByCategory(ArrayList<ActionsByCategory> actionsByCategory) {
		this.actionsByCategory = actionsByCategory;
	}
	
	/**
	 * 
	 * @param name
	 * @param description
	 * @param inheritanceAllowed
	 * @param precedence
	 * @param parent
	 * @param actionsByCategory
	 */
	public SecurityPolicy(String name, String description,
			String inheritanceAllowed, String precedence, Parent parent,
			ArrayList<ActionsByCategory> actionsByCategory) {
		super();
		this.name = name;
		this.description = description;
		this.inheritanceAllowed = inheritanceAllowed;
		this.precedence = precedence;
		this.parent = parent;
		this.actionsByCategory = actionsByCategory;
	}
	public SecurityPolicy() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
