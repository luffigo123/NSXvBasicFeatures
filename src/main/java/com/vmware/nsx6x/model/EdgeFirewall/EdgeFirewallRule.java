package com.vmware.nsx6x.model.EdgeFirewall;

import java.util.ArrayList;

public class EdgeFirewallRule {
	private String name;
	private String enabled;
	private String desc;
	private String action;
	private String loggingEnabled;
	private String matchTranslated;
	private String direction;
	private SourceDestination source;
	private SourceDestination destination;
	private ArrayList<String> applicationList;
	
	public EdgeFirewallRule() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public EdgeFirewallRule(String name, String enabled, String desc,
			String action, String loggingEnabled, String matchTranslated,
			String direction, SourceDestination source,
			SourceDestination destination) {
		super();
		this.name = name;
		this.enabled = enabled;
		this.desc = desc;
		this.action = action;
		this.loggingEnabled = loggingEnabled;
		this.matchTranslated = matchTranslated;
		this.direction = direction;
		this.source = source;
		this.destination = destination;
	}

	public EdgeFirewallRule(String name, String enabled, String desc,
			String action, String loggingEnabled, String matchTranslated,
			String direction, SourceDestination source,
			SourceDestination destination, ArrayList<String> applicationList) {
		super();
		this.name = name;
		this.enabled = enabled;
		this.desc = desc;
		this.action = action;
		this.loggingEnabled = loggingEnabled;
		this.matchTranslated = matchTranslated;
		this.direction = direction;
		this.source = source;
		this.destination = destination;
		this.applicationList = applicationList;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getLoggingEnabled() {
		return loggingEnabled;
	}
	public void setLoggingEnabled(String loggingEnabled) {
		this.loggingEnabled = loggingEnabled;
	}
	public String getMatchTranslated() {
		return matchTranslated;
	}
	public void setMatchTranslated(String matchTranslated) {
		this.matchTranslated = matchTranslated;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	public SourceDestination getSource() {
		return source;
	}
	public void setSource(SourceDestination source) {
		this.source = source;
	}
	public SourceDestination getDestination() {
		return destination;
	}
	public void setDestination(SourceDestination destination) {
		this.destination = destination;
	}

	public ArrayList<String> getApplicationList() {
		return applicationList;
	}

	public void setApplicationList(ArrayList<String> applicationList) {
		this.applicationList = applicationList;
	}

	
}
