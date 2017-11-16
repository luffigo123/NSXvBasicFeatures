package TestSample;

import org.testng.annotations.Test;

import com.vmware.nsx6x.service.Installation.UniversalSyncRoleManager;

public class TestObject {
	
//	@Test
//	public void testController() {
//		ControllerManager mgr = new ControllerManager();
//		String result = mgr.getSpecificControllerId("あア１ａ中鷗屢简体ÖÜß體字₩겨ㅊㅝஹ்สดีşıĐứсη");
//		System.out.println(result);
//	}
//	
	@Test
	public void testUniversalSyncRole() {
		UniversalSyncRoleManager mgr = new UniversalSyncRoleManager();
		mgr.queryUniveralSyncRole();
		mgr.assignRoleToManager("set-as-primary");
	}
}
