package com.satya.Persistence.Impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.satya.BusinessObjects.Game;
import com.satya.BusinessObjects.GameResult;
import com.satya.BusinessObjects.User;
import com.satya.Persistence.GameResultDataStoreI;
import com.satya.Persistence.RowMapper;
import com.satya.enums.GameSkillType;

public class GameResultDataStore implements GameResultDataStoreI, RowMapper {

	private final static String SAVE = "Insert into gameresults(userseq,gameseq,dated,score,correct,"
			+ "levels,totalattempts,timeplayedseconds,opportunities,accuratepercent,reactiontimeseconds) values (?,?,?,?,?,?,?,?,?,?,?)";

	private final static String FINDRESULTSBYUSER = "select gr.*,g.name gamename,g.skilltype skilltype from gameresults gr, games g where "
			+ "gr.gameseq = g.seq and gr.userseq=? order by seq desc;";

	private final static String FINDMAXSCORE = "select max(score) as max from gameresults where gameseq = ? and userseq = ?";

	private final static String COUNT_GAMES_PLAYED = "select count(*) from gameresults where userseq=?";
	private final static String FIND_BY_SKILL_TYPE = "select count(*) from gameresults,games where gameresults.gameseq = games.seq and games.skilltype = ? and userseq = ?";
	private final static String FIND_BY_DAYS = "select count(*) from  gameresults where userseq=? and DATE(dated) >= DATE(CURDATE()- ?)";
	private final static String FIND_BY_ORG = "select gameresults.*, users.username from gameresults"
			+ " inner join users on users.seq = gameresults.userseq "
			+ " where gameresults.gameseq = ? and users.orgseq = ?";

	private PersistenceMgr persistenceMgr;

	public GameResultDataStore(PersistenceMgr persistenceMgr) {
		this.persistenceMgr = persistenceMgr;
	}

	@Override
	public int getResultByDays(long userId, int days) {
		Object[] params = new Object[] { userId, days };
		return persistenceMgr.executeCountQuery(FIND_BY_DAYS, params);
	}

	@Override
	public int getTotalResultsBySkillTypeForUser(GameSkillType skillType,
			long userId) {
		Object[] params = new Object[] { skillType.key(), userId };
		return persistenceMgr.executeCountQuery(FIND_BY_SKILL_TYPE, params);
	}

	@Override
	public List<GameResult> getResultByOrg(long gameSeq, long orgSeq) {
		Object[] params = new Object[] { gameSeq, orgSeq };
		return (List<GameResult>) persistenceMgr.executePSQuery(FIND_BY_ORG,
				params, this);
	}

	@Override
	public int findMaxScore(long gameSeq, long userSeq) {
		Object[] params = new Object[] { gameSeq, userSeq };
		return (Integer) persistenceMgr.executeSingleObjectQuery(FINDMAXSCORE,
				params, new RowMapper() {

					@Override
					public Object mapRow(ResultSet rs) throws SQLException {
						// TODO Auto-generated method stub
						return rs.getInt("max");
					}
				});
	}

	@Override
	public void SaveGameResult(GameResult obj) {
		try {
			Object[] params = new Object[] { obj.getUser().getSeq(),
					obj.getGame().getSeq(), obj.getDated(), obj.getScore(),
					obj.getCorrect(), obj.getLevels(), obj.getTotalAttempts(),
					obj.getTimePlayedSeconds(), obj.getOpportunities(),
					obj.getAccuratePercent(), obj.getReactionTimeSeconds() };
			persistenceMgr.excecuteUpdate(SAVE, params);
		} catch (Exception e) {
		}

	}

	@Override
	public List<GameResult> findResultsByUser(long userId) {
		Object[] params = new Object[] { userId };
		return (List<GameResult>) persistenceMgr.executePSQuery(
				FINDRESULTSBYUSER, params, this);
	}

	@Override
	public int getTotalGamesPlayedByUser(long userSeq) {
		Object[] params = new Object[] { userSeq };
		return persistenceMgr.executeCountQuery(COUNT_GAMES_PLAYED, params);
	}

	@Override
	public Object mapRow(ResultSet rs) throws SQLException {
		return populateObjectFromResultSet(rs);
	}

	protected Object populateObjectFromResultSet(ResultSet rs)
			throws SQLException {

		long seq = rs.getLong("seq");
		long userSeq = rs.getLong("userseq");
		long gameSeq = rs.getLong("gameseq");
		int score = rs.getInt("score");
		int correct = rs.getInt("correct");
		int levels = rs.getInt("levels");
		int totalAttempts = rs.getInt("totalattempts");
		int timePlayedSeconds = rs.getInt("timeplayedseconds");
		int opportunities = rs.getInt("opportunities");
		double accuratePercent = rs.getDouble("accuratepercent");
		int reactionTimeSeconds = rs.getInt("reactiontimeseconds");
		Timestamp datedTS = rs.getTimestamp("dated");

		GameResult gameResult = new GameResult(seq);
		gameResult.setSeq(seq);
		gameResult.setUser(new User(userSeq));
		gameResult.setGame(new Game(gameSeq));
		try {
			String gameName = rs.getString("gamename");
			gameResult.getGame().setName(gameName);

			String userName = rs.getString("username");
			gameResult.getUser().setUserName(userName);

			
			String skillType = rs.getString("skilltype");
			gameResult.getGame().setGameSkillType(
					GameSkillType.valueOf(skillType));
		} catch (Exception e) {
		}

		gameResult.setScore(score);
		gameResult.setCorrect(correct);
		gameResult.setLevels(levels);
		gameResult.setTotalAttempts(totalAttempts);
		gameResult.setTimePlayedSeconds(timePlayedSeconds);
		gameResult.setOpportunities(opportunities);
		gameResult.setAccuratePercent(accuratePercent);
		gameResult.setReactionTimeSeconds(reactionTimeSeconds);

		if (datedTS != null) {
			gameResult.setDated(new Date(datedTS.getTime()));
		}
		return gameResult;

	}

}
