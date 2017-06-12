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
import dk.dtu.model.dao.MySQLProductBatchCompDAO;
import dk.dtu.model.dto.ProductBatchCompDTO;
import dk.dtu.model.dto.ProductBatchCompOverviewDTO;
import dk.dtu.model.dto.ProductBatchCompSupplierDetailsDTO;
import dk.dtu.model.exceptions.DALException;
import dk.dtu.model.exceptions.validation.InvalidIDException;
import dk.dtu.model.exceptions.validation.PositiveDoubleValidationException;
import dk.dtu.model.exceptions.validation.PositiveIntegerValidationException;
import dk.dtu.model.interfaces.ProductBatchCompDAO;

@Path("v1/productbatchcomp")
@Produces(MediaType.APPLICATION_JSON)
public class ProductBatchCompService {

	// This class implements all MySQLProductBatchDAO methods
	private ProductBatchCompDAO dao = new MySQLProductBatchCompDAO();
	private IWebInterfaceController controller = new WebInterfaceController();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Secured( roles = { Role.Pharmacist })
	public void createProductBatchComp(ProductBatchCompDTO productBatchComp) throws DALException, PositiveDoubleValidationException, InvalidIDException {
		controller.createProductBatchCompValidation(productBatchComp);
	}
	
	@GET
	@Path("/{pbId}/{rbId}")
	@Secured( roles = { Role.Pharmacist })
	public ProductBatchCompDTO getProductBatchComp(@PathParam("pbId") int pbId, @PathParam("rbId") int rbId) throws DALException, PositiveIntegerValidationException {
		Validation.isPositiveInteger(pbId);
		Validation.isPositiveInteger(rbId);
		return dao.readProductBatchComp(pbId, rbId);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Secured( roles = { Role.Pharmacist })
	public void updateProductBatchComp(ProductBatchCompDTO productBatchComp) throws DALException, PositiveDoubleValidationException, InvalidIDException {
		controller.updateProductBatchCompValidation(productBatchComp);
	}
	
	@DELETE
	@Path("/{pbId}/{rbId}")
	@Secured( roles = { Role.Pharmacist })
	public void deleteProductBatchComp(@PathParam("pbId") int pbId, @PathParam("rbId") int rbId) throws DALException, PositiveIntegerValidationException {
		Validation.isPositiveInteger(pbId);
		Validation.isPositiveInteger(rbId);
		dao.deleteProductBatchComp(pbId, rbId);
	}
	
	@GET
	@Path("/list")
	@Secured( roles = { Role.Pharmacist })
	public List<ProductBatchCompDTO> getProductBatchCompList() throws DALException {
		return dao.getProductBatchCompList();
	}
	
	@GET
	@Path("/overview")
	@Secured( roles = { Role.Pharmacist })
	public List<ProductBatchCompOverviewDTO> getProductBatchCompOverview() throws DALException {
		return dao.getProductBatchCompOverview();
	}
	
	@GET
	@Path("/list/{pbId}")
	@Secured( roles = { Role.Pharmacist })
	public List<ProductBatchCompSupplierDetailsDTO> getRecipeComp(@PathParam("pbId") int pbId) throws DALException, PositiveIntegerValidationException {
		Validation.isPositiveInteger(pbId);
		return dao.getProductBatchComponentSupplierDetailsByPbId(pbId);
	}
	
	
	
}
