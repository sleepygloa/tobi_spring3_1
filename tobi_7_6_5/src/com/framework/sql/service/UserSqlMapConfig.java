package com.framework.sql.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.framework.sql.interfaces.SqlMapConfig;
import com.tobi.user.interfaces.UserDao;

public class UserSqlMapConfig implements SqlMapConfig{

	@Override
	public Resource getSqlMapResource() {
		return new ClassPathResource("sqlmap.xml", UserDao.class);
	}
	

}
