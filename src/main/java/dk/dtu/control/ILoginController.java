package dk.dtu.control;

import javax.ws.rs.core.Response;

import dk.dtu.model.ValidationException;
import dk.dtu.model.interfaces.DALException;

public interface ILoginController {

	Response authenticateUser(int oprId, String password) throws AuthException, DALException;
	void authenticateUser(String oprId, String password) throws ValidationException, AuthException, DALException;
	
}
