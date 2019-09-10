package com.framework.sql.service;

import javax.annotation.PostConstruct;

import com.framework.exception.SqlNotFoundException;
import com.framework.sql.interfaces.SqlReader;
import com.framework.sql.interfaces.SqlRegistry;
import com.framework.sql.interfaces.SqlService;

public class BaseSqlService implements SqlService{
	
	protected SqlReader sqlReader;
	protected SqlRegistry sqlRegistry;
	
	public void setSqlReader(SqlReader sqlReader) {
		this.sqlReader = sqlReader;
	}
	public void setSqlRegistry(SqlRegistry sqlRegistry) {
		this.sqlRegistry = sqlRegistry;
	}
	
	@PostConstruct
	public  void loadSql() {
		this.sqlReader.read(this.sqlRegistry);
	}
	
	public String getSql (String key) throws SqlRetrivalFailureException{
		try {
			return this.sqlRegistry.findSql(key);
		}catch(SqlNotFoundException e) {
			throw new SqlRetrivalFailureException(e);
		}
	}

}
