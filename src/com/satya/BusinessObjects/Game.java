package com.satya.BusinessObjects;

import java.util.Date;
import java.util.List;

import com.satya.ApplicationContext;
import com.satya.Persistence.UserGameDataStoreI;
import com.satya.enums.GameSkillType;

public class Game {

	private long seq;
	private String name;
	private String path;
	private boolean isActive;
	private boolean isDelete;
	private boolean isFree;
	private String extra;
	private GameSkillType gameSkillType;
	private List<Tag> tags;
	private String description;
	private int myBestScore; //is used to show my best on game play page
	
	public Game(long seq) {
		this.setSeq(seq);
	}

	public Game() {

	}

	public long getSeq() {
		return seq;
	}

	public void setSeq(long seq) {
		this.seq = seq;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public boolean isFree() {
		return isFree;
	}

	public void setFree(boolean isFree) {
		this.isFree = isFree;
	}

	public GameSkillType getGameSkillType() {
		return gameSkillType;
	}

	public void setGameSkillType(GameSkillType gameSkillType) {
		this.gameSkillType = gameSkillType;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTagsString() {
		String tagsString = "";
		for (Tag tag : this.getTags()) {
			tagsString += tag.getTag();
			if (this.getTags().indexOf(tag) != this.getTags().size() - 1) {
				tagsString += ", ";
			}
		}
		return tagsString;
	}

	public boolean getIsGameEnrolled(User user) {
		boolean bool = false;
		if (user.getIsLimited()) {
			UserGameDataStoreI UGDS = ApplicationContext
					.getApplicationContext().getDataStoreMgr()
					.getUserGameDataStore();
			List<UserGame> userGames = UGDS.getGameByUser(user.getSeq());
			for(UserGame userGame : userGames){
				if(userGame.getGame().getSeq() == this.getSeq() &&
						userGame.getValidTillDate().after(new Date())){
					bool =  true;
				}
			}
			return bool;
		}else{
			return true;
		}
	}

	public int getMyBestScore() {
		return myBestScore;
	}

	public void setMyBestScore(int myBestScore) {
		this.myBestScore = myBestScore;
	}
}
