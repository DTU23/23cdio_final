package main.java.dk.dtu.model.interfaces;

import java.util.List;

import main.java.dk.dtu.model.dto.RecipeDTO;
import main.java.dk.dtu.model.dto.RecipeListDTO;

public interface RecipeDAO {
	void createRecipe(RecipeDTO recipe) throws DALException;
	RecipeDTO readRecipe(int receptId) throws DALException;
	void updateRecipe(RecipeDTO recipe) throws DALException;
	void deleteRecipe(int receptId) throws DALException;
	List<RecipeDTO> getRecipeList() throws DALException;
	List<RecipeListDTO> getRecipeDetailsByID(int recipeID) throws DALException;
}
