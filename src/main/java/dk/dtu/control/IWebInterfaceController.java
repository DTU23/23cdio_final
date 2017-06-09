package dk.dtu.control;

import dk.dtu.model.ValidationException;
import dk.dtu.model.dto.OperatorDTO;
import dk.dtu.model.dto.ProduceBatchDTO;
import dk.dtu.model.dto.ProduceDTO;
import dk.dtu.model.dto.ProductBatchDTO;
import dk.dtu.model.dto.RecipeCompDTO;
import dk.dtu.model.dto.RecipeDTO;
import dk.dtu.model.interfaces.DALException;

public interface IWebInterfaceController {

	// Operator
	void createOperatorValidation(OperatorDTO opr) throws DALException, ValidationException;
	void updateOperatorValidation(OperatorDTO opr) throws DALException, ValidationException;
	// Produce Batch
	void createProduceBatchValidation(int produce_id, double amount) throws DALException, ValidationException;
	void updateProduceBatchValidation(ProduceBatchDTO produceBatch) throws DALException, ValidationException;
	// Produce
	void createProduceValidation(ProduceDTO produce) throws DALException, ValidationException;
	void updateProduceValidation(ProduceDTO produce) throws DALException, ValidationException;
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
