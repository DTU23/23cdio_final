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
import dk.dtu.model.exceptions.validation.InvalidCprException;
import dk.dtu.model.exceptions.validation.InvalidIDException;
import dk.dtu.model.exceptions.validation.InvalidInitialsException;
import dk.dtu.model.exceptions.validation.InvalidNameException;
import dk.dtu.model.exceptions.validation.InvalidPasswordException;
import dk.dtu.model.exceptions.validation.InvalidRoleException;
import dk.dtu.model.exceptions.validation.InvalidStatusException;
import dk.dtu.model.exceptions.validation.PositiveDoubleValidationException;

public interface IWebInterfaceController {

	// Operator
	OperatorDTO createOperatorValidation(OperatorDTO opr) throws DALException, InvalidIDException, InvalidNameException,
	InvalidInitialsException, InvalidCprException, InvalidRoleException;
	
	void updateOperatorValidation(OperatorDTO opr) throws InvalidIDException, InvalidNameException, InvalidInitialsException,
	InvalidCprException, InvalidRoleException, InvalidPasswordException, DALException;
	
	void updateOperatorValidation(OperatorNewPWDTO opr) throws InvalidIDException, InvalidNameException, InvalidInitialsException,
	InvalidCprException, InvalidRoleException, InvalidPasswordException, DALException, AuthException;
	
	// Produce Batch
	void createProduceBatchValidation(int produce_id, double amount) throws PositiveDoubleValidationException,
	InvalidIDException, DALException;
	
	void updateProduceBatchValidation(ProduceBatchDTO produceBatch) throws PositiveDoubleValidationException,
	InvalidIDException, DALException;
	
	// Produce
	void createProduceValidation(ProduceDTO produce) throws InvalidIDException, InvalidNameException, DALException;
	
	void updateProduceValidation(ProduceDTO produce) throws InvalidIDException, InvalidNameException, DALException;
	
	// Product Batch Comp
	void createProductBatchCompValidation(ProductBatchCompDTO productBatchComp)	throws PositiveDoubleValidationException,
	InvalidIDException, DALException;
	
	void updateProductBatchCompValidation(ProductBatchCompDTO productBatchComp) throws PositiveDoubleValidationException,
	InvalidIDException, DALException;
	
	// Product Batch
	void createProductBatchValidation(int recipeId) throws InvalidIDException, DALException;
	
	void updateProductBatchValidation(ProductBatchDTO productBatch) throws InvalidIDException, InvalidStatusException, DALException;
	
	// Recipe Comp
	void createRecipeCompValidation(RecipeCompDTO recipeComp) throws PositiveDoubleValidationException,
	InvalidIDException, DALException;
	
	void updateRecipeCompValidation(RecipeCompDTO recipeComp) throws PositiveDoubleValidationException,
	InvalidIDException, DALException;
	
	// Recipe
	void createRecipeValidation(String recipeName) throws InvalidNameException, DALException;
	
	void updateRecipeValidation(RecipeDTO recipe) throws InvalidIDException, InvalidNameException, DALException;
}
