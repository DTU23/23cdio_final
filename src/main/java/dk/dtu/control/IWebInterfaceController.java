package dk.dtu.control;

import dk.dtu.model.dto.OperatorDTO;
import dk.dtu.model.dto.OperatorNewPWDTO;
import dk.dtu.model.dto.ProduceBatchDTO;
import dk.dtu.model.dto.ProduceDTO;
import dk.dtu.model.dto.ProductBatchCompDTO;
import dk.dtu.model.dto.ProductBatchDTO;
import dk.dtu.model.dto.RecipeCompDTO;
import dk.dtu.model.dto.RecipeDTO;
import dk.dtu.model.exceptions.AuthException;
import dk.dtu.model.exceptions.DALException;
import dk.dtu.model.exceptions.ValidationException;

public interface IWebInterfaceController {

	// Operator
	OperatorDTO createOperatorValidation(OperatorDTO opr) throws ValidationException, DALException;
	void updateOperatorValidation(OperatorDTO opr) throws ValidationException, DALException;
	void updateOperatorValidation(OperatorNewPWDTO opr) throws AuthException, ValidationException, DALException;
	// Produce Batch
	void createProduceBatchValidation(int produce_id, double amount) throws ValidationException, DALException;
	void updateProduceBatchValidation(ProduceBatchDTO produceBatch) throws ValidationException, DALException;
	// Produce
	void createProduceValidation(ProduceDTO produce) throws ValidationException, DALException;
	void updateProduceValidation(ProduceDTO produce) throws ValidationException, DALException;
	// Product Batch Comp
	void createProductBatchCompValidation(ProductBatchCompDTO productBatchComp)	throws ValidationException, DALException;
	void updateProductBatchCompValidation(ProductBatchCompDTO productBatchComp) throws ValidationException, DALException;
	// Product Batch
	void createProductBatchValidation(int recipeId) throws ValidationException, DALException;
	void updateProductBatchValidation(ProductBatchDTO productBatch) throws ValidationException, DALException;
	// Recipe Comp
	void createRecipeCompValidation(RecipeCompDTO recipeComp) throws ValidationException, DALException;
	void updateRecipeCompValidation(RecipeCompDTO recipeComp) throws ValidationException, DALException;
	// Recipe
	void createRecipeValidation(String recipeName) throws ValidationException, DALException;
	void updateRecipeValidation(RecipeDTO recipe) throws ValidationException, DALException;
}
