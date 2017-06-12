package dk.dtu.model.exceptions.dal;

import dk.dtu.model.exceptions.DALException;

public class IntegrityConstraintViolationException extends DALException {

	private static final long serialVersionUID = -435060687965724743L;
	public IntegrityConstraintViolationException(String msg) { super(msg); }
	public IntegrityConstraintViolationException(Exception e) { super(e); }

}
