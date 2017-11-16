package com.vmware.nsx6x.model.Domain;

public class Domain {

	private String name;
	private String type;
	private String netbiosName;
	private String username;
	private String password;
			
	/**
	 * 
	 */
	public Domain() {
		super();
	}
	
	/**
	 * @param name
	 * @param type 			AGENT_DISCOVERED | ACTIVE_DIRECTORY | SPECIAL
	 * @param netbiosName
	 * @param username
	 * @param password
	 */
	public Domain(String name, String type, String netbiosName,
			String username, String password) {
		super();
		this.name = name;
		this.type = type;
		this.netbiosName = netbiosName;
		this.username = username;
		this.password = password;
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
	 * @return the netbiosName
	 */
	public String getNetbiosName() {
		return netbiosName;
	}

	/**
	 * @param netbiosName the netbiosName to set
	 */
	public void setNetbiosName(String netbiosName) {
		this.netbiosName = netbiosName;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}		

}
