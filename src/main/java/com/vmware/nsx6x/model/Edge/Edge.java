package com.vmware.nsx6x.model.Edge;


import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name = "edge")
@XmlSeeAlso({})
public class Edge {
	private String datacenterMoid;
	private String tenant;
	private String name;
	private String fqdn;
	private String description;
	private String enableAesni;
	private String enableFips;
	private String vseLogLevel;
	private ArrayList<Vnic> vnics;
	private ArrayList<LDRInterface> interfaces;	
	private Appliances appliances;
	private CliSetting cliSettings;
	private AutoConfiguration autoConfiguration;
	private String type;
	private QueryDaemon queryDaemon;
	
	private ManegementInterface mgmtInterface;
	
	@XmlElement(name = "datacenterMoid")
	public String getDatacenterMoid() {
		return datacenterMoid;
	}
	public void setDatacenterMoid(String datacenterMoid) {
		this.datacenterMoid = datacenterMoid;
	}
	
	@XmlElement(name = "tenant")
	public String getTenant() {
		return tenant;
	}
	public void setTenant(String tenant) {
		this.tenant = tenant;
	}
	
	@XmlElement(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@XmlElement(name = "fqdn")
	public String getFqdn() {
		return fqdn;
	}
	public void setFqdn(String fqdn) {
		this.fqdn = fqdn;
	}
	
	@XmlElement(name = "description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	@XmlElement(name = "enableAesni")
	public String getEnableAesni() {
		return enableAesni;
	}
	public void setEnableAesni(String enableAesni) {
		this.enableAesni = enableAesni;
	}
	
	@XmlElement(name = "enableFips")
	public String getEnableFips() {
		return enableFips;
	}
	public void setEnableFips(String enableFips) {
		this.enableFips = enableFips;
	}
	
	@XmlElement(name = "vseLogLevel")
	public String getVseLogLevel() {
		return vseLogLevel;
	}
	public void setVseLogLevel(String vseLogLevel) {
		this.vseLogLevel = vseLogLevel;
	}
	
	@XmlElementWrapper(name = "vnics")
	@XmlElement(name = "vnic")
	public ArrayList<Vnic> getVnics() {
		return vnics;
	}
	public void setVnics(ArrayList<Vnic> vnics) {
		this.vnics = vnics;
	}
	
	@XmlElementWrapper(name = "interfaces")
	@XmlElement(name = "interface")
	public ArrayList<LDRInterface> getInterfaces() {
		return interfaces;
	}
	public void setInterfaces(ArrayList<LDRInterface> interfaces) {
		this.interfaces = interfaces;
	}
	
	
	@XmlElement(name = "appliances")
	public Appliances getAppliances() {
		return appliances;
	}

	public void setAppliances(Appliances appliances) {
		this.appliances = appliances;
	}
	
	@XmlElement(name = "cliSettings")
	public CliSetting getCliSettings() {
		return cliSettings;
	}
	public void setCliSettings(CliSetting cliSettings) {
		this.cliSettings = cliSettings;
	}
	
	@XmlElement(name = "autoConfiguration")
	public AutoConfiguration getAutoConfiguration() {
		return autoConfiguration;
	}
	public void setAutoConfiguration(AutoConfiguration autoConfiguration) {
		this.autoConfiguration = autoConfiguration;
	}
	
	@XmlElement(name = "type")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	@XmlElement(name = "queryDaemon")
	public QueryDaemon getQueryDaemon() {
		return queryDaemon;
	}
	public void setQueryDaemon(QueryDaemon queryDaemon) {
		this.queryDaemon = queryDaemon;
	}
	
	@XmlElement(name = "mgmtInterface")
	public ManegementInterface getMgmtInterface() {
		return mgmtInterface;
	}
	public void setMgmtInterface(ManegementInterface mgmtInterface) {
		this.mgmtInterface = mgmtInterface;
	}
	/**
	 * 
	 * @param datacenterMoid
	 * @param tenant
	 * @param name
	 * @param fqdn
	 * @param description
	 * @param enableAesni
	 * @param enableFips
	 * @param vseLogLevel
	 * @param vnics
	 * @param appliances
	 * @param cliSettings
	 * @param autoConfiguration
	 * @param type
	 * @param queryDaemon
	 */
	public Edge(String datacenterMoid, String tenant, String name, String fqdn,
			String description, String enableAesni, String enableFips,
			String vseLogLevel, ArrayList<Vnic> vnics, Appliances appliances,
			CliSetting cliSettings, AutoConfiguration autoConfiguration,
			String type, QueryDaemon queryDaemon) {
		super();
		this.datacenterMoid = datacenterMoid;
		this.tenant = tenant;
		this.name = name;
		this.fqdn = fqdn;
		this.description = description;
		this.enableAesni = enableAesni;
		this.enableFips = enableFips;
		this.vseLogLevel = vseLogLevel;
		this.vnics = vnics;
		this.appliances = appliances;
		this.cliSettings = cliSettings;
		this.autoConfiguration = autoConfiguration;
		this.type = type;
		this.queryDaemon = queryDaemon;
	}
	public Edge() {
		super();
	}
	/**
	 * 
	 * @param datacenterMoid
	 * @param tenant
	 * @param name
	 * @param fqdn
	 * @param description
	 * @param enableAesni
	 * @param enableFips
	 * @param vseLogLevel
	 * @param vnics
	 * @param appliances
	 * @param cliSettings
	 * @param autoConfiguration
	 * @param type
	 * @param queryDaemon
	 * @param mgmtInterface
	 */
	public Edge(String datacenterMoid, String tenant, String name, String fqdn, String description, String enableAesni,
			String enableFips, String vseLogLevel, ArrayList<Vnic> vnics, Appliances appliances, CliSetting cliSettings,
			AutoConfiguration autoConfiguration, String type, QueryDaemon queryDaemon,
			ManegementInterface mgmtInterface) {
		super();
		this.datacenterMoid = datacenterMoid;
		this.tenant = tenant;
		this.name = name;
		this.fqdn = fqdn;
		this.description = description;
		this.enableAesni = enableAesni;
		this.enableFips = enableFips;
		this.vseLogLevel = vseLogLevel;
		this.vnics = vnics;		
		this.appliances = appliances;
		this.cliSettings = cliSettings;
		this.autoConfiguration = autoConfiguration;
		this.type = type;
		this.queryDaemon = queryDaemon;
		this.mgmtInterface = mgmtInterface;
	}
	/**
	 * @param datacenterMoid
	 * @param tenant
	 * @param name
	 * @param fqdn
	 * @param description
	 * @param enableAesni
	 * @param enableFips
	 * @param vseLogLevel
	 * @param interfaces
	 * @param appliances
	 * @param cliSettings
	 * @param autoConfiguration
	 * @param type
	 * @param mgmtInterface
	 */
	public Edge(String datacenterMoid, String tenant, String name, String fqdn, String description, String enableAesni,
			String enableFips, String vseLogLevel, ArrayList<LDRInterface> interfaces, Appliances appliances,
			CliSetting cliSettings, AutoConfiguration autoConfiguration, String type,
			ManegementInterface mgmtInterface) {
		super();
		this.datacenterMoid = datacenterMoid;
		this.tenant = tenant;
		this.name = name;
		this.fqdn = fqdn;
		this.description = description;
		this.enableAesni = enableAesni;
		this.enableFips = enableFips;
		this.vseLogLevel = vseLogLevel;
		this.interfaces = interfaces;
		this.appliances = appliances;
		this.cliSettings = cliSettings;
		this.autoConfiguration = autoConfiguration;
		this.type = type;
		this.mgmtInterface = mgmtInterface;
	}
}

