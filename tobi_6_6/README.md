# 설명 

- xml 내  AOP와 TX 를 이용, 어드바이스와 포인트컷으로 어떤 클래스와 메서드의 에서 트랜젝션이 일어날때 속성을 제어하는 기능을 테스트
- 그러나 잘 안됨.

- UserServiceTest 메서의 TestUserService 를 빈으로 등록하고 getAll, get* 로 시작하는 메서드를 읽기 전용으로 바꾸고 그안에서 update를 쳤지만 에러가 나지 않고 잘됨
