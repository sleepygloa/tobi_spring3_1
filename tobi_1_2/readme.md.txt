### ����


1. User.java �� VO
2. UserDao.java �� Connection �� sql ����
-- �߰�
3. 2���� Connection �κ��� �������� �и�
4. ��ü���� ������ Dao �� ���� �и�, (DUserDao.java, NUserDao.java)


�ֿ�ҽ�.
	private Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(dbClass);
		return DriverManager.getConnection(dbUrl, "test", "1234");
	}

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
