package dk.dtu.control.api.v1;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import dk.dtu.control.IWebInterfaceController;
import dk.dtu.control.WebInterfaceController;
import dk.dtu.control.api.Role;
import dk.dtu.control.api.Secured;
import dk.dtu.model.Validation;
import dk.dtu.model.dao.MySQLProduceBatchDAO;
import dk.dtu.model.dto.ProduceBatchDTO;
import dk.dtu.model.dto.StockDTO;
import dk.dtu.model.exceptions.DALException;
import dk.dtu.model.exceptions.ValidationException;
import dk.dtu.model.interfaces.ProduceBatchDAO;

@Path("v1/producebatch")
@Produces(MediaType.APPLICATION_JSON)
public class ProduceBatchService {

	// This class implements the methods from MySQLProduceBatchDAO
	private ProduceBatchDAO dao = new MySQLProduceBatchDAO();
	private IWebInterfaceController controller = new WebInterfaceController();

	@POST
	@Path("/{id}/{amount}")
	@Secured( roles = { Role.Foreman })
	public void createProduceBatch(@PathParam("id") int produceId, @PathParam("amount") double amount) throws DALException, ValidationException {
		controller.createProduceBatchValidation(produceId, amount);
	}
	
	@GET
	@Path("/{id}")
	@Secured( roles = { Role.Foreman })
	public ProduceBatchDTO getProduceBatch(@PathParam("id") int pbId) throws ValidationException, DALException {
		Validation.isPositiveInteger(pbId);
		return dao.readProduceBatch(pbId);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Secured( roles = { Role.Foreman })
	public void updateProduceBatch(ProduceBatchDTO produceBatch) throws DALException, ValidationException {
		controller.updateProduceBatchValidation(produceBatch);
	}
	
	@DELETE
	@Secured( roles = { Role.Foreman })
	public void deleteProduceBatch(int rbId) throws DALException, ValidationException {
		Validation.isPositiveInteger(rbId);
		dao.deleteProduceBatch(rbId);
	}
	
	@GET
	@Secured( roles = { Role.Foreman })
	public List<ProduceBatchDTO> getProduceBatchList() throws DALException {
		return dao.getProduceBatchList();
	}
	
	@GET
	@Path("/list/{id}")
	@Secured( roles = { Role.Foreman })
	public ProduceBatchDTO getProduceBatchListWithProduceName(int rbId) throws DALException {
		return dao.getProduceBatchWithProduceName(rbId);
	}

	@GET
	@Path("/stock")
	@Secured( roles = {Role.Foreman})
	public List<StockDTO> getProduceStock() throws DALException{
		return dao.getProduceInStock();
	}
}
