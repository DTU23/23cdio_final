package dk.dtu.model.exceptions.dal;

import dk.dtu.model.exceptions.DALException;

public class DataInsertException extends DALException {

	private static final long serialVersionUID = -3483955489417301432L;
	public DataInsertException(String msg) { super(msg); }
	public DataInsertException(Exception e) { super(e); }

}