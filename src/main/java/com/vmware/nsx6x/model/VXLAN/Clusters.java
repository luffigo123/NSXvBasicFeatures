package com.vmware.nsx6x.model.VXLAN;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;

public class Clusters {
	private ArrayList<Cluster> cluster;

	@XmlElement(name = "cluster")
	public ArrayList<Cluster> getCluster() {
		return cluster;
	}

	public void setCluster(ArrayList<Cluster> cluster) {
		this.cluster = cluster;
	}

	public Clusters(ArrayList<Cluster> cluster) {
		super();
		this.cluster = cluster;
	}

	public Clusters() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
