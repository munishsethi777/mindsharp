package com.satya.BusinessObjects;

import java.util.Date;

import com.satya.enums.GameSkillBadgeType;

public class UserBadge {

	private long seq;
	private User user;
	private Game game;
	private GameSkillBadgeType badgeType;
	private Date dated;

	public UserBadge(){
		
	}
	public UserBadge(long seq, User user, Game game, GameSkillBadgeType badge, Date date) {
		this.seq = seq;
		this.user = user;
		this.game = game;
		this.badgeType = badge;
		this.dated = date;
	}

	public UserBadge(User user, Game game, GameSkillBadgeType badge, Date date){
		this.user = user;
		this.game = game;
		this.badgeType = badge;
		this.dated = date;
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

	public GameSkillBadgeType getBadgeType() {
		return badgeType;
	}

	public void setBadgeType(GameSkillBadgeType badgeType) {
		this.badgeType = badgeType;
	}

	public Date getDated() {
		return dated;
	}

	public void setDated(Date dated) {
		this.dated = dated;
	}

}
