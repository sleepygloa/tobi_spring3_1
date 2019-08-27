package com.tobi.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.tobi.domain.User;

public class UserDao {

	private DataSource dataSource;
	private ConnectionMaker connecionMaker;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void setConnectionMaker(ConnectionMaker connectionMaker) {
		this.connecionMaker = connectionMaker;
	}
	
	//user add
	public void add(User user) throws ClassNotFoundException, SQLException {
//		Connection c = connecionMaker.makeConnection();
		Connection c = dataSource.getConnection();

		PreparedStatement ps = c.prepareStatement("insert into TB_TOBI_USER (id, name, password) values (?, ?, ?)");
		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());

		ps.executeUpdate();

		ps.close();
		c.close();
	}

	//user get
	public User get(String id) throws ClassNotFoundException, SQLException {
//		Connection c = connecionMaker.makeConnection(); //�������̽�
		Connection c = dataSource.getConnection();

		PreparedStatement ps = c.prepareStatement("select * from TB_TOBI_USER where id = ?");
		ps.setString(1, id);

		ResultSet rs = ps.executeQuery();
		
		User user = null;
		if(rs.next()) {
			user = new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
			
		}

		rs.close();
		ps.close();
		c.close();

//		if(user == null) throw new EmptyResultDataAccessException();
		
		return user;
	}
	
	//user delete All
	public void deleteAll() throws ClassNotFoundException, SQLException {
		Connection c = dataSource.getConnection();

		PreparedStatement ps = c.prepareStatement("delete from TB_TOBI_USER");

		ps.executeUpdate();
		
		ps.close();
		c.close();
	}

	//user Count get
	public int getCount() throws SQLException {
		Connection c = dataSource.getConnection();

		PreparedStatement ps = c.prepareStatement("select count(*) from TB_TOBI_USER");
		ResultSet rs = ps.executeQuery();
		rs.next();
		int count = rs.getInt(1);
		

		rs.close();
		ps.close();
		c.close();
		
		return count;
	}
}
