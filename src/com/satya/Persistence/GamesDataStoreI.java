package com.satya.Persistence;

import java.util.List;

import com.satya.BusinessObjects.Game;
import com.satya.enums.GameSkillType;

public interface GamesDataStoreI {

	public List<Game> findAllGames();

	public Game findBySeq(long seq);

	public List<Game> findByUserSeq(long seq);

	public List<Game> findByFreeOrPaid(boolean isFree);

	public List<Game> findBySkill(GameSkillType gameSkillType);

	public List<Game> findByTag(long tagSeq);

	public List<Game> findByTagAndSkill(long tagSeq, String skillType);
}
