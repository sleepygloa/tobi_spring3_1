package com.tobi;

import java.sql.SQLException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.tobi.config.AppConfig;
import com.tobi.dao.UserDao;
import com.tobi.domain.User;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		//--> Bean ����
				AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
				UserDao dao = applicationContext.getBean("userDao", UserDao.class);

		//--> �ݺ� �׽�Ʈ�� ���� ������ ����
				dao.deleteAll();

		//--> VO ������ ����
				User user = new User();
				user.setId("1");
				user.setName("noname");
				user.setPassword("1234");

		//--> ������ ����
				dao.add(user);

		//--> ������ Ȯ��
				User user2 = dao.get(user.getId());
				System.out.println("user name --> " + user2.getName());
				System.out.println("user passwd --> " + user2.getPassword());
				System.out.println("user id --> " + user2.getId());

		//--> ������ ���� üũ
				int cnt = dao.getCount();
				System.out.println("getCount --> " + cnt);

			}


}
