package com.tobi.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.tobi.domain.Level;
import com.tobi.domain.User;

public class UserDaoJdbc implements UserDao {
	
	private JdbcTemplate jdbcTemplate;
	
	private RowMapper<User> userRowMapper = new RowMapper<User>() {
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
			user.setLevel(Level.valueOf(rs.getInt("level")));
			user.setLogin(rs.getInt("login"));
			user.setRecommend(rs.getInt("recommend"));
			user.setEmail(rs.getString("email"));
			return user;
		}
	};

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void add(User user) {
		this.jdbcTemplate.update("insert into TB_TOBI_USER(id, name, password, level, login, recommend, email) values (?, ?, ?, ?, ?, ?, ?)", user.getId(), user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommend(), user.getEmail());
	}

	@Override
	public User get(String id) {
		return this.jdbcTemplate.queryForObject("select * from TB_TOBI_USER where id = ?", new Object[]{id}, userRowMapper);
	}

	@Override
	public void deleteAll() {
		this.jdbcTemplate.update("delete from TB_TOBI_USER");
	}

	@Override
	public int getCount() {
		return this.jdbcTemplate.queryForInt("select count(*) from TB_TOBI_USER");
	}

	@Override
	public List<User> getAll() {
		return this.jdbcTemplate.query("select * from TB_TOBI_USER order by id", this.userRowMapper);
	}

	@Override
	public void update(User user) {
		this.jdbcTemplate.update("update TB_TOBI_USER set name = ?, password = ?, level = ?, login = ?, recommend = ? where id = ?", user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommend(), user.getId());
	}
}