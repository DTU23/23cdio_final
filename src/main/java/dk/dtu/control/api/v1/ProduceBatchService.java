package dk.dtu.control.api.v1;

import dk.dtu.control.api.Role;
import dk.dtu.control.api.Secured;
import dk.dtu.model.ValidationException;
import dk.dtu.model.dao.MySQLProduceBatchDAO;
import dk.dtu.model.dto.ProduceBatchDTO;
import dk.dtu.model.interfaces.DALException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("v1/producebatch")
@Produces(MediaType.APPLICATION_JSON)
public class ProduceBatchService {

	private MySQLProduceBatchDAO dao = new MySQLProduceBatchDAO();

	@GET
	@Path("/{id}")
	@Secured( roles = { Role.Foreman })
	public ProduceBatchDTO getProduceBatch(@PathParam("id") int pb_id) throws ValidationException, DALException {
		return dao.getProduceBatch(pb_id);
	}

	@GET
	@Secured( roles = { Role.Foreman })
	public List<ProduceBatchDTO> getProduceBatchList() throws DALException {
		return dao.getProduceBatchList();
	}

	@POST
	@Path("/{id};{amount}")
	@Secured( roles = { Role.Foreman })
	public void createProduceBatch(@PathParam("id") int produce_id, @PathParam("amount") double amount) throws DALException {
		dao.createProduceBatch(produce_id, amount);
	}
}
