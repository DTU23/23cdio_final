package dk.dtu.model.exceptions.validation;

import dk.dtu.model.exceptions.ValidationException;

public class PositiveDoubleValidationException extends ValidationException {
	
	private static final long serialVersionUID = -8291283821546196646L;
	public PositiveDoubleValidationException(String msg) { super(msg); }
	public PositiveDoubleValidationException(Exception e) { super(e); }

}
