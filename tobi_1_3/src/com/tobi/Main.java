package com.tobi;

import java.sql.SQLException;

import com.tobi.dao.ConnectionMaker;
import com.tobi.dao.DConnectionMaker;
import com.tobi.dao.UserDao;
import com.tobi.domain.User;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ConnectionMaker connectionMaker = new DConnectionMaker();
		
		UserDao ud = new UserDao(connectionMaker);

		try {
			System.out.println("====================");
			System.out.println("새로운 유져 입력");
			User user = new User();
			user.setId("1");
			user.setName("root");
			user.setPassword("password");
			ud.add(user);
			//새로운 유져 입력 성공
			System.out.println("유져 입력 성공");
			System.out.println("====================");


			System.out.println("====================");
			System.out.println("입력한 유져정보 호출");
			User user2 = ud.get("1");
			//입력한 데이터를 불러와 입력 확인
			System.out.println("user id -->  " + user2.getId());
			System.out.println("user name --> " + user2.getName());
			System.out.println("user password --> " + user2.getPassword());
			System.out.println("유져정보 호출 성공");
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
