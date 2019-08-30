package com.tobi.service;

import java.util.List;

import com.tobi.domain.Level;
import com.tobi.domain.User;
import com.tobi.interfaces.UserDao;

public class UserService {

	UserDao userDao;
	
	public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
	public static final int MIN_RECOMMNED_FOR_GOLD = 30;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	

	
	public void add(User user) {
		if(user.getLevel() == null) user.setLevel(Level.BASIC);
		userDao.add(user);
	}
	
	public void upgradeLevels() {
		List<User> users = userDao.getAll();
		for(User user : users) {
			if(canUpgradeLevel(user)) {
				upgradeLevel(user);
			}
		}
	}
	
	private void upgradeLevel(User user) {
		if(user.getLevel() == Level.BASIC) {
			user.setLevel(Level.SILVER);
		}else if(user.getLevel() == Level.SILVER) {
			user.setLevel(Level.GOLD);
		}
		userDao.update(user);
	}
	
	private boolean canUpgradeLevel(User user) {
		Level currentLevel = user.getLevel();
		switch(currentLevel) {
			case BASIC : return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER);
			case SILVER : return (user.getRecommend() >= MIN_RECOMMNED_FOR_GOLD);
			case GOLD : return false;
			default: throw new IllegalArgumentException("Unknown Level :  " + currentLevel);
		}
	}
}
