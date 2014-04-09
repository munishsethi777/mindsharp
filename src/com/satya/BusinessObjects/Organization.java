package com.satya.BusinessObjects;

import java.util.List;

import com.satya.enums.OrganizationType;

public class Organization {
	private long seq;
	private OrganizationType type;
	private String name;
	private String address;
	private String state;
	private String country;
	
	public Organization(){
		
	}
	public Organization(long seq){
		this.setSeq(seq);
	}
	public long getSeq() {
		return seq;
	}
	public void setSeq(long seq) {
		this.seq = seq;
	}
	public OrganizationType getType() {
		return type;
	}
	public void setType(OrganizationType type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
}
