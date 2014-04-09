package com.satya.Persistence.Impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.satya.BusinessObjects.UserBadge;
import com.satya.Persistence.RowMapper;
import com.satya.Persistence.UserBadgeDataStoreI;
import com.satya.enums.GameSkillBadgeType;


public class UserBadgeDataStore implements UserBadgeDataStoreI, RowMapper {

	private final static String SELECT_BY_SEQ = "Select * from userbadges where seq = ?";
	private final static String SAVE = "Insert into userbadges(userseq, badge, gameseq, date) values(?,?,?,?)";
	private final static String SELECT_BY_USERSEQ = "Select * from userbadges where userseq=?";
	private final static String SELECT_BADGES_BY_USERSEQ = "select badge from userbadges where userseq=?";
	private PersistenceMgr persistenceMgr;

	public UserBadgeDataStore(PersistenceMgr ps) {
		this.persistenceMgr = ps;
	}

	@Override
	public Object mapRow(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return populateObject(rs);
	}

	@Override
	public void Save(UserBadge userBadge) {
		try {
			Object[] params = new Object[] { userBadge.getUser().getSeq(),
					userBadge.getBadgeType().key(),
					userBadge.getGame().getSeq(), userBadge.getDated() };
			persistenceMgr.excecuteUpdate(SAVE, params);
		} catch (Exception e) {
		}

	}

	@Override
	public UserBadge findBySeq(long seq) {
		Object[] params = new Object[] { seq };
		return (UserBadge) persistenceMgr.executeSingleObjectQuery(
				SELECT_BY_SEQ, params, this);
	}

	@Override
	public List<UserBadge> FindByUser(long userSeq) {
		Object[] params = new Object[] { userSeq };
		return (List<UserBadge>) persistenceMgr.executePSQuery(
				SELECT_BY_USERSEQ, params, this);
	}
	@Override
	public List<String> FindBadgesNameByUser(long userSeq) {
		Object[] params = new Object[] { userSeq };
		return (List<String>) persistenceMgr.executePSQuery(
				SELECT_BADGES_BY_USERSEQ, params, this);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<GameSkillBadgeType> findBadges(long userSeq) {
		Object[] params = new Object[]{ userSeq};
		return (List<GameSkillBadgeType>) persistenceMgr.executePSQuery(SELECT_BADGES_BY_USERSEQ, params, new RowMapper() {			
			@Override
			public Object mapRow(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				String badge = rs.getString("badge");
				return GameSkillBadgeType.valueOf(badge);
			}
		});
	}
	private UserBadge populateObject(ResultSet rs) {
		UserBadge userBadge = new UserBadge();
		return userBadge;
	}
}
