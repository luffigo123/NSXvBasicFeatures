package com.vmware.nsx6x.utils;

import org.apache.log4j.Logger;

import com.vmware.vc.ManagedObjectReference;
import com.vmware.vc.UserSession;
import com.vmware.vcqa.ConnectAnchor;
import com.vmware.vcqa.execution.setup.util.DatacenterHelper;
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
import com.vmware.vcqa.vim.profile.host.HostProfile;
import com.vmware.vcqa.vim.profile.host.ProfileManager;
import com.vmware.vcqa.vim.profile.host.ProfileManagerUtil;

public class VC2 {
//	public Config cfg = Config.getInstance();
	public String VCIP;
	public String VCPort;
	public String VCUserName;
	public String VCPassword;
//	public String ssoLookupServiceUrl = cfg.ConfigMap.get("ssoLookupServiceUrl");
//	public String ssoAdminUsername = cfg.ConfigMap.get("ssoAdminUsername");
//	public String ssoAdminUserpassword = cfg.ConfigMap.get("ssoAdminUserpassword");
/*	public String DNSServer = cfg.ConfigMap.get("DNSServer"); 
	public String DatacenterName = cfg.ConfigMap.get("DatacenterName");*/
	
	private static Logger info = Log4jInstance.getLoggerInstance();
	

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
	
	public VC2()
	{
		VCIP = DefaultEnvironment.VCIP;
		VCPort = DefaultEnvironment.VCPort;
		VCUserName = DefaultEnvironment.VCUserName;
		VCPassword = DefaultEnvironment.VCPassword;
		
		
		if (this.connectAnchor == null)
		{
			try {
				this.connectAnchor = new ConnectAnchor(this.VCIP, Integer.parseInt(this.VCPort));
				this.sessionManager = new SessionManager(this.connectAnchor);
				userSession = SessionManager.login(this.connectAnchor, this.VCUserName, this.VCPassword);
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
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

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
	}
}
