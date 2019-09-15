package com.framework.proxy.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.junit.Test;

import com.framework.proxy.HelloTarget;
import com.framework.proxy.HelloUppercase;
import com.framework.proxy.UppercaseHandler;
import com.framework.proxy.interfaces.Hello;

public class ReflectionTest {

	@Test
	public void invokeMethod() throws Exception{
		String name = "Spring";
		
		//length()
		assertThat(name.length(), is(6));
		
		Method lengthMethod = String.class.getMethod("length");
		assertThat((Integer)lengthMethod.invoke(name), is(6));
		
		//chatAt()
		assertThat(name.charAt(0), is('S'));
		
		Method charAtMethod = String.class.getMethod("charAt", int.class);
		assertThat((Character)charAtMethod.invoke(name,  0), is('S'));
	}
	
	//프록시 패턴 적용 테스트
	@Test
	public void simpeProxy() {
		Hello hello = new HelloTarget();
		assertThat(hello.sayHello("Toby"), is("Hello Toby"));
		assertThat(hello.sayHi("Toby"), is("Hi Toby"));
		assertThat(hello.sayThankYou("Toby"), is("Thank You Toby"));
		
		Hello proxiedHello = new HelloUppercase(new HelloTarget());
		assertThat(proxiedHello.sayHello("Toby"), is("HELLO TOBY"));
		assertThat(proxiedHello.sayHi("Toby"), is("HI TOBY"));
		assertThat(proxiedHello.sayThankYou("Toby"), is("THANK YOU TOBY"));
		
		Hello proxiedHello2 = (Hello)Proxy.newProxyInstance(
				getClass().getClassLoader(),
				new Class[] { Hello.class },
				new UppercaseHandler(new HelloTarget())
				);
		assertThat(proxiedHello2.sayHello("Toby"), is("HELLO TOBY"));
		assertThat(proxiedHello2.sayHi("Toby"), is("HI TOBY"));
		assertThat(proxiedHello2.sayThankYou("Toby"), is("THANK YOU TOBY"));
				
		
		
		
	}
}
