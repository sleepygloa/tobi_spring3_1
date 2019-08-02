### 설명


1. User.java 의 VO
2. UserDao.java 의 Connection 및 sql 실행

주요소스.
Class.forName("org.mariadb.jdbc.Driver");
Connection c = DriverManager.getConnection("URL");

PreparedStatement ps = c.prepareStatement("insert into TB_TOBI_USER (id, name, password) values (?, ?, ?)");
ps.setString(1, user.getId());
ps.setString(2, user.getName());
ps.setString(3, user.getPassword());

ps.executeUpdate();

ps.close();
c.close();

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
