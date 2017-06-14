package dk.dtu.model.exceptions.validation;

import dk.dtu.model.exceptions.ValidationException;

public class InvalidIDException extends ValidationException {
	
	private static final long serialVersionUID = 4599156264515158601L;
	public InvalidIDException(String msg) { super(msg); }
	public InvalidIDException(Exception e) { super(e); }

}
