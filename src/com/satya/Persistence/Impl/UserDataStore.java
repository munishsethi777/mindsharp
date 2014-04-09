package com.satya.Persistence.Impl;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.satya.ApplicationContext;
import com.satya.BusinessObjects.Organization;
import com.satya.BusinessObjects.User;
import com.satya.Persistence.RowMapper;
import com.satya.Persistence.UserBadgeDataStoreI;
import com.satya.Persistence.UserDataStoreI;
import com.satya.Utils.FileUtils;
import com.satya.Utils.ImageUtils;
import com.satya.enums.GameSkillBadgeType;
import com.satya.enums.GameSkillType;
import com.satya.enums.OrganizationType;
import com.sun.imageio.plugins.common.ImageUtil;

public class UserDataStore implements UserDataStoreI,RowMapper {
	
	Logger logger = Logger.getLogger(UserDataStore.class);
	private final static String SELECT_COMMON = "select users.*, organizations.* FROM users LEFT join organizations" +
			" on users.orgseq = organizations.seq";
	private final static String SELECT_BY_SEQ = SELECT_COMMON + " where users.seq = ?";
	private final static String SELECT_UNAME = SELECT_COMMON + " where users.username = ?";
	private final static String SELECT_UNAME_PASS = SELECT_COMMON + " where users.username=? and users.password=?";
	private final static String SAVE_USER = "insert into users(emailid, username, password, " +
			"fullname, mobile, country, signupdate, address, isactive, orgseq,islimited) " +
			"values (?,?,?,?,?,?,?,?,?,?,?)";
	private final static String UPDATE_PASSWORD = "Update users set password = ? where seq = ?";
	private final static String UPDATE_IMAGE = "Update users set imagename = ? where seq = ?";
	private final static String UPDATE_MY_SKILLS = "Update users set myskills = ? where seq = ?";
	
	private PersistenceMgr persistenceMgr;
	public UserDataStore(PersistenceMgr psmgr){
		this.persistenceMgr = psmgr;
	}
	@Override
	public void Save(User user) {
		try{
			long orgSeq = 0;
			if(user.getOrganization() != null){
				orgSeq = user.getOrganization().getSeq();
			}
			Object[] params  = new Object[]{
					user.getEmailId(),
					user.getUserName(),
					user.getPassword(),
					user.getFullName(),
					user.getMobile(),
					user.getMobile(),
					user.getSignupDate(),
					user.getAddress(),
					user.getIsActive(),
					orgSeq,
					user.getIsLimited()
			};
			persistenceMgr.excecuteUpdate(SAVE_USER, params);
			user.setSeq(persistenceMgr.getLastUpdatedSeq());
		}catch(Exception e){
			logger.error(e);
		}
		
	}

	@Override
	public void Delete(long userSeq) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User findBySeq(long seq) {
		Object [] params = new Object [] {seq};
		return (User) persistenceMgr.executeSingleObjectQuery(SELECT_BY_SEQ, params, this);
	}

	@Override
	public User findByUserName(String userName) {
		Object [] params = new Object [] {userName};
		return (User) persistenceMgr.executeSingleObjectQuery(SELECT_UNAME, params, this);
	}

	@Override
	public User findByUserNamePassword(String userName, String password) {
		Object [] params = new Object [] {userName,password};
		User user =  (User) persistenceMgr.executeSingleObjectQuery(SELECT_UNAME_PASS, params, this);
		return user;
	}


	@Override
	public Object mapRow(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return populateObjectFromResultSet(rs);
	}
	

