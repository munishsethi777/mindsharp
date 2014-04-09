package com.satya.Managers;

import java.util.ArrayList;
import java.util.List;

import com.satya.ApplicationContext;
import com.satya.BusinessObjects.GameResult;
import com.satya.BusinessObjects.User;
import com.satya.BusinessObjects.UserBadge;
import com.satya.Persistence.UserBadgeDataStoreI;
import com.satya.enums.GameSkillBadgeType;
import com.satya.enums.GameSkillType;


public class BadgeMgr {

	public void allotBadge(GameResult gameResult)throws Exception {
		List<GameSkillBadgeType> badges = this.getTotalGamesBadge(gameResult);
		for (GameSkillBadgeType badge : badges) {
			this.SaveBadge(gameResult,badge);
		}
	}

	private void SaveBadge(GameResult gameResult,GameSkillBadgeType badge) {
		UserBadge userBadge = new UserBadge();	
		userBadge.setUser(gameResult.getUser());
		userBadge.setGame(gameResult.getGame());
		userBadge.setBadgeType(badge);
		userBadge.setDated(gameResult.getDated());
		UserBadgeDataStoreI UBDS = ApplicationContext.getApplicationContext()
		.getDataStoreMgr().getUserBadgeDataStore();
		UBDS.Save(userBadge);
	}

//	private List<GameSkillBadgeType> getBadgeForUser(GameResult gameResult) {
//		List<GameSkillBadgeType> badges = new ArrayList<GameSkillBadgeType>();
//		badges = getTotalGamesBadge(gameResult);
//		return null;
//	}// return all the possible badges on this game success

	private List<GameSkillBadgeType> getTotalGamesBadge(GameResult gameResult) {
		/*
		 * Allot a badge as per overall games played by this user <10
		 * Newbiee,10+Starter,25+Amateur,+50 Enthusiast,100 + Professional,250 +
		 * Super Star
		 */
	
		//List<UserBadge> userBadges = UBDS.FindByUser(user.getSeq());
		
		GameResultMgr gameResultMgr = ApplicationContext.getApplicationContext().getGameResultsMgr();
		GameSkillType skillType = gameResult.getGame().getGameSkillType();
		User user = gameResult.getUser();
		int totalGamesPlayed = gameResultMgr.getTotalResultBySkillTypeForUser(skillType,user);
		//int totalGamesPlayed = gameResultMgr.getTotalGamesPlayedByUser(user);
		List<GameSkillBadgeType> badges = new ArrayList<GameSkillBadgeType>();
		if (totalGamesPlayed <= 10) {
			badges.add(GameSkillBadgeType.newbie);
		}
		if (totalGamesPlayed > 10 && totalGamesPlayed <= 25) {
			badges.add(GameSkillBadgeType.starter);
		}
		if (totalGamesPlayed > 22 && totalGamesPlayed <= 50) {
			badges.add(GameSkillBadgeType.amateur);
		}
		if (totalGamesPlayed > 50 && totalGamesPlayed <= 100) {
			badges.add(GameSkillBadgeType.enthusiast);
		}
		if (totalGamesPlayed > 100 && totalGamesPlayed <= 250) {
			badges.add(GameSkillBadgeType.professional);
		}
		if (totalGamesPlayed > 250) {
			badges.add(GameSkillBadgeType.superStar);
		}
		return badges;
	}
}
