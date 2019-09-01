package com.tobi.reflection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UppercaseHandler implements InvocationHandler {
	
	//타킷 오브젝트로 위임하기 위한 변수와 메서드.
	Object target;
	
	public UppercaseHandler(Object target) {
		this.target = target;
	}
	
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable{
		Object ret = (String)method.invoke(target, args);
		if(ret instanceof String && method.getName().startsWith("say")) {
			return ((String)ret).toUpperCase();
		}else {
			return ret;
		}
	}
	

}
