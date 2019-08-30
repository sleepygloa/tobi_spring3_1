package com.tobi.user.service;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.tobi.user.domain.User;
import com.tobi.user.interfaces.UserService;

public class UserServiceTx implements UserService{
	
	//DI 받음
	UserService userService;
	private PlatformTransactionManager transactionManager;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	//DI 받은 오브젝트에 기능을 위임.
	@Override
	public void add(User user) {
		userService.add(user);
	}
	
	@Override
	public void upgradeLevels() {
		TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
		
		try {
			
			userService.upgradeLevels();
			this.transactionManager.commit(status);
			
		}catch(RuntimeException e) {
			this.transactionManager.rollback(status);
			throw e;
		}
	}

}
