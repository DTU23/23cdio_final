package dk.dtu.control.api.v1;

import java.util.List;

import javax.ws.rs.GET;
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
import dk.dtu.model.dto.ProductBatchCompSupplierDetailsDTO;
import dk.dtu.model.exceptions.DALException;
import dk.dtu.model.exceptions.ValidationException;
import dk.dtu.model.interfaces.ProductBatchCompDAO;

@Path("v1/productbatchcomp")
@Produces(MediaType.APPLICATION_JSON)
public class ProductBatchCompService {

	// This class implements all MySQLProductBatchDAO methods
	private ProductBatchCompDAO dao = new MySQLProductBatchCompDAO();
	private IWebInterfaceController controller = new WebInterfaceController();

	@GET
	@Path("/list/{pbId}")
	@Secured( roles = { Role.Pharmacist })
	public List<ProductBatchCompSupplierDetailsDTO> getRecipeComp(@PathParam("pbId") int pbId) throws DALException, ValidationException {
		Validation.isPositiveInteger(pbId);
		return dao.getProductBatchComponentSupplierDetailsByPbId(pbId);
	}
}
