package dk.dtu.control;

import java.util.Random;

import dk.dtu.model.Validation;
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
		Validation.isValidID(opr.getOprId());
		Validation.isValidUserName(opr.getOprName());
		Validation.isValidInitials(opr.getIni());
		Validation.isValidCpr(opr.getCpr());
		Validation.isValidRole(opr.getRole());
		String generatedPassword;
		while(true) {
			try {
			generatedPassword = generatePassword(7);
			Validation.isValidPassword(generatedPassword);
			break;
			} catch (ValidationException e) {}
		}
		opr.setPassword(generatedPassword);
		OperatorDAO dao = new MySQLOperatorDAO();
		dao.createOperator(opr);
	}

	@Override
	public void updateOperatorValidation(OperatorDTO opr) throws DALException, ValidationException {
		Validation.isValidID(opr.getOprId());
		Validation.isValidUserName(opr.getOprName());
		Validation.isValidInitials(opr.getIni());
		Validation.isValidCpr(opr.getCpr());
		Validation.isValidRole(opr.getRole());
		Validation.isValidPassword(opr.getPassword());
		OperatorDAO dao = new MySQLOperatorDAO();
		dao.updateOperator(opr);
	}

	@Override
	public void createProduceBatchValidation(int produceId, double amount) throws DALException, ValidationException {
		Validation.isPositiveDouble(amount);
		Validation.isPositiveInteger(produceId);
		ProduceBatchDAO dao = new MySQLProduceBatchDAO();
		dao.createProduceBatch(produceId, amount);
	}

	@Override
	public void updateProduceBatchValidation(ProduceBatchDTO produceBatch) throws DALException, ValidationException {
		Validation.isPositiveInteger(produceBatch.getProduceId());
		Validation.isPositiveInteger(produceBatch.getRbId());
		Validation.isPositiveDouble(produceBatch.getAmount());
		ProduceBatchDAO dao = new MySQLProduceBatchDAO();
		dao.updateProduceBatch(produceBatch.getProduceId(), produceBatch.getAmount());
	}

	@Override
	public void createProduceValidation(ProduceDTO produce) throws DALException, ValidationException {
		Validation.isPositiveInteger(produce.getProduceId());
		Validation.isValidUserName(produce.getProduceName());
		Validation.isValidUserName(produce.getSupplier());
		ProduceDAO dao = new MySQLProduceDAO();
		dao.createProduce(produce);
	}

	@Override
	public void updateProduceValidation(ProduceDTO produce) throws DALException, ValidationException {
		Validation.isPositiveInteger(produce.getProduceId());
		Validation.isValidUserName(produce.getProduceName());
			Validation.isValidUserName(produce.getSupplier());
		ProduceDAO dao = new MySQLProduceDAO();
		dao.updateProduce(produce);		
	}

	@Override
	public void createProductBatchValidation(int recipeId) throws DALException, ValidationException {
		Validation.isPositiveInteger(recipeId);
		ProductBatchDAO dao = new MySQLProductBatchDAO();
		dao.createProductBatch(recipeId);
	}

	@Override
	public void updateProductBatchValidation(ProductBatchDTO productBatch) throws DALException, ValidationException {
		Validation.isPositiveInteger(productBatch.getPbId());
		Validation.isPositiveInteger(productBatch.getRecipeId());
		Validation.isPositiveInteger(productBatch.getStatus());
		ProductBatchDAO dao = new MySQLProductBatchDAO();
		dao.updateProductBatch(productBatch);
	}

	@Override
	public void createRecipeCompValidation(RecipeCompDTO recipeComp) throws DALException, ValidationException {
		Validation.isPositiveInteger(recipeComp.getProduceId());
		Validation.isPositiveInteger(recipeComp.getRecipeId());
		Validation.isPositiveDouble(recipeComp.getNomNetto());
		Validation.isPositiveDouble(recipeComp.getTolerance());
		RecipeCompDAO dao = new MySQLRecipeCompDAO();
		dao.createRecipeComp(recipeComp);
	}

	@Override
	public void updateRecipeCompValidation(RecipeCompDTO recipeComp) throws DALException, ValidationException {
		Validation.isPositiveInteger(recipeComp.getProduceId());
		Validation.isPositiveInteger(recipeComp.getRecipeId());
		Validation.isPositiveDouble(recipeComp.getNomNetto());
		Validation.isPositiveDouble(recipeComp.getTolerance());
		RecipeCompDAO dao = new MySQLRecipeCompDAO();		
		dao.updateRecipeComp(recipeComp);
	}

	@Override
	public void createRecipeValidation(String recipeName) throws DALException, ValidationException {
		Validation.isValidUserName(recipeName);
		RecipeDAO dao = new MySQLRecipeDAO();
		dao.createRecipe(recipeName);
	}

	@Override
	public void updateRecipeValidation(RecipeDTO recipe) throws DALException, ValidationException {
		Validation.isPositiveInteger(recipe.getRecipeId());
		Validation.isValidUserName(recipe.getRecipeName());
		RecipeDAO dao = new MySQLRecipeDAO();
		dao.updateRecipe(recipe);
	}
	
	/**
	 * Method taken from CDIO2.1
	 * Generates a random password with listed characters and symbols
	 * @param length - how many characters should the password be
	 * @return String
	 */
	private String generatePassword(int length){
		String charactersCaps = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String characters = "abcdefghijklmnopqrstuvwxyz";
		String numbers = "0123456789";
		String passwordCharacters = charactersCaps + characters + numbers;
		Random rnd = new Random();
		char[] password = new char[length];
		for (int i = 0; i < length; i++) {
			password[i] = passwordCharacters.charAt(rnd.nextInt(passwordCharacters.length()));
		}
		return new String(password);
	}
}
