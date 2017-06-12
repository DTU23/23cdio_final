package dk.dtu.control.api.v1;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import dk.dtu.control.IWebInterfaceController;
import dk.dtu.control.WebInterfaceController;
import dk.dtu.control.api.ExtendedSecurityContext;
import dk.dtu.control.api.Role;
import dk.dtu.control.api.Secured;
import dk.dtu.model.Validation;
import dk.dtu.model.dao.MySQLOperatorDAO;
import dk.dtu.model.dto.OperatorDTO;
import dk.dtu.model.dto.OperatorNewPWDTO;
import dk.dtu.model.dto.OperatorNoPWDTO;
import dk.dtu.model.exceptions.AuthException;
import dk.dtu.model.exceptions.DALException;
import dk.dtu.model.exceptions.ValidationException;
import dk.dtu.model.interfaces.OperatorDAO;

import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("v1/operator")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OperatorService {

	// This class implements the methods from MySQLOperatorDAO
	private OperatorDAO dao = new MySQLOperatorDAO();
	private IWebInterfaceController controller = new WebInterfaceController();

	@POST
	@Secured( admin = true )
	public void createOperator(OperatorDTO opr) throws DALException, ValidationException {
		controller.createOperatorValidation(opr);
	}

	@GET
	@Secured( admin = true )
	@Path("/{id}")
	public OperatorDTO getOperator(@PathParam("id") String oprID) throws ValidationException, DALException {
		Validation.isPositiveInteger(oprID);
		return dao.readOperator(Integer.parseInt(oprID));
	}

	@GET
	@Secured( roles = {Role.None} )
	@Path("/me")
	public OperatorNoPWDTO getSelf(ContainerRequestContext requestContext) throws ValidationException, DALException{
		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
		DecodedJWT decodedToken = JWT.decode(authorizationHeader);
		return dao.readOperatorNoPW(decodedToken.getClaim("oprId").asInt());
	}

	@PUT
	@Secured( admin = true )
	public void updateOperator(OperatorDTO opr) throws DALException, ValidationException {
		controller.updateOperatorValidation(opr);
	}

	@PUT
	@Path("/update")
	public void updateOperator(OperatorNewPWDTO opr) throws DALException, ValidationException, AuthException {
		controller.updateOperatorValidation(opr);
	}

	@DELETE
	@Secured( admin = true )
	@Path("/{id}")
	public void deleteOperator(@PathParam("id") String oprID) throws DALException, ValidationException {
		Validation.isPositiveInteger(oprID);
		dao.deleteOperator(Integer.parseInt(oprID));
	}

	@GET
	@Secured( admin = true )
	public List<OperatorNoPWDTO> getOperatorList() throws DALException {
		return dao.getOperatorList();
	}
}
