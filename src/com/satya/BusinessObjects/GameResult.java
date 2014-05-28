package com.satya.BusinessObjects;

import java.util.Date;

import com.satya.Utils.DateUtils;

public class GameResult {
	private long seq;
	private User user;
	private Game game;
	private Date dated;
	private int score;
	private int correct;
	private int levels;
	private int totalAttempts;
	private int timePlayedSeconds;
	private int opportunities;
	private double accuratePercent;
	private int reactionTimeSeconds;
	
	
	public GameResult(long seq){
		this.seq = seq;
	}
	public GameResult(){
		
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
	public Date getDated() {
		return dated;
	}
	public void setDated(Date dated) {
		this.dated = dated;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getCorrect() {
		return correct;
	}
	public void setCorrect(int correct) {
		this.correct = correct;
	}
	public int getLevels() {
		return levels;
	}
	public void setLevels(int levels) {
		this.levels = levels;
	}
	public int getTotalAttempts() {
		return totalAttempts;
	}
	public void setTotalAttempts(int totalAttempts) {
		this.totalAttempts = totalAttempts;
	}
	public int getTimePlayedSeconds() {
		return timePlayedSeconds;
	}
	public void setTimePlayedSeconds(int timePlayedSeconds) {
		this.timePlayedSeconds = timePlayedSeconds;
	}
	public int getOpportunities() {
		return opportunities;
	}
	public void setOpportunities(int opportunities) {
		this.opportunities = opportunities;
	}
	public double getAccuratePercent() {
		return accuratePercent;
	}
	public void setAccuratePercent(double accuratePercent) {
		this.accuratePercent = accuratePercent;
	}
	public int getReactionTimeSeconds() {
		return reactionTimeSeconds;
	}
	public void setReactionTimeSeconds(int reactionTimeSeconds) {
		this.reactionTimeSeconds = reactionTimeSeconds;
	}
	public String getFormattedResultDate(){
		String formattedDate = "";
		if(this.getDated() != null){
			formattedDate =  DateUtils.getGridDateFormat(this.getDated());
		}
		return formattedDate;
	}
	public float getTimePlayedMinutes() {
		if(this.getTimePlayedSeconds()!=0){
			return getTimePlayedSeconds()/60;
		}
		return 0;
	}							
}
