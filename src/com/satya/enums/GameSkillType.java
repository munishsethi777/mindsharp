package com.satya.enums;

public enum GameSkillType {
	speed("Speed"),
	flexibility("Flexibility"),
	problemSolving("Problem Solving"),
	attention("Attention"),
	memory("Memory");
	
	
	
	private String key;
	
	private GameSkillType(String key){
		this.key = key;
	}
	
	public String key(){
		return key;
	}
}
