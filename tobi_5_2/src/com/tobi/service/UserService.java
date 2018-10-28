package com.tobi.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.tobi.dao.UserDao;
import com.tobi.domain.Level;
import com.tobi.domain.User;

public class UserService {

	public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
	public static final int MIN_RECOMMEND_FOR_GOLD = 30;
	private UserDao userDao;
	private DataSource dataSource;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	
	//1. Use Transaction Manager
	public void upgradeLevels() throws SQLException {
		//Manager of Transaction Sync.
		TransactionSynchronizationManager.initSynchronization();
		//DB Connection Init; 
		Connection connection = DataSourceUtils.getConnection(dataSource);
		connection.setAutoCommit(false);

		try {
			List<User> users = userDao.getAll();

			for (User user : users) {
				if (canUpgradeLevel(user)) {
					upgradeLevel(user);
				}
			}
			connection.commit();
		} catch (SQLException e) {
			connection.rollback();
			throw e;
		} finally {
			//Safety Close Transaction in Spring Utility.
			DataSourceUtils.releaseConnection(connection, dataSource);
			
			//Transaction Sync End.
			TransactionSynchronizationManager.unbindResource(this.dataSource);
			TransactionSynchronizationManager.clearSynchronization();
		}
		
		//2. Use DataSourceTransactionManager
		//3. JtaTransactionManager
//		PlatformTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
////	PlatformTransactionManager transactionManager = new JtaTransactionManager();
//	TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
//
//	try {
//		List<User> users = userDao.getAll();
//
//		for (User user : users) {
//			if (canUpgradeLevel(user)) {
//				upgradeLevel(user);
//			}
//		}
//		transactionManager.commit(status);
//	} catch (RuntimeException e) {
//		transactionManager.rollback(status);
//		throw e;
//	}
	}
	
	/**
	 * 신규유저는 BASIC
	 * */
	public void add(User user) {
		if (user.getLevel() == null) {
			user.setLevel(Level.BASIC);
		}

		userDao.add(user);
	}

	private boolean canUpgradeLevel(User user) {
		Level currentLevel = user.getLevel();

		switch (currentLevel) {
			case BASIC:
				return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER);
			case SILVER:
				return (user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD);
			case GOLD:
				return false;
			default:
				throw new IllegalArgumentException("Unknown Level: " + currentLevel);
		}
	}

	protected void upgradeLevel(User user) {
		if (user.getLevel() == Level.BASIC) user.setLevel(Level.SILVER);
		else if (user.getLevel() == Level.SILVER) user.setLevel(Level.GOLD);
		
		user.upgradeLevel();
		userDao.update(user);
	}
	
}
