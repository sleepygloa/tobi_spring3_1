package com.tobi.user.domain;

import java.util.List;

public class User {
	private String id;
	private String name;
	private String password;
	
	private static final int BASIC = 1;
	private static final int SILVER = 1;
	private static final int GOLD = 1;
	
	Level level;
	int login;
	int recommend;
	
	String mail;
	
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public User(String id, String name, String password, Level level, int login, int recommend) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.level = level;
		this.login = login;
		this.recommend = recommend;
	}
	
	public User() {
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Level getLevel() {
		return level;
	}
	
	public void setLevel(Level level) {
		this.level = level;
	}

	public int getLogin() {
		return login;
	}

	public void setLogin(int login) {
		this.login = login;
	}

	public int getRecommend() {
		return recommend;
	}

	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}
	
	public void upgradeLevel() {
		Level nextLevel = this.level.nextLevel();
		if(nextLevel == null) {
			throw new IllegalStateException(this.level + " is not possible to upgrade level");
		}else {
			this.level = nextLevel;
		}
	}
	
	
}
