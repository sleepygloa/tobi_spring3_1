package com.tobi.exception;

public class DuplicateUserIdException extends RuntimeException{
	public DuplicateUserIdException(Throwable cause) {
		super(cause);
	}
}
