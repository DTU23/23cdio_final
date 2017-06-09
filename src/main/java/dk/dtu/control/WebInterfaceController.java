package dk.dtu.control;

import dk.dtu.model.ValidationException;
import dk.dtu.model.dao.MySQLOperatorDAO;
import dk.dtu.model.dao.MySQLProduceBatchDAO;
import dk.dtu.model.dao.MySQLProduceDAO;
import dk.dtu.model.dao.MySQLProductBatchDAO;
import dk.dtu.model.dao.MySQLRecipeCompDAO;
import dk.dtu.model.dao.MySQLRecipeDAO;
import dk.dtu.model.dto.OperatorDTO;
import dk.dtu.model.dto.ProduceBatchDTO;
import dk.dtu.model.dto.ProduceDTO;
import dk.dtu.model.dto.ProductBatchDTO;
import dk.dtu.model.dto.RecipeCompDTO;
import dk.dtu.model.dto.RecipeDTO;
import dk.dtu.model.interfaces.DALException;
import dk.dtu.model.interfaces.OperatorDAO;
import dk.dtu.model.interfaces.ProduceBatchDAO;
import dk.dtu.model.interfaces.ProduceDAO;
import dk.dtu.model.interfaces.ProductBatchDAO;
import dk.dtu.model.interfaces.RecipeCompDAO;
import dk.dtu.model.interfaces.RecipeDAO;

public class WebInterfaceController implements IWebInterfaceController {

	@Override
	public void createOperatorValidation(OperatorDTO opr) throws DALException, ValidationException {
		OperatorDAO dao = new MySQLOperatorDAO();
		dao.createOperator(opr);
	}

	@Override
	public void updateOperatorValidation(OperatorDTO opr) throws DALException, ValidationException {
		OperatorDAO dao = new MySQLOperatorDAO();
		dao.updateOperator(opr);
	}

	@Override
	public void createProduceBatchValidation(int produce_id,double amount) throws DALException, ValidationException {
		ProduceBatchDAO dao = new MySQLProduceBatchDAO();
		dao.createProduceBatch(produce_id, amount);
	}

	@Override
	public void updateProduceBatchValidation(ProduceBatchDTO produceBatch) throws DALException, ValidationException {
		ProduceBatchDAO dao = new MySQLProduceBatchDAO();
		dao.updateProduceBatch(produceBatch.getProduceId(), produceBatch.getAmount());
	}

	@Override
	public void createProduceValidation(ProduceDTO produce) throws DALException, ValidationException {
		ProduceDAO dao = new MySQLProduceDAO();
		dao.createProduce(produce);
	}

	@Override
	public void updateProduceValidation(ProduceDTO produce) throws DALException, ValidationException {
		ProduceDAO dao = new MySQLProduceDAO();
		dao.updateProduce(produce);		
	}

	@Override
	public void createProductBatchValidation(int recipeId) throws DALException, ValidationException {
		ProductBatchDAO dao = new MySQLProductBatchDAO();
		dao.createProductBatch(recipeId);
	}

	@Override
	public void updateProductBatchValidation(ProductBatchDTO productBatch) throws DALException, ValidationException {
		ProductBatchDAO dao = new MySQLProductBatchDAO();
		dao.updateProductBatch(productBatch);
	}

	@Override
	public void createRecipeCompValidation(RecipeCompDTO recipeComp) throws DALException, ValidationException {
		RecipeCompDAO dao = new MySQLRecipeCompDAO();
		dao.createRecipeComp(recipeComp);
	}

	@Override
	public void updateRecipeCompValidation(RecipeCompDTO recipeComp) throws DALException, ValidationException {
		RecipeCompDAO dao = new MySQLRecipeCompDAO();		
		dao.updateRecipeComp(recipeComp);
	}

	@Override
	public void createRecipeValidation(RecipeDTO recipe) throws DALException, ValidationException {
		RecipeDAO dao = new MySQLRecipeDAO();
		dao.createRecipe(recipe.getRecipeName());
	}

	@Override
	public void updateRecipeValidation(RecipeDTO recipe) throws DALException, ValidationException {
		RecipeDAO dao = new MySQLRecipeDAO();
		dao.updateRecipe(recipe);
	}

}
