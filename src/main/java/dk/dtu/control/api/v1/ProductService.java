package main.java.dk.dtu.control.api.v1;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import main.java.dk.dtu.control.api.Role;
import main.java.dk.dtu.control.api.Secured;
import main.java.dk.dtu.model.ValidationException;
import main.java.dk.dtu.model.dao.MySQLProduceBatchDAO;
import main.java.dk.dtu.model.dto.OperatorDTO;
import main.java.dk.dtu.model.dto.OperatorNoPWDTO;
import main.java.dk.dtu.model.dto.ProductBatchDTO;
import main.java.dk.dtu.model.interfaces.DALException;

@Path("v1/product")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductService {

	private MySQLProduceBatchDAO dao = new MySQLProduceBatchDAO();

	@GET
	@Path("/{id}")
	@Secured( roles = { Role.Pharmacist })
	public OperatorDTO getProduct(@PathParam("id") String pbId) throws ValidationException, DALException {
		return null;
	}

	@GET
	@Secured( roles = { Role.Foreman })
	public List<OperatorNoPWDTO> getProductList() throws DALException {
		return null;
	}

	@POST
	@Secured( roles = { Role.Foreman })
	public void createProduct(ProductBatchDTO pb) throws DALException {

	}
}
