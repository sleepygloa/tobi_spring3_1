package com.framework.sql.service;

import java.util.Map;

import com.framework.sql.interfaces.SqlService;

public class SimpleSqlService implements SqlService{

	private Map<String, String> sqlMap;

	public void setSqlMap(Map<String, String> sqlMap) {
		this.sqlMap = sqlMap;
	}

	public String getSql(String key) throws SqlRetrivalFailureException {
		String sql = sqlMap.get(key);
		if(sql == null) {
			throw new SqlRetrivalFailureException("No found SQL for the "+ key);
		}else {
			return sql;
		}
	}
	

}
