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
import dk.dtu.model.dao.MySQLRecipeCompDAO;
import dk.dtu.model.dto.RecipeCompDTO;
import dk.dtu.model.interfaces.DALException;
import dk.dtu.model.interfaces.RecipeCompDAO;

@Path("v1/recipecomp")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RecipeCompService {

	// This class implements the methods from MySQLRecipeCompDAO
	private RecipeCompDAO dao = new MySQLRecipeCompDAO();

	@POST
	@Secured( roles = { Role.Pharmacist })
	public void createRecipeComp(RecipeCompDTO recipeComp) throws DALException {
		dao.createRecipeComp(recipeComp);
	}
	
	@GET
	@Path("/{recipeId};{produceId}")
	@Secured( roles = { Role.Pharmacist })
	public RecipeCompDTO getRecipeComp(@PathParam("recipeId") int recipeId, @PathParam("produceId") int produceId) throws DALException {
		return dao.readRecipeComp(recipeId, produceId);
	}
	
	@PUT
	@Secured( roles = { Role.Pharmacist })
	public void updateRecipeComp(RecipeCompDTO recipeComp) throws DALException {
		dao.updateRecipeComp(recipeComp);
	}
	
	@DELETE
	@Path("/{recipeId};{produceId}")
	@Secured( roles = { Role.Pharmacist })
	public void deleteProduce(@PathParam("recipeId") int recipeId, @PathParam("produceId") int produceId) throws DALException {
		dao.deleteRecipeComp(recipeId, produceId);
	}

	@GET
	@Path("/{id}")
	@Secured( roles = { Role.Pharmacist })
	public List<RecipeCompDTO> getRecipeCompList(@PathParam("id") int recipeId) throws DALException {
		return dao.getRecipeCompByRecipeId(recipeId);
	}
	
	@GET
	@Path("/list")
	@Secured( roles = { Role.Pharmacist })
	public List<RecipeCompDTO> getWholeRecipeCompList() throws DALException {
		return dao.getRecipeCompList();
	}
}