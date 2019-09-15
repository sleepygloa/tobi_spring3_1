package com.tobi.user.interfaces;

import java.util.List;

import com.tobi.user.domain.User;

public interface UserService {
	void add(User user);
	
	User get(String id);
	List<User> getAll();
	void deleteAll();
	void update(User user);
	
	void upgradeLevels();
}
