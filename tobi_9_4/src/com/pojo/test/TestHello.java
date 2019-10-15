package com.pojo.test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import com.pojo.interfaces.Printer;
import com.pojo.service.AnnotatedHello;
import com.pojo.service.AnnotatedHelloConfig;
import com.pojo.service.Hello;
import com.pojo.service.StringPrinter;

public class TestHello {
	
	//StaticApplicationContext  : java
	//GenericApplicationContext : xml, 외부 메타
	
	//9.1.1
//	@Test
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
	
	
	//9.1.2
	/**
	 * 
	 */
//	@Test
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
	
	//9.1.2
//	@Test
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

	
	//9.1.3
	String basePath = StringUtils.cleanPath(ClassUtils.classPackageAsResourcePath(getClass()))+"/";
	
//	@Test
	public void  dd() {
		ApplicationContext parent = new GenericXmlApplicationContext(basePath + "xml/parentContext.xml");
		
		GenericApplicationContext child = new GenericApplicationContext(parent);
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(child);
		reader.loadBeanDefinitions(basePath + "xml/childContext.xml");
		child.refresh();
		
		Printer printer = child.getBean("printer", Printer.class);
		assertThat(printer, is(notNullValue()));
		
		Hello hello = child.getBean("hello", Hello.class);
		assertThat(hello, is(notNullValue()));
		
		hello.print();
		assertThat(printer.toString(), is("Hello Child"));
	}

	
	//9.2
//	@Test
	public void simpleBeanScanning() {
		//패키지 안(com.pojo.service)안에서 @Component 를 찾는다.
		ApplicationContext ctx = new AnnotationConfigApplicationContext("com.pojo.service");
		AnnotatedHello hello = ctx.getBean("annotatedHello", AnnotatedHello.class);
		
		assertThat(hello, is(notNullValue()));
	}
	
	//2.8.2
//	@Test
	public void simpleBeanScannings() {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(AnnotatedHelloConfig.class);
		AnnotatedHello hello = ctx.getBean("annotatedHello", AnnotatedHello.class);
		assertThat(hello, is(notNullValue()));
		
		AnnotatedHelloConfig config = ctx.getBean("annotatedHelloConfig", AnnotatedHelloConfig.class);
		assertThat(config, is(notNullValue()));
	}
	
	//9.3 싱글톤 빈 테스트, 1개의 오브젝트를 계속 사용한다.
//	@Test
	public void singletonScope() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(SingletonBean.class, SingletonClientBean.class);
		Set<SingletonBean> beans = new HashSet<SingletonBean>();
		beans.add(ac.getBean(SingletonBean.class));
		beans.add(ac.getBean(SingletonBean.class));
		assertThat(beans.size(), is(1));
	}
	static class SingletonBean{}
	static class SingletonClientBean{
		@Autowired SingletonBean bean1;
		@Autowired SingletonBean bean2;
	}
	
	//9.3 프로토타입빈 테스트, 매번 신규 오브젝트가 만들어진다.
	@Test
	public void prototypeScope() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class, PrototypeClientBean.class);
		Set<PrototypeBean> beans = new HashSet<PrototypeBean>();
		beans.add(ac.getBean(PrototypeBean.class));
		assertThat(beans.size(), is(1));
		beans.add(ac.getBean(PrototypeBean.class));
		assertThat(beans.size(), is(2));
		
		beans.add(ac.getBean(PrototypeClientBean.class).bean1);
		assertThat(beans.size(), is(3));
		beans.add(ac.getBean(PrototypeClientBean.class).bean2);
		assertThat(beans.size(), is(4));
	}
	@Scope("prototype")
	static class PrototypeBean{}
	static class PrototypeClientBean{
		@Autowired PrototypeBean bean1;
		@Autowired PrototypeBean bean2;
	}
	
	
	
	
}