	protected Object populateObjectFromResultSet(ResultSet rs)throws SQLException{
		User user = null;
		try{
			long userSeq = rs.getLong("seq");
			String userName = rs.getString("username");
			String password = rs.getString("password");
			String emailid = rs.getString("emailid");
			String fullName = rs.getString("fullname");
			String address = rs.getString("address");
			String mobile = rs.getString("mobile");
			Timestamp lastLoginTS = null;
			
			if(rs.getDate("lastlogindate") != null){
				lastLoginTS = rs.getTimestamp("lastlogindate");
			}
			
			Timestamp signupTS = null;
			if(rs.getDate("signupdate") != null){
				signupTS = rs.getTimestamp("signupdate");
			}
			
			boolean isActive = rs.getBoolean("isactive");
			boolean isLimited = rs.getBoolean("islimited");
			long orgSeq = rs.getLong("orgseq");
			String mySkillsStr = rs.getString("myskills");
			String imageName = rs.getString("imagename");
			
			user = new User();
			user.setSeq(userSeq);
			user.setUserName(userName);
			user.setPassword(password);
			user.setEmailId(emailid);
			user.setFullName(fullName);
			user.setAddress(address);
			user.setMobile(mobile);
			user.setLastLoginDate(lastLoginTS);
			user.setSignupDate(signupTS);
			user.setIsActive(isActive);
			user.setIsLimited(isLimited);
			user.setMySkillsStr(mySkillsStr);
			user.setImageName(imageName);
			if(orgSeq != 0){
				String orgName = rs.getString("orgname");
				String orgType = rs.getString("orgtype");
				String orgAddress = rs.getString("orgaddress");
				String orgState = rs.getString("orgstate");
				String orgCountry = rs.getString("orgcountry");
				
				Organization org = new Organization(orgSeq);
				org.setName(orgName);
				org.setAddress(orgAddress);
				org.setCountry(orgCountry);
				org.setState(orgState);
				if(orgType != null){
					org.setType(OrganizationType.valueOf(orgType));
				}
				user.setOrganization(org);
			}
			
			try{
				String[] mySkillsArr = mySkillsStr.split(",");
				List<GameSkillType> mySkills = new ArrayList<GameSkillType>();
				
				for(String str : mySkillsArr){
					try{
						if(str != null && !str.equals(""))
							mySkills.add(GameSkillType.valueOf(str));
					}catch(Exception e){
						logger.error("Error converting to GameSkillType from "+str,e);
					}
				}
				user.setMySkills(mySkills);
				UserBadgeDataStoreI UBDS = ApplicationContext.getApplicationContext().getDataStoreMgr().getUserBadgeDataStore();
				List<GameSkillBadgeType> mySkillBadges = UBDS.findBadges(userSeq);
				user.setMySkillBadges(mySkillBadges);
			}catch(Exception e){
				logger.error("Error setting myskills on user",e);
			}
			
		}catch(Exception e){
			logger.error("UserDataStore populate method error",e);
		}
		
		return user;
		
	}
	@Override
	public void changePassword(User user) {
		Object [] params = new Object [] {user.getPassword(),user.getSeq()};
		persistenceMgr.excecuteUpdate(UPDATE_PASSWORD, params);
	}
	@Override
	public void updateImage(User user) throws Exception {	
		saveFileContent(user);
		Object [] params = new Object [] {user.getImageName(),user.getSeq()};
		persistenceMgr.excecuteUpdate(UPDATE_IMAGE, params);		
	}
	
	@Override
	public void updateMySkills(User user, String mySkillsStr){
		Object [] params = new Object [] {mySkillsStr,user.getSeq()};
		persistenceMgr.excecuteUpdate(UPDATE_MY_SKILLS, params);
	}
	private void saveFileContent(User user) throws Exception{
		String fullImageFileName =  "D:\\projects\\Java Projects\\mindSharp\\images\\userImages\\" + user.getImageName();
	    File f = new File(fullImageFileName);
		File parentDir = f.getParentFile();
		if(!parentDir.exists()){
	    	parentDir.mkdir();
		}
		FileUtils.writeFile(fullImageFileName,user.getImageByte());
		String newImageName = String.valueOf(user.getSeq()) + "_thumb" +  "." + ImageUtils.getImageExtension(user.getImageName());
		user.setImageName(newImageName);
		ImageUtils.createThumbnail(fullImageFileName, 100, 150, newImageName);
	}
	
}
