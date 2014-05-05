package com.satya.Persistence;

import com.satya.BusinessObjects.User;

public interface UserDataStoreI {

	public void Save(User user);

	public void Delete(long userSeq);

	public User findBySeq(long seq);

	public User findByUserName(String userName);

	public User findByUserNamePassword(String userName, String password);

	public void changePassword(User user);

	public void updateMySkills(User user, String mySkillsStr);

	public void updateImage(User user) throws Exception;

	public void updateOrganization(User user);
}