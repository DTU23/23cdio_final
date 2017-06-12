package dk.dtu.model.interfaces;

import java.util.List;

import dk.dtu.model.dto.RecipeCompDTO;
import dk.dtu.model.exceptions.dal.ConnectivityException;
import dk.dtu.model.exceptions.dal.IntegrityConstraintViolationException;
import dk.dtu.model.exceptions.dal.NotFoundException;

public interface RecipeCompDAO {
	void createRecipeComp(RecipeCompDTO recipecomponent) throws ConnectivityException, IntegrityConstraintViolationException;
	RecipeCompDTO readRecipeComp(int recipeId, int produceId) throws ConnectivityException, NotFoundException;
	void updateRecipeComp(RecipeCompDTO recipeComponent) throws ConnectivityException, NotFoundException, IntegrityConstraintViolationException;
	void deleteRecipeComp(int recipeId, int produceId) throws ConnectivityException, NotFoundException, IntegrityConstraintViolationException;
	List<RecipeCompDTO> getRecipeCompList() throws ConnectivityException, NotFoundException;
	List<RecipeCompDTO> getRecipeCompByRecipeId(int recipeId) throws ConnectivityException, NotFoundException;
}
