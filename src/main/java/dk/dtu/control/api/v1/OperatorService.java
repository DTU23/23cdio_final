package dk.dtu.control.api.v1;

import dk.dtu.control.api.Secured;
import dk.dtu.model.Validation;
import dk.dtu.model.ValidationException;
import dk.dtu.model.dao.MySQLOperatorDAO;
import dk.dtu.model.dto.OperatorDTO;
import dk.dtu.model.dto.OperatorNoPWDTO;
import dk.dtu.model.interfaces.DALException;
import dk.dtu.model.interfaces.OperatorDAO;
import dk.dtu.control.api.Role;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Path("v1/operator")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Secured( admin = true )
public class OperatorService {

	private OperatorDAO dao = new MySQLOperatorDAO();

	@GET
	@Path("/{id}")
	public OperatorDTO getOperator(@PathParam("id") String oprID) throws ValidationException, DALException {
		Validation.isPositiveInteger(oprID);
		return dao.getOperator(Integer.parseInt(oprID));
	}

	@GET
	public List<OperatorNoPWDTO> getOperatorList() throws DALException {
		return dao.getOperatorList();
	}

	@POST
	public void createOperator(OperatorDTO opr) throws DALException {
		dao.createOperator(opr);
	}

	@PUT
	public void updateOperator(OperatorDTO opr) throws DALException {
		dao.updateOperator(opr);
	}

	//	@DELETE
	//	@Path("/{id}")
	//	public boolean deleteOperator(@PathParam("id") String oprID) throws DALException {
	//		if (Validation.isPositiveInteger(oprID)) {
	//			return dao.deleteOperator(Integer.parseInt(oprID));
	//		} else {
	//			return false;
	//		}
	//	}
}
