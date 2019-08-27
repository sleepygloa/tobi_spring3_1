package com.tobi.notuse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.tobi.dao.ConnectionMaker;

public class DConnectionMaker implements ConnectionMaker {
	
	//ConnectionMaker 의 makeConnection 메소드를 오버라이드 함.
	@Override
	public Connection makeConnection() throws ClassNotFoundException, SQLException {
		Class.forName("org.mariadb.jdbc.Driver");
		Connection c = DriverManager.getConnection("jdbc:mariadb://61.252.235.153:18081/seonhoblog?useUnicode=true&amp;characterEncoding=utf8", "test", "1234");
		return c;
	}

}
