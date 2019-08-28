package com.tobi.datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.tobi.dao.StatementStrategy;

public class JdbcContext {

	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void workWithStatementStrategy(StatementStrategy strat) throws SQLException {
		Connection c = null;
		PreparedStatement ps = null;
		
		try {
			c = this.dataSource.getConnection();

			ps = strat.makePreparedStatement(c);
			ps.executeUpdate();
			
		}catch(SQLException e) {
			
		}finally {
			if( ps != null ) try { ps.close(); }catch(SQLException e) {  }
			if( c  != null ) try { c.close(); }catch(SQLException e) {  }
		}
		
	}
	
}
