package dk.dtu.model.exceptions.dal;

import dk.dtu.model.exceptions.DALException;

public class NotFoundException extends DALException {
	
	private static final long serialVersionUID = 4866510675235064437L;
	public NotFoundException(String msg) { super(msg); }
	public NotFoundException(Exception e) { super(e); }
	
}
