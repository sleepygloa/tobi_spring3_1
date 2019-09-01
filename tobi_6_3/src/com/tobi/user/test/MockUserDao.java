package com.tobi.user.test;

import java.util.ArrayList;
import java.util.List;

import com.tobi.user.domain.User;
import com.tobi.user.interfaces.UserDao;

public class MockUserDao implements UserDao {
	
	private List<User> users;
	private List<User> updated = new ArrayList();
	
	public MockUserDao(List<User> users) {
		this.users = users;
	}
	
	public List<User> getUpdated(){
		return this.updated;
	}

	public List<User> getAll(){
		return this.users;
	}
	
	public void update(User user) {
		updated.add(user);
	}
	
	//사용하지 않는 메서드는 아래와 같이 처리한다.
	public void add(User user) {  throw new UnsupportedOperationException(); };
	public void deleteAll() {  throw new UnsupportedOperationException(); };
	public User get(String id) {  throw new UnsupportedOperationException(); };
	public int getCount() {  throw new UnsupportedOperationException(); };
	
}
