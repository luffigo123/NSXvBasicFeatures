package com.vmware.nsx6x.service.BasicFeatures;

import com.vmware.nsx6x.service.Controller.ControllerManager;
import com.vmware.nsx6x.service.Edge.EdgeManger;
import com.vmware.nsx6x.service.Edge.SSLVPNManager;
import com.vmware.nsx6x.service.LogicalSwitch.LogicalSwitchService;
import com.vmware.nsx6x.service.VXLAN.VXLANManager;

public class ComposerManager {
	
	private VXLANManager vxlanMgr;
	private ControllerManager controllerMgr;
	private EdgeManger edgeMgr;
	private SSLVPNManager sslvpnMgr;
	private LogicalSwitchService logicalSwitchService;
	
	public ComposerManager() {
		vxlanMgr = new VXLANManager();
		controllerMgr = new ControllerManager();
		edgeMgr = new EdgeManger();
		sslvpnMgr = new SSLVPNManager();
		logicalSwitchService = new LogicalSwitchService();
	}
	
	public void setupVIB() {
		this.vxlanMgr.setupVIB_Env();
		try {
			Thread.sleep(3000);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setupVXLANConfigure() {
		this.vxlanMgr.setup_VXLANConfigure();
		try {
			Thread.sleep(3000);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setupSegmentIPPool() {
		this.vxlanMgr.setDefaultSegmentIPPool();
		try {
			Thread.sleep(3000);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setupMulticast() {
		this.vxlanMgr.setDefaultMulticast();
		try {
			Thread.sleep(3000);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void setupTransportZone() {
		this.vxlanMgr.setDefaultTransportZone();
		this.vxlanMgr.setDefault_UniversalTransportZone();
		try {
			Thread.sleep(120000);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void installControllers() {
		this.controllerMgr.setupThreeControllers();
		try {
			Thread.sleep(3000);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void installEdge() {
		this.edgeMgr.setDefaultEdgeEnv();
		try {
			Thread.sleep(3000);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setupSSLVPN() {
		this.sslvpnMgr.setServerSettingEnv();
		this.sslvpnMgr.setDefaulIPPoolENV();
		this.sslvpnMgr.setDefaultAuthEnv();
//		this.sslvpnMgr.setDefaultInstallationPackageEnv();
		this.sslvpnMgr.setDefaultPrivateNetworkENV();
		this.sslvpnMgr.setDefaultInstallationPackageEnv();
		this.sslvpnMgr.setDefaultUserEnv();
		this.sslvpnMgr.enableSSLVPN();
		try {
			Thread.sleep(3000);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setupLogicalSwitch() {
		logicalSwitchService.setup2DefaultLogicalSwitch();
		logicalSwitchService.setup2Default_UniversalLogicalSwitch();
		try {
			Thread.sleep(3000);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setupLogicalRouter() {
		this.edgeMgr.setupDefaultLDR();
		try {
			Thread.sleep(3000);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setupUniversalLogicalRouter() {
		this.edgeMgr.setupDefaultUniversalLogicalRouter();
		try {
			Thread.sleep(3000);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
