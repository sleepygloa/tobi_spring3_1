package com.tobi.dao;

import com.tobi.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDaoAdd extends UserDao {
	@Override
	protected PreparedStatement makeStatement(Connection c) throws SQLException {
		PreparedStatement ps = c.prepareStatement("insert into TB_TOBI_USER (id, name, password) values (?, ?, ?)");
		User user = getUser();

		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());

		ps.executeUpdate();
		return ps;

	}

	private User getUser() {
		User user = new User();
		user.setId("1");
		user.setName("noname");
		user.setPassword("1234");
		return user;
	}
}
