package com.framework.exception;

public class SqlUpdateFailureException extends RuntimeException {
	
	private String name;
	
	public SqlUpdateFailureException() {}
	
	public SqlUpdateFailureException(String name) {
		this.name = name;
	}

}
