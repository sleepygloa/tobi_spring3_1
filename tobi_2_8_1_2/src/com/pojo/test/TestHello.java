package com.pojo.test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.StaticApplicationContext;

import com.pojo.service.Hello;

public class TestHello {
	
	public void main() {
		StaticApplicationContext ac = new StaticApplicationContext();
		ac.registerSingleton("hello1", Hello.class);
		
		Hello hello1 = ac.getBean("hello1", Hello.class);
		assertThat(hello1, is(notNullValue()));
		
		BeanDefinition helloDef = new RootBeanDefinition(Hello.class);
		helloDef.getPropertyValues().addPropertyValue("name", "Spring");
		ac.registerBeanDefinition("hello2", helloDef);
		
		Hello hello2 = ac.getBean("hello2", Hello.class);
		assertThat(hello2.sayHello(), is("Hello Spring"));
		
		assertThat(hello1, is(not("hello2")));
		assertThat(ac.getBeanFactory().getBeanDefinitionCount(), is(2));
	}
	
//	@Test
	public void registerBeanWithDependency() {
		StaticApplicationContext ac = new StaticApplicationContext();
		ac.registerBeanDefinition("printer", new RootBeanDefinition(StringBuffer.class));
		
		BeanDefinition helloDef = new RootBeanDefinition(Hello.class);
		helloDef.getPropertyValues().addPropertyValue("name", "Spring");
		helloDef.getPropertyValues().addPropertyValue("printer", new RuntimeBeanReference("printer"));
	
		ac.registerBeanDefinition("hello", helloDef);
		
		Hello hello = ac.getBean("hello", Hello.class);
		hello.print();
		
		assertThat(ac.getBean("printer").toString(), is("Hello Spring"));
	}
	
	@Test
	public void genericApplicationContext() {
		GenericApplicationContext ac = new GenericApplicationContext();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(ac);
		reader.loadBeanDefinitions("/resource/xml/genericApplicationContext.xml");
		
		ac.refresh();
		
		Hello hello = ac.getBean("hello", Hello.class);
		hello.print();
		
		assertThat(ac.getBean("printer").toString(), is("Hello Spring"));
	}
//P43

}
