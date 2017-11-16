package com.vmware.nsx6x.service.ExclusionList;

import com.vmware.nsx6x.utils.DefaultEnvironment;
import com.vmware.nsx6x.utils.HttpReq;
import com.vmware.nsx6x.utils.VCUtils;



public class ExclusionListManager {

	public HttpReq httpReq;
	private String VSMIP;
	
//	public VC vc1;
	
	public VCUtils vcUtils;
	
	//private String vmName = Config.getInstance().ConfigMap.get("VM1");
	public String vmName = DefaultEnvironment.VM1;
//	public ExclusionListManager el = new ExclusionListManager();
	public Boolean isBaseVMExist = false;
	
	public ExclusionListManager(){
		VSMIP = DefaultEnvironment.VSMIP;
		httpReq = HttpReq.getInstance();
//		vc1 = VC.getInstance();
		
		vcUtils = new VCUtils();
	}
	
	
	/**
	 * Add VM into the Exclusion List
	 * @param vmId
	 * @throws Exception
	 */
	public void addVM2ExclusionList(String vmId) throws Exception{
		String endPoint = "https://" + VSMIP + "/api/2.1/app/excludelist/" + vmId;
		httpReq.putRequest("\r",endPoint);
	}
	
	/**
	 * Retrive the set of VMs in the exclusion list
	 * @return
	 * @throws Exception
	 */
	public String getVMExlusionList() throws Exception {
		String endPoint = "https://" + VSMIP + "/api/2.1/app/excludelist";
		return httpReq.getRequest(endPoint);
	}
	
	/**
	 * Delete a VM from exclusion list
	 * @param vmId
	 * @throws Exception
	 */
	public void delVMfromExclusionList(String vmId) throws Exception{
		String endPoint = "https://" + VSMIP + "/api/2.1/app/excludelist/" + vmId;
		httpReq.delRequest(endPoint);
	}
	
	/**
	 * Check if the specified VM exist in the Exclusion List
	 * @param vmName
	 * @return
	 * @throws Exception
	 */
	public Boolean chkVMinExclusionList(String vmName) throws Exception{
		Boolean isExist = false;
		String listStr = getVMExlusionList();
		if(listStr.contains(vmName)) {
			isExist = true;
		}
		return isExist;
	}
	
//****************************************************************************

	
	/**
	 * check the base env for case Add vm into exclusion list
	 * @throws Exception
	 */
	public void chkBaseAddEL() throws Exception{
		if(chkVMinExclusionList(vmName)) {
			isBaseVMExist = true;
//			delVMfromExclusionList(vc1.getVirtualMachineMoIdByName(vmName));
delVMfromExclusionList(vcUtils.getVirtualMachineMoIdByName(vmName));
		}
	}
	
	/**
	 * Add VM into exclusion list
	 * @return
	 * @throws Exception
	 */
	public Boolean addVM2EL() throws Exception{
		Boolean isPassed = false;
//		String vmId = vc1.getVirtualMachineMoIdByName(vmName);
String vmId = vcUtils.getVirtualMachineMoIdByName(vmName);		
		addVM2ExclusionList(vmId);
		if(chkVMinExclusionList(vmName)) {
			isPassed = true;
		}
		return isPassed;
	}
	
	/**
	 * Clear env after running cases of add vm into exclusion list
	 * @throws Exception
	 */
	public void clrAddVM2EL() throws Exception{
		if(chkVMinExclusionList(vmName)) {
			//el.addVM2ExclusionList(vc1.getVirtualMachineMoIdByName(vmName));
			this.delEL();
		}
	}
	
	/**
	 * Check base env for del vm from exclusion list
	 * @throws Exception
	 */
	public void chkBaseDelEL() throws Exception {
//		String vmId = vc1.getVirtualMachineMoIdByName(vmName);
String vmId = vcUtils.getVirtualMachineMoIdByName(vmName);
		if(chkVMinExclusionList(vmName)) {
			isBaseVMExist = true;
			delVMfromExclusionList(vmId);
		}
		else {
			addVM2ExclusionList(vmId);
		}
	}
	
	/**
	 * Del vm from exclusion list
	 * @return
	 * @throws Exception
	 */
	public Boolean delEL() throws Exception {
		Boolean isDel = false;
//		String vmId = vc1.getVirtualMachineMoIdByName(vmName);
String vmId = vcUtils.getVirtualMachineMoIdByName(vmName);
		delVMfromExclusionList(vmId);
		if(!chkVMinExclusionList(vmName))
			isDel = true;
		return isDel;
	}
	
	/**
	 * Clear env after running case of del vm from exclusion list
	 * @throws Exception
	 */
	public void clrBaseDelEl() throws Exception {
//		String vmId = vc1.getVirtualMachineMoIdByName(vmName);
String vmId = vcUtils.getVirtualMachineMoIdByName(vmName);
		if(isBaseVMExist) {
			addVM2ExclusionList(vmId);
		}
	}
	
}
