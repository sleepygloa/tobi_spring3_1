package com.tobi;

import static com.tobi.service.UserService.MIN_LOGCOUNT_FOR_SILVER;
import static com.tobi.service.UserService.MIN_RECOMMNED_FOR_GOLD;
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

import com.tobi.domain.Level;
import com.tobi.domain.User;
import com.tobi.interfaces.UserDao;
import com.tobi.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/com/tobi/xml/applicationContext.xml")
public class UserServiceTest {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private PlatformTransactionManager transactionManager;

	List<User> users;
	
	@Autowired
	private UserDao userDao;
	
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
	
//	@Test
//	public void updateLevels() {
//		userDao.deleteAll();
//		for(User user : users) { userDao.add(user); }
//		
//		userService.upgradeLevels();
//		
//		checkLevelUpgraded(users.get(0), false);
//		checkLevelUpgraded(users.get(1), true);
//		checkLevelUpgraded(users.get(2), false);
//		checkLevelUpgraded(users.get(3), true);
//		checkLevelUpgraded(users.get(4), false);
//	}
	
	private void checkLevelUpgraded(User user, Boolean upgraded) {
		User userUpdate = userDao.get(user.getId());
		
		if(upgraded) {
			assertThat(userUpdate.getLevel(), is(user.getLevel().nextLevel()));
		}else {
			assertThat(userUpdate.getLevel(), is(user.getLevel()));
		}
		
		
	}
	
	private void checkLevel(User user, Level expectedLevel) {
		User userUpdate = userDao.get(user.getId());
		assertThat(userUpdate.getLevel(), is(expectedLevel));
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
	
	
	static class TestUserService extends UserService{
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
		UserService testUserService = new TestUserService(users.get(3).getId());
		testUserService.setUserDao(this.userDao);
//		testUserService.setDataSource(this.dataSource);
		testUserService.setTransactionManager(this.transactionManager);
		
		
		userDao.deleteAll();
		for(User user : users) {
			userDao.add(user);
		}
		
		try {
			testUserService.upgradeLevels();
		}catch(TestUserServiceException e) {
			fail("TestUserServiceException expected ");
			
		}
		
		checkLevelUpgraded(users.get(1), false);
	}
	
}
