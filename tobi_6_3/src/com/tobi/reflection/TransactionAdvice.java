package com.tobi.reflection;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class TransactionAdvice implements MethodInterceptor	 {

	PlatformTransactionManager transactionManager;
	
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	//Advice 를 이용해 트랜젝션 기능을 동적으로 사용
	//또한 메서드를 콜백을 이용하여 호출
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
		
		try {
			
			Object ret = invocation.proceed();
			this.transactionManager.commit(status);
			return ret;
			
		}catch(RuntimeException e){
			this.transactionManager.rollback(status);
			throw e;
		}
	}

	
	
}
