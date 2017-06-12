package dk.dtu.model.exceptions.dal;

import dk.dtu.model.exceptions.DALException;

public class ConnectivityException extends DALException {

	private static final long serialVersionUID = -8961067185633700591L;
	public ConnectivityException(String msg) { super(msg); }
	public ConnectivityException(Exception e) { super(e); }

}
