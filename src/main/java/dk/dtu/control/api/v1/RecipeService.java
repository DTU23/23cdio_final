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
import dk.dtu.model.ValidationException;
import dk.dtu.model.dao.MySQLRecipeDAO;
import dk.dtu.model.dto.RecipeDTO;
import dk.dtu.model.dto.RecipeListDTO;
import dk.dtu.model.interfaces.DALException;
import dk.dtu.model.interfaces.RecipeDAO;

@Path("v1/recipe")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RecipeService {
	
	// This class implements the methods from MySQLRecipeDAO
	private RecipeDAO dao = new MySQLRecipeDAO();
	private IWebInterfaceController controller = new WebInterfaceController();
	
	@POST
	@Secured( roles = { Role.Pharmacist })
	public void createRecipe(RecipeDTO recipe) throws DALException, ValidationException {
		controller.createRecipeValidation(recipe);
	}
	
	@GET
	@Path("/{id}")
	@Secured( roles = { Role.Pharmacist })
	public RecipeDTO getRecipe(@PathParam("id") int recipeId) throws DALException {
		return dao.readRecipe(recipeId);
	}
	
	@PUT
	@Secured( roles = { Role.Pharmacist })
	public void updateRecipe(RecipeDTO recipe) throws DALException, ValidationException {
		controller.updateRecipeValidation(recipe);
	}
	
	@DELETE
	@Path("/{id}")
	@Secured( roles = { Role.Pharmacist })
	public void deleteRecipe(@PathParam("id") int recipeId) throws DALException {
		dao.deleteRecipe(recipeId);
	}

	@GET
	@Secured( roles = { Role.Pharmacist })
	public List<RecipeDTO> getRecipeList() throws DALException {
		return dao.getRecipeList();
	}
	
	@GET
	@Path("details/{id}")
	@Secured( roles = { Role.Pharmacist })
	public List<RecipeListDTO> getRecipeDetailsByID(int recipeID) throws DALException {
		return dao.getRecipeDetailsByID(recipeID);
	}

}
