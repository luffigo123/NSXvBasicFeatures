package com.vmware.nsx6x.model.EdgeIPSecVPN;

public class Site {
	private String enabled;
	private String name;
	private String localId;
	private String localIp;
	private String peerId;
	private String peerIp;
	private String encryptionAlgorithm;
	private String enablePfs;
	private String dhGroup;
	private String localSubnet;
	private String peerSubnet;
	private String psk;
	private String authenticationMode;
	
	public Site() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param enabled				true | false
	 * @param name
	 * @param localId
	 * @param localIp				| uplinkIP
	 * @param peerId
	 * @param peerIp
	 * @param encryptionAlgorithm	aes | aes256 | triple des | aes-gcm
	 * @param enablePfs				true | false
	 * @param dhGroup				dh2 | dh5
	 * @param localSubnet
	 * @param peerSubnet
	 * @param psk
	 * @param authenticationMode	PSK
	 */
	public Site(String enabled, String name, String localId, String localIp,
			String peerId, String peerIp, String encryptionAlgorithm,
			String enablePfs, String dhGroup, String localSubnet,
			String peerSubnet, String psk, String authenticationMode) {
		super();
		this.enabled = enabled;
		this.name = name;
		this.localId = localId;
		this.localIp = localIp;
		this.peerId = peerId;
		this.peerIp = peerIp;
		this.encryptionAlgorithm = encryptionAlgorithm;
		this.enablePfs = enablePfs;
		this.dhGroup = dhGroup;
		this.localSubnet = localSubnet;
		this.peerSubnet = peerSubnet;
		this.psk = psk;
		this.authenticationMode = authenticationMode;
	}
	
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocalId() {
		return localId;
	}
	public void setLocalId(String localId) {
		this.localId = localId;
	}
	public String getLocalIp() {
		return localIp;
	}
	public void setLocalIp(String localIp) {
		this.localIp = localIp;
	}
	public String getPeerId() {
		return peerId;
	}
	public void setPeerId(String peerId) {
		this.peerId = peerId;
	}
	public String getPeerIp() {
		return peerIp;
	}
	public void setPeerIp(String peerIp) {
		this.peerIp = peerIp;
	}
	public String getEncryptionAlgorithm() {
		return encryptionAlgorithm;
	}
	public void setEncryptionAlgorithm(String encryptionAlgorithm) {
		this.encryptionAlgorithm = encryptionAlgorithm;
	}
	public String getEnablePfs() {
		return enablePfs;
	}
	public void setEnablePfs(String enablePfs) {
		this.enablePfs = enablePfs;
	}
	public String getDhGroup() {
		return dhGroup;
	}
	public void setDhGroup(String dhGroup) {
		this.dhGroup = dhGroup;
	}
	public String getLocalSubnet() {
		return localSubnet;
	}
	public void setLocalSubnet(String localSubnet) {
		this.localSubnet = localSubnet;
	}
	public String getPeerSubnet() {
		return peerSubnet;
	}
	public void setPeerSubnet(String peerSubnet) {
		this.peerSubnet = peerSubnet;
	}
	public String getPsk() {
		return psk;
	}
	public void setPsk(String psk) {
		this.psk = psk;
	}
	public String getAuthenticationMode() {
		return authenticationMode;
	}
	public void setAuthenticationMode(String authenticationMode) {
		this.authenticationMode = authenticationMode;
	}
}
