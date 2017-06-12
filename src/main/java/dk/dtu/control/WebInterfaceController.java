package dk.dtu.control;

import java.util.Random;

import dk.dtu.model.Validation;
import dk.dtu.model.dao.MySQLOperatorDAO;
import dk.dtu.model.dao.MySQLProduceBatchDAO;
import dk.dtu.model.dao.MySQLProduceDAO;
import dk.dtu.model.dao.MySQLProductBatchCompDAO;
import dk.dtu.model.dao.MySQLProductBatchDAO;
import dk.dtu.model.dao.MySQLRecipeCompDAO;
import dk.dtu.model.dao.MySQLRecipeDAO;
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
import dk.dtu.model.interfaces.OperatorDAO;
import dk.dtu.model.interfaces.ProduceBatchDAO;
import dk.dtu.model.interfaces.ProduceDAO;
import dk.dtu.model.interfaces.ProductBatchCompDAO;
import dk.dtu.model.interfaces.ProductBatchDAO;
import dk.dtu.model.interfaces.RecipeCompDAO;
import dk.dtu.model.interfaces.RecipeDAO;

public class WebInterfaceController implements IWebInterfaceController {

	@Override
	public OperatorDTO createOperatorValidation(OperatorDTO opr) throws InvalidIDException, InvalidNameException,
	InvalidInitialsException, InvalidCprException, InvalidRoleException, DALException {
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
			} catch (InvalidPasswordException e) {}
		}
		opr.setPassword(generatedPassword);
		OperatorDAO dao = new MySQLOperatorDAO();
		dao.createOperator(opr);
		return opr;
	}

	@Override
	public void updateOperatorValidation(OperatorDTO opr) throws InvalidIDException, InvalidNameException,
	InvalidInitialsException, InvalidCprException, InvalidRoleException, InvalidPasswordException, DALException {
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
	public void updateOperatorValidation(OperatorNewPWDTO opr) throws InvalidIDException, InvalidNameException, InvalidInitialsException,
	InvalidCprException, InvalidRoleException, InvalidPasswordException, DALException, AuthException {
		ILoginController lc = new LoginController();
		lc.authenticateUser(opr.getOprId(), opr.getPassword());
		OperatorDTO updatedOpr = new OperatorDTO(opr);
		updateOperatorValidation(updatedOpr);
	}

	@Override
	public void createProduceBatchValidation(int produceId, double amount) throws PositiveDoubleValidationException,
	InvalidIDException, DALException {
		Validation.isPositiveDouble(amount);
		Validation.isValidID(produceId);
		ProduceBatchDAO dao = new MySQLProduceBatchDAO();
		dao.createProduceBatch(produceId, amount);
	}

	@Override
	public void updateProduceBatchValidation(ProduceBatchDTO produceBatch) throws PositiveDoubleValidationException,
	InvalidIDException, DALException {
		Validation.isValidID(produceBatch.getProduceId());
		Validation.isValidID(produceBatch.getRbId());
		Validation.isPositiveDouble(produceBatch.getAmount());
		ProduceBatchDAO dao = new MySQLProduceBatchDAO();
		dao.updateProduceBatch(produceBatch.getProduceId(), produceBatch.getAmount());
	}

	@Override
	public void createProduceValidation(ProduceDTO produce) throws InvalidIDException, InvalidNameException, DALException {
		Validation.isValidID(produce.getProduceId());
		Validation.isValidUserName(produce.getProduceName());
		Validation.isValidUserName(produce.getSupplier());
		ProduceDAO dao = new MySQLProduceDAO();
		dao.createProduce(produce);
	}

	@Override
	public void updateProduceValidation(ProduceDTO produce) throws InvalidIDException, InvalidNameException, DALException {
		Validation.isValidID(produce.getProduceId());
		Validation.isValidUserName(produce.getProduceName());
		Validation.isValidUserName(produce.getSupplier());
		ProduceDAO dao = new MySQLProduceDAO();
		dao.updateProduce(produce);		
	}

	@Override
	public void createProductBatchCompValidation(ProductBatchCompDTO productBatchComp) throws PositiveDoubleValidationException,
	InvalidIDException, DALException {
		Validation.isValidID(productBatchComp.getPbId());
		Validation.isValidID(productBatchComp.getRbId());
		Validation.isValidID(productBatchComp.getOprId());
		Validation.isPositiveDouble(productBatchComp.getTara());
		Validation.isPositiveDouble(productBatchComp.getNetto());
		ProductBatchCompDAO dao = new MySQLProductBatchCompDAO();
		dao.createProductBatchComp(productBatchComp);
	}

	@Override
	public void updateProductBatchCompValidation(ProductBatchCompDTO productBatchComp) throws PositiveDoubleValidationException,
	InvalidIDException, DALException {
		Validation.isValidID(productBatchComp.getPbId());
		Validation.isValidID(productBatchComp.getRbId());
		Validation.isValidID(productBatchComp.getOprId());
		Validation.isPositiveDouble(productBatchComp.getTara());
		Validation.isPositiveDouble(productBatchComp.getNetto());
		ProductBatchCompDAO dao = new MySQLProductBatchCompDAO();
		dao.updateProductBatchComp(productBatchComp);
	}

	@Override
	public void createProductBatchValidation(int recipeId) throws InvalidIDException, DALException {
		Validation.isValidID(recipeId);
		ProductBatchDAO dao = new MySQLProductBatchDAO();
		dao.createProductBatch(recipeId);
	}

	@Override
	public void updateProductBatchValidation(ProductBatchDTO productBatch) throws InvalidIDException, InvalidStatusException, DALException {
		Validation.isValidID(productBatch.getPbId());
		Validation.isValidID(productBatch.getRecipeId());
		Validation.isValidStatus(productBatch.getStatus());
		ProductBatchDAO dao = new MySQLProductBatchDAO();
		dao.updateProductBatch(productBatch);
	}

	@Override
	public void createRecipeCompValidation(RecipeCompDTO recipeComp) throws PositiveDoubleValidationException,
	InvalidIDException, DALException {
		Validation.isValidID(recipeComp.getProduceId());
		Validation.isValidID(recipeComp.getRecipeId());
		Validation.isPositiveDouble(recipeComp.getNomNetto());
		Validation.isPositiveDouble(recipeComp.getTolerance());
		RecipeCompDAO dao = new MySQLRecipeCompDAO();
		dao.createRecipeComp(recipeComp);
	}

	@Override
	public void updateRecipeCompValidation(RecipeCompDTO recipeComp) throws PositiveDoubleValidationException,
	InvalidIDException, DALException {
		Validation.isValidID(recipeComp.getProduceId());
		Validation.isValidID(recipeComp.getRecipeId());
		Validation.isPositiveDouble(recipeComp.getNomNetto());
		Validation.isPositiveDouble(recipeComp.getTolerance());
		RecipeCompDAO dao = new MySQLRecipeCompDAO();		
		dao.updateRecipeComp(recipeComp);
	}

	@Override
	public void createRecipeValidation(String recipeName) throws InvalidNameException, DALException {
		Validation.isValidUserName(recipeName);
		RecipeDAO dao = new MySQLRecipeDAO();
		dao.createRecipe(recipeName);
	}

	@Override
	public void updateRecipeValidation(RecipeDTO recipe) throws InvalidIDException, InvalidNameException, DALException {
		Validation.isValidID(recipe.getRecipeId());
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
