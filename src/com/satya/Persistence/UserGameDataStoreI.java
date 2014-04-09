package com.satya.Persistence;

import java.util.List;

import com.satya.BusinessObjects.UserGame;

public interface UserGameDataStoreI {
	
	public void saveUserGame(UserGame userGame);
	public List<UserGame> getGameByUser(long userSeq);
	public boolean isGameEnrolled(long userSeq, long gameSeq);
}
