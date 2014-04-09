package com.satya.Persistence.Impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.satya.ApplicationContext;
import com.satya.BusinessObjects.Game;
import com.satya.BusinessObjects.Tag;
import com.satya.Persistence.GamesDataStoreI;
import com.satya.Persistence.RowMapper;
import com.satya.Persistence.TagDataStoreI;
import com.satya.enums.GameSkillType;

public class GamesDataStore implements GamesDataStoreI,RowMapper{
	
	private final static String SELECT_ALL = "select games.*, tags.tag from games, tags , gametags " +
					"where games.isactive=1 and games.seq = gametags.gameseq and gametags.tagseq = tags.seq";
	private final static String FIND_BY_SEQ = "select games.* from games where games.seq = ?";
	private final static String FIND_BY_SKILL = SELECT_ALL + " and games.skilltype like ?";
	private PersistenceMgr persistenceMgr;
	
	public GamesDataStore(PersistenceMgr persistenceMgr){
		this.persistenceMgr = persistenceMgr;
	}
	
	private List<Game> getUniqueGamesWithTags(List<Game> gamesList){
		Map<Long, Game> gamesMap = new HashMap<Long, Game>();
		for(Game game : gamesList){
			if(!gamesMap.containsKey(game.getSeq())){
				gamesMap.put(game.getSeq(), game);
			}else{
				gamesMap.get(game.getSeq()).getTags().add(game.getTags().get(0));
			}
			
		}
		List<Game> games = new ArrayList<Game>();
		for (Object value : gamesMap.values()) {
		    games.add((Game)value);
		}
		return games;
	}
	
	@Override
	public List<Game> findAllGames() {
		List<Game> games =  (List<Game>)persistenceMgr.executePSQuery(SELECT_ALL,this);
		return getUniqueGamesWithTags(games);
	}

	@Override
	public Game findBySeq(long seq) {
		Object [] params = new Object [] {seq};
		Game game =  (Game) persistenceMgr.executeSingleObjectQuery(FIND_BY_SEQ, params, this);
		if(game != null){
			TagDataStoreI TDS = ApplicationContext.getApplicationContext().getDataStoreMgr().getTagDataStore();
			List<Tag> tags = TDS.findTagsByGame(game.getSeq());
			game.setTags(tags);
		}
		return game;
	}

	@Override
	public List<Game> findByUserSeq(long seq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Game> findByFreeOrPaid(boolean isFree) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Game> findBySkill(GameSkillType gameSkillType) {
		Object [] params = new Object [] {gameSkillType.name()};
		List<Game> games = (List<Game>) persistenceMgr.executePSQuery(FIND_BY_SKILL, params, this);
		return getUniqueGamesWithTags(games);
	}
	
	@Override
	public Object mapRow(ResultSet rs) throws SQLException {
		return populateObjectFromResultSet(rs);
	}
	protected Object populateObjectFromResultSet(ResultSet rs)throws SQLException{
		
		long seq = rs.getLong("seq");
		String name = rs.getString("name");
		String path = rs.getString("path");
		boolean isActive = rs.getBoolean("isactive");
		boolean isDelete = rs.getBoolean("isdelete");
		boolean isFree = rs.getBoolean("isfree");
		String extra = rs.getString("extra");
		String gameSkillType = rs.getString("skilltype");
		String description = rs.getString("description");

		
		Game game = new Game();
		game.setSeq(seq);
		game.setName(name);
		game.setPath(path);
		game.setActive(isActive);
		game.setDelete(isDelete);
		game.setFree(isFree);
		game.setExtra(extra);
		game.setDescription(description);
		if(gameSkillType != null && !gameSkillType.equals("")){
			game.setGameSkillType(GameSkillType.valueOf(gameSkillType));
		}
		try{
			String tagStr = rs.getString("tag");
			if(tagStr != null){
				Tag tag = new Tag(tagStr);
				List tagsList = new ArrayList<Tag>();
				tagsList.add(tag);
				game.setTags(tagsList);
			}
		}catch(Exception e){}
		
		return game;
		
	}

}
