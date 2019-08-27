package com.tobi;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.annotation.DirtiesContext;

import com.tobi.dao.UserDao;
import com.tobi.domain.User;


//기존 환경과는 별개로 독립적으로 작용하는 환경을 구성할때.
@DirtiesContext
public class UserDaoTest {
	
	@Autowired
	private UserDao ud;
	
	
	//JUnit @Test 실행되기전 실행되는 어노테이션
	@Before
	public void setUp() {
		ud = new UserDao();
		//SingleConnectionDataSource 는 하나의 커넥트를 이용해 계속 트랜젝션을 사용하는 방법. 빠르다.
		DataSource dataSource = new SingleConnectionDataSource("jdbc:mariadb://61.252.235.153:18081/seonhoblog?useUnicode=true&amp;characterEncoding=utf8", "test", "1234", true);
		ud.setDataSource(dataSource);
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
		
		ud.add(user2);
		assertThat(ud.getCount(), is(2));
		
		ud.add(user3);
		assertThat(ud.getCount(), is(3));
		
		//TEST2
		assertThat(ud.getCount(), is(3));
		
		User getUser1 = ud.get(user1.getId());
		assertThat(user1.getName(), is(getUser1.getName()));
		
		User getUser2 = ud.get(user2.getId());
		assertThat(user2.getName(), is(getUser2.getName()));
		
		User getUser3 = ud.get(user3.getId());
		assertThat(user3.getName(), is(getUser3.getName()));
	}


	//@Test(expected=EmptyResultDataAccessException.class)
	@Test
	public void getUserFauilure()throws SQLException {
		
	}



}
