package com.satya.Servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.satya.ApplicationContext;
import com.satya.BusinessObjects.Organization;
import com.satya.Managers.OrganizationMgr;
import com.satya.Persistence.OrganizationDataStoreI;
import com.satya.enums.OrganizationType;

/**
 * Servlet implementation class OrganizationServlet
 */
public class OrganizationServlet extends BaseServletClass {
	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(OrganizationServlet.class.getName());
  
	private static final String FIND_ORGANIZATION = "findOrganization";
	private static final String GET_ORGANIZATION_TYPES_JSON = "getOrganizationTypesJSON";
	private static final String GET_ORGANIZATION_STREAMS_JSON = "getOrganizationStreamsJSON";
	
    public OrganizationServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> errorMsgs = new ArrayList<String>();
		String action = request.getParameter("action");
		log.debug("Organization Servlet called with :" + action);
		if (action.equals("")) {
			request.getRequestDispatcher("index.jsp")
					.forward(request, response);
			return;
		}
		OrganizationMgr organizationMgr = ApplicationContext.getApplicationContext().getOrganizationMgr();
		
		if (action.equals(FIND_ORGANIZATION)) {
			String orgCode = request.getParameter("term");
			String orgType = request.getParameter("orgType");
			JSONArray arr = organizationMgr.findOrganizationJsonArray(orgCode);
			response.getWriter().print(arr.toString());
		
		} else if (action.equals(GET_ORGANIZATION_TYPES_JSON)) {
			JSONObject json = organizationMgr.findOrganizationTypesJsonArray();
			response.getWriter().print(json.toString());
		
		}else if (action.equals(GET_ORGANIZATION_STREAMS_JSON)) {
			String orgType = request.getParameter("orgType");
			JSONObject json = organizationMgr.getOrganizationStreamTypesJson(OrganizationType.valueOf(orgType));
			response.getWriter().print(json.toString());
		
		}  
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	
	
	
	

}
