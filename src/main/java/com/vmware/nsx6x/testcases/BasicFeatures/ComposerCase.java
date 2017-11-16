package com.vmware.nsx6x.testcases.BasicFeatures;

import org.testng.annotations.Test;

import com.vmware.nsx6x.service.BasicFeatures.ComposerManager;

public class ComposerCase {
	
	@Test(priority=0,groups = {"basicFunction"},alwaysRun=true)
	public void luanch() {
		ComposerManager mgr = new ComposerManager();
		mgr.setupVIB();
		mgr.setupVXLANConfigure();
		mgr.setupSegmentIPPool();
		mgr.setupMulticast();
		mgr.setupTransportZone();
		mgr.setupLogicalSwitch();
		mgr.installEdge();
		mgr.setupSSLVPN();
		mgr.installControllers();
		mgr.setupLogicalRouter();
		mgr.setupUniversalLogicalRouter();
	}
	
}
