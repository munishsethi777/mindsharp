package com.satya.Persistence.Impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.satya.BusinessObjects.Game;
import com.satya.BusinessObjects.Organization;
import com.satya.BusinessObjects.Promotion;
import com.satya.Persistence.PromotionsDataStoreI;
import com.satya.Persistence.RowMapper;

public class PromotionsDataStore implements PromotionsDataStoreI, RowMapper{
	
	private PersistenceMgr persistenceMgr;
	private final static String SELECT = "select * from promotions";
	private final static String SELECT_BY_SEQ = SELECT + " where promotions.seq = ?";
	private final static String SELECT_BY_CODE = SELECT + " where promotions.code = ? ";
	
	
	public PromotionsDataStore(PersistenceMgr persistenceMgr){
		this.persistenceMgr = persistenceMgr;
	}
	
	@Override
	public Object mapRow(ResultSet rs) throws SQLException {
		return populateObjectFromResultSet(rs);
	}

	@Override
	public Promotion findBySeq(long seq) {
		Object[] params = new Object[]{seq};
		return (Promotion) persistenceMgr.executeSingleObjectQuery(SELECT_BY_SEQ, params, this);
	}

	@Override
	public Promotion findByCode(String code) {
		Object[] params = new Object[]{code};
		return (Promotion) persistenceMgr.executeSingleObjectQuery(SELECT_BY_CODE, params, this);
	}
	
	protected Object populateObjectFromResultSet(ResultSet rs)throws SQLException{
		
		long seq = rs.getLong("seq");
		String code = rs.getString("code");
		String description = rs.getString("description");
		String freeGamesStr = rs.getString("freegames");
		long orgSeq = rs.getLong("orgseq");
		Date createDate = rs.getDate("createdate");
		Date expiryDate = rs.getDate("expirydate");
		int gamesValidityDays = rs.getInt("gamesvaliditydays");
		
		
		Promotion promotion = new Promotion(seq);
		promotion.setCode(code);
		promotion.setDescription(description);
		if(freeGamesStr != null){
			String[] gameSeqs = freeGamesStr.split(",");
			if(gameSeqs.length > 0){
				promotion.setFreeGames(new ArrayList<Game>());
			}
			for(String gameSeqStr: gameSeqs){
				Game game = new Game(Long.parseLong(gameSeqStr));
				promotion.getFreeGames().add(game);
			}
		}
		promotion.setOrganization(new Organization(orgSeq));
		promotion.setCreateDate(createDate);
		promotion.setExpiryDate(expiryDate);
		promotion.setGamesValidityDays(gamesValidityDays);
		return promotion;
		
	}

}
