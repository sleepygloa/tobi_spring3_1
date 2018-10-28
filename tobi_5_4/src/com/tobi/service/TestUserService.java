package com.tobi.service;

import org.junit.Test;

import com.tobi.domain.User;

public class TestUserService extends UserService{
	
	private String id;
	
	TestUserService(String id) {
		this.id = id;
	}
	
	protected void upgradeLevel(User user) {
		if(user.getId().equals(this.id)) throw new TestUserServiceException();
		super.upgradeLevel(user);
	}
	
	static class TestUserServiceException extends RuntimeException{
		
	}


	
	
}
