package com.framework.sql.interfaces;

import com.framework.exception.SqlNotFoundException;

public interface SqlRegistry {
	void registerSql(String key, String sql);
	
//	String findSql(String key) throws RuntimeException;
	String findSql(String key) throws SqlNotFoundException;
	
	
}
