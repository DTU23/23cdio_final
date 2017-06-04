package dk.dtu.model.interfaces;

import java.util.List;

import dk.dtu.model.dto.RecipeDTO;
import dk.dtu.model.dto.RecipeListDTO;

public interface RecipeDAO {
	RecipeDTO getRecipe(int recipeId) throws DALException;
	List<RecipeDTO> getRecipeList() throws DALException;
	void createRecipe(RecipeDTO recipe) throws DALException;
	List<RecipeListDTO> getRecipeDetailsByID(int recipeID) throws DALException;
}
