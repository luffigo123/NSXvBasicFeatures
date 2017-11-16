package com.vmware.nsx6x.model.EdgeL2VPN;

public class L2VPNClient {
	private String serverAddress;
	private String serverPort;
	private String vnic;
	private String gatewayIpAddress;
	private String caCertificate;
	private String encryptionAlgorithm;
	private String l2VpnUser_userId;
	private String l2VpnUser_password;
	private String proxyType;
	private String proxyAddress;
	private String proxyPort;
	private String proxyUsername;
	private String proxyPassword;
	
	
	public L2VPNClient() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param serverAddress			<!-- Required. IP/Hostname to connect -->
	 * @param serverPort			<!-- optional. 443 by default. Port to connect on -->
	 * @param vnic					<!-- Required. Traffic from this internal vnic interfaces will be forwarded to L2VPN tunnel -->
	 * @param gatewayIpAddress		<!-- optional. To block the internet requests over tunnel-->
	 * @param caCertificate			<!-- Optional. Validate server certificate sent from server againt this cerficate-->
	 * @param encryptionAlgorithm	<!-- Mandatory. Supported ciphers are "RC4-MD5","AES128-SHA","AES256-SHA","DES-CBC3-SHA","AES128-GCM-SHA256" and "NULL-MD5"-->
	 * @param l2VpnUser_userId
	 * @param l2VpnUser_password
	 * @param proxyType
	 * @param proxyAddress
	 * @param proxyPort
	 * @param proxyUsername
	 * @param proxyPassword
	 */
	public L2VPNClient(String serverAddress, String serverPort, String vnic,
			String gatewayIpAddress, String caCertificate,
			String encryptionAlgorithm, String l2VpnUser_userId,
			String l2VpnUser_password, String proxyType, String proxyAddress,
			String proxyPort, String proxyUsername, String proxyPassword) {
		super();
		this.serverAddress = serverAddress;
		this.serverPort = serverPort;
		this.vnic = vnic;
		this.gatewayIpAddress = gatewayIpAddress;
		this.caCertificate = caCertificate;
		this.encryptionAlgorithm = encryptionAlgorithm;
		this.l2VpnUser_userId = l2VpnUser_userId;
		this.l2VpnUser_password = l2VpnUser_password;
		this.proxyType = proxyType;
		this.proxyAddress = proxyAddress;
		this.proxyPort = proxyPort;
		this.proxyUsername = proxyUsername;
		this.proxyPassword = proxyPassword;
	}

	public String getServerAddress() {
		return serverAddress;
	}

	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}

	public String getServerPort() {
		return serverPort;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

	public String getVnic() {
		return vnic;
	}

	public void setVnic(String vnic) {
		this.vnic = vnic;
	}

	public String getGatewayIpAddress() {
		return gatewayIpAddress;
	}

	public void setGatewayIpAddress(String gatewayIpAddress) {
		this.gatewayIpAddress = gatewayIpAddress;
	}

	public String getCaCertificate() {
		return caCertificate;
	}

	public void setCaCertificate(String caCertificate) {
		this.caCertificate = caCertificate;
	}

	public String getEncryptionAlgorithm() {
		return encryptionAlgorithm;
	}

	public void setEncryptionAlgorithm(String encryptionAlgorithm) {
		this.encryptionAlgorithm = encryptionAlgorithm;
	}

	public String getL2VpnUser_userId() {
		return l2VpnUser_userId;
	}

	public void setL2VpnUser_userId(String l2VpnUser_userId) {
		this.l2VpnUser_userId = l2VpnUser_userId;
	}

	public String getL2VpnUser_password() {
		return l2VpnUser_password;
	}

	public void setL2VpnUser_password(String l2VpnUser_password) {
		this.l2VpnUser_password = l2VpnUser_password;
	}

	public String getProxyType() {
		return proxyType;
	}

	public void setProxyType(String proxyType) {
		this.proxyType = proxyType;
	}

	public String getProxyAddress() {
		return proxyAddress;
	}

	public void setProxyAddress(String proxyAddress) {
		this.proxyAddress = proxyAddress;
	}

	public String getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(String proxyPort) {
		this.proxyPort = proxyPort;
	}

	public String getProxyUsername() {
		return proxyUsername;
	}

	public void setProxyUsername(String proxyUsername) {
		this.proxyUsername = proxyUsername;
	}

	public String getProxyPassword() {
		return proxyPassword;
	}

	public void setProxyPassword(String proxyPassword) {
		this.proxyPassword = proxyPassword;
	}
}
