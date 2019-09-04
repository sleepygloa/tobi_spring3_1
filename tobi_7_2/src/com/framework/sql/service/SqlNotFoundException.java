package com.framework.sql.service;

public class SqlNotFoundException extends RuntimeException {

	private String name;
	
	public SqlNotFoundException() {}
	
	public SqlNotFoundException(String name) {
		this.name = name;
	}
}
