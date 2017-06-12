package dk.dtu.model.interfaces;

import java.util.List;

import dk.dtu.model.dto.RecipeDTO;
import dk.dtu.model.dto.RecipeListDTO;
import dk.dtu.model.exceptions.dal.ConnectivityException;
import dk.dtu.model.exceptions.dal.IntegrityConstraintViolationException;
import dk.dtu.model.exceptions.dal.NotFoundException;

public interface RecipeDAO {
	void createRecipe(String recipeName) throws ConnectivityException, IntegrityConstraintViolationException;
	RecipeDTO readRecipe(int receptId) throws ConnectivityException, NotFoundException;
	void updateRecipe(RecipeDTO recipe) throws ConnectivityException, NotFoundException, IntegrityConstraintViolationException;
	void deleteRecipe(int receptId) throws ConnectivityException, NotFoundException, IntegrityConstraintViolationException;
	List<RecipeDTO> getRecipeList() throws ConnectivityException, NotFoundException;
	List<RecipeListDTO> getRecipeDetailsByID(int recipeID) throws ConnectivityException, NotFoundException;
}
