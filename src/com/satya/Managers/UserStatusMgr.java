package com.satya.Managers;

import com.satya.ApplicationContext;
import com.satya.BusinessObjects.User;
import com.satya.enums.UserStatusType;

public class UserStatusMgr {
	public String getUserSatus(User user){
		String status = "-N.A-";
		GameResultMgr gameMgr = ApplicationContext.getApplicationContext().getGameResultsMgr();
		int gamePlayCount = gameMgr.getResultByDays(user.getSeq(), 7);
		if(gamePlayCount >= 5 && gamePlayCount <= 25){
			return UserStatusType.Active.name();
		}
		int twoDaysCount = gameMgr.getResultByDays(user.getSeq(), 2);
		if(twoDaysCount > 10){
			return UserStatusType.Energized.name();
		}
		if(gamePlayCount == 0){
			return UserStatusType.Dormant.name();
		}
		return status;
	}
}
