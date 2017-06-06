package main.java.dk.dtu.model.interfaces;

import java.util.List;

import main.java.dk.dtu.model.dto.RecipeDTO;
import main.java.dk.dtu.model.dto.RecipeListDTO;

public interface RecipeDAO {
	RecipeDTO getRecipe(int receptId) throws DALException;
	List<RecipeDTO> getRecipeList() throws DALException;
	void createRecipe(RecipeDTO recipe) throws DALException;
	List<RecipeListDTO> getRecipeDetailsByID(int recipeID) throws DALException;
	void deleteRecipe(int receptId) throws DALException;
}
