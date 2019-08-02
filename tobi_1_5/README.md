### 설명
팩토리 - 객체의 생성방법을 결정하고 만들어진 오브젝트를 돌려주는 기능을 하는 것.

1. User.java 의 VO
2. UserDao.java 의 Connection 및 sql 실행
3. 2번읜 Connection 부분을 공통으로 분리
4. 업체에서 접근할 Dao 를 따로 분리, (DUserDao.java, NUserDao.java)
5. Connection Interface 구현하여 결합도를 낮춤(DB 정보가 없어도 에러나지 않음, 할때마다 변경하지 않아도됨)
6. DB Connection 정보를 다른 곳에서 불러와 실행 가능하게 변경.
1-4. DaoFactory 생성, 함으로 userDao 를 수정할 필요가 없다(중요한 로직, 수정 하면 안되는 로직등)
1-5. Ioc, @Configuration, @Bean 등록




주요소스.

### 실행 방법
Main.java 실행

결과 :
====================
새로운 유져 입력
유져 입력 성공
====================
====================
입력한 유져정보 호출
user id -->  1
user name --> root
user password --> password
유져정보 호출 성공
====================
