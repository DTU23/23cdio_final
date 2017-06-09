package dk.dtu.control.api.v1;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dk.dtu.control.AuthException;
import dk.dtu.control.ILoginController;
import dk.dtu.control.LoginController;
import dk.dtu.model.Validation;
import dk.dtu.model.ValidationException;
import dk.dtu.model.dto.OperatorDTO;
import dk.dtu.model.interfaces.DALException;

@Path("/v1/login")
public class LoginService {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response authorize(OperatorDTO opr) throws AuthException, ValidationException, DALException {
		ILoginController controller = new LoginController();
		Validation.isPositiveInteger(opr.getOprId());
		return controller.authenticateUser(opr.getOprId(), opr.getPassword());
	}
}
