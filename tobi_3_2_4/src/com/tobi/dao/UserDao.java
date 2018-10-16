package com.tobi.dao;

import java.sql.SQLException;

import javax.sql.DataSource;

import com.tobi.context.JdbcContext;

public class UserDao {
	private JdbcContext jdbcContext;
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.jdbcContext = new JdbcContext();
		this.jdbcContext.setDataSource(dataSource);
		this.dataSource = dataSource;
	}

	public void deleteAll() throws SQLException {
		this.jdbcContext.executeSql("delete from TB_TOBI_USER");
	}
}
