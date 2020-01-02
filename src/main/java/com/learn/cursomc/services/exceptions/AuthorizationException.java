package com.learn.cursomc.services.exceptions;

public class AuthorizationException extends RuntimeException {
	private static final long serialVersionUID = - 5365203665246508092L;

	public AuthorizationException(String msg) {
		super(msg);
	}
	
	public AuthorizationException(String msg, Throwable cause) {
		super(msg, cause);
	}
}