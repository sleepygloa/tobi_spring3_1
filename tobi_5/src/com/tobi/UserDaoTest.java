package com.tobi;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tobi.dao.UserDaoJdbc;
import com.tobi.domain.Level;
import com.tobi.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/com/tobi/xml/applicationContext.xml")
public class UserDaoTest {
	
	//둘다가능
//	private UserDaoJdbc ud;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private UserDaoJdbc ud;
	
	//JUnit 사용방법
	@Test
	public void addAndGet() throws SQLException, ClassNotFoundException {
		
		//delete Test
		ud.deleteAll();
		assertThat(ud.getCount(), is(0));

		
		User user1 = new User("1","root","1111", Level.BASIC, 1, 0);
		User user2 = new User("2","rosemary","2222", Level.SILVER, 55, 10);
		User user3 = new User("3","flower","66666", Level.GOLD, 100, 40);
		
		//TEST1
		ud.add(user1);
		assertThat(ud.getCount(), is(1));
		List<User> users1 = ud.getAll();
		assertThat(users1.size(), is(1));
		checkSameUser(user1, users1.get(0));
		
		
		ud.add(user2);
		assertThat(ud.getCount(), is(2));
		List<User> users2 = ud.getAll();
		assertThat(users2.size(), is(2));
		checkSameUser(user2, users2.get(1));
		
		ud.add(user3);
		assertThat(ud.getCount(), is(3));
		List<User> users3 = ud.getAll();
		assertThat(users3.size(), is(3));
		checkSameUser(user3, users3.get(2));
		
		User getUser1 = ud.get(user1.getId());
		checkSameUser(getUser1, user1);
		
		User getUser2 = ud.get(user2.getId());
		checkSameUser(getUser2, user2);
		
		User getUser3 = ud.get(user3.getId());
		checkSameUser(getUser3, user3);
		
		update();
	}

	private void checkSameUser(User user1, User user2) {
		assertThat(user1.getId(), is(user2.getId()));
		assertThat(user1.getName(), is(user2.getName()));
		assertThat(user1.getPassword(), is(user2.getPassword()));
		assertThat(user1.getLevel(), is(user2.getLevel()));
		assertThat(user1.getLogin(), is(user2.getLogin()));
		assertThat(user1.getRecommend(), is(user2.getRecommend()));
	}
	
	public void update() {
		
		User user1 = new User();
		user1.setId("1");
		user1.setName("nnnnnn");
		user1.setPassword("soring");
		user1.setLevel(Level.GOLD);
		user1.setLogin(1000);
		user1.setRecommend(9999);
		
		
		ud.update(user1);
		
		User user1Update = ud.get(user1.getId());
		checkSameUser(user1, user1Update);
		
		
		
	}


}
