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
import dk.dtu.model.dao.MySQLProductBatchDAO;
import dk.dtu.model.dto.ProductBatchDTO;
import dk.dtu.model.dto.ProductBatchListDTO;
import dk.dtu.model.exceptions.DALException;
import dk.dtu.model.exceptions.ValidationException;
import dk.dtu.model.interfaces.ProductBatchDAO;

@Path("v1/productbatch")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductBatchService {

	// This class implements all MySQLProductBatchDAO methods
	private ProductBatchDAO dao = new MySQLProductBatchDAO();
	private IWebInterfaceController controller = new WebInterfaceController();

	@POST
	@Secured( roles = { Role.Foreman })
	public void createProductBatch(ProductBatchDTO productBatch) throws ValidationException, DALException {
		controller.createProductBatchValidation(productBatch);
	}
	
	@GET
	@Path("/{id}")
	@Secured( roles = { Role.Foreman })
	public ProductBatchDTO getProductBatch(@PathParam("id") int pbId) throws ValidationException, DALException {
		Validation.isPositiveInteger(pbId);
		return dao.readProductBatch(pbId);
	}
	
	@PUT
	@Secured( roles = { Role.Foreman })
	public void updateProductBatch(ProductBatchDTO productBatch) throws ValidationException, DALException {
		controller.updateProductBatchValidation(productBatch);
	}
	
	@DELETE
	@Path("/{id}")
	@Secured( roles = { Role.Foreman })
	public void deleteProductBatch(@PathParam("id") int pbId) throws ValidationException, DALException {
		Validation.isPositiveInteger(pbId);
		dao.deleteProductBatch(pbId);
	}
	
	@GET
	@Secured( roles = { Role.Foreman })
	public List<ProductBatchListDTO> getProductList() throws DALException {
		return dao.getProductBatchList();
	}
}
