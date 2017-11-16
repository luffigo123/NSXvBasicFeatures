package com.vmware.nsx6x.model.EdgeFirewall;

import java.util.ArrayList;

public class SourceDestination {
	private ArrayList<String> ipAddress;
	private ArrayList<String> groupingObjectId;
	private ArrayList<String> vnicGroupId;
	
	public SourceDestination() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public SourceDestination(ArrayList<String> ipAddress,
			ArrayList<String> groupingObjectId, ArrayList<String> vnicGroupId) {
		super();
		this.ipAddress = ipAddress;
		this.groupingObjectId = groupingObjectId;
		this.vnicGroupId = vnicGroupId;
	}

	public ArrayList<String> getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(ArrayList<String> ipAddress) {
		this.ipAddress = ipAddress;
	}
	public ArrayList<String> getGroupingObjectId() {
		return groupingObjectId;
	}
	public void setGroupingObjectId(ArrayList<String> groupingObjectId) {
		this.groupingObjectId = groupingObjectId;
	}
	public ArrayList<String> getVnicGroupId() {
		return vnicGroupId;
	}
	public void setVnicGroupId(ArrayList<String> vnicGroupId) {
		this.vnicGroupId = vnicGroupId;
	}
	
	

}
