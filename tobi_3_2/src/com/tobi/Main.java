package com.tobi;

import java.sql.SQLException;

import com.tobi.dao.UserDaoTest;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, Exception {

		UserDaoTest udt = new UserDaoTest();
		
		udt.testUserDaoDeleteAllExecute();
		udt.testUserDaoAddExecute();
		
	}


}
