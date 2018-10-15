package com.tobi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.tobi.domain.User;

public abstract class UserDao {

	public DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void execute() throws SQLException {
		Connection c = null;
		PreparedStatement ps = null;

		try {
			c = dataSource.getConnection();
			ps = makeStatement(c);
		} catch (SQLException e) {
			throw e;
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
				}
			}

			if (c != null) {
				try {
					c.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	protected abstract PreparedStatement makeStatement(Connection c) throws SQLException;
	
//
//	/**
//	 * ��� ����� ���� ���� 1 -> 2 ���·� ��ȯ
//	 * 2�� �ҽ��� add�� getId
//	 * 3�� �ҽ��� deleteAll�� getCount
//	 * ���� ó�� �߰�
//	 * */
//
//	//��������� �߰�
//	public void add(User user) throws ClassNotFoundException, SQLException {
//		Connection c = null;
//		PreparedStatement ps = null;
//
//		try {
//			c = dataSource.getConnection();
//			ps = c.prepareStatement("insert into TB_TOBI_USER (id, name, password) values (?, ?, ?)");
//
//			ps.setString(1, user.getId());
//			ps.setString(2, user.getName());
//			ps.setString(3, user.getPassword());
//
//			ps.executeUpdate();
//		} catch (SQLException e) {
//			throw e;
//		} finally {
//			if (ps != null) { try { ps.close();	} catch (SQLException e) { } }
//			if (c != null) { try { c.close(); } catch (SQLException e) { } }
//		}
//
//	}
//
//	//��������� ��ȯ
//	public User get(String id) throws ClassNotFoundException, SQLException {
//		Connection c = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//
//		try {
//			c = dataSource.getConnection();
//			ps = c.prepareStatement("select * from TB_TOBI_USER where id = " + id);
//
//			rs = ps.executeQuery();
//			rs.next();
//
//			User user = new User();
//			user.setId(rs.getString("id"));
//			user.setName(rs.getString("name"));
//			user.setPassword(rs.getString("password"));
//
//			return user;
//		} catch (SQLException e) {
//			throw e;
//		} finally {
//			if (rs != null) { try { rs.close();	} catch (SQLException e) { } }
//			if (ps != null) { try { ps.close();	} catch (SQLException e) { } }
//			if (c != null) { try { c.close(); } catch (SQLException e) { } }
//		}
//
//
//	}
//
//	//��� ����� ���� ���� 1
////	public void deleteAll() throws SQLException {
////		Connection c = dataSource.getConnection();
////
////		PreparedStatement ps = c.prepareStatement("delete from users");
////		ps.executeUpdate();
////
////		ps.close();
////		c.close();
////	}
//
//	//������� ���� ���� 2
//	public void deleteAll() throws SQLException {
//		Connection c = null;
//		PreparedStatement ps = null;
//
//		try {
//			c = dataSource.getConnection();
//			ps = c.prepareStatement("delete from TB_TOBI_USER");
//			ps.executeUpdate();
//		} catch (SQLException e) {
//			throw e;
//		} finally {
//			if (ps != null) { try { ps.close();	} catch (SQLException e) { } }
//			if (c != null) { try { c.close(); } catch (SQLException e) { } }
//		}
//	}
//
//	//��������� �� ��ȯ
//	public int getCount() throws SQLException {
//		Connection c = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//
//		try {
//			c = dataSource.getConnection();
//			ps = c.prepareStatement("select count(*) from TB_TOBI_USER");
//			rs = ps.executeQuery();
//			rs.next();
//
//			return rs.getInt(1);
//		} catch (SQLException e) {
//			throw e;
//		} finally {
//			if (rs != null) { try {	rs.close();	} catch (SQLException e) { } }
//			if (ps != null) { try {	ps.close();	} catch (SQLException e) { } }
//			if (c != null) { try { c.close(); } catch (SQLException e) { } }
//		}
//	}

}
