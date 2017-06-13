package dk.dtu.model.exceptions.validation;

import dk.dtu.model.exceptions.ValidationException;

public class InvalidInitialsException extends ValidationException {

	private static final long serialVersionUID = 6088089058936459666L;
	public InvalidInitialsException(String msg) { super(msg); }
	public InvalidInitialsException(Exception e) { super(e); }
	
}
