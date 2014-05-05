package com.satya.Managers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
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
				json.put("seq", organization.getSeq());
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
		OrganizationType[] types = OrganizationType.college.getDeclaringClass()
				.getEnumConstants();
		JSONObject json = new JSONObject();
		try {
			for (OrganizationType type : types) {
				json.put(type.key(), type.name());
			}
		} catch (Exception e) {
		}

		return json;
	}

	public JSONObject getOrganizationStreamTypesJson(
			OrganizationType organizationType) {
		JSONObject json = new JSONObject();
		if (organizationType == null) {
			return json;
		}
		if (organizationType == OrganizationType.school) {
			try {
				json.put("class5to8", "Class 5 - 8");
				json.put("class8to10", "Class 8 - 10");
				json.put("class11to12", "Class 11 - 12");

			} catch (Exception e) {
			}
		} else if (organizationType == OrganizationType.professional) {
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

	public JSONObject addOrgaziation(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String state = request.getParameter("state");
		String country = request.getParameter("country");
		String orgType = request.getParameter("orgTypeSelect");
		OrganizationDataStoreI ods = ApplicationContext.getApplicationContext()
				.getDataStoreMgr().getOrganizationDataStore();

		String status = "success";
		String message = "";
		Organization org = new Organization();
		JSONObject json = new JSONObject();
		try {
			org.setName(name);
			org.setAddress(address);
			org.setState(state);
			org.setCountry(country);
			OrganizationType type = OrganizationType.valueOf(orgType);
			org.setType(type);
			ods.save(org);
		} catch (Exception e) {
			status = "failed";
			message = "Error During Save Organization" + e.getMessage();
		}

		try {
			json.put("seq", org.getSeq());
			json.put("message", message);
			json.put("status", status);
		} catch (JSONException e) {
			// TODO Auto-generated catch blocks
			e.printStackTrace();
		}
		return json;
	}

}
