package com.satya.Managers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.satya.ApplicationContext;
import com.satya.IConstants;
import com.satya.BusinessObjects.Game;
import com.satya.BusinessObjects.Promotion;
import com.satya.BusinessObjects.User;
import com.satya.BusinessObjects.UserGame;
import com.satya.Persistence.PromotionsDataStoreI;
import com.satya.Persistence.UserDataStoreI;
import com.satya.Persistence.UserGameDataStoreI;
import com.satya.enums.GameSkillType;

public class UserMgr {
	Logger log = Logger.getLogger(UserMgr.class.getName());

	public void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		UserDataStoreI UDS = ApplicationContext.getApplicationContext()
				.getDataStoreMgr().getUserDataStore();
		User user = UDS.findByUserNamePassword(userName, password);
		List<String> errorMsgs = new ArrayList<String>();

		if (user == null) {
			errorMsgs.add(IConstants.err_invalidUsernamePassword);
			request.setAttribute(IConstants.errMessages, errorMsgs);
			request.getRequestDispatcher("index.jsp")
					.forward(request, response);
		} else {
			log.debug("user logged in successfully");
			HttpSession session = request.getSession(true);
			session.setAttribute(IConstants.loggedInUser, user);
			request.setAttribute(IConstants.loggedInUser, user);
			String sourceURL = (String) session
					.getAttribute(IConstants.SOURCE_URL);
			if (sourceURL != null) {
				sourceURL = sourceURL.substring(1);
				request.getRequestDispatcher("dashboard.jsp").forward(request,
						response);
			} else {
				request.getRequestDispatcher("dashboard.jsp").forward(request,
						response);
			}
		}

	}

	public void signup(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String emailId = request.getParameter("emailId");
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String cpassword = request.getParameter("cpassword");
		String fullName = request.getParameter("fullName");
		String promCode = request.getParameter("refCode");

		List<String> errorMsgs = new ArrayList<String>();

		if (checkEmpty(emailId)) {
			errorMsgs.add("EmailId is Required");
		}
		if (checkEmpty(userName)) {
			errorMsgs.add("UserName is Required");
		}
		if (checkEmpty(password)) {
			errorMsgs.add("Password is Required");
		}
		if (checkEmpty(cpassword)) {
			errorMsgs.add("Confirm Password is Required");
		}

		if (!password.equals(cpassword)) {
			errorMsgs.add("Password and Confirm password must be same");
		}
		UserDataStoreI UDS = ApplicationContext.getApplicationContext()
				.getDataStoreMgr().getUserDataStore();
		User userExists = UDS.findByUserName(userName);
		if (userExists != null) {
			errorMsgs.add("User with username '" + userName
					+ "' already exists, Enter some other username");
		}

		User user = new User();
		// Any user signinup is a limited user.
		// will be termed full when paid for the application.
		user.setIsLimited(true);
		PromotionsDataStoreI PDS = ApplicationContext.getApplicationContext()
				.getDataStoreMgr().getPromotionsDataStore();
		Promotion promotion = PDS.findByCode("Free");
		List<Game> freeGames = promotion.getFreeGames();

		if (promCode != null && !promCode.equals("")) {
			log.info("Using promotion code as " + promCode);
			promotion = PDS.findByCode(promCode);
			if (promotion == null) {
				log.error("No promotion code found");
				errorMsgs.add("Invalid Promotion Code entered.");
			} else if (promotion.getExpiryDate().after(new Date())) {
				log.error("Expired Promotion Code");
				errorMsgs.add("Promotion Code expired.");
			} else {
				user.setOrganization(promotion.getOrganization());
				freeGames = promotion.getFreeGames();
			}
		}

		user.setEmailId(emailId);
		user.setUserName(userName);
		user.setPassword(password);
		user.setFullName(fullName);
		user.setSignupDate(new Date());

		if (errorMsgs != null && errorMsgs.size() > 0) {
			request.setAttribute(IConstants.errMessages, errorMsgs);
			request.setAttribute("user", user);
			request.getRequestDispatcher("signup.jsp").forward(request,
					response);
		} else {
			UDS.Save(user);
			HttpSession session = request.getSession(true);
			session.setAttribute(IConstants.loggedInUser, user);
			request.setAttribute(IConstants.loggedInUser, user);
			if (freeGames != null) {
				UserGameDataStoreI UGDS = ApplicationContext
						.getApplicationContext().getDataStoreMgr()
						.getUserGameDataStore();

				for (Game freeGame : freeGames) {
					UserGame userGame = new UserGame();
					userGame.setEnrollmentDate(new Date());
					Calendar validTillCalendar = Calendar.getInstance();
					validTillCalendar.add(Calendar.DAY_OF_YEAR,
							promotion.getGamesValidityDays());
					userGame.setValidTillDate(validTillCalendar.getTime());
					userGame.setGame(freeGame);
					userGame.setUser(ApplicationContext.getApplicationContext()
							.getLoggedinUser(request));
					UGDS.saveUserGame(userGame);
				}
			}

			request.getRequestDispatcher("dashboard.jsp").forward(request,
					response);
		}

	}

	public void uploadImage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// Check that we have a file upload request
		List<String> errorMsgs = new ArrayList<String>();
		String imageName = request.getParameter("userImg");
		UserDataStoreI UDS = ApplicationContext.getApplicationContext()
				.getDataStoreMgr().getUserDataStore();
		User user = UDS.findBySeq(ApplicationContext.getApplicationContext()
				.getLoggedinUser(request).getSeq());
		if (checkEmpty(imageName)) {
			// errorMsgs.add("Choose Image File");
		}
		if (errorMsgs != null && errorMsgs.size() > 0) {
			request.setAttribute(IConstants.errMessages, errorMsgs);
		} else {
			Boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (isMultipart) {
				FileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
				try {
					List fileItems = upload.parseRequest(request);
					Iterator i = fileItems.iterator();
					while (i.hasNext()) {
						FileItem fi = (FileItem) i.next();
						if (!fi.isFormField()) {
							String fileName = fi.getName();
							user.setImageName(fileName);
							user.setImageByte(fi.get());
							UDS.updateImage(user);
						}
					}
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
			HttpSession session = request.getSession(true);
			session.setAttribute(IConstants.loggedInUser, user);
			request.setAttribute(IConstants.loggedInUser, user);
			List<String> sccMsgs = new ArrayList<String>();
			sccMsgs.add("Image Uploaded Sucessfully.");
			request.setAttribute(IConstants.sccMessages, sccMsgs);
		}
		request.getRequestDispatcher("myAccount.jsp")
				.forward(request, response);
	}

	// public void uploadImage(HttpServletRequest request, HttpServletResponse
	// response)throws ServletException, IOException{
	// List<String> errorMsgs = new ArrayList<String>();
	// String imageName = request.getParameter("userImg");
	// if (checkEmpty(imageName)) {
	// errorMsgs.add("Choose Image File");
	// }
	// if (errorMsgs != null && errorMsgs.size() > 0) {
	// request.setAttribute(IConstants.errMessages, errorMsgs);
	// } else {
	// UserDataStoreI UDS =
	// ApplicationContext.getApplicationContext().getDataStoreMgr().getUserDataStore();
	// User user =
	// UDS.findBySeq(ApplicationContext.getApplicationContext().getLoggedinUser(request).getSeq());
	// user.setImageName(imageName);
	// try {
	// UDS.updateImage(user);
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// HttpSession session = request.getSession(true);
	// session.setAttribute(IConstants.loggedInUser, user);
	// request.setAttribute(IConstants.loggedInUser, user);
	// List<String> sccMsgs = new ArrayList<String>();
	// sccMsgs.add("Image Uploaded Sucessfully.");
	// request.setAttribute(IConstants.sccMessages, sccMsgs);
	// }
	// request.getRequestDispatcher("myAccount.jsp").forward(request,
	// response);
	//
	// }
	public void changePassword(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<String> errorMsgs = new ArrayList<String>();
		String currentPassword = request.getParameter("currentPassword");
		String newPassword = request.getParameter("newPassword");
		String confirmPassword = request.getParameter("confirmPassword");

		if (checkEmpty(currentPassword)) {
			errorMsgs.add("Current Password is Required");
		}
		if (checkEmpty(newPassword)) {
			errorMsgs.add("New Password is Required");
		}
		if (checkEmpty(confirmPassword)) {
			errorMsgs.add("Confirm Password is Required");
		}
		if (newPassword != null && confirmPassword != null) {
			if (!newPassword.equals(confirmPassword)) {
				errorMsgs
						.add("New Password and Confirm Password can not be different");
			}
		}
		if (!ApplicationContext.getApplicationContext()
				.getLoggedinUser(request).getPassword().equals(currentPassword)) {
			errorMsgs.add("False Current Password. Pls check");
		}
		if (errorMsgs != null && errorMsgs.size() > 0) {
			request.setAttribute(IConstants.errMessages, errorMsgs);

		} else {
			UserDataStoreI UDS = ApplicationContext.getApplicationContext()
					.getDataStoreMgr().getUserDataStore();
			User user = UDS.findBySeq(ApplicationContext
					.getApplicationContext().getLoggedinUser(request).getSeq());
			user.setPassword(newPassword);
			UDS.changePassword(user);
			HttpSession session = request.getSession(true);
			session.setAttribute(IConstants.loggedInUser, user);
			request.setAttribute(IConstants.loggedInUser, user);
			List<String> sccMsgs = new ArrayList<String>();
			sccMsgs.add("Password Updated successfully.");
			request.setAttribute(IConstants.sccMessages, sccMsgs);
		}
		request.getRequestDispatcher("myAccount.jsp")
				.forward(request, response);
	}

	private boolean checkEmpty(String str) {
		boolean isNull = true;
		if (str != null && !str.trim().equals("")) {
			isNull = false;
		}
		return isNull;
	}

	public void reloadSessionUser(HttpServletRequest request) {
		UserDataStoreI UDS = ApplicationContext.getApplicationContext()
				.getDataStoreMgr().getUserDataStore();
		HttpSession session = request.getSession(true);
		User loggedUser = (User) session.getAttribute(IConstants.loggedInUser);
		User user = UDS.findBySeq(loggedUser.getSeq());
		session.setAttribute(IConstants.loggedInUser, user);
		request.setAttribute(IConstants.loggedInUser, user);
	}

	public JSONObject saveMySkills(String skillIds, User user,
			HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {
			UserDataStoreI UDS = ApplicationContext.getApplicationContext()
					.getDataStoreMgr().getUserDataStore();
			UDS.updateMySkills(user, skillIds);
			json.put("RESPONSE", "SUCCESS");
			ApplicationContext.getApplicationContext().getUserMgr()
					.reloadSessionUser(request);
		} catch (Exception e) {
			try {
				json.put("RESPONSE", "FAILURE");
			} catch (Exception e1) {
			}
		}
		return json;
	}

	public JSONObject getMySkills(User user) {
		JSONObject json = new JSONObject();
		try {
			for (GameSkillType skillType : user.getMySkills()) {
				json.put(skillType.key(), skillType.name());
			}
		} catch (Exception e) {

		}
		return json;
	}

	public void updateAccountInfo(HttpServletRequest request,
			HttpServletResponse response) {
		UserDataStoreI UDS = ApplicationContext.getApplicationContext()
				.getDataStoreMgr().getUserDataStore();
		HttpSession session = request.getSession(true);
		User loggedUser = (User) session.getAttribute(IConstants.loggedInUser);
		User user = UDS.findBySeq(loggedUser.getSeq());
		UserStatusMgr userStatusMgr = ApplicationContext
				.getApplicationContext().getUserStatusMgr();
		String userStatus = userStatusMgr.getUserSatus(user);
		user.setStatus(userStatus);
		session.setAttribute(IConstants.loggedInUser, user);
		request.setAttribute(IConstants.loggedInUser, user);
	}
}
