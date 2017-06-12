package dk.dtu.control.api.v1;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dk.dtu.control.IWebInterfaceController;
import dk.dtu.control.WebInterfaceController;
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
import dk.dtu.control.api.Role;

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

	@PUT
	@Secured( admin = true )
	public void updateOperator(OperatorDTO opr) throws DALException, ValidationException {
		controller.updateOperatorValidation(opr);
	}

	@PUT
	@Secured( roles={Role.None} )
	@Path("/changePassword")
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
