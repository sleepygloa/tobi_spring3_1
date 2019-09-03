package com.framework.sql.interfaces;

public interface SqlService {
	String getSql(String key) throws SqlRetrivalFailureException;
	
	public class SqlRetrivalFailureException extends RuntimeException {
		public SqlRetrivalFailureException (String message) {
			super(message);
		}
		
		public SqlRetrivalFailureException (String message, Throwable cause) {
			super(message, cause);
		}

	}
}
