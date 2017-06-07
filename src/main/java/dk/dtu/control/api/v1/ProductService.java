package dk.dtu.control.api.v1;

import dk.dtu.control.api.Role;
import dk.dtu.control.api.Secured;
import dk.dtu.model.ValidationException;
import dk.dtu.model.dao.MySQLProduceBatchDAO;
import dk.dtu.model.dto.OperatorDTO;
import dk.dtu.model.dto.OperatorNoPWDTO;
import dk.dtu.model.dto.ProductBatchDTO;
import dk.dtu.model.interfaces.DALException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

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
