package dk.dtu.model.exceptions.dal;

import dk.dtu.model.exceptions.DALException;

public class DataResetException extends DALException {
	
	private static final long serialVersionUID = -6353793563806340449L;
	public DataResetException(String msg) { super(msg); }
	public DataResetException(Exception e) { super(e); }
	
}
