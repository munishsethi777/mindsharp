package com.satya.Managers;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import com.satya.ApplicationContext;
import com.satya.Persistence.DataStoreMgr;
import com.satya.Persistence.Impl.JDBCDataStoreMgr;
import com.satya.Persistence.Impl.PersistenceMgr;

public class ApplicationMgr {

	Logger log = Logger.getLogger(ApplicationMgr.class);

	public void ContextLoader(ServletContext ctx, ApplicationContext applicationContext) {
		log.info("Reloading Context Servlet Called....");
		String jdbcUrl = ctx.getInitParameter("jdbcURL");
		String user = ctx.getInitParameter("dbuser");
		String pass = ctx.getInitParameter("dbpassword");
		
		PersistenceMgr psmgr = new PersistenceMgr(jdbcUrl,user,pass);
		DataStoreMgr dataStoreMgr = new JDBCDataStoreMgr(psmgr);
		applicationContext .setDataStoreMgr(dataStoreMgr);
		
		GameResultMgr gameResultMgr = new GameResultMgr();
		applicationContext.setGameResultsMgr(gameResultMgr);
		
		UserMgr userMgr = new UserMgr();
		applicationContext.setUserMgr(userMgr);
		
		ApplicationMgr applicationMgr = new ApplicationMgr();
		applicationContext.setApplicationMgr(applicationMgr);
		
		GamesMgr gamesMgr = new GamesMgr();
		applicationContext.setGamesMgr(gamesMgr);
		
		OrganizationMgr organizationMgr = new OrganizationMgr();
		applicationContext.setOrganizationMgr(organizationMgr);
		
		BadgeMgr badgeMgr = new BadgeMgr();
		applicationContext.setBadgeMgr(badgeMgr);
		
		UserStatusMgr userStatusMgr = new UserStatusMgr();
		applicationContext.setUserStatusMgr(userStatusMgr);
		
		applicationContext.setApplicationContext(applicationContext);
		log.info("Context Reloaded");
	}
}
