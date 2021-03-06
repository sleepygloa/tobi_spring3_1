package com.tobi;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.tobi.dao.DaoFactory;
import com.tobi.dao.UserDao;
import com.tobi.domain.User;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//기존 클래스에서 congifuration, Bean 을 등록하여 사용하는 방식
//		ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
//		UserDao ud = context.getBean("userDao", UserDao.class);
		
		//xml을 등록하여 사용하는 방식
		ApplicationContext context = new GenericXmlApplicationContext("/com/tobi/xml/applicationContext.xml");
		UserDao ud = context.getBean("UserDao", UserDao.class);

		try {
			System.out.println("====================");
			System.out.println("사용자 정보입력");
			User user = new User();
			user.setId("1");
			user.setName("root");
			user.setPassword("password");
			ud.add(user);
			//���ο� ���� �Է� ����
			System.out.println("사용자 정보입력 끝");
			System.out.println("====================");


			System.out.println("====================");
			System.out.println("사용자 정보입력");
			User user2 = ud.get("1");
			//�Է��� �����͸� �ҷ��� �Է� Ȯ��
			System.out.println("user id -->  " + user2.getId());
			System.out.println("user name --> " + user2.getName());
			System.out.println("user password --> " + user2.getPassword());
			System.out.println("사용자 정보입력 끝");
			System.out.println("====================");
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException error");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("SQLException error");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}
