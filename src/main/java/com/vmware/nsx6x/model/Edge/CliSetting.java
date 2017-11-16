package com.vmware.nsx6x.model.Edge;

import javax.xml.bind.annotation.XmlElement;

public class CliSetting {
	private String userName;
	private String password;
	private String remoteAccess;
	
	@XmlElement(name = "userName")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@XmlElement(name = "password")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@XmlElement(name = "remoteAccess")
	public String getRemoteAccess() {
		return remoteAccess;
	}
	public void setRemoteAccess(String remoteAccess) {
		this.remoteAccess = remoteAccess;
	}
	
	/**
	 * 
	 * @param userName
	 * @param password
	 * @param remoteAccess
	 */
	public CliSetting(String userName, String password, String remoteAccess) {
		super();
		this.userName = userName;
		this.password = password;
		this.remoteAccess = remoteAccess;
	}
	
	public CliSetting() {
		super();

	}
	
}
