package com.tobi.user.dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.framework.sql.interfaces.SqlService;
import com.tobi.user.domain.Level;
import com.tobi.user.domain.User;
import com.tobi.user.interfaces.UserDao;

//@Component
@Repository
public class UserDaoJdbc implements UserDao{

	//신규
	private Map<String, String> sqlMap;
	
	public void setSqlMap(Map<String, String> sqlMap) {
		this.sqlMap = sqlMap;
	}
	
	private SqlService sqlService;
	
	@Autowired
	public void setSqlService(SqlService sqlService) {
		this.sqlService = sqlService;
	}

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);	
//		this.dataSource = dadtaSource;
	}
	
	private RowMapper<User> userMapper = 
			new RowMapper<User>() {
				public User mapRow(ResultSet rs, int rowNum) throws SQLException{
					User user = new User();
					user.setId(rs.getString("id"));
					user.setName(rs.getString("name"));
					user.setPassword(rs.getString("password"));
					user.setLevel(Level.valueOf(rs.getInt("level")));
					user.setLogin(rs.getInt("login"));
					user.setRecommend(rs.getInt("recommend"));
					return user;
				}
	};
	
	//user add
	@Transactional
	public void add(User user) {
		this.jdbcTemplate.update(this.sqlService.getSql("userAdd"), user.getId(), user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommend());
	}
	
	//user update.
	@Transactional
	public void update(User user) {
		this.jdbcTemplate.update(this.sqlService.getSql("userUpdate"), user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommend(), user.getId());
	}

	//user get
	@Transactional
	public User get(String id)  {
		return this.jdbcTemplate.queryForObject(this.sqlService.getSql("userGet"), new Object[] {id}, this.userMapper);
	}
	
	//user Get all
	@Transactional
	public List<User> getAll(){
		return this.jdbcTemplate.query(this.sqlService.getSql("userGetAll"), this.userMapper);
	}
	
	//user delete All
	@Transactional
	public void deleteAll() {
		this.jdbcTemplate.update(this.sqlService.getSql("userDeleteAll"));
	}
	
	//user Count get
	@Transactional
	public int getCount() {
		return this.jdbcTemplate.queryForInt(this.sqlService.getSql("userGetCount"));
	}


	
}
