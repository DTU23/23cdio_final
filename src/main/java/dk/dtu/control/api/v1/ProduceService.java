package dk.dtu.control.api.v1;

import dk.dtu.control.api.Role;
import dk.dtu.control.api.Secured;
import dk.dtu.model.ValidationException;
import dk.dtu.model.dao.MySQLProduceDAO;
import dk.dtu.model.dto.ProduceDTO;
import dk.dtu.model.interfaces.DALException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("v1/produce")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProduceService {

	private MySQLProduceDAO dao = new MySQLProduceDAO();

	@GET
	@Path("/{id}")
	@Secured( roles = { Role.Foreman })
	public ProduceDTO getProduce(@PathParam("id") int produce_id) throws ValidationException, DALException {
		return dao.readProduce(produce_id);
	}

	@GET
	@Secured( roles = { Role.Foreman })
	public List<ProduceDTO> getProduceList() throws DALException {
		return dao.getProduceList();
	}

	@POST
	@Secured( roles = { Role.Foreman })
	public void createProduce(ProduceDTO pb) throws DALException {
		dao.createProduce(pb);
	}
}
