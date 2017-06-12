package dk.dtu.model.exceptions.validation;

import dk.dtu.model.exceptions.ValidationException;

public class PositiveIntegerValidationException extends ValidationException {

	private static final long serialVersionUID = 822986729950818354L;
	public PositiveIntegerValidationException(String msg) { super(msg); }
	public PositiveIntegerValidationException(Exception e) { super(e); }
	
}
