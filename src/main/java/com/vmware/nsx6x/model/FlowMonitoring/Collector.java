package com.vmware.nsx6x.model.FlowMonitoring;

public class Collector {
		String ip;
		String port;
		
		/**
		 * 
		 */
		public Collector() {
			super();
		}
		/**
		 * @param ip
		 * @param port
		 */
		public Collector(String ip, String port) {
			super();
			this.ip = ip;
			this.port = port;
		}
		/**
		 * @return the ip
		 */
		public String getIp() {
			return ip;
		}
		/**
		 * @param ip the ip to set
		 */
		public void setIp(String ip) {
			this.ip = ip;
		}
		/**
		 * @return the port
		 */
		public String getPort() {
			return port;
		}
		/**
		 * @param port the port to set
		 */
		public void setPort(String port) {
			this.port = port;
		}
	
}
