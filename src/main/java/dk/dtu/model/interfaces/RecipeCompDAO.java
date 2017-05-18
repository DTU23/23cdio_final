package main.java.dk.dtu.model.interfaces;

import java.util.List;

import main.java.dk.dtu.model.dto.RecipeCompDTO;

public interface RecipeCompDAO {
	RecipeCompDTO getRecipeComp(int recipeId, int produceId) throws DALException;
	List<RecipeCompDTO> getRecipeCompList(int recipeId) throws DALException;
	List<RecipeCompDTO> getRecipeCompList() throws DALException;
	void createRecipeComp(RecipeCompDTO recipecomponent) throws DALException;
}
