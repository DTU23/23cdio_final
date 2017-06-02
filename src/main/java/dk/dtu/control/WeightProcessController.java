package dk.dtu.control;

import dk.dtu.control.api.v1.IWeightAdaptor;
import dk.dtu.control.api.v1.IWeightAdaptor.AdaptorException;
import dk.dtu.control.api.v1.WeightAdaptor;
import dk.dtu.model.Validation;
import dk.dtu.model.connector.Connector;
import dk.dtu.model.dao.MySQLOperatorDAO;
import dk.dtu.model.dao.MySQLProductBatchDAO;
import dk.dtu.model.dao.MySQLRecipeDAO;
import dk.dtu.model.interfaces.OperatorDAO;
import dk.dtu.model.interfaces.ProductBatchDAO;
import dk.dtu.model.interfaces.RecipeDAO;

public class WeightProcessController implements IWeightProcessController {

	private IWeightAdaptor weightAdaptor;
	private OperatorDAO operatorDAO;
	private ProductBatchDAO productBatchDAO;
	private RecipeDAO recipeDAO;

	@Override
	public void run() {
		try {
			// TODO Kig på database forbindelse!!!
			new Connector();
			operatorDAO = new MySQLOperatorDAO();
			productBatchDAO = new MySQLProductBatchDAO();
			recipeDAO = new MySQLRecipeDAO();
		} catch (Exception e) {
			
		}
		
		weightAdaptor = new WeightAdaptor();

		//TODO Kan der laves et loop, hvis dette fejler??? Hvis der kan, hvor mange gange skal vi så køre det før vi terminerer
		try {
			weightAdaptor.establishConnection("localhost", 8000);
			weightAdaptor.setupUnit();
		} catch (AdaptorException e) {
			System.out.println("Connection couldn't be established!");
			e.printStackTrace();
			System.exit(1);
		}

		while(true) {
			// Login
			try {
				String oprId;
				String oprPW;

				do {
					oprId = weightAdaptor.getOperatorId();
				} while(!Validation.isPositiveInteger(oprId));

				do {
					oprPW = weightAdaptor.getOperatorPassword();
					weightAdaptor.loginResult(!Validation.isValidPassword(oprPW));
				} while (!Validation.isValidPassword(oprPW));

				

			} catch (Exception e) {
				// TODO: handle exception
			}

			// 
		}

	}
}
