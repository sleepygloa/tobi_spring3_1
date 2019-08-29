package com.tobi;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tobi.dao.UserDao;
import com.tobi.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/com/tobi/xml/applicationContext.xml")
public class UserDaoTest {
	
	@Autowired
	private ApplicationContext context;
	private UserDao ud;
	
	
	//JUnit @Test 실행되기전 실행되는 어노테이션
	@Before
	public void setUp() {
		this.ud = this.context.getBean("UserDao", UserDao.class);
		
		//Check
		System.out.println(this.context); //싱글톤 확인 RunWith, ContextConfiguration 으로 인한.
		System.out.println(this);
	}
	
	
	//JUnit 사용방법
	//1번째 메소드는 public, @Test
//	@Test(expected=EmptyResultDataAccessException.class)
	@Test
	public void addAndGet() throws SQLException, ClassNotFoundException {
		
		//delete Test
		ud.deleteAll();
		assertThat(ud.getCount(), is(0));

		
		User user1 = new User("1","root","1111");
		User user2 = new User("2","rosemary","2222");
		User user3 = new User("3","flower","66666");
		
		//TEST1
		ud.add(user1);
		assertThat(ud.getCount(), is(1));
		List<User> users1 = ud.getAll();
		assertThat(users1.size(), is(1));
		checkSameUser(user1, users1.get(0));
		
		
		ud.add(user2);
		assertThat(ud.getCount(), is(2));
		List<User> users2 = ud.getAll();
		assertThat(users2.size(), is(1));
		checkSameUser(user2, users2.get(2));
		
		ud.add(user3);
		assertThat(ud.getCount(), is(3));
		List<User> users3 = ud.getAll();
		assertThat(users3.size(), is(1));
		checkSameUser(user2, users3.get(2));
		
		User getUser1 = ud.get(user1.getId());
		assertThat(user1.getName(), is(getUser1.getName()));
		
		User getUser2 = ud.get(user2.getId());
		assertThat(user2.getName(), is(getUser2.getName()));
		
		User getUser3 = ud.get(user3.getId());
		assertThat(user3.getName(), is(getUser3.getName()));
	}

	private void checkSameUser(User user1, User user2) {
		assertThat(user1.getId(), is(user2.getId()));
		assertThat(user1.getName(), is(user2.getName()));
		assertThat(user1.getPassword(), is(user2.getPassword()));
	}


}
