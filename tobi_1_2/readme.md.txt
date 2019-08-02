### 설명


1. User.java 의 VO
2. UserDao.java 의 Connection 및 sql 실행
-- 추가
3. 2번읜 Connection 부분을 공통으로 분리
4. 업체에서 접근할 Dao 를 따로 분리, (DUserDao.java, NUserDao.java)


주요소스.
	private Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(dbClass);
		return DriverManager.getConnection(dbUrl, "test", "1234");
	}

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
