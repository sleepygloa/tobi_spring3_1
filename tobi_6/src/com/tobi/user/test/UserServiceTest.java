package com.tobi.user.test;

import static com.tobi.user.service.UserServiceImpl.MIN_LOGCOUNT_FOR_SILVER;
import static com.tobi.user.service.UserServiceImpl.MIN_RECOMMNED_FOR_GOLD;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;

import com.tobi.user.dao.UserDaoJdbc;
import com.tobi.user.domain.Level;
import com.tobi.user.domain.User;
import com.tobi.user.interfaces.UserService;
import com.tobi.user.service.UserServiceImpl;
import com.tobi.user.service.UserServiceTx;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/com/tobi/xml/applicationContext.xml")
public class UserServiceTest {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	@Autowired
	private PlatformTransactionManager transactionManager;

	List<User> users;
	
	@Autowired
	private UserDaoJdbc userDao;
	
//	@Autowired
//	private MailSender mailSender;
	
	@Before
	public void setUp() {
		users = Arrays.asList(
				new User("1", "spring1", "password1", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER-1, 0),
				new User("2", "spring2", "password2", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER, 0),
				new User("3", "spring3", "password3", Level.SILVER, 60, MIN_RECOMMNED_FOR_GOLD-1),
				new User("4", "spring4", "password4", Level.SILVER, 60, MIN_RECOMMNED_FOR_GOLD),
				new User("5", "spring5", "password5", Level.GOLD, 100, Integer.MAX_VALUE)
				);
		
	}
	
	private void checkLevelUpgraded(User user, Boolean upgraded) {
		User userUpdate = userDao.get(user.getId());
		
		if(upgraded) {
			assertThat(userUpdate.getLevel(), is(user.getLevel().nextLevel()));
		}else {
			assertThat(userUpdate.getLevel(), is(user.getLevel()));
		}
	}
	
	@Test
	public void add() {
		userDao.deleteAll();
		
		User userWithLevel  = users.get(4);
		User userWithoutLevel = users.get(0);
		userWithoutLevel.setLevel(null);
		
		userService.add(userWithLevel);
		userService.add(userWithoutLevel);
		
		
		User userWithLevelRead = userDao.get(userWithLevel.getId());
		User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());
		
		assertThat(userWithLevelRead.getLevel(), is(userWithLevel.getLevel()));
		assertThat(userWithoutLevelRead.getLevel(), is(Level.BASIC));
	}
	
//	@Test
	public void upgradeLevels() {
		userDao.deleteAll();
		for(User user : users) {
			userDao.add(user);
		}
		
		userServiceImpl.upgradeLevels();
		
//		MockMailSender mockMailSender = new MockMailSender();
//		userServiceImpl.setMailSender(mocMailSender);
	}
	
	
	
	static class TestUserService extends UserServiceImpl{
		private String id;
		
		private TestUserService(String id) {
			this.id = id;
		} 
		
		protected void upgradeLevel(User user) {
			if(user.getId().equals(this.id)) {
				super.upgradeLevel(user);
			}
		}
	}
	
	static class TestUserServiceException extends RuntimeException{
	}
	
	@Test
	public void upgradeAllOrNothing() throws Exception {
		UserServiceImpl testUserService = new TestUserService(users.get(3).getId());
		testUserService.setUserDao(this.userDao);
//		testUserService.setMailSender(mailSender);
		
		UserServiceTx txUserService = new UserServiceTx();
		txUserService.setTransactionManager(transactionManager);
		txUserService.setUserService(txUserService);
		
		
		userDao.deleteAll();
		for(User user : users) userDao.add(user);
		
		try {
			testUserService.upgradeLevels();
			fail("TestUserServiceException expected ");
		}catch(TestUserServiceException e) {
			
		}
		
		checkLevelUpgraded(users.get(1), false);
	}
	
}
