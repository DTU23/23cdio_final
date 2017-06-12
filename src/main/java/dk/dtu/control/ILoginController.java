package dk.dtu.control;

import javax.ws.rs.core.Response;

import dk.dtu.model.exceptions.AuthException;
import dk.dtu.model.exceptions.DALException;
import dk.dtu.model.exceptions.validation.PositiveIntegerValidationException;

public interface ILoginController {

	Response authenticateUser(int oprId, String password) throws AuthException, DALException;
	void authenticateUser(String oprId, String password) throws PositiveIntegerValidationException, AuthException, DALException;
	
}
