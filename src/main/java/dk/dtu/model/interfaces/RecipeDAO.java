package dk.dtu.model.interfaces;

import java.util.List;

import dk.dtu.model.dto.RecipeDTO;
import dk.dtu.model.dto.RecipeListDTO;
import dk.dtu.model.exceptions.DALException;

public interface RecipeDAO {
	void createRecipe(RecipeDTO recipe) throws DALException;
	RecipeDTO readRecipe(int recipe_id) throws DALException;
	void updateRecipe(RecipeDTO recipe) throws DALException;
	void deleteRecipe(int recipe_id) throws DALException;
	List<RecipeDTO> getRecipeList() throws DALException;
	List<RecipeListDTO> getRecipeDetailsByID(int recipe_id) throws DALException;
}
