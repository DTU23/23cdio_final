package dk.dtu.control.api.v1;

import dk.dtu.control.IWebInterfaceController;
import dk.dtu.control.WebInterfaceController;
import dk.dtu.control.api.Role;
import dk.dtu.control.api.Secured;
import dk.dtu.model.Validation;
import dk.dtu.model.ValidationException;
import dk.dtu.model.dao.MySQLProductBatchCompDAO;
import dk.dtu.model.dto.ProductBatchCompDTO;
import dk.dtu.model.dto.ProductBatchCompSupplierDetailsDTO;
import dk.dtu.model.dto.ProductBatchDTO;
import dk.dtu.model.interfaces.DALException;
import dk.dtu.model.interfaces.ProductBatchCompDAO;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("v1/product")
@Produces(MediaType.APPLICATION_JSON)
public class ProductCompService {

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
