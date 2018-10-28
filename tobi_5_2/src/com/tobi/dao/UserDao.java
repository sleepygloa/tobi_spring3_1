package com.tobi.dao;

import java.util.List;

import com.tobi.domain.User;

public interface UserDao {

	public void add(User user);
	
	public void update(User user1);

	public User get(String id);

	public void deleteAll();

	public int getCount();

	public List<User> getAll();
}
