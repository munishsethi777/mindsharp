package com.satya.enums;

public enum OrganizationType {
	school("School"),
	college("College "),
	university("University"),
	professional("Professional");
	
	private String key;
	
	private OrganizationType(String key){
		this.key = key;
	}
	
	public String key(){
		return key;
	}
}
