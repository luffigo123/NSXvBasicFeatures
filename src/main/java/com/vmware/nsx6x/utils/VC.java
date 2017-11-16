package com.vmware.nsx6x.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.vmware.vc.ManagedObjectReference;
import com.vmware.vc.UserSession;
import com.vmware.vcqa.ConnectAnchor;
import com.vmware.vcqa.execution.setup.util.DatacenterHelper;
import com.vmware.vcqa.internal.vim.InternalServiceDirectory;
import com.vmware.vcqa.vim.ClusterComputeResource;
import com.vmware.vcqa.vim.Datacenter;
import com.vmware.vcqa.vim.Datastore;
import com.vmware.vcqa.vim.DistributedVirtualPortgroup;
import com.vmware.vcqa.vim.DistributedVirtualSwitch;
import com.vmware.vcqa.vim.Folder;
import com.vmware.vcqa.vim.HostSystem;
import com.vmware.vcqa.vim.Network;
import com.vmware.vcqa.vim.ResourcePool;
import com.vmware.vcqa.vim.SessionManager;
import com.vmware.vcqa.vim.VirtualApp;
import com.vmware.vcqa.vim.VirtualMachine;
import com.vmware.vcqa.vim.dvs.DVSUtil;
import com.vmware.vcqa.vim.profile.host.HostProfile;
import com.vmware.vcqa.vim.profile.host.ProfileManager;
import com.vmware.vcqa.vim.profile.host.ProfileManagerUtil;


public class VC {
	private static VC vc = null;
	public Config cfg = Config.getInstance();
	public String VCIP = cfg.ConfigMap.get("VCIP");
	public String VCPort =  cfg.ConfigMap.get("VCPort");
	public String VCUserName = cfg.ConfigMap.get("VCUserName");
	public String VCPassword = cfg.ConfigMap.get("VCPassword");
	public String ssoLookupServiceUrl = cfg.ConfigMap.get("ssoLookupServiceUrl");
	public String ssoAdminUsername = cfg.ConfigMap.get("ssoAdminUsername");
	public String ssoAdminUserpassword = cfg.ConfigMap.get("ssoAdminUserpassword");
	public String DNSServer = cfg.ConfigMap.get("DNSServer"); 
	public String DatacenterName = cfg.ConfigMap.get("DatacenterName");
	
//	private static LocalLog info = LocalLog.getInstance("localLogFile");
	private static Logger info = Log4jInstance.getLoggerInstance();
	
	public enum MorType {
		Datacenter, DistributedVirtualSwitch, Network, Datastore, Cluster, Host, VirtualMachine, Folder
	}
	/*
	
	public String ESXStandAlone_Name = cfg.ConfigMap.get("ESXStandAlone_Name");
	public String ESXStandAlone_UserName = cfg.ConfigMap.get("ESXStandAlone_UserName");
	public String ESXStandAlone_Password = cfg.ConfigMap.get("ESXStandAlone_Password");
	public String ESXNoneAdd1_Name = cfg.ConfigMap.get("ESXNoneAdd1_Name");
	public String ESXNoneAdd1_UserName = cfg.ConfigMap.get("ESXNoneAdd1_UserName");
	public String ESXNoneAdd1_Password = cfg.ConfigMap.get("ESXNoneAdd1_Password");
	public String ESXNoneAdd2_Name = cfg.ConfigMap.get("ESXNoneAdd2_Name");
	public String ESXNoneAdd2_UserName = cfg.ConfigMap.get("ESXNoneAdd2_UserName");
	public String ESXNoneAdd2_Password = cfg.ConfigMap.get("ESXNoneAdd2_Password");
	public String ESXResource_Name = cfg.ConfigMap.get("ESXResource_Name");
	public String ESXResource_UserName = cfg.ConfigMap.get("ESXResource_UserName");
	public String ESXResource_Password = cfg.ConfigMap.get("ESXResource_Password");
	public String VM_With_GuestOS_W2K8R2 = cfg.ConfigMap.get("VM_With_GuestOS_W2K8R2");
	public String NFS_IP = cfg.ConfigMap.get("NFS_IP");
	public String NFS_Folder = cfg.ConfigMap.get("NFS_Folder");
	*/
	public ConnectAnchor connectAnchor = null;
	public SessionManager sessionManager = null;
	public UserSession userSession = null;
	public Folder folder = null;
	public ManagedObjectReference rootFdrMor = null;
	public Datacenter datacenter = null;
	public DatacenterHelper dcHelper = null;
	public ClusterComputeResource cluster = null;
	public HostSystem hostSystem = null;
	public Datastore datastore = null;
	public Network network = null;
	public VirtualMachine vm = null;
	public ResourcePool resourcePool = null;
	public VirtualApp vApp = null;
	public DistributedVirtualSwitch dvs = null;
	public DistributedVirtualPortgroup dvPortGroup = null;
	public HostProfile hostProfile = null;
	public ProfileManager profileMgr = null;
	public ProfileManagerUtil profileMgrUtil = null;
	
