package com.satya.Managers;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.satya.ApplicationContext;
import com.satya.BusinessObjects.Organization;
import com.satya.Persistence.OrganizationDataStoreI;
import com.satya.enums.OrganizationType;

public class OrganizationMgr {
	
	/*
	 * Method used for AutoComplete Organization on settings page
	 */
	public JSONArray findOrganizationJsonArray(String orgCode) {
		OrganizationDataStoreI ODS = ApplicationContext.getApplicationContext()
				.getDataStoreMgr().getOrganizationDataStore();
		List<Organization> organizations = ODS.findByNameStartsWith(orgCode);
		JSONArray arr = new JSONArray();
		try {
			for(Organization organization : organizations){
				JSONObject json = new JSONObject();
				json.put("label", organization.getName());
				json.put("value", organization.getName());
				json.put("orgType", organization.getType().name());
				json.put("orgAddress", organization.getAddress());
				json.put("orgState", organization.getState());
				json.put("orgCountry", organization.getCountry());
				
				arr.put(json);
			}
		} catch (Exception e) {
		}
		
		return arr;
	}

	public JSONObject findOrganizationTypesJsonArray() {
		OrganizationType[] types = OrganizationType.college.getDeclaringClass().getEnumConstants();
		JSONObject json = new JSONObject();
		try {
			for(OrganizationType type : types){
				json.put(type.key(), type.name());
			}
		} catch (Exception e) {
		}
		
		return json;
	}
	
	public JSONObject getOrganizationStreamTypesJson(OrganizationType organizationType) {
		JSONObject json = new JSONObject();
		if(organizationType == null){
			return json;
		}
		if(organizationType == OrganizationType.school){
			try {
				json.put("class5to8", "Class 5 - 8");
				json.put("class8to10", "Class 8 - 10");
				json.put("class11to12", "Class 11 - 12");
				
			} catch (Exception e) {
			}	
		}else if(organizationType == OrganizationType.professional){
			try {
				json.put("finance", "Finance");
				json.put("computerSoftware", "Computer Software");
				json.put("computerHardware", "Computer Hardware");
				json.put("marketing", "Marketing");
				json.put("humanResource", "Human Resource");
				
			} catch (Exception e) {
			}	
		}
		return json;
	}

}
