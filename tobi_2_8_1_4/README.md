# 설명 

#### 이전 챕터
- StaticApplicationContext
> 을 이용하면 자바로 등록된 VO나 기타 파일을 빈으로 등록할 수 있다.

- genericApptlicationContext();
- GenericApplicationContext ac = new GenericApplicationContext();
- XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(ac);
- reader.loadBeanDefinitions("/resource/xml/genericApplicationContext.xml");
> 을 이용하여 xml이나 외부메타정보를 불러온다.

 - GenericApplicationContext ac = new GenericXmlApplicationContext("/resource/xml/genericApplicationContext.xml");
 > 을 이용하면 한번에 xml을 불러올수있다.

 - parentContext.xml, childContext.xml
 > 여러가지 메타데이터를 빈으로 등록 할 때, 다른 xml 파일에서 등록된 빈을 다른 xml 에서 참조할 수 있다
 
  #### 지금 챕터
  - web.xml 에 ContextLoaderListner 등록.
  > 어플리케이션 컨텍스트 클래스 : XmlWebApplicationContext
  > XML 설정 파일 위치 : /WEB-INF/applicationContext.xml
  
  - Default 를 설정하는 XML
  > contextConfigLocation, 리스너 등록
 <listner>
 	<listner-class>org.springframework.web.context.ContextLoaderListner</listner-class>
 </listner>
  
  > XML 설정 파일 위치를 초기화.
  <context-param>
  	<param-name>contextConfigLocation</param-name>
  	<param-value>
  		/WEB-INF/daoContext.xml
  		/WEB-INF/applicationContext.xml
  	</param-value>
  </context-param>
  > 어플리케이션 컨텍스트 클래스 초기화
<context-param>
  	<param-name>contextClass</param-name>
  	<param-value>
		org.springframework.web.context.support.AnnotationConfigWebApplicationContext
  	</param-value>
</context-param>

- web.xml 에 DispatcherServlet 등록
<servlet>
  	<servlet-name>spring</servlet-name>
  	<servlet-class>
		org.springframework.web.servlet.DispatcherServlet
  	</servlet-class>
  	<load-on-startup>1</load-on-startup>
</servlet>


- ContextLoaderListner, DispatcherServlet 한번에 등록
<servlet>
  	<servlet-name>spring</servlet-name>
  	<servlet-class>
		org.springframework.web.servlet.DispatcherServlet
  	</servlet-class>
  	<init-param>
	  	<param-name>contextConfigLocation</param-name>
	  	<param-value>
	  		/WEB-INF/applicationContext.xml
	  		/WEB-INF/spring-servlet.xml
	  	</param-value>
  	</init-param>
  	<load-on-startup>1</load-on-startup>
</servlet>
