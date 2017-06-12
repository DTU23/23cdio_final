package dk.dtu.model.exceptions.validation;

import dk.dtu.model.exceptions.ValidationException;

public class InvalidStatusException extends ValidationException {

	private static final long serialVersionUID = 4711703462831627724L;
	public InvalidStatusException(String msg) { super(msg); }
	public InvalidStatusException(Exception e) { super(e); }

}