	private Boolean loggedOn = false;
	private VC()
	{
		try {
			Init();
			info.info("Init VC successfully!");
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void Init() throws NumberFormatException, Exception
	{
		// login
		if (this.connectAnchor == null)
		{
			this.connectAnchor = new ConnectAnchor(this.VCIP, Integer.parseInt(this.VCPort));
			this.sessionManager = new SessionManager(this.connectAnchor);
			userSession = SessionManager.login(this.connectAnchor, this.VCUserName, this.VCPassword);
			this.loggedOn = true;
			this.folder = new Folder(this.connectAnchor);
			this.rootFdrMor = folder.getRootFolder();
			this.datacenter = new Datacenter(this.connectAnchor);
			this.dcHelper = new DatacenterHelper(connectAnchor);
			this.cluster = new ClusterComputeResource(this.connectAnchor);
			this.hostSystem = new HostSystem(this.connectAnchor);
			this.datastore = new Datastore(this.connectAnchor);
			this.network = new Network(this.connectAnchor);
			this.vm = new VirtualMachine(this.connectAnchor);
			this.resourcePool = new ResourcePool(this.connectAnchor);
			this.vApp = new VirtualApp(this.connectAnchor);
			this.dvs = new DistributedVirtualSwitch(this.connectAnchor);
			this.dvPortGroup = new DistributedVirtualPortgroup(this.connectAnchor);
			this.hostProfile = new HostProfile(this.connectAnchor);
			this.profileMgr = new ProfileManager(this.connectAnchor);
			this.profileMgrUtil = new ProfileManagerUtil(this.connectAnchor);
			
			info.info("Init VC instance successfully!");
			info.info("connectAnchor values: " + connectAnchor );
			info.info("sessionManager values: " + sessionManager );
			info.info("userSession values: " + userSession );
			info.info("folder values: " + folder );
			info.info("rootFdrMor values: " + rootFdrMor );
			info.info("datacenter values: " + datacenter );
			info.info("cluster values: " + cluster );
			info.info("datastore values: " + datastore );
			info.info("network values: " + network );
			
		}
		// check default environment
	}
	public static synchronized VC getInstance()
	{
		try {
			if (vc == null)
				vc = new VC();
				info.info("Create a new VC instance");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			info.info("Failed to get  VC instance: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
		return vc;
	}

	public void cleanUp() throws Exception{
		// ToDo: Clean up the environment.
		
		// info out
		if (this.loggedOn)
			SessionManager.logout(vc.connectAnchor);
	
	}
	/**
	 * Get the NSX VM's IP address for the specific vCenter Server (vCenterIPDeploy key in config) with username (vCenterAdminUser), password (vCenterAdminPassword) and ESXi host(EsxiHostIPDeploy)
	 * @param vmName the NSX Virutal mahchine's name
	 * @return the String of IP Address
	 * @throws Exception 
	 */
	public String getVMIPAddress (String hostIp, String vmName) throws Exception {
		ManagedObjectReference hmor = this.hostSystem.getHost(hostIp);
		ManagedObjectReference vmor = this.vm.getVM(vmName);
		com.vmware.vc.GuestInfo gi = vm.getVMGuestInfo(vmor);
		return gi.getIpAddress();
	}

	
	///The methods for getting the ID for the managed objects
	/**
	 * Getting the MOID for the specified cluster name
	 * @param ca
	 * @param clusterName
	 * @return the cluster MOID
	 * @throws Exception 
	 */
	public String getClusterMOID (String clusterName) {
		String clusterMoid = null;
			try {
				clusterMoid = this.cluster.getClusterByName(clusterName).getValue().trim();
				info.info("Get clusterMoid successfully! And the value is :" +  clusterMoid);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				info.info("Failed to get clusterMoid!" + e.getMessage());
				e.printStackTrace();
			}
			return clusterMoid;
	}
	
	/**
	 * Get the DataCenter MOID for the specified datacenter name.
	 * @param ca
	 * @param dataCenterName
	 * @return the datacenter MOID
	 * @throws Exception
	 */
	public String getDataCenterMOID (String dataCenterName) {
		//ManagedObjectReference dcmo = this.datacenter.getDataCenter(this.folder.getDataCenter(dataCenterName));
			ManagedObjectReference dcmo = null;
			try {
				dcmo = this.datacenter.getDataCenter(this.folder.getDataCenter(dataCenterName));
				info.info("Get datacenterMoid successfully! And the value is :" +  dcmo.getValue());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				info.info("Failed to get datacenterMoid!" + e.getMessage());
				e.printStackTrace();
			}
			return dcmo.getValue().trim();
	}
	
	/**
	 * Get the host moid by its host ip or host name.
	 * @param ca
	 * @param hostIp
	 * @return the host moid string
	 * @throws Exception
	 */
	public String getHostMOID (String hostIp) {
			//ManagedObjectReference mor = this.hostSystem.getHost(hostIp);
		ManagedObjectReference mor = null;
		try {
			mor = this.hostSystem.getHost(hostIp);
			info.info("Get hostMoid successfully! And the value is :" +  mor.getValue());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			info.info("Failed to get hostMoid!" + e.getMessage());
			e.printStackTrace();
		}
		return mor.getValue().trim();
	}
	
	/**
	 * Get the datastore id for the specified datastore name in the host
	 * @param hostIp or hostname
	 * @param dataStoreName
	 * @return the datastore id String
	 */
	public String getDatastoreMOID (String hostIp, String dataStoreName) {
			//ManagedObjectReference mor = this.datastore.getDatastore(this.hostSystem.getHost(hostIp), dataStoreName);
		ManagedObjectReference mor = null;
		try {
			mor = this.datastore.getDatastore(this.hostSystem.getHost(hostIp), dataStoreName);
			info.info("Get datastoreMoid successfully! And the value is :" +  mor.getValue());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			info.info("Failed to get datastoreMoid!" + e.getMessage());
			e.printStackTrace();
		}
			return mor.getValue().trim();
	}

	
	/**
	 * Get the Network MOR by the specified name, e.g. "dvPortGroup"
	 * @param networkName
	 * @return the Network ID
	 * @throws Exception
	 */
	public String getNetworkMoIdByName (String networkName) {
//			Vector<ManagedObjectReference> morListNet = this.folder.getAllChildEntity(this.rootFdrMor,MorType.Network.toString());
//			for (int i=0; i<morListNet.size();i++) {
//				if(this.network.getName(morListNet.get(i)).equals(networkName)) {									//Locate the MOR with the specified name
//					return morListNet.get(i).getValue();
//				}	
//			}
//			return null;
			
		Vector<ManagedObjectReference> morListNet =null;
		try {
			morListNet = this.folder.getAllChildEntity(this.rootFdrMor,MorType.Network.toString());
		} catch (Exception e) {
			info.info("Failed to get networkMor List!" + e.getMessage());
			e.printStackTrace();
		}
		for (int i=0; i<morListNet.size();i++) {
			try {
				if(this.network.getName(morListNet.get(i)).equals(networkName)) {
					info.info("Get networkMoid successfully! And the value is :" +  morListNet.get(i).getValue());
					return morListNet.get(i).getValue();
				}
			} catch (Exception e) {
				info.info("Failed to get networkMor value!" + e.getMessage());
				e.printStackTrace();
			}	
		}
			return null;
	}

	
	/**
	 * Get vCenter Server's SSL Thumbprint
	 * @param ca
	 * @return the vCenter server's SSL thumbprint
	 * @throws Exception
	 */
	public String getVcSslThumbprint () throws Exception {
		InternalServiceDirectory isd = new InternalServiceDirectory(this.connectAnchor);
		com.vmware.vcqa.vim.ServiceInstance si = new com.vmware.vcqa.vim.ServiceInstance(this.connectAnchor);
			if (si.isVirtualCenter()) {
		         List<com.vmware.vc.ServiceEndpoint> serviceEndPoints = isd.queryServiceEndpointList();
		         for (com.vmware.vc.ServiceEndpoint serviceEndPoint:serviceEndPoints) {
		            if (serviceEndPoint.getProtocol().equals(
		                InternalServiceDirectory.SVC_PROT_VIM_API)) {
		            	return serviceEndPoint.getSslThumbprint();
		            }
		         }
		      }
			return null;
	 }
	
	
	/**
	 * Get the MoId for DistributedVirtualSwitch for the specified dvsWitch name (Format: dvs-18 (dvSwitch), in mob page)
	 * @param dvSwithcName
	 * @return the 
	 * @throws Exception
	 */
	public String getdvSwitchMoIdByName (String dvSwitchName) {		
//		String morTypeStr = MorType.DistributedVirtualSwitch.toString(); //Should be "DistributedVirtualSwitch"
//			Vector<ManagedObjectReference> morListNet = this.folder.getAllChildEntity(this.rootFdrMor,morTypeStr);			
//			for (int i=0; i<morListNet.size();i++) {
//				if(this.dvs.getName(morListNet.get(i)).equals(dvSwitchName)) {									//Locate the MOR with the specified name
//					return morListNet.get(i).getValue();
//				}
//			}
//			return null;
		
		String morTypeStr = MorType.DistributedVirtualSwitch.toString(); 
		Vector<ManagedObjectReference> morListNet = null;
		try {
			morListNet = this.folder.getAllChildEntity(this.rootFdrMor,morTypeStr);
		} catch (Exception e) {
			info.info("Failed to get dvSwitchMor List!" + e.getMessage());
			e.printStackTrace();
		}			
		for (int i=0; i<morListNet.size();i++) {
			try {
				if(this.dvs.getName(morListNet.get(i)).equals(dvSwitchName)) {
					info.info("Get dvSwitchMoid successfully! And the value is :" +  morListNet.get(i).getValue());
					return morListNet.get(i).getValue();
				}
			} catch (Exception e) {
				info.info("Failed to get dvSwitchMor value!" + e.getMessage());
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * Get the MO ID for the Virtual Machine by its name
	 * @param vmName
	 * @return VM MOId
	 * @throws Exception
	 */
	public String getVirtualMachineMoIdByName (String vmName) {
//			Vector<ManagedObjectReference> morListNet = this.folder.getAllChildEntity(this.rootFdrMor,MorType.VirtualMachine.toString());			
//			for (int i=0; i<morListNet.size();i++) {
//				if(this.vm.getName(morListNet.get(i)).equals(vmName)) {	//Locate the MOR with the specified name
//					return morListNet.get(i).getValue();
//				}
//			}
//			return null;
		Vector<ManagedObjectReference> morListNet = null;
		try {
			morListNet = this.folder.getAllChildEntity(this.rootFdrMor,MorType.VirtualMachine.toString());
		} catch (Exception e) {
			info.info("Failed to get VMmoid List!" + e.getMessage());
			e.printStackTrace();
		}			
		for (int i=0; i<morListNet.size();i++) {
			try {
				if(this.vm.getName(morListNet.get(i)).equals(vmName)) {
					info.info("Get vmMoid successfully! And the value is :" +  morListNet.get(i).getValue());
					return morListNet.get(i).getValue();
				}
			} catch (Exception e) {
				info.info("Failed to get VMmoid!" + e.getMessage());
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	/**
	 * Get the VM Folder's MOID
	 * @return
	 * @throws Exception
	 */
	public String getVmFolderMoId () throws Exception {
			Vector<ManagedObjectReference> morListFold = this.folder.getAllChildEntity(this.rootFdrMor,MorType.Folder.toString());
			for (int i=0; i<morListFold.size();i++) {
				if(this.vm.getName(morListFold.get(i)).equals("vm")) {									//Locate the MOR with the specified name
					return morListFold.get(i).getValue();
				}
			}
			return null;
	}
	
	/**Need add dvPortGroup and return the dvmoid!!!!!
	 * Create dvSwitch (???Only 1 uplink added)
	 * @param hostIp
	 * @param dvSName
	 * @return
	 * @throws Exception
	 */
	public String createDVSwitch (String[] hostIp, String dvSName) throws Exception {
		String dvSMoId = "";
		ManagedObjectReference [] hsMor = new ManagedObjectReference [hostIp.length]; 
		List<ManagedObjectReference> lhMor = new ArrayList<ManagedObjectReference>();
			//Add all hosts in array hostIp[] with one free vNic
			for (int j = 0; j < hostIp.length; j++) {
				hsMor [j] = this.hostSystem.getHost(hostIp[j]);
				if(lhMor.add(hsMor[j]))
					continue;
				else
					break;
			}
			//DVSUtil.??? find the method for creat dvsw with 4 uplink ports.
			ManagedObjectReference dvsMor = DVSUtil.createDVSWithMAXPorts(this.connectAnchor, lhMor, 4, dvSName); //create the dv switch but only 1 uplink port added (even add the uplink or port group.
			DVSUtil.createUplinkPortGroup(this.connectAnchor, dvsMor, "TestUpLinkGroup", 8); //Add uplinks with specify ports
			dvSMoId = getdvSwitchMoIdByName(dvSName);
			this.dvs.addPortGroup(dvsMor, com.vmware.vc.DistributedVirtualPortgroupPortgroupType.EARLY_BINDING.value(), 8, "dvs0001");//Add port group (specify the port number
		return dvSMoId;
	}
	
	public String addDvpGroup (String dvsName, String dvpGroupName) throws Exception {
		String dvpGroupMoId = "";
		String dvsMoId = getdvSwitchMoIdByName(dvsName);
		try {
//			DistributedVirtualSwitch vDs = new DistributedVirtualSwitch(ca);
//			ManagedObjectReference dvsMor = DVSUtil.getHostDVSMgrMor(ca, hostMor)
//			vDs.addPortGroup(dvsMor, type, numPort, name)
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//vDS.addPortGroup(dvsMor, type, numPort, name)
		
		
		return dvpGroupMoId;
	}
	
	
}
