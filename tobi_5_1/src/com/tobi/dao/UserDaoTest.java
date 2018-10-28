package com.tobi.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tobi.domain.Level;
import com.tobi.domain.User;

public class UserDaoTest {
	
	@Autowired
	private UserDao dao;

	private User user1 = new User();
	private User user2 = new User();
	private User user3 = new User();

	@Before
	public void setUp() {
		
		this.user1 = new User("1", "hayo1", "1234", Level.BASIC, 1, 0);
		this.user2 = new User("2", "hayo2", "1234", Level.SILVER, 55, 10);
		this.user3 = new User("3", "hayo3", "1234", Level.GOLD, 100, 40);
		
	}
	
	private void checkSameUser(User user1, User user2) {
		assertThat(user1.getId(), is(user2.getId()));
		assertThat(user1.getName(), is(user2.getName()));
		assertThat(user1.getPassword(), is(user2.getPassword()));
		assertThat(user1.getLevel(), is(user2.getLevel()));
		assertThat(user1.getLogin(), is(user2.getLogin()));
		assertThat(user1.getRecommend(), is(user2.getRecommend()));
	}
	
//	@Test
//	public void addAndGet() {
//		
//		User userget1 = dao.get(user1.getId());
//		checkSameUser(userget1, user1);
//		
//		User userget2 = dao.get(user2.getId());
//		checkSameUser(userget2, user2);
//	}
//	
//	@Test
//	public void update() {
//		dao.deleteAll();
//		
//		dao.add(user1);
//		dao.add(user2);
//		
//		user1.setName("ohohoh");
//		user1.setPassword("1234");
//		user1.setLevel(Level.GOLD);
//		user1.setLogin(1000);
//		user1.setRecommend(999);
//		
//		dao.update(user1);
//		
//		User user1update = dao.get(user1.getId());
//		checkSameUser(user1, user1update);
//		User user2same = dao.get(user2.getId());
//		checkSameUser(user2, user2same);
//		
//	}
	
	
}
