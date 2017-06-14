package dk.dtu.model.exceptions.validation;

import dk.dtu.model.exceptions.ValidationException;

public class InvalidNameException extends ValidationException {
	
	private static final long serialVersionUID = -8981367995021596754L;
	public InvalidNameException(String msg) { super(msg); }
	public InvalidNameException(Exception e) { super(e); }

}
