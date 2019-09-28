# 설명 

#### 이전 챕터
- StaticApplicationContext
> 을 이용하면 자바로 등록된 VO나 기타 파일을 빈으로 등록할 수 있다.

#### 지금 챕터
- genericApptlicationContext();
- GenericApplicationContext ac = new GenericApplicationContext();
- XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(ac);
- reader.loadBeanDefinitions("/resource/xml/genericApplicationContext.xml");
> 을 이용하여 xml이나 외부메타정보를 불러온다.

 - GenericApplicationContext ac = new GenericXmlApplicationContext("/resource/xml/genericApplicationContext.xml");
 > 을 이용하면 한번에 xml을 불러올수있다.
