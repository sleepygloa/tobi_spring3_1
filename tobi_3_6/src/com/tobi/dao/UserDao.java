package com.tobi.dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.tobi.domain.User;

public class UserDao {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);	
		this.dataSource = dataSource;
	}
	
	private RowMapper<User> userMapper = 
			new RowMapper<User>() {
				public User mapRow(ResultSet rs, int rowNum) throws SQLException{
					User user = new User();
					user.setId(rs.getString("id"));
					user.setName(rs.getString("name"));
					user.setPassword(rs.getString("password"));
					return user;
				}
	};
	
	//user add
	public void add(User user)  {
		this.jdbcTemplate.update("insert into TB_TOBI_USER (id, name, password) values (?, ?, ?)", user.getId(), user.getName(), user.getPassword());
	}

	//user get
	public User get(String id)  {
		return this.jdbcTemplate.queryForInt("select * from TB_TOBI_USER where id = ?",
			new Object[] {id},
			this.userMapper);
	}
	
	//user Get all
	public List<User> getAll(){
		return this.jdbcTemplate.query("select * from TB_TOBI_USER order by id",
				this.userMapper);
	}
	
	//user delete All
	public void deleteAll() {
		this.jdbcTemplate.update("delete from TB_TOBI_USER");
	}
	
	//user Count get
	public int getCount() {
		return this.jdbcTemplate.queryForInt("select count(*) from TB_TOBI_USER");
	}


	
}
