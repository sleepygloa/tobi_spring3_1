package com.tobi.xml;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.tobi.user.interfaces.UserService;
import com.tobi.user.test.TestUserService;

@Configuration
@Profile("test")
public class TestAppContext {

	
	
	@Bean
	public UserService testUserService() {
		return new TestUserService();
	}

}
