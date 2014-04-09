package com.satya.BusinessObjects;

import java.util.Date;
import java.util.List;

public class Promotion {

	private long seq;
	private String code;
	private String description;
	private List<Game> freeGames;
	private Organization organization;
	private Date createDate;
	private Date expiryDate;
	private int gamesValidityDays;
	
	
	public Promotion(){
		
	}
	public Promotion(long seq){
		this.setSeq(seq);
	}
	
	public long getSeq() {
		return seq;
	}
	public void setSeq(long seq) {
		this.seq = seq;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Game> getFreeGames() {
		return freeGames;
	}
	public void setFreeGames(List<Game> freeGames) {
		this.freeGames = freeGames;
	}
	public Organization getOrganization() {
		return organization;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public int getGamesValidityDays() {
		return gamesValidityDays;
	}
	public void setGamesValidityDays(int gamesValidityDays) {
		this.gamesValidityDays = gamesValidityDays;
	}
}
