package com.nelioalves.cursomc.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError{
	private static final long serialVersionUID = 1L;

	private List<FieldMessage> errors = new ArrayList<>();
	
	public ValidationError(Integer status, String msg, Long timeStamp) {
		super(status, msg, timeStamp);
		// TODO Auto-generated constructor stub
	}

	public List<FieldMessage> getError() {
		return errors;
	}
	
	//adiciona erro por erro na lista
	public void addErro(String fieldName, String message) {
		errors.add(new FieldMessage(fieldName, message));
	}	
}
