### ����


1. User.java �� VO
2. UserDao.java �� Connection �� sql ����
3. 2���� Connection �κ��� �������� �и�
4. ��ü���� ������ Dao �� ���� �и�, (DUserDao.java, NUserDao.java)
--�߰�
5. Connection Interface �����Ͽ� ���յ��� ����(DB ������ ��� �������� ����, �Ҷ����� �������� �ʾƵ���)
6. DB Connection ������ �ٸ� ������ �ҷ��� ���� �����ϰ� ����.


�ֿ�ҽ�.
ConnectionMaker connectionMaker = new DConnectionMaker();
		
UserDao ud = new UserDao(connectionMaker);

------

	//Ŭ����
//	private SimpleConnectionMaker simpleConnectionMaker;
	
	//�������̽�
	private ConnectionMaker connecionMaker;
	
	public UserDao(ConnectionMaker connectionMaker) {
		this.connecionMaker = connectionMaker;
//		simpleConnectionMaker = new SimpleConnectionMaker;
	}
	
	//���� �߰�
	public void add(User user) throws ClassNotFoundException, SQLException {
//		Connection c = getConnection();
//		Connection c = simpleConnectionMaker.makeNewConnection(); //Ŭ����
		Connection c = connecionMaker.makeConnection(); //�������̽�


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
