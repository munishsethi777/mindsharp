package com.satya.enums;

public enum GameSkillBadgeType {
	newbie("Newbie"),
	starter("Starter"),
	amateur("Amateur"),
	enthusiast("Enthusiast"),
	professional("Professional"),
	superStar("Super Star");
	
	private String key;
	
	private GameSkillBadgeType(String key){
		this.key = key;
	}
	
	public String key(){
		return key;
	}
}
