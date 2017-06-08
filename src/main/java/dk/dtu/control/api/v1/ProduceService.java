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

import dk.dtu.control.api.Role;
import dk.dtu.control.api.Secured;
import dk.dtu.model.ValidationException;
import dk.dtu.model.dao.MySQLProduceDAO;
import dk.dtu.model.dto.ProduceDTO;
import dk.dtu.model.dto.ProduceOverviewDTO;
import dk.dtu.model.interfaces.DALException;
import dk.dtu.model.interfaces.ProduceDAO;

@Path("v1/produce")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProduceService {

	// This class implements all MySQLProduceDAO methods
	private ProduceDAO dao = new MySQLProduceDAO();

	@POST
	@Secured( roles = { Role.Foreman })
	public void createProduce(ProduceDTO pb) throws DALException {
		dao.createProduce(pb);
	}
	
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
	
	@GET
	@Path("/overview")
	@Secured( roles = { Role.Foreman })
	public List<ProduceOverviewDTO> getProduceOverview() throws DALException {
		return dao.getProduceOverview();
	}
	
	@PUT
	@Secured( roles = { Role.Foreman })
	public void updateProduce(ProduceDTO produce) throws DALException {
		dao.updateProduce(produce);
	}
	
	@DELETE
	@Path("/{id}")
	@Secured( roles = { Role.Foreman })
	public void deleteProduce(@PathParam("id") int produce_id) throws DALException {
		dao.deleteProduce(produce_id);
	}
}
