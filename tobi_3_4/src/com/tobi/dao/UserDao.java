package com.tobi.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.tobi.datasource.JdbcContext;
import com.tobi.domain.User;

public class UserDao {

	private DataSource dataSource;
	private JdbcContext jdbcContext;
	
	public void setDataSource(DataSource dataSource) {
		
		this.jdbcContext = new JdbcContext();
		
		this.jdbcContext.setDataSource(dataSource);
		
		this.dataSource = dataSource;
	}
	
	public void setJdbcContext(JdbcContext jdbcContext) {
		this.jdbcContext = jdbcContext;
	}
	
	//user add
	public void add(User user) throws SQLException {
		this.jdbcContext.workWithStatementStrategy( new StatementStrategy() {
			public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
				// TODO Auto-generated method stub
				PreparedStatement ps = c.prepareStatement("insert into TB_TOBI_USER (id, name, password) values (?, ?, ?)");
				ps.setString(1, user.getId());
				ps.setString(2, user.getName());
				ps.setString(3, user.getPassword());
				
				return ps;
			}
		});
	}

	//user get
	public User get(String id) throws ClassNotFoundException, SQLException {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = null;		
				
		try {
			c = dataSource.getConnection();

			ps = c.prepareStatement("select * from TB_TOBI_USER where id = ?");
			ps.setString(1, id);

			rs = ps.executeQuery();

			if(rs.next()) {
				user = new User();
				user.setId(rs.getString("id"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				
			}
				
		}catch(SQLException e) {
			
		}finally {
			if( rs != null ) try { rs.close(); }catch(SQLException e) {  }
			if( ps != null ) try { ps.close(); }catch(SQLException e) {  }
			if( c  != null ) try { c.close(); }catch(SQLException e) {  }
		}
		
//		if(user == null) throw new EmptyResultDataAccessException();
		
		return user;
	}
	
	//user delete All
	public void deleteAll() throws SQLException {
		this.jdbcContext.workWithStatementStrategy( new StatementStrategy() {
			public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
				PreparedStatement ps = c.prepareStatement("delete from TB_TOBI_USER");
				return ps;
			}
		});
	}
	

	//user Count get
	public int getCount() throws SQLException {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		int count = -1;
				
		try {
			c = dataSource.getConnection();

			ps = c.prepareStatement("select count(*) from TB_TOBI_USER");
			rs = ps.executeQuery();
			rs.next();
			count = rs.getInt(1);
				
		}catch(SQLException e) {
			
		}finally {
			if( rs != null ) try { rs.close(); }catch(SQLException e) {  }
			if( ps != null ) try { ps.close(); }catch(SQLException e) {  }
			if( c  != null ) try { c.close(); }catch(SQLException e) {  }
		}
		
		return count;
		
	}
	
	//CRUD, transaction 전략패턴
	public void jdbcContextWithStatementStrategy(StatementStrategy strat) throws SQLException{
		Connection c = null;
		PreparedStatement ps = null;
		
		try {
			c = dataSource.getConnection();

			ps = strat.makePreparedStatement(c);
			ps.executeUpdate();
				
		}catch(SQLException e) {
			
		}finally {
			if( ps != null ) try { ps.close(); }catch(SQLException e) {  }
			if( c  != null ) try { c.close(); }catch(SQLException e) {  }
		}
	}
	
}
