package dk.dtu.model.exceptions.validation;

import dk.dtu.model.exceptions.ValidationException;

public class InvalidRoleException extends ValidationException {

	private static final long serialVersionUID = -7044921556932626614L;
	public InvalidRoleException(String msg) { super(msg); }
	public InvalidRoleException(Exception e) { super(e); }

}
