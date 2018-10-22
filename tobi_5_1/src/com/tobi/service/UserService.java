package com.tobi.service;

import com.tobi.dao.UserDao;
import com.tobi.domain.Level;
import com.tobi.domain.User;

import java.util.List;

public class UserService {
	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void upgradeLevels() {
		List<User> users = userDao.getAll();

		for (User user : users) {
			Boolean changed = null;

			Level level = user.getLevel();
			int login = user.getLogin();
			int recommend = user.getRecommend();

			if (level == Level.BASIC && login >= 50) {
				user.setLevel(Level.SILVER);
				changed = true;
			} else if (level == Level.SILVER && recommend >= 30) {
				user.setLevel(Level.GOLD);
				changed = true;
			} else if (level == Level.GOLD) {
				changed = false;
			} else {
				changed = false;
			}

			if (changed) {
				userDao.update(user);
			}
		}
	}

	public void add(User user) {
		if (user.getLevel() == null) {
			user.setLevel(Level.BASIC);
		}

		userDao.add(user);
	}
}
