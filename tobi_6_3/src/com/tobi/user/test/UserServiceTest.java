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
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;

import com.framework.util.MockMailSender;
import com.tobi.user.dao.UserDaoJdbc;
import com.tobi.user.domain.Level;
import com.tobi.user.domain.User;
import com.tobi.user.interfaces.UserService;
import com.tobi.user.service.UserServiceImpl;

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
	
	@Autowired
	ApplicationContext context;
	
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
	
	//userDao -> MockUserDao
	@Test
	public void upgradeLevels() {
//		userDao.deleteAll();
//		for(User user : users) userDao.add(user);
		
		UserServiceImpl userServiceImpl = new UserServiceImpl();
		
		MockUserDao mockUserDao = new MockUserDao(this.users);
		userServiceImpl.setUserDao(mockUserDao);

		MockMailSender mockMailSender = new MockMailSender();
		userServiceImpl.setMailSender(mockMailSender);
		
		userServiceImpl.upgradeLevels();
		
//		checkLevelUpgraded(users.get(0), false);
//		checkLevelUpgraded(users.get(1), true);
//		checkLevelUpgraded(users.get(2), false);
//		checkLevelUpgraded(users.get(3), true);
//		checkLevelUpgraded(users.get(4), false);
		
		List<User> updated = mockUserDao.getUpdated();
		assertThat(updated.size(), is(2));
		checkUserAndLevel(updated.get(0), "2", Level.SILVER);
		checkUserAndLevel(updated.get(1), "4", Level.GOLD);
		
//		List<String> request = mockMailSender.getRequest();
//		assertThat(request.size(), is(2));
//		assertThat(request.get(0), is(users.get(1).getMail()));
//		assertThat(request.get(1), is(users.get(3).getMail()));
	}
	
//	@Test
//	public void mockUpgradeLevel() throws Exception{
//		UserServiceImpl UserServiceImpl = new UserServiceImpl();
//		
//		UserDao mockUserDao = mock(UserDao.class);
//		when(mockUserDao.getAll().thenReturn(this.users));
//		userServiceImpl.setUserDao(mockUserDao);
//		
//		MailSender mockMailSender = mock(MailSender.class);
//		userServiceImpl.setMailSender(mailSender);
//		
//		userServiceImpl.upgradeLevels();
//		
//		verify(mockUserDao, times(2)).update(any(User.class));
//		verify(mockUserDao, times(2)).update(any(User.class));
//		verify(mockUserDao).update(users.get(1));
//		assertThat(users.get(1).getLevel(), is(Level.SILVER));
//		verify(mockUserDao).update(users.get(3));
//		assertThat(users.get(3).getLevel(), is(Level.GOLD));
//		
//		ArgumentCaptor<SimpleMailMessage> mailMessageArg = ArgumentCaptor(SimpleMailMessage.class);
//		verify(mockMailSender, times(2)).send(mailMessageArg.capture());
//		List<SimpleMailMessage> mailMessages = mailMessageArg.getAllValues();
//		assertThat(mailMessages.get(0).getTo()[0], is(users.get(1).getMail()));
//		assertThat(mailMessages.get(1).getTo()[0], is(users.get(3).getMail()));
//	}
	
	private void checkUserAndLevel(User updated, String expectedId, Level expectedLevel) {
		assertThat(updated.getId(), is(expectedId));
		assertThat(updated.getLevel(), is(expectedLevel));
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
	@DirtiesContext
	public void upgradeAllOrNothing() throws Exception {
		UserServiceImpl testUserService = new TestUserService(users.get(3).getId());
		testUserService.setUserDao(this.userDao);
//		testUserService.setMailSender(mailSender);
		
		//기본 transaction 클래스이용
//		UserServiceTx txUserService = new UserServiceTx();
//		txUserService.setTransactionManager(transactionManager);
//		txUserService.setUsetrService(txUserService);
		
		
		//프록시 타입 transaction 이용
//		TransactionHandler txHandler = new TransactionHandler();
//		txHandler.setTarget(testUserService);
//		txHandler.setTransactionManager(transactionManager);txHandler.setPattern("upgradeLevels");
//		UserService txUserService = (UserService)Proxy.newProxyInstance(
//				getClass().getClassLoader(), new Class[] { UserService.class }, txHandler);
		
		
//		TxProxyFactoryBean txProxyFactoryBean = context.getBean("&userService", TxProxyFactoryBean.class);
//		txProxyFactoryBean.setTarget(testUserService);
//		UserService txUserService = (UserService)txProxyFactoryBean.getObject();
		
		//동적 프록시의 advisor와 pointcut 적용
		ProxyFactoryBean txProxyFactoryBean = context.getBean("&userService", ProxyFactoryBean.class);
		txProxyFactoryBean.setTarget(testUserService);
		UserService txUserService = (UserService)txProxyFactoryBean.getObject();
		
		userDao.deleteAll();
		for(User user : users) userDao.add(user);
		
		try {
			testUserService.upgradeLevels();
//			fail("TestUserServiceException expected ");
		}catch(TestUserServiceException e) {
			
		}
		
		checkLevelUpgraded(users.get(1), false);
	}
	
}
