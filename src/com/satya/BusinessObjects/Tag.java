package com.satya.BusinessObjects;

public class Tag {

	private long seq;
	private String tag;
	
	public Tag(String tag){
		this.setTag(tag);
	}
	
	public long getSeq() {
		return seq;
	}
	public void setSeq(long seq) {
		this.seq = seq;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
}
