package dk.dtu.model.exceptions.validation;

import dk.dtu.model.exceptions.ValidationException;

public class NotLettersException extends ValidationException {

	private static final long serialVersionUID = 1297818129608670116L;
	public NotLettersException(String msg) { super(msg); }
	public NotLettersException(Exception e) { super(e); }

}
