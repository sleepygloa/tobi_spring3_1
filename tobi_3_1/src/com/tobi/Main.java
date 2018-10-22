package com.tobi;

import java.sql.SQLException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.tobi.config.AppConfig;
import com.tobi.dao.UserDao;
import com.tobi.domain.User;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		//--> Bean 주입
				AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
				UserDao dao = applicationContext.getBean("userDao", UserDao.class);

		//--> 반복 테스트를 위해 데이터 삭제
				dao.deleteAll();

		//--> VO 데이터 생성
				User user = new User();
				user.setId("1");
				user.setName("noname");
				user.setPassword("1234");

		//--> 데이터 생성
				dao.add(user);

		//--> 데이터 확인
				User user2 = dao.get(user.getId());
				System.out.println("user name --> " + user2.getName());
				System.out.println("user passwd --> " + user2.getPassword());
				System.out.println("user id --> " + user2.getId());

		//--> 데이터 수량 체크
				int cnt = dao.getCount();
				System.out.println("getCount --> " + cnt);

			}


}
