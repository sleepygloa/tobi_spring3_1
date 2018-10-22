package com.tobi.context;

import com.tobi.dao.AddStatement;
import com.tobi.dao.DeleteAllStatement;
import com.tobi.dao.StatementStrategy;
import com.tobi.domain.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcContext {

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * jdbcContextWithStatementStrategy �� deleteAll �� �썝�옒 �븯�굹���쑝�굹
	 * jdbc~ �쓽 connection 遺�遺꾩쓣 �뵲濡� , deleteAll 遺�遺꾩쓽 �룆由쎌쟻�씤 遺�遺꾩쓣 鍮쇱꽌
	 * 異뷀썑 以묐났�릺�뒗 遺�遺꾩쓣 怨듯넻�쑝濡� �벐湲� �쐞�빐 遺꾨━ �떆�궓 肄붾뱶
	 * */
	public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException {
		Connection c = null;
		PreparedStatement ps = null;

		try {
			c = dataSource.getConnection();
			ps = stmt.makePreparedStatement(c);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			if (ps != null) { try { ps.close(); } catch (SQLException e) { } }
			if (c != null) { try { c.close(); } catch (SQLException e) { } }
		}
	}


	public void deleteAll() throws SQLException {
		StatementStrategy st = new DeleteAllStatement();
		jdbcContextWithStatementStrategy(st);
	}

	public void add(User user) throws SQLException {
		StatementStrategy st = new AddStatement(user);
		jdbcContextWithStatementStrategy(st);
	}

}
