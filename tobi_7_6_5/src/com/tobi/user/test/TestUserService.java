package com.tobi.user.test;

import static com.tobi.user.service.UserServiceImpl.MIN_LOGCOUNT_FOR_SILVER;
import static com.tobi.user.service.UserServiceImpl.MIN_RECOMMNED_FOR_GOLD;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.framework.util.mail.interfaces.MailSender;
import com.framework.util.mail.service.MockMailSender;
import com.tobi.user.dao.MockUserDao;
import com.tobi.user.dao.UserDaoJdbc;
import com.tobi.user.domain.Level;
import com.tobi.user.domain.User;
import com.tobi.user.interfaces.UserDao;
import com.tobi.user.interfaces.UserService;
import com.tobi.user.service.UserServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/com/tobi/xml/applicationContext.xml")
public class TestUserService implements UserService{

	@Override
	public void add(User user) {
		System.out.println("add");
		// TODO Auto-generated method stub
		
	}

	@Override
	public User get(String id) {
		System.out.println("get");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getAll() {
		System.out.println("getAll");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAll() {
		System.out.println("deleteAll");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(User user) {
		System.out.println("update");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void upgradeLevels() {
		System.out.println("upgradeLevels");
		// TODO Auto-generated method stub
		
	}

	
}
