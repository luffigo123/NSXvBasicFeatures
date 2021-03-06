package com.vmware.nsx6x.model.VXLAN;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name = "multicastRange")
@XmlSeeAlso({})
public class MultiCast {
	
	private String name;
	private String begin;
	private String end;
	private String isUniversal;
	
	@XmlElement(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@XmlElement(name = "begin")
	public String getBegin() {
		return begin;
	}
	public void setBegin(String begin) {
		this.begin = begin;
	}
	@XmlElement(name = "end")
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	
	@XmlElement(name = "isUniversal")
	public String getIsUniversal() {
		return isUniversal;
	}
	public void setIsUniversal(String isUniversal) {
		this.isUniversal = isUniversal;
	}
	public MultiCast() {
		super();
	}
	
	/**
	 * 
	 * @param name
	 * @param begin
	 * @param end
	 */
	public MultiCast(String name, String begin, String end) {
		super();
		this.name = name;
		this.begin = begin;
		this.end = end;
	}
	
	/**
	 * 
	 * @param name
	 * @param begin
	 * @param end
	 * @param isUniversal
	 */
	public MultiCast(String name, String begin, String end, String isUniversal) {
		super();
		this.name = name;
		this.begin = begin;
		this.end = end;
		this.isUniversal = isUniversal;
	}
	
	
}
