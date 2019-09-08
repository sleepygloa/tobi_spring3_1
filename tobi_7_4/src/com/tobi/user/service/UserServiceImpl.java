package com.tobi.user.service;

import java.util.List;

import org.springframework.mail.SimpleMailMessage;

import com.framework.util.mail.interfaces.MailSender;
import com.tobi.user.domain.Level;
import com.tobi.user.domain.User;
import com.tobi.user.interfaces.UserDao;
import com.tobi.user.interfaces.UserService;


public class UserServiceImpl implements UserService {

	public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
	public static final int MIN_RECOMMNED_FOR_GOLD = 30;

	//User transaction
	UserDao userDao;

	//spring Mailing
	private MailSender mailSender;
	
	//xml DI
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	
	
	
	
	//Business Logic
	//Add User
	@Override
	public void add(User user) {
		if(user.getLevel() == null) user.setLevel(Level.BASIC);
		userDao.add(user);
	}
	
	//User Level Upgrade
	@Override
	public void upgradeLevels() {
		List<User> users = userDao.getAll();
		for(User user : users) {
			if(canUpgradeLevel(user)) {
				upgradeLevel(user);
			}
		}
	}

	
	//User get
	public User get(String id) {
		return userDao.get(id);
	}
	//user All Get
	public List<User> getAll() {
		return userDao.getAll();
	}
	//User Delete
	public void deleteAll() {
		userDao.deleteAll();
	}
	//User update
	public void update(User user) {
		userDao.update(user);
	}
	
	
	
	
	/////////////////////////////////////////////////////////////////
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
	
}
