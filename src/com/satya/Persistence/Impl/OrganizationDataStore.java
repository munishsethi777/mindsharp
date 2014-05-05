package com.satya.Persistence.Impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.satya.BusinessObjects.Organization;
import com.satya.Persistence.OrganizationDataStoreI;
import com.satya.Persistence.RowMapper;
import com.satya.enums.OrganizationType;

public class OrganizationDataStore implements OrganizationDataStoreI, RowMapper {
	Logger logger = Logger.getLogger(OrganizationDataStore.class);
	private PersistenceMgr persistenceMgr;
	private final static String SELECT_ALL = "select * from organizations";
	private final static String FIND_NAME_STARTS_WITH = "select * from organizations where orgname like ?";
	private final static String SAVE = "insert into organizations(orgname, orgaddress, orgstate, orgcountry,orgtype) "
			+ "values (?,?,?,?,?)";
	private final static String FIND_BY_SEQ = SELECT_ALL + " where seq = ?";

	public OrganizationDataStore(PersistenceMgr pressisteceMgr) {
		this.persistenceMgr = pressisteceMgr;
	}

	@Override
	public void save(Organization org) {
		try {
			Object[] params = new Object[] { org.getName(), org.getAddress(),
					org.getState(), org.getCountry(), org.getType().name() };
			persistenceMgr.excecuteUpdate(SAVE, params);
			org.setSeq(persistenceMgr.getLastUpdatedSeq());
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public Organization findBySeq(long seq) {
		Object[] params = new Object[] { seq };
		return (Organization) persistenceMgr.executeSingleObjectQuery(
				FIND_BY_SEQ, params, this);
	}

	@Override
	public Organization findByCode(String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Organization> findByNameStartsWith(String nameStartsWith) {
		Object[] params = new Object[] { nameStartsWith.concat("%") };
		return persistenceMgr.executePSQuery(FIND_NAME_STARTS_WITH, params,
				this);
	}

	@Override
	public Object mapRow(ResultSet rs) throws SQLException {
		return populateObjectFromResultSet(rs);
	}

	protected Object populateObjectFromResultSet(ResultSet rs)
			throws SQLException {

		long seq = rs.getLong("seq");
		String type = rs.getString("orgtype");
		String name = rs.getString("orgname");
		String address = rs.getString("orgaddress");
		String state = rs.getString("orgstate");
		String country = rs.getString("orgcountry");

		Organization organization = new Organization();
		organization.setSeq(seq);
		organization.setType(OrganizationType.valueOf(type));
		organization.setName(name);
		organization.setAddress(address);
		organization.setState(state);
		organization.setCountry(country);

		return organization;

	}

}
