package com.tobi;

import java.sql.SQLException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.tobi.config.AppConfig;
import com.tobi.dao.UserDao;
import com.tobi.domain.User;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
		UserDao dao = applicationContext.getBean("userDao", UserDao.class);

		System.out.println("====================");
		dao.deleteAll();
		System.out.println("=====삭제성공=======");

		User user = new User();
		user.setId("1");
		user.setName("noname");
		user.setPassword("1234");

		dao.add(user);
		System.out.println("=====등록성공=======");

		User user2 = dao.get(user.getId());
		System.out.println("사용자이름 --> " + user2.getName());
		System.out.println("사용자비번 --> " + user2.getPassword());
		System.out.println("사용자아이디 --> " + user2.getId());
		System.out.println("=====조회성공=======");

		int cnt = dao.getCount();
		System.out.println("사용자수 --> " + cnt);
		System.out.println("=====사용자수 조회 성공=======");


	}


}
