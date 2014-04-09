package com.satya.Persistence.Impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.satya.BusinessObjects.Game;
import com.satya.BusinessObjects.Organization;
import com.satya.BusinessObjects.Tag;
import com.satya.Persistence.OrganizationDataStoreI;
import com.satya.Persistence.RowMapper;
import com.satya.enums.GameSkillType;
import com.satya.enums.OrganizationType;

public class OrganizationDataStore implements OrganizationDataStoreI, RowMapper{
	
	private PersistenceMgr persistenceMgr;
	private final static String SELECT_ALL = "select * from organizations";
	private final static String FIND_NAME_STARTS_WITH = "select * from organizations where orgname like ?";
	
	public OrganizationDataStore(PersistenceMgr pressisteceMgr){
		this.persistenceMgr = pressisteceMgr;
	}
	@Override
	public Organization findBySeq(long seq) {
		Object [] params = new Object [] {seq};
		return  (Organization)persistenceMgr.executeSingleObjectQuery(SELECT_ALL, params, this);
	}

	@Override
	public Organization findByCode(String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Organization> findByNameStartsWith(String nameStartsWith) {
		Object [] params = new Object [] {nameStartsWith.concat("%")};
		return persistenceMgr.executePSQuery(FIND_NAME_STARTS_WITH, params, this);
	}
	
	@Override
	public Object mapRow(ResultSet rs) throws SQLException {
		return populateObjectFromResultSet(rs);
	}
	protected Object populateObjectFromResultSet(ResultSet rs)throws SQLException{
		
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
