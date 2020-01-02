package com.learn.cursomc.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {
	private static final long serialVersionUID = - 783199078281021663L;
	
	private List<FieldMessage> errors = new ArrayList<FieldMessage>();
	
	public ValidationError(String timestamp, Integer status, String error, String message, String path) {
		super(timestamp, status, error, message, path);
	}
	
	public List<FieldMessage> getErrors() {
		return errors;
	}
	
	public void addError(String fieldName, String message) {
		this.errors.add(new FieldMessage(fieldName, message));
	}
	
	public void addError(FieldMessage obj) {
		this.errors.add(obj);
	}
}
