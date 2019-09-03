package com.framework.proxy.interfaces;

public interface FactoryBean<T> {
		 T getObject() throws Exception;
		 Class<? extends T> getObjectType();
		 boolean isSingleton();

}
