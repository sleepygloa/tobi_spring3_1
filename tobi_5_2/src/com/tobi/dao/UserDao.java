package com.tobi.dao;

import java.util.List;

import com.tobi.domain.User;

public interface UserDao {

	public void add(User user);

	public User get(String id);

	public void deleteAll();

	public int getCount();

	public List<User> getAll();

	void update(User user);
}
