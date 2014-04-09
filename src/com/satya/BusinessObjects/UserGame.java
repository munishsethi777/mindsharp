package com.satya.BusinessObjects;

import java.util.Date;

public class UserGame {
	private long seq;
	private User user;
	private Game game;
	private Date enrollmentDate;
	private Date validTillDate;
	
	public UserGame(){
		
	}
	public UserGame(long seq){
		this.seq= seq;
	}
	public UserGame(User user, Game game, Date enrollmentDate){
		this.user = user;
		this.game = game;
		this.enrollmentDate = enrollmentDate;
	}
	
	public long getSeq() {
		return seq;
	}
	public void setSeq(long seq) {
		this.seq = seq;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	public Date getEnrollmentDate() {
		return enrollmentDate;
	}
	public void setEnrollmentDate(Date enrollmentDate) {
		this.enrollmentDate = enrollmentDate;
	}
	public Date getValidTillDate() {
		return validTillDate;
	}
	public void setValidTillDate(Date validTillDate) {
		this.validTillDate = validTillDate;
	}
}
