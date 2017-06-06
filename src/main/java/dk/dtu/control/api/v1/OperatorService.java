package main.java.dk.dtu.control.api.v1;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import main.java.dk.dtu.control.api.Secured;
import main.java.dk.dtu.model.Validation;
import main.java.dk.dtu.model.ValidationException;
import main.java.dk.dtu.model.dao.MySQLOperatorDAO;
import main.java.dk.dtu.model.dto.OperatorDTO;
import main.java.dk.dtu.model.dto.OperatorNoPWDTO;
import main.java.dk.dtu.model.interfaces.DALException;
import main.java.dk.dtu.model.interfaces.OperatorDAO;


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
