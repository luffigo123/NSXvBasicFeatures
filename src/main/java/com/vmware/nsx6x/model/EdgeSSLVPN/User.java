package com.vmware.nsx6x.model.EdgeSSLVPN;

public class User {
	
	private String userId;
	private String password;
	private String firstName;
	private String lastName;
	private String description;
	private String disableUserAccount;
	private String passwordNeverExpires;
	private String changePasswordOnNextLogin;
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String userId, String password, String firstName,
			String lastName, String description, String disableUserAccount,
			String passwordNeverExpires, String changePasswordOnNextLogin) {
		super();
		this.userId = userId;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.description = description;
		this.disableUserAccount = disableUserAccount;
		this.passwordNeverExpires = passwordNeverExpires;
		this.changePasswordOnNextLogin = changePasswordOnNextLogin;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDisableUserAccount() {
		return disableUserAccount;
	}
	public void setDisableUserAccount(String disableUserAccount) {
		this.disableUserAccount = disableUserAccount;
	}
	public String getPasswordNeverExpires() {
		return passwordNeverExpires;
	}
	public void setPasswordNeverExpires(String passwordNeverExpires) {
		this.passwordNeverExpires = passwordNeverExpires;
	}
	public String getChangePasswordOnNextLogin() {
		return changePasswordOnNextLogin;
	}
	public void setChangePasswordOnNextLogin(String changePasswordOnNextLogin) {
		this.changePasswordOnNextLogin = changePasswordOnNextLogin;
	}
	
	
	
	

}
