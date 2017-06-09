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
import dk.dtu.model.ValidationException;
import dk.dtu.model.dao.MySQLOperatorDAO;
import dk.dtu.model.dto.OperatorDTO;
import dk.dtu.model.dto.OperatorNoPWDTO;
import dk.dtu.model.interfaces.DALException;
import dk.dtu.model.interfaces.OperatorDAO;

@Path("v1/operator")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Secured( admin = true )
public class OperatorService {

	// This class implements the methods from MySQLOperatorDAO
	private OperatorDAO dao = new MySQLOperatorDAO();
	private IWebInterfaceController controller = new WebInterfaceController();

	@POST
	public void createOperator(OperatorDTO opr) throws DALException, ValidationException {
		controller.createOperatorValidation(opr);
	}

	@GET
	@Path("/{id}")
	public OperatorDTO getOperator(@PathParam("id") String oprID) throws ValidationException, DALException {
		Validation.isPositiveInteger(oprID);
		return dao.readOperator(Integer.parseInt(oprID));
	}

	@PUT
	public void updateOperator(OperatorDTO opr) throws DALException, ValidationException {
		controller.updateOperatorValidation(opr);
	}

	@DELETE
	@Path("/{id}")
	public boolean deleteOperator(@PathParam("id") String oprID) throws DALException {
		try {
			Validation.isPositiveInteger(oprID);
			dao.deleteOperator(Integer.parseInt(oprID));
			return true;
		} catch (ValidationException e) {
			return false;
		}
	}

	@GET
	public List<OperatorNoPWDTO> getOperatorList() throws DALException {
		return dao.getOperatorList();
	}
}
