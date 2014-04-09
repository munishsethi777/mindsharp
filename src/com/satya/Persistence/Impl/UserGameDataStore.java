package com.satya.Persistence.Impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.satya.BusinessObjects.Game;
import com.satya.BusinessObjects.GameResult;
import com.satya.BusinessObjects.User;
import com.satya.BusinessObjects.UserGame;
import com.satya.Persistence.GameResultDataStoreI;
import com.satya.Persistence.RowMapper;
import com.satya.Persistence.UserGameDataStoreI;

public class UserGameDataStore implements UserGameDataStoreI,RowMapper {
	
	private final static String SAVE= "Insert into usergames(userseq,gameseq,enrollmentdate,validtilldate) values (?,?,?,?)";
	
	private final static String FINDRESULTSBYUSER= "select ug.*,g.name gamename from usergames ug, games g where " +
			"ug.gameseq = g.seq and ug.userseq=? order by seq desc;";
	
	private final static String FIND_BY_USER_GAME = "select * from usergames where gameseq=? and userseq=?";
	private PersistenceMgr persistenceMgr;
	public UserGameDataStore(PersistenceMgr persistenceMgr){
		this.persistenceMgr = persistenceMgr;
	}
	
	@Override
	public void saveUserGame(UserGame obj) {
		try{
			Object[] params = new Object[]{
					obj.getUser().getSeq(),
					obj.getGame().getSeq(),
					obj.getEnrollmentDate(),
					obj.getValidTillDate()};
			persistenceMgr.excecuteUpdate(SAVE,params);
		}catch(Exception e){}
	}


	@Override
	public List<UserGame> getGameByUser(long userSeq) {
		Object[] params = new Object[]{userSeq};
		return (List<UserGame>) persistenceMgr.executePSQuery(FINDRESULTSBYUSER, params, this);
	}
	@Override
	public boolean isGameEnrolled(long userSeq, long gameSeq) {
		Object[] params = new Object[]{userSeq, gameSeq};
		UserGame userGame =(UserGame) persistenceMgr.executeSingleObjectQuery(FIND_BY_USER_GAME, params, this);
		if(userGame == null){
			return false;
		}else{
			return true;
		}
	}
		
	@Override
	public Object mapRow(ResultSet rs) throws SQLException {
		return populateObjectFromResultSet(rs);
	}
	protected Object populateObjectFromResultSet(ResultSet rs)throws SQLException{
		
		long seq = rs.getLong("seq");
		long userSeq = rs.getLong("userseq");
		long gameSeq = rs.getLong("gameseq");
		Timestamp datedTS = rs.getTimestamp("enrollmentdate");
		Timestamp validTillDateTS = rs.getTimestamp("validtilldate");
		
		UserGame userGame = new UserGame(seq);
		userGame.setUser(new User(userSeq));
		userGame.setGame(new Game(gameSeq));
		try{
			String gameName = rs.getString("gamename");
			userGame.getGame().setName(gameName);
		}catch(Exception e){}
		
		if(datedTS != null){
			userGame.setEnrollmentDate(new Date(datedTS.getTime()));
		}
		if(validTillDateTS != null){
			userGame.setValidTillDate(new Date(validTillDateTS.getTime()));
		}
		return userGame;
		
	}




}
