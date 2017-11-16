package com.vmware.nsx6x.common;

public class EdgeConstant {
	
	public enum EdgeSize
	{
		COMPACT ("compact"),
		LARGE ("large"),
		XLARGE ("xlarge"),
		QUADLARGE ("quadlarge");
		
		private String value;

		private EdgeSize(String value)
		{
			this.value = value;
		}

		/**
		 * @return the value
		 */
		public String getValue() {
			return value;
		}
	}

}
