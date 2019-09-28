package com.pojo.test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.context.support.StaticApplicationContext;

import com.pojo.service.Hello;
import com.pojo.service.StringPrinter;

public class TestHello {
	
	//StaticApplicationContext  : java
	//GenericApplicationContext : xml, 외부 메타
	
	@Test
	public void main() {
		/**
		 * 1
		 * */
		//IOC 컨테이너 생성 및 동작
		StaticApplicationContext ac = new StaticApplicationContext();
		//싱글톤 빈 등록.
		ac.registerSingleton("hello1", Hello.class);
		
		//확인.(hello1 으로 등록됬는지)
		Hello hello1 = ac.getBean("hello1", Hello.class);
		assertThat(hello1, is(notNullValue()));
		
		/**
		 * 2
		 * */
		//비어있는 메타정보의 빈을 생성.
		BeanDefinition helloDef = new RootBeanDefinition(Hello.class);
		//빈의 name과 value 지정<property name="name" value="Spring" />
		helloDef.getPropertyValues().addPropertyValue("name", "Spring");
		//<bean id="hello2" class="..." >바로위의 메타정보</bean>
		ac.registerBeanDefinition("hello2", helloDef);
		
		//확인.(hello2 빈이 등록됬는지 확인
		Hello hello2 = ac.getBean("hello2", Hello.class);
		//확인.(hello2 의 property 등록되었는지 확인)
		assertThat(hello2.sayHello(), is("Hello Spring"));
		//확인.(hello1과 hello2 는 다른 빈으로 등록되었다)
		assertThat(hello1, is(not("hello2")));
		//확인.(빈의 개수)
		assertThat(ac.getBeanFactory().getBeanDefinitionCount(), is(2));
	}
	
	//DI 정보 테스트
	/**
	 * 
	 */
	@Test
	public void registerBeanWithDependency() {
		StaticApplicationContext ac = new StaticApplicationContext();
		ac.registerBeanDefinition("printer", new RootBeanDefinition(StringPrinter.class));
		
		BeanDefinition helloDef = new RootBeanDefinition(Hello.class);
		helloDef.getPropertyValues().addPropertyValue("name", "Spring");
		//<property name="printer" ref="printer" />
		helloDef.getPropertyValues().addPropertyValue("printer", new RuntimeBeanReference("printer"));
	
		//빈등록
		ac.registerBeanDefinition("hello", helloDef);
		
		//빈 조회, print 호가인
		Hello hello = ac.getBean("hello", Hello.class);
		hello.print();
		
		assertThat(ac.getBean("printer").toString(), is("Hello Spring"));
	}
	
	@Test
	public void genericApptlicationContext() {
//		//첫번재 방법
//		GenericApplicationContext ac = new GenericApplicationContext();
//		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(ac);
//		reader.loadBeanDefinitions("/resource/xml/genericApplicationContext.xml");
//		
//		//메타정보 초기화(등록 완료후 선언)
//		ac.refresh();
//		//첫번째 방법 끝
		
		//두번째 방법
		GenericApplicationContext ac = new GenericXmlApplicationContext("/resource/xml/genericApplicationContext.xml");
		//두번째 방법 끝
		
		Hello hello = ac.getBean("hello", Hello.class);
		hello.print();
		
		assertThat(ac.getBean("printer").toString(), is("Hello Spring"));
	}
//P43

}
