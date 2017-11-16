package com.vmware.nsx6x.utils;

public class DefaultEnvironment {
	private static Config cfg = Config.getInstance();
	public static final String VSMIP = cfg.ConfigMap.get("VSMIPAddress");
	public static final String VSMUserName = cfg.ConfigMap.get("VSMUserName");
	public static final String VSMPassword = cfg.ConfigMap.get("VSMPassword");
	public static final String VCIP = cfg.ConfigMap.get("VCIP");
	public static final String VCUserName = cfg.ConfigMap.get("VCUserName");
	public static final String VCPassword = cfg.ConfigMap.get("VCPassword");
	public static final String VCPort = cfg.ConfigMap.get("VCPort");
	
	public static final String InputLanguage = cfg.ConfigMap.get("InputLanguage");

	public static final String ssoLookupServiceUrl = cfg.ConfigMap.get("ssoLookupServiceUrl");
	public static final String DNSServer = cfg.ConfigMap.get("DNSServer");
	public static final String ssoAdminUsername = cfg.ConfigMap.get("ssoAdminUsername");
	public static final String ssoAdminUserpassword = cfg.ConfigMap.get("ssoAdminUserpassword");
	public static final String dvSwitchName = cfg.ConfigMap.get("dvSwitchName");
	public static final String dvPortGroupName = cfg.ConfigMap.get("dvPortGroupName");
	public static final String DatacenterName = cfg.ConfigMap.get("DatacenterName");
	public static final String Cluster1 = cfg.ConfigMap.get("Cluster1");
	public static final String Cluster2 = cfg.ConfigMap.get("Cluster2");
	public static final String DataStore1 = cfg.ConfigMap.get("DataStore1");
	public static final String Host1InDvSwitch = cfg.ConfigMap.get("Host1InDvSwitch");
	public static final String Uplink01 = cfg.ConfigMap.get("Uplink01");
	public static final String Internal01 = cfg.ConfigMap.get("Internal01");
	public static final String UplinkIP = cfg.ConfigMap.get("UplinkIP");
	public static final String VM1 = cfg.ConfigMap.get("VM1");
	public static final String VM2 = cfg.ConfigMap.get("VM2");

	//Controller IPPool info
	public static final String IPPoolGateway = cfg.ConfigMap.get("IPPoolGateway");
	public static final String IPPoolPrefixLength = cfg.ConfigMap.get("IPPoolPrefixLength");
	public static final String IPPoolPrimaryDNS = cfg.ConfigMap.get("IPPoolPrimaryDNS");
	public static final String IPPoolSecondaryDNS = cfg.ConfigMap.get("IPPoolSecondaryDNS");
	public static final String IPPoolDNSSuffix = cfg.ConfigMap.get("IPPoolDNSSuffix");
	public static final String IPPoolStartIPAddress = cfg.ConfigMap.get("IPPoolStartIPAddress");
	public static final String IPPoolEndIPAddress = cfg.ConfigMap.get("IPPoolEndIPAddress");
	
	//Edge part
	public static final String edgeGatewayUplinkIPAddress = cfg.ConfigMap.get("edgeGatewayUplinkIPAddress");
	public static final String edgeGatewayUplinkSubnetMask = cfg.ConfigMap.get("edgeGatewayUplinkSubnetMask");
	public static final String edgeGatewayUplinkSubnetPrefixLength = cfg.ConfigMap.get("edgeGatewayUplinkSubnetPrefixLength");
	public static final String edgeGatewayInternalIPAddress = cfg.ConfigMap.get("edgeGatewayInternalIPAddress");
	public static final String edgeGatewayInternalSubnetMask = cfg.ConfigMap.get("edgeGatewayInternalSubnetMask");
	public static final String edgeGatewayInternalSubnetPrefixLength = cfg.ConfigMap.get("edgeGatewayInternalSubnetPrefixLength");
	
	public static final String logicalRouterManagementIPAddress = cfg.ConfigMap.get("logicalRouterManagementIPAddress");
	public static final String logicalRouterManagementSubnetMask = cfg.ConfigMap.get("logicalRouterManagementSubnetMask");
	public static final String logicalRouterManagementSubnetPrefixLength = cfg.ConfigMap.get("logicalRouterManagementSubnetPrefixLength");
	public static final String logicalRouterUplinkIPAddress = cfg.ConfigMap.get("logicalRouterUplinkIPAddress");
	public static final String logicalRouterUplinkSubnetMask = cfg.ConfigMap.get("logicalRouterUplinkSubnetMask");
	public static final String logicalRouterUplinkSubnetPrefixLength = cfg.ConfigMap.get("logicalRouterUplinkSubnetPrefixLength");
	public static final String logicalRouterInternalIPAddress = cfg.ConfigMap.get("logicalRouterInternalIPAddress");
	public static final String logicalRouterInternalSubnetMask = cfg.ConfigMap.get("logicalRouterInternalSubnetMask");
	public static final String logicalRouterInternalSubnetPrefixLength = cfg.ConfigMap.get("logicalRouterInternalSubnetPrefixLength");
	
	public static final String universalLogicalRouterManagementIPAddress = cfg.ConfigMap.get("universalLogicalRouterManagementIPAddress");
	public static final String universalLogicalRouterManagementSubnetMask = cfg.ConfigMap.get("universalLogicalRouterManagementSubnetMask");
	public static final String universalLogicalRouterManagementSubnetPrefixLength = cfg.ConfigMap.get("universalLogicalRouterManagementSubnetPrefixLength");
	public static final String universalLogicalRouterUplinkIPAddress = cfg.ConfigMap.get("universalLogicalRouterUplinkIPAddress");
	public static final String universalLogicalRouterUplinkSubnetMask = cfg.ConfigMap.get("universalLogicalRouterUplinkSubnetMask");
	public static final String universalLogicalRouterUplinkSubnetPrefixLength = cfg.ConfigMap.get("universalLogicalRouterUplinkSubnetPrefixLength");
	public static final String universalLogicalRouterInternalIPAddress = cfg.ConfigMap.get("universalLogicalRouterInternalIPAddress");
	public static final String universalLogicalRouterInternalSubnetMask = cfg.ConfigMap.get("universalLogicalRouterInternalSubnetMask");
	public static final String universalLogicalRouterInternalSubnetPrefixLength = cfg.ConfigMap.get("universalLogicalRouterInternalSubnetPrefixLength");
	
		

}
