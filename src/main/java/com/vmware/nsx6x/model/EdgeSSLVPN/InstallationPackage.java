package com.vmware.nsx6x.model.EdgeSSLVPN;

public class InstallationPackage {
	private String profileName;
	private String hostName;
	private String port;
	private String enabled;
	private String description;
	private String startClientOnLogon;
	private String hideSystrayIcon;
	private String rememberPassword;
	private String silentModeOperation;
	private String silentModeInstallation;
	private String hideNetworkAdaptor;
	private String createDesktopIcon;
	private String enforceServerSecurityCertValidation;
	private String createLinuxClient;
	private String createMacClient;
	
	
	public InstallationPackage() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public InstallationPackage(String profileName, String hostName,
			String port, String enabled, String description) {
		super();
		this.profileName = profileName;
		this.hostName = hostName;
		this.port = port;
		this.enabled = enabled;
		this.description = description;
	}


	public InstallationPackage(String profileName, String hostName,
			String port, String enabled, String description,
			String startClientOnLogon, String hideSystrayIcon,
			String rememberPassword, String silentModeOperation,
			String silentModeInstallation, String hideNetworkAdaptor,
			String createDesktopIcon,
			String enforceServerSecurityCertValidation,
			String createLinuxClient, String createMacClient) {
		super();
		this.profileName = profileName;
		this.hostName = hostName;
		this.port = port;
		this.enabled = enabled;
		this.description = description;
		this.startClientOnLogon = startClientOnLogon;
		this.hideSystrayIcon = hideSystrayIcon;
		this.rememberPassword = rememberPassword;
		this.silentModeOperation = silentModeOperation;
		this.silentModeInstallation = silentModeInstallation;
		this.hideNetworkAdaptor = hideNetworkAdaptor;
		this.createDesktopIcon = createDesktopIcon;
		this.enforceServerSecurityCertValidation = enforceServerSecurityCertValidation;
		this.createLinuxClient = createLinuxClient;
		this.createMacClient = createMacClient;
	}




	public String getProfileName() {
		return profileName;
	}
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStartClientOnLogon() {
		return startClientOnLogon;
	}
	public void setStartClientOnLogon(String startClientOnLogon) {
		this.startClientOnLogon = startClientOnLogon;
	}
	public String getHideSystrayIcon() {
		return hideSystrayIcon;
	}
	public void setHideSystrayIcon(String hideSystrayIcon) {
		this.hideSystrayIcon = hideSystrayIcon;
	}
	public String getRememberPassword() {
		return rememberPassword;
	}
	public void setRememberPassword(String rememberPassword) {
		this.rememberPassword = rememberPassword;
	}
	public String getSilentModeOperation() {
		return silentModeOperation;
	}
	public void setSilentModeOperation(String silentModeOperation) {
		this.silentModeOperation = silentModeOperation;
	}
	public String getSilentModeInstallation() {
		return silentModeInstallation;
	}
	public void setSilentModeInstallation(String silentModeInstallation) {
		this.silentModeInstallation = silentModeInstallation;
	}
	public String getHideNetworkAdaptor() {
		return hideNetworkAdaptor;
	}
	public void setHideNetworkAdaptor(String hideNetworkAdaptor) {
		this.hideNetworkAdaptor = hideNetworkAdaptor;
	}
	public String getCreateDesktopIcon() {
		return createDesktopIcon;
	}
	public void setCreateDesktopIcon(String createDesktopIcon) {
		this.createDesktopIcon = createDesktopIcon;
	}
	public String getEnforceServerSecurityCertValidation() {
		return enforceServerSecurityCertValidation;
	}
	public void setEnforceServerSecurityCertValidation(
			String enforceServerSecurityCertValidation) {
		this.enforceServerSecurityCertValidation = enforceServerSecurityCertValidation;
	}
	public String getCreateLinuxClient() {
		return createLinuxClient;
	}
	public void setCreateLinuxClient(String createLinuxClient) {
		this.createLinuxClient = createLinuxClient;
	}
	public String getCreateMacClient() {
		return createMacClient;
	}
	public void setCreateMacClient(String createMacClient) {
		this.createMacClient = createMacClient;
	}
	
	

}
