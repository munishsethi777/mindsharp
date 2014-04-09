package com.satya;


import javax.servlet.http.HttpServletRequest;

import com.satya.BusinessObjects.User;
import com.satya.Managers.ApplicationMgr;
import com.satya.Managers.BadgeMgr;
import com.satya.Managers.GameResultMgr;
import com.satya.Managers.GamesMgr;
import com.satya.Managers.OrganizationMgr;
import com.satya.Managers.UserMgr;
import com.satya.Managers.UserStatusMgr;
import com.satya.Persistence.DataStoreMgr;


public class ApplicationContext {

	private static ApplicationContext applicationContext;
	private static DataStoreMgr dataStoreMgr;
	private static GameResultMgr gameResultsMgr;
	private static UserMgr userMgr;
	private static ApplicationMgr applicationMgr;
	private static GamesMgr gamesMgr;
	private static OrganizationMgr organizationMgr;
	private static BadgeMgr badgeMgr;
	private UserStatusMgr userStatusMgr;
	
	public static ApplicationContext getApplicationContext(){
    	if(applicationContext == null){
    		throw new RuntimeException("No Application Context was initialized");
        }else{
        	return applicationContext;
        }        
    }
	
	public static void setApplicationContext(ApplicationContext ac){
		applicationContext = ac;
	}

	public DataStoreMgr getDataStoreMgr() {
		return dataStoreMgr;
	}

	public void setDataStoreMgr(DataStoreMgr dataStoreMgr) {
		ApplicationContext.dataStoreMgr = dataStoreMgr;
	}
	
	public User getLoggedinUser(HttpServletRequest request){
		User user = (User) request.getSession().getAttribute(IConstants.loggedInUser);
		return user;
	}

	public GameResultMgr getGameResultsMgr() {
		return gameResultsMgr;
	}
	public static void setGameResultsMgr(GameResultMgr gameResultsMgr) {
		ApplicationContext.gameResultsMgr = gameResultsMgr;
	}
	public UserStatusMgr getUserStatusMgr() {
		return this.userStatusMgr;
	}
	public void setUserStatusMgr(UserStatusMgr userStatusMgr) {
		this.userStatusMgr = userStatusMgr;
	}
	

	public static UserMgr getUserMgr() {
		return userMgr;
	}

	public static void setUserMgr(UserMgr userMgr) {
		ApplicationContext.userMgr = userMgr;
	}

	public static ApplicationMgr getApplicationMgr() {
		return applicationMgr;
	}

	public static void setApplicationMgr(ApplicationMgr applicationMgr) {
		ApplicationContext.applicationMgr = applicationMgr;
	}

	public static GamesMgr getGamesMgr() {
		return gamesMgr;
	}

	public static void setGamesMgr(GamesMgr gamesMgr) {
		ApplicationContext.gamesMgr = gamesMgr;
	}

	public static OrganizationMgr getOrganizationMgr() {
		return organizationMgr;
	}

	public static void setOrganizationMgr(OrganizationMgr organizationMgr) {
		ApplicationContext.organizationMgr = organizationMgr;
	}

	public static BadgeMgr getBadgeMgr() {
		return badgeMgr;
	}

	public static void setBadgeMgr(BadgeMgr badgeMgr) {
		ApplicationContext.badgeMgr = badgeMgr;
	}
	
}
