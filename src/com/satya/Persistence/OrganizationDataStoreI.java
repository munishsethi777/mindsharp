package com.satya.Persistence;

import java.util.List;

import com.satya.BusinessObjects.Organization;

public interface OrganizationDataStoreI {

	public Organization findBySeq(long seq);
	public Organization findByCode(String code);
	public List<Organization> findByNameStartsWith(String nameStartsWith);
}
