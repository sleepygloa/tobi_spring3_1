package com.tobi.dao;

import com.tobi.domain.User;

import java.sql.*;

public class UserDao {

	String dbClass = "org.mariadb.jdbc.Driver";
	String dbUrl = "jdbc:mariadb://61.252.235.153:18081/seonhoblog?useUnicode=true&amp;characterEncoding=utf8";

	String add = "";
	String selet = "";
	
	//클래스
//	private SimpleConnectionMaker simpleConnectionMaker;
	
	//인터페이스
	private ConnectionMaker connecionMaker;
	
	public UserDao(ConnectionMaker connectionMaker) {
		this.connecionMaker = connectionMaker;
//		simpleConnectionMaker = new SimpleConnectionMaker;
	}
	
	//유저 추가
	public void add(User user) throws ClassNotFoundException, SQLException {
//		Connection c = getConnection();
//		Connection c = simpleConnectionMaker.makeNewConnection(); //클래스
		Connection c = connecionMaker.makeConnection(); //인터페이스

		PreparedStatement ps = c.prepareStatement("insert into TB_TOBI_USER (id, name, password) values (?, ?, ?)");
		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());

		ps.executeUpdate();

		ps.close();
		c.close();
	}

	//유저 정보 조회
	public User get(String id) throws ClassNotFoundException, SQLException {
//		Connection c = getConnection();
//		Connection c = simpleConnectionMaker.makeNewConnection(); //클래스
		Connection c = connecionMaker.makeConnection(); //인터페이스

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

	//기존 커넥션 방식
	private Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(dbClass);
		return DriverManager.getConnection(dbUrl, "test", "1234");
	}

}
