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
			System.out.println("���ο� ���� �Է�");
			User user = new User();
			user.setId("1");
			user.setName("root");
			user.setPassword("password");
			ud.add(user);
			//���ο� ���� �Է� ����
			System.out.println("���� �Է� ����");
			System.out.println("====================");


			System.out.println("====================");
			System.out.println("�Է��� �������� ȣ��");
			User user2 = ud.get("1");
			//�Է��� �����͸� �ҷ��� �Է� Ȯ��
			System.out.println("user id -->  " + user2.getId());
			System.out.println("user name --> " + user2.getName());
			System.out.println("user password --> " + user2.getPassword());
			System.out.println("�������� ȣ�� ����");
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
