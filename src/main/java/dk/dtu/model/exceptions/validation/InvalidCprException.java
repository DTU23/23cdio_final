package dk.dtu.model.exceptions.validation;

import dk.dtu.model.exceptions.ValidationException;

public class InvalidCprException extends ValidationException {
	
	private static final long serialVersionUID = -7887401412654771723L;
	public InvalidCprException(String msg) { super(msg); }
	public InvalidCprException(Exception e) { super(e); }
	public InvalidCprException(String msg, Exception e) { super(msg, e); }

}
