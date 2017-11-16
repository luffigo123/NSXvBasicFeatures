package com.vmware.nsx6x.utils;

import java.util.Vector;

import org.apache.log4j.Logger;

import com.vmware.vc.ManagedObjectReference;


public class VCUtils {
	public VC2 vc = null;
	private static Logger info = Log4jInstance.getLoggerInstance();
	
	public VCUtils()
	{
		vc = new VC2();
	}
		
	public enum MorType {
		Datacenter, DistributedVirtualSwitch, Network, Datastore, Cluster, Host, VirtualMachine, Folder
	}
		

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
				clusterMoid = vc.cluster.getClusterByName(clusterName).getValue().trim();
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
		//ManagedObjectReference dcmo = vc.datacenter.getDataCenter(vc.folder.getDataCenter(dataCenterName));
			ManagedObjectReference dcmo = null;
			try {
				dcmo = vc.datacenter.getDataCenter(vc.folder.getDataCenter(dataCenterName));
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
			//ManagedObjectReference mor = vc.hostSystem.getHost(hostIp);
		ManagedObjectReference mor = null;
		try {
			mor = vc.hostSystem.getHost(hostIp);
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
			//ManagedObjectReference mor = vc.datastore.getDatastore(vc.hostSystem.getHost(hostIp), dataStoreName);
		ManagedObjectReference mor = null;
		try {
			mor = vc.datastore.getDatastore(vc.hostSystem.getHost(hostIp), dataStoreName);
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
		Vector<ManagedObjectReference> morListNet =null;
		try {
			morListNet = vc.folder.getAllChildEntity(vc.rootFdrMor,MorType.Network.toString());
		} catch (Exception e) {
			info.info("Failed to get networkMor List!" + e.getMessage());
			e.printStackTrace();
		}
		for (int i=0; i<morListNet.size();i++) {
			try {
				if(vc.network.getName(morListNet.get(i)).equals(networkName)) {
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
	 * Get the MoId for DistributedVirtualSwitch for the specified dvsWitch name (Format: dvs-18 (dvSwitch), in mob page)
	 * @param dvSwithcName
	 * @return the 
	 * @throws Exception
	 */
	public String getdvSwitchMoIdByName (String dvSwitchName) {		

		String morTypeStr = MorType.DistributedVirtualSwitch.toString(); 
		Vector<ManagedObjectReference> morListNet = null;
		try {
			morListNet = vc.folder.getAllChildEntity(vc.rootFdrMor,morTypeStr);
		} catch (Exception e) {
			info.info("Failed to get dvSwitchMor List!" + e.getMessage());
			e.printStackTrace();
		}			
		for (int i=0; i<morListNet.size();i++) {
			try {
				if(vc.dvs.getName(morListNet.get(i)).equals(dvSwitchName)) {
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
		Vector<ManagedObjectReference> morListNet = null;
		try {
			morListNet = vc.folder.getAllChildEntity(vc.rootFdrMor,MorType.VirtualMachine.toString());
		} catch (Exception e) {
			info.info("Failed to get VMmoid List!" + e.getMessage());
			e.printStackTrace();
		}			
		for (int i=0; i<morListNet.size();i++) {
			try {
				if(vc.vm.getName(morListNet.get(i)).equals(vmName)) {
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

}

