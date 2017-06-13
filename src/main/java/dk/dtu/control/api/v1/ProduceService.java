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
import dk.dtu.control.api.Role;
import dk.dtu.control.api.Secured;
import dk.dtu.model.Validation;
import dk.dtu.model.dao.MySQLProduceDAO;
import dk.dtu.model.dto.ProduceDTO;
import dk.dtu.model.dto.ProduceOverviewDTO;
import dk.dtu.model.exceptions.DALException;
import dk.dtu.model.exceptions.ValidationException;
import dk.dtu.model.interfaces.ProduceDAO;

@Path("v1/produce")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProduceService {

	// This class implements the methods from MySQLProduceDAO
	private ProduceDAO dao = new MySQLProduceDAO();
	private IWebInterfaceController controller = new WebInterfaceController();

	@POST
	@Secured( roles = { Role.Pharmacist })
	public void createProduce(ProduceDTO produce) throws ValidationException, DALException {
		controller.createProduceValidation(produce);
	}
	
	@GET
	@Path("/{id}")
	@Secured( roles = { Role.Pharmacist })
	public ProduceDTO getProduce(@PathParam("id") int produceId) throws ValidationException, DALException {
		Validation.isPositiveInteger(produceId);
		return dao.readProduce(produceId);
	}
	
	@PUT
	@Secured( roles = { Role.Pharmacist })
	public void updateProduce(ProduceDTO produce) throws ValidationException, DALException {
		controller.updateProduceValidation(produce);
	}
	
	@DELETE
	@Path("/{id}")
	@Secured( roles = { Role.Pharmacist })
	public void deleteProduce(@PathParam("id") int produceId) throws ValidationException, DALException {
		Validation.isPositiveInteger(produceId);
		dao.deleteProduce(produceId);
	}

	@GET
	@Secured( roles = { Role.Pharmacist })
	public List<ProduceDTO> getProduceList() throws DALException {
		return dao.getProduceList();
	}
	
	@GET
	@Path("/overview")
	@Secured( roles = { Role.Pharmacist })
	public List<ProduceOverviewDTO> getProduceOverview() throws DALException {
		return dao.getProduceOverview();
	}
}
