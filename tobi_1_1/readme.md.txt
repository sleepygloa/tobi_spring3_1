### ����


1. User.java �� VO
2. UserDao.java �� Connection �� sql ����

�ֿ�ҽ�.
Class.forName("org.mariadb.jdbc.Driver");
Connection c = DriverManager.getConnection("URL");

PreparedStatement ps = c.prepareStatement("insert into TB_TOBI_USER (id, name, password) values (?, ?, ?)");
ps.setString(1, user.getId());
ps.setString(2, user.getName());
ps.setString(3, user.getPassword());

ps.executeUpdate();

ps.close();
c.close();

### ���� ��� 
Main.java ����

��� : 
====================
���ο� ���� �Է�
���� �Է� ����
====================
====================
�Է��� �������� ȣ��
user id -->  1
user name --> root
user password --> password
�������� ȣ�� ����
====================
