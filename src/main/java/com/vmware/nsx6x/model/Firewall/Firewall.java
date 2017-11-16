package com.vmware.nsx6x.model.Firewall;

import java.util.ArrayList;

public class Firewall {

	private String name;
	private String action;
	private String notes;
	private ArrayList<Member> appliedToList;
	private ArrayList<Member> sources;
	private ArrayList<Member> destinations;
	private ArrayList<Member> services;
	
	/**
	 * 
	 */
	public Firewall() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param name
	 * @param action
	 * @param notes
	 * @param appliedToList
	 * @param sources
	 * @param destinations
	 * @param services
	 */
	public Firewall(String name, String action, String notes,
			ArrayList<Member> appliedToList, ArrayList<Member> sources,
			ArrayList<Member> destinations, ArrayList<Member> services) {
		super();
		this.name = name;
		this.action = action;
		this.notes = notes;
		this.appliedToList = appliedToList;
		this.sources = sources;
		this.destinations = destinations;
		this.services = services;
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
	 * @return the action
	 */
	public String getAction() {
		return action;
	}
	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}
	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}
	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}
	/**
	 * @return the appliedToList
	 */
	public ArrayList<Member> getAppliedToList() {
		return appliedToList;
	}
	/**
	 * @param appliedToList the appliedToList to set
	 */
	public void setAppliedToList(ArrayList<Member> appliedToList) {
		this.appliedToList = appliedToList;
	}
	/**
	 * @return the sources
	 */
	public ArrayList<Member> getSources() {
		return sources;
	}
	/**
	 * @param sources the sources to set
	 */
	public void setSources(ArrayList<Member> sources) {
		this.sources = sources;
	}
	/**
	 * @return the destinations
	 */
	public ArrayList<Member> getDestinations() {
		return destinations;
	}
	/**
	 * @param destinations the destinations to set
	 */
	public void setDestinations(ArrayList<Member> destinations) {
		this.destinations = destinations;
	}
	/**
	 * @return the services
	 */
	public ArrayList<Member> getServices() {
		return services;
	}
	/**
	 * @param services the services to set
	 */
	public void setServices(ArrayList<Member> services) {
		this.services = services;
	}

}
