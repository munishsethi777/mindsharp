package com.satya.Persistence;

import java.util.List;

import com.satya.BusinessObjects.GameResult;
import com.satya.enums.GameSkillBadgeType;
import com.satya.enums.GameSkillType;

public interface GameResultDataStoreI {

	public void SaveGameResult(GameResult gameResult);
	public List<GameResult> findResultsByUser(long userId);
	public int findMaxScore(long gameSeq, long userSeq);
	public int getTotalGamesPlayedByUser(long userSeq);
	public int getTotalResultsBySkillTypeForUser(GameSkillType skillType,long userId);
	public int getResultByDays(long userId,int Days);
}
