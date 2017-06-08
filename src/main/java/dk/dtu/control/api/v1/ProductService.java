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
import dk.dtu.model.dao.MySQLProductBatchDAO;
import dk.dtu.model.dto.ProductBatchDTO;
import dk.dtu.model.interfaces.DALException;
import dk.dtu.model.interfaces.ProductBatchDAO;

@Path("v1/product")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductService {

	// This class implements all MySQLProductBatchDAO methods
	private ProductBatchDAO dao = new MySQLProductBatchDAO();

	@POST
	@Secured( roles = { Role.Foreman })
	public void createProductBatch(int recipeId) throws DALException {
		dao.createProductBatch(recipeId);
	}
	
	@GET
	@Path("/{id}")
	@Secured( roles = { Role.Foreman })
	public ProductBatchDTO getProductBatch(@PathParam("id") int pbId) throws ValidationException, DALException {
		return dao.readProductBatch(pbId);
	}
	
	@PUT
	@Secured( roles = { Role.Foreman })
	public void updateProductBatch(ProductBatchDTO productBatch) throws DALException {
		dao.updateProductBatchStatus(productBatch);
	}
	
	@DELETE
	@Secured( roles = { Role.Foreman })
	public void deleteProductBatch(int pbId) throws DALException {
		dao.deleteProductBatch(pbId);
	}
	
	@GET
	@Secured( roles = { Role.Foreman })
	public List<ProductBatchDTO> getProductList() throws DALException {
		return dao.getProductBatchList();
	}
	
	//TODO Methods not implemented here from DAO - but necessary? They're used for weighing process.
	//getProductBatchListDetailsByPbId
	//getProductBatchDetailsByPbId

}
