package dk.dtu.control;

import dk.dtu.model.dto.*;
import dk.dtu.model.exceptions.AuthException;
import dk.dtu.model.exceptions.DALException;
import dk.dtu.model.exceptions.ValidationException;

public interface IWebInterfaceController {

	// Operator
	OperatorDTO createOperatorValidation(OperatorDTO opr) throws DALException, ValidationException;
	void updateOperatorValidation(OperatorDTO opr) throws DALException, ValidationException;
	void updateOperatorValidation(OperatorNewPWDTO opr) throws DALException, ValidationException, AuthException;
	// Produce Batch
	void createProduceBatchValidation(int produce_id, double amount) throws DALException, ValidationException;
	void updateProduceBatchValidation(ProduceBatchDTO produceBatch) throws DALException, ValidationException;
	// Produce
	void createProduceValidation(ProduceDTO produce) throws DALException, ValidationException;
	void updateProduceValidation(ProduceDTO produce) throws DALException, ValidationException;
	// Product Batch Comp
	void createProductBatchCompValidation(ProductBatchCompDTO productBatchComp)	throws DALException, ValidationException;
	void updateProductBatchCompValidation(ProductBatchCompDTO productBatchComp) throws DALException, ValidationException;
	// Product Batch
	void createProductBatchValidation(int recipeId) throws DALException, ValidationException;
	void updateProductBatchValidation(ProductBatchDTO productBatch) throws DALException, ValidationException;
	// Recipe Comp
	void createRecipeCompValidation(RecipeCompDTO recipeComp) throws DALException, ValidationException;
	void updateRecipeCompValidation(RecipeCompDTO recipeComp) throws DALException, ValidationException;
	// Recipe
	void createRecipeValidation(String recipeName) throws DALException, ValidationException;
	void updateRecipeValidation(RecipeDTO recipe) throws DALException, ValidationException;
	

	
}
