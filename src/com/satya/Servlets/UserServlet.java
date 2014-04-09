package com.satya.Servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.satya.ApplicationContext;
import com.satya.IConstants;
import com.satya.BusinessObjects.GameResult;
import com.satya.BusinessObjects.User;
import com.satya.Managers.GameResultMgr;
import com.satya.Managers.GamesMgr;
import com.satya.Managers.UserMgr;
import com.satya.Persistence.GameResultDataStoreI;
import com.satya.Persistence.UserDataStoreI;
import com.satya.enums.GameSkillType;

public class UserServlet extends BaseServletClass {

	private static final long serialVersionUID = 1L;
	private static final String LOGOUT = "logout";
	private static final String LOGIN = "login";
	private static final String SIGNUP = "signup";
	private static final String RELOADCONTEXT = "reloadContext";
	private static final String MYACCOUNT = "myAccount";
	private static final String CHANGEPASSWORD = "changePassword";
	private static final String SCORECHARTJSON = "scoreChartJSON";
	private static final String GET_AVAILABLE_SKILLS = "getAvailableSkills";
	private static final String GET_MY_SKILLS = "getMySkills";
	private static final String SAVE_MY_SKILLS = "saveMySkills";
	private static final String UPLOAD_IMAGE = "uploadImage";

	Logger log = Logger.getLogger(UserServlet.class.getName());

	public UserServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<String> errorMsgs = new ArrayList<String>();
		String action = request.getParameter("action");
		log.debug("Get Action on User Servlet called:" + action);
		if (action.equals("")) {
			request.getRequestDispatcher("index.jsp")
					.forward(request, response);
			return;
		}
		if (!action.equals("") && action.equals(RELOADCONTEXT)) {
			this.reloadContext();
			response.getWriter().print("Context Initialized success");
			return;
		}
		if (getLoggedinUser(request) == null) {
			errorMsgs.add(IConstants.err_pleaseLogin);
			request.setAttribute(IConstants.errMessages, errorMsgs);
			request.getRequestDispatcher("index.jsp")
					.forward(request, response);
		} else {
			UserDataStoreI UDS = ApplicationContext.getApplicationContext()
					.getDataStoreMgr().getUserDataStore();
			UserMgr userMgr = ApplicationContext.getApplicationContext()
					.getUserMgr();
			GamesMgr gamesMgr = ApplicationContext.getApplicationContext()
					.getGamesMgr();
			GameResultMgr gameResultMgr = ApplicationContext
					.getApplicationContext().getGameResultsMgr();

			if (action.equals(MYACCOUNT)) {
				userMgr.updateAccountInfo(request,response);
				request.getRequestDispatcher("myAccount.jsp").forward(request,
						response);				
			} else if (action.equals(LOGOUT)) {
				request.getSession().removeAttribute(IConstants.loggedInUser);
				request.getRequestDispatcher("index.jsp").forward(request,
						response);
				
			} else if (action.equals(SCORECHARTJSON)) {
				String chartString = gameResultMgr
						.getScoreChartJSON(getLoggedinUser(request).getSeq());
				response.getWriter().print(chartString);

			} else if (action.equals(GET_MY_SKILLS)) {
				User user = getLoggedinUser(request);
				JSONObject json = userMgr.getMySkills(user);
				response.getWriter().print(json.toString());

			} else if (action.equals(GET_AVAILABLE_SKILLS)) {
				JSONObject json = gamesMgr
						.getAvailableSkills(getLoggedinUser(request));
				response.getWriter().print(json.toString());

			} else if (action.equals(SAVE_MY_SKILLS)) {
				String skillIds = (String) request.getParameter("skills");
				JSONObject json = userMgr.saveMySkills(skillIds,
						getLoggedinUser(request), request);
				response.getWriter().print(json.toString());

			}
			else {
				request.getRequestDispatcher("index.jsp").forward(request,
						response);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// LOGIN METHOD
		String action = (String) request.getParameter("action");
		log.debug("Post Action on User Servlet called:" + action);
		if (action != null) {
			if (action.equals(SIGNUP)) {
				ApplicationContext.getApplicationContext().getUserMgr()
						.signup(request, response);
			
			}else if(action.equals(LOGIN)) {
				ApplicationContext.getApplicationContext().getUserMgr()
						.login(request, response);
			
			}else if(action.equals(CHANGEPASSWORD)) {
				ApplicationContext.getApplicationContext().getUserMgr()
						.changePassword(request, response);
			}
			else if (action.equals(UPLOAD_IMAGE)) {
				ApplicationContext.getApplicationContext().getUserMgr()
				.uploadImage(request, response);

			}
		}

	}

	private void reloadContext() {
		ApplicationContext applicationContext = ApplicationContext
				.getApplicationContext();
		ServletContext ctx = getServletContext();
		ApplicationContext.getApplicationContext().getApplicationMgr()
				.ContextLoader(ctx, applicationContext);
	}

}
