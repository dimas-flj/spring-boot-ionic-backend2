package com.learn.cursomc.services.exceptions;

public class FileException extends RuntimeException {
	private static final long serialVersionUID = - 5365203665246508092L;
	
	public FileException(String msg) {
		super(msg);
	}
	
	public FileException(String msg, Throwable cause) {
		super(msg, cause);
	}
}