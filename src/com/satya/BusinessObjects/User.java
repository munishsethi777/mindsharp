package com.satya.BusinessObjects;

import java.util.Date;
import java.util.List;

import com.satya.ApplicationContext;
import com.satya.Managers.UserStatusMgr;
import com.satya.enums.GameSkillBadgeType;
import com.satya.enums.GameSkillType;

public class User {

	private long seq;
	private String userName;
	private String password;
	private String decriptedPassword;
	private String emailId;
	private String fullName;
	private String address;
	private String country;
	
	private String mobile;
	private Date signupDate;
	private Date lastLoginDate;
	private boolean isActive;
	private boolean isLimited;
	private String refCode;
	private Organization organization;
	private List<GameSkillType> mySkills;
	private List<GameSkillBadgeType>myBadges;
	private String mySkillsStr;
	private String status;
	private String imageName;
	private byte[] imageByte;
	

	public User(){
		
	}
	public User(long seq){
		this.seq = seq;
	}
	public long getSeq() {
		return seq;
	}
	public void setSeq(long seq) {
		this.seq = seq;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDecriptedPassword() {
		return decriptedPassword;
	}
	public void setDecriptedPassword(String decriptedPassword) {
		this.decriptedPassword = decriptedPassword;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public Date getSignupDate() {
		return signupDate;
	}
	public void setSignupDate(Date signupDate) {
		this.signupDate = signupDate;
	}
	public Date getLastLoginDate() {
		return lastLoginDate;
	}
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	
	public boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
	public boolean getIsLimited() {
		return isLimited;
	}
	public void setIsLimited(boolean isLimited) {
		this.isLimited = isLimited;
	}
	public String getRefCode() {
		return refCode;
	}
	public void setRefCode(String refCode) {
		this.refCode = refCode;
	}
	public Organization getOrganization() {
		return organization;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	public List<GameSkillType> getMySkills() {
		return mySkills;
	}
	public void setMySkills(List<GameSkillType> mySkills) {
		this.mySkills = mySkills;
	}
	public String getMySkillsStr() {
		return mySkillsStr;
	}
	public void setMySkillsStr(String mySkillsStr) {
		this.mySkillsStr = mySkillsStr;
	}
	
	public List<GameSkillBadgeType> getMySkillBadges() {
		return myBadges;
	}
	public void setMySkillBadges(List<GameSkillBadgeType> mySkillBadges) {		
		this.myBadges = mySkillBadges;
	}
	public String getStatus() {
		if(this.status == null){
			UserStatusMgr userStatusMgr = ApplicationContext.getApplicationContext().getUserStatusMgr();
			String userStatus = userStatusMgr.getUserSatus(this);
			this.status = userStatus;
		}
		return status;
	}
	public void setStatus(String userStatus) {
		this.status = userStatus;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imgName) {
		this.imageName = imgName;
	}
	public byte[] getImageByte() {
		return imageByte;
	}
	public void setImageByte(byte[] imgbyte) {
		this.imageByte = imgbyte;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
}
