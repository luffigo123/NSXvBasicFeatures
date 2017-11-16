package com.vmware.nsx6x.model.VXLAN;

import javax.xml.bind.annotation.XmlElement;

public class Cluster {
	private ClusterNest cluster;

	@XmlElement(name = "cluster")
	public ClusterNest getCluster() {
		return cluster;
	}

	public void setCluster(ClusterNest cluster) {
		this.cluster = cluster;
	}

	public Cluster(ClusterNest cluster) {
		super();
		this.cluster = cluster;
	}

	public Cluster() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
