package dk.dtu.model.interfaces;

import java.util.List;

import dk.dtu.model.dto.RecipeDTO;

public interface RecipeDAO {
	RecipeDTO getRecipe(int recipeId) throws DALException;
	List<RecipeDTO> getRecipeList() throws DALException;
	void createRecipe(RecipeDTO recipe) throws DALException;
}
