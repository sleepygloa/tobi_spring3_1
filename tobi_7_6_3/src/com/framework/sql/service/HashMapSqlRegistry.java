package com.framework.sql.service;

import java.util.HashMap;
import java.util.Map;

import com.framework.exception.SqlNotFoundException;
import com.framework.sql.interfaces.SqlRegistry;

public class HashMapSqlRegistry implements SqlRegistry {
	private Map<String, String> sqlMap = new HashMap<String, String>();
	
	public String findSql(String key) throws SqlNotFoundException{
		String sql = sqlMap.get(key);
		if(sql == null) {
			throw new SqlNotFoundException();
		}else{
			return sql;
		}
	}
	
	
	public void registerSql(String key, String sql) {
		sqlMap.put(key, sql); 
	};
}
