# 설명 


- AOP 란 무엇인가?
 1. 트랜젝션 서비스 추상화
  - 특정 트랜젝션 기술의 종속화를 구체적인 방법와 서버환경에 유동적으로 DI 시킴
 2. 프록시와 데코레이터 패턴
  - 비즈니스 로직 과 트랜젝션 코드에서의 분리
 3. 다이내믹 프록시와 프록시 팩토리 빈
  - 다이나믹하게 비즈니스로직에 접근함으로써, 비즈니스 로직당 DI하던 트랜젝션 코드를 추상화함.
 4. 자동 프록시 생성과 포인트컷
  - 스프링 빈 생성 이후의 후처리 방법
  - 트랜젝션을 DI 할 전략을 설정
 5. 부가기능 모듈화
  - 부가기능의 자료를 한데 모음으로써 프로그램파일들 마다 종속적이지 않고, 따로 유연하게 관리가능하다.
 6. AOP : 애스펙트 지향 프로그래밍t
  - OOP와 햇갈릴 수 잇지만, 프레임워크나 애플리케이션 측면에서 면단위로 기능을 유연하게 부가할 수 있다는 점에서 말하는 방법이다. 
 
