package com.tobi.dao;

import com.tobi.domain.User;

import java.sql.*;

public class UserDao {

	String dbClass = "org.mariadb.jdbc.Driver";
	String dbUrl = "jdbc:mariadb://61.252.235.153:18081/seonhoblog?useUnicode=true&amp;characterEncoding=utf8";

	String add = "";
	String selet = "";
	
	//Ŭ����
//	private SimpleConnectionMaker simpleConnectionMaker;
	
	//�������̽�
	private ConnectionMaker connecionMaker;
	
//	public UserDao(ConnectionMaker connectionMaker) {
//		this.connecionMaker = connectionMaker;
////		simpleConnectionMaker = new SimpleConnectionMaker;
//	}
	
	public void setConnectionMaker(ConnectionMaker connectionMaker) {
		this.connecionMaker = connectionMaker;
	}
	
	//���� �߰�
	public void add(User user) throws ClassNotFoundException, SQLException {
//		Connection c = getConnection();
//		Connection c = simpleConnectionMaker.makeNewConnection(); //Ŭ����
		Connection c = connecionMaker.makeConnection(); //�������̽�

		PreparedStatement ps = c.prepareStatement("insert into TB_TOBI_USER (id, name, password) values (?, ?, ?)");
		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());

		ps.executeUpdate();

		ps.close();
		c.close();
	}

	//���� ���� ��ȸ
	public User get(String id) throws ClassNotFoundException, SQLException {
//		Connection c = getConnection();
//		Connection c = simpleConnectionMaker.makeNewConnection(); //Ŭ����
		Connection c = connecionMaker.makeConnection(); //�������̽�

		PreparedStatement ps = c.prepareStatement("select * from TB_TOBI_USER where id = ?");
		ps.setString(1, id);

		ResultSet rs = ps.executeQuery();
		rs.next();

		User user = new User();
		user.setId(rs.getString("id"));
		user.setName(rs.getString("name"));
		user.setPassword(rs.getString("password"));

		rs.close();
		ps.close();
		c.close();

		return user;
	}

	//���� Ŀ�ؼ� ���
//	private Connection getConnection() throws ClassNotFoundException, SQLException {
//		Class.forName(dbClass);
//		return DriverManager.getConnection(dbUrl, "test", "1234");
//	}

}
