package com.vmware.nsx6x.model.EdgeIPSecVPN;

import java.util.ArrayList;

public class IPSecVPN {
	private String enabled;
	private String logging_enable;
	private String logLevel;
	private String global_psk;
	private ArrayList<Site> siteList;
	
	public IPSecVPN() {
		super();
		// TODO Auto-generated constructor stub
	}

	public IPSecVPN(String enabled, String logging_enable, String logLevel,
			String global_psk, ArrayList<Site> siteList) {
		super();
		this.enabled = enabled;
		this.logging_enable = logging_enable;
		this.logLevel = logLevel;
		this.global_psk = global_psk;
		this.siteList = siteList;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getLogging_enable() {
		return logging_enable;
	}

	public void setLogging_enable(String logging_enable) {
		this.logging_enable = logging_enable;
	}

	public String getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}

	public String getGlobal_psk() {
		return global_psk;
	}

	public void setGlobal_psk(String global_psk) {
		this.global_psk = global_psk;
	}

	public ArrayList<Site> getSiteList() {
		return siteList;
	}

	public void setSiteList(ArrayList<Site> siteList) {
		this.siteList = siteList;
	}
	

}
