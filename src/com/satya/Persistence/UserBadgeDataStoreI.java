package com.satya.Persistence;

import java.util.List;

import com.satya.BusinessObjects.User;
import com.satya.BusinessObjects.UserBadge;
import com.satya.enums.GameSkillBadgeType;

public interface UserBadgeDataStoreI {

	public void Save(UserBadge userBadge);
	public UserBadge findBySeq(long seq);
	public List<UserBadge> FindByUser(long userSeq);
	public List<String> FindBadgesNameByUser(long userSeq);
	public List<GameSkillBadgeType> findBadges(long userSeq);
	
}
