package dk.dtu.control.api.v1;

import java.util.List;

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
import dk.dtu.model.ValidationException;
import dk.dtu.model.dao.MySQLProduceBatchDAO;
import dk.dtu.model.dto.ProduceBatchDTO;
import dk.dtu.model.interfaces.DALException;
import dk.dtu.model.interfaces.ProduceBatchDAO;

@Path("v1/producebatch")
@Produces(MediaType.APPLICATION_JSON)
public class ProduceBatchService {

	// This class implements the methods from MySQLProduceBatchDAO
	private ProduceBatchDAO dao = new MySQLProduceBatchDAO();
	private IWebInterfaceController controller = new WebInterfaceController();

	@POST
	@Path("/{id};{amount}")
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
	@Path("/{id}")
	@Secured( roles = { Role.Foreman })
	public ProduceBatchDTO getProduceBatchListWithProduceName(int rbId) throws DALException {
		return dao.getProduceBatchWithProduceName(rbId);
	}
}
