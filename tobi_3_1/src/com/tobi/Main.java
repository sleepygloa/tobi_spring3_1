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
		System.out.println("=====��������=======");

		User user = new User();
		user.setId("1");
		user.setName("noname");
		user.setPassword("1234");

		dao.add(user);
		System.out.println("=====��ϼ���=======");

		User user2 = dao.get(user.getId());
		System.out.println("������̸� --> " + user2.getName());
		System.out.println("����ں�� --> " + user2.getPassword());
		System.out.println("����ھ��̵� --> " + user2.getId());
		System.out.println("=====��ȸ����=======");

		int cnt = dao.getCount();
		System.out.println("����ڼ� --> " + cnt);
		System.out.println("=====����ڼ� ��ȸ ����=======");


	}


}
