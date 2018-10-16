package com.tobi.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.tobi.domain.User;

public class UserDao {
	private JdbcTemplate jdbcTemplate;
	private RowMapper<User> userRowMapper = new RowMapper<User>() {
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
			return user;
		}
	};

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void add(User user) {
		this.jdbcTemplate.update("insert into TB_TOBI_USER (id, name, password) values (?, ?, ?)", user.getId(), user.getName(), user.getPassword());
	}

	public User get(String id) {
		return this.jdbcTemplate.queryForObject("select * from TB_TOBI_USER where id = ?", new Object[]{id}, userRowMapper);
	}

	public void deleteAll() {
		this.jdbcTemplate.update("delete from TB_TOBI_USER");
	}

	public int getCount() {
		return this.jdbcTemplate.queryForInt("select count(*) from TB_TOBI_USER");
	}

	public List<User> getAll() {
		return this.jdbcTemplate.query("select * from TB_TOBI_USER order by id", this.userRowMapper);
	}
}
