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
		Connection c = null;
		PreparedStatement ps = null;
		
		try {
			c.prepareStatement("insert into TB_TOBI_USER (id, name, password) values (?, ?, ?)");
			ps.setString(1, user.getId());
			ps.setString(2, user.getName());
			ps.setString(3, user.getPassword());

			ps.executeUpdate();
			
				
		}catch(SQLException e) {
			
		}finally {
			if( ps != null ) try { ps.close(); }catch(SQLException e) {  }
			if( c  != null ) try { c.close(); }catch(SQLException e) {  }
		}
		
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
	
	public void deleteAll() throws ClassNotFoundException, SQLException {
		StatementStrategy strat = new DeleteAllStratement();
		jdbcContextWithStatementStrategy(strat);
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
