package com.tobi.user.service;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.framework.interfaces.MailSender;
import com.tobi.user.domain.Level;
import com.tobi.user.domain.User;
import com.tobi.user.interfaces.UserDao;


public class UserService {

	UserDao userDao;
	
	public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
	public static final int MIN_RECOMMNED_FOR_GOLD = 30;
	
	private DataSource dataSource;
	
	private PlatformTransactionManager transactionManager;
	
	private MailSender mailSender;
	
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public void add(User user) {
		if(user.getLevel() == null) user.setLevel(Level.BASIC);
		userDao.add(user);
	}
	
	public void upgradeLevels() throws Exception {
		TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
		
		try {
			
			List<User> users = userDao.getAll();
			for(User user : users) {
				if(canUpgradeLevel(user)) {
					upgradeLevel(user);
				}
			}
			
			this.transactionManager.commit(status);
		}catch(RuntimeException e) {
			this.transactionManager.rollback(status);
			throw e;
		}
	}
	
	protected void upgradeLevel(User user) {
		user.upgradeLevel();
		userDao.update(user);
//		sendUpgradeEMail(user);
	}
	
	private boolean canUpgradeLevel(User user) {
		Level currentLevel = user.getLevel();
		switch(currentLevel) {
			case BASIC : return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER);
			case SILVER : return (user.getRecommend() >= MIN_RECOMMNED_FOR_GOLD);
			case GOLD : return false;
			default: throw new IllegalArgumentException("Unknown Level :  " + currentLevel);
		}
	}
	
	
	//스프링을 이용한 메일 보내기
	private void sendUpgradeEMail(User user) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(user.getMail());
		mailMessage.setFrom("sleepygloa@gmail.com");
		mailMessage.setSubject("Notice : Upgrade");
		mailMessage.setText("Your grade is upgraded : " + user.getLevel().name() );
		
		this.mailSender.send(mailMessage);
	}
	
	//자바를 이용한 메일 보내기
//	private void sendUpgradeEMail(User user) {
//		Properties props = new Properties();
//		props.put("mail.smtp.host", "mail.ksug.org");
//		Session s = Session.getInstance(props, null);
//		
//		MimeMessage message = new MimeMessage(s);
//		try {
//			message.setFrom(new InternetAddress("sleepygloa@gmail.com"));
//			message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getMail()));
//			message.setSubject("Notice : Upgrade");
//			message.setText("Your grade is upgraded : " + user.getLevel().name() );
//			
//			Transport.send(message);
//		}catch(AddressException e) {
//			throw new RuntimeException(e);
//		}catch(MessagingException e) {
//			throw new RuntimeException(e);
//		}catch(UnsupportedEncodingException e) {
//			throw new RuntimeException(e);
//		}
//	}
}
