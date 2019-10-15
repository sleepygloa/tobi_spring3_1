# 설명 

#### 이전 챕터
> 세션 스코프를 활용한 request .
-로그인 사용자의 정보를 HTTP 세션안에 유지하도록 세션스코프를 만듬
#### 지금 챕터
> 빈을 주입하는 여러가지 방법 중 생명주기를 관리

- 초기화 콜백 인터페이스(InitializingBean)

1.init-method 설정
빈설정 작업을 마친뒤 initResource()실행
<bean id="beanId" class="beanClass" init-method="initResource" />

init-method 를 선언하는 걸 까먹으면 안된다.

2. @PostContruct
토비가 가장 권장하는 방식이고 직관적이다.
클래스의 빈을 주입하는 단계가 framework가 아니고 패키지 단계이다면 자바에서
빈을 주입하게 될텐데 이방법을 쓰는게 적절할 듯 싶다. 스프링을 계속 쓴다면..

3. @Bean(init-method)


- 제거 메소드
1.destory-method
<bean id="beanId" class="beanClass" init-method="destroy-method" />
2.@PreDestory

3.@Bean(destroyMethod)


> 스프링 API에 종속 되는걸 주의하자.


> @ComponentScan

메서드나 변수 위에 @ComponentScan 을 선언하면 메서드 내의 스테레오 타입 애노테이션이 붙은 빈을 자동으로 찾아 등록해준다.
<context:component-scan> 과 같은 기능을 보여준다.

@ComponentScan(basePackage="myproject", excludeFilters=@Filter(Configuration.class))

@ComponentScan(basePackage="myproject", excludeFilters=@Filter(type=FilterType.ASSIGNALBLE_TYPE, 
value=AppConfig.class))

> @Import
> @ImportResource
> @EnableTransactionManagement