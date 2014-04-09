package com.satya.Persistence.Impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.satya.BusinessObjects.Game;
import com.satya.BusinessObjects.Tag;
import com.satya.Persistence.RowMapper;
import com.satya.Persistence.TagDataStoreI;
import com.satya.enums.GameSkillType;

public class TagDataStore implements TagDataStoreI,RowMapper{

	private final static String SELECT_BY_GAME = "select tags.seq, tags.tag from tags join gametags ON " +
			"tags.seq = gametags.tagseq and gametags.gameseq = ?";
	
	private final static String FIND_ALL = "select * from tags";
	
	private PersistenceMgr persistenceMgr;
	
	public TagDataStore(PersistenceMgr persistenceMgr){
		this.persistenceMgr = persistenceMgr;
	}
	@Override
	public List<Tag> findTagsByGame(long gameSeq) {
		Object[] params = new Object[]{gameSeq};
		return (List<Tag>)persistenceMgr.executePSQuery(SELECT_BY_GAME,params,this);
	}

	@Override
	public Object mapRow(ResultSet rs) throws SQLException {
		return populateObjectFromResultSet(rs);
	}
	
	protected Object populateObjectFromResultSet(ResultSet rs)throws SQLException{
		
		long seq = rs.getLong("seq");
		String tagStr = rs.getString("tag");

		Tag tag = new Tag(tagStr);
		tag.setSeq(seq);
		tag.setTag(tagStr);
		return tag;
		
	}
	@Override
	public List<Tag> findAll() {
		return persistenceMgr.executePSQuery(FIND_ALL, this);
	}
}
