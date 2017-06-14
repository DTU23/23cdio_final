package dk.dtu.model.exceptions.validation;

import dk.dtu.model.exceptions.ValidationException;

public class InvalidPasswordException extends ValidationException {

	private static final long serialVersionUID = -8955952815007378796L;
	public InvalidPasswordException(String msg) { super(msg); }
	public InvalidPasswordException(Exception e) { super(e); }

}
