package main.java.dk.dtu.model.interfaces;

import java.util.List;

import main.java.dk.dtu.model.dto.RecipeCompDTO;

public interface RecipeCompDAO {
	void createRecipeComp(RecipeCompDTO recipecomponent) throws DALException;
	RecipeCompDTO readRecipeComp(int recipeId, int produceId) throws DALException;
	void updateRecipeComp(RecipeCompDTO recipeComponent) throws DALException;
	void deleteRecipeComp(int recipeId, int produceId) throws DALException;
	List<RecipeCompDTO> getRecipeCompList(int recipeId) throws DALException;
	List<RecipeCompDTO> getWholeRecipeCompList() throws DALException;
}
