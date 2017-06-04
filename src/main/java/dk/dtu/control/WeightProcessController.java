package dk.dtu.control;

import java.util.ArrayList;
import java.util.List;

import dk.dtu.control.api.v1.IWeightAdaptor;
import dk.dtu.control.api.v1.IWeightAdaptor.AdaptorException;
import dk.dtu.control.api.v1.WeightAdaptor;
import dk.dtu.model.Validation;
import dk.dtu.model.connector.Connector;
import dk.dtu.model.dao.MySQLOperatorDAO;
import dk.dtu.model.dao.MySQLProductBatchDAO;
import dk.dtu.model.dao.MySQLRecipeDAO;
import dk.dtu.model.dto.ProductBatchCompOverviewDTO;
import dk.dtu.model.dto.RecipeListDTO;
import dk.dtu.model.interfaces.DALException;
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
			e.printStackTrace();
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

			String oprId = null;
			String oprPW = null;
			String productBatchNumber = null;

			try {
				// Get operator identification number and validate it
				do {
					oprId = weightAdaptor.getOperatorId();
				} while(!Validation.isPositiveInteger(oprId));

				// Get the specific operators password and validate password
				do {
					oprPW = weightAdaptor.getOperatorPassword();
					weightAdaptor.loginResult(!Validation.isValidPassword(oprPW));
				} while (!Validation.isValidPassword(oprPW));

				// Asks for the product batch (number), which will be weighed
				do {
					productBatchNumber = weightAdaptor.getProductBatchNumber();
				} while(true); // TODO HVILKEN VALIDERING?
			} catch (Exception e) { // TODO EXCEPTION SKAL HÅNDTERES
				e.printStackTrace();
			}


			// TODO NEDENSTÅENDE MANGLER DO-WHILE/VALIDERING


			List<RecipeListDTO> recipeList = null;
			List<ProductBatchCompOverviewDTO> productBatchCompOverviewList = null;
			try {
				// Saves list of ProductBatchCompOverviewDTO's specified by the product batch number
				productBatchCompOverviewList = productBatchDAO.get_product_batch_details_by_pb_id(Integer.parseInt(productBatchNumber));
				// Saves list of RecipeListDTO's from the recipe that is used for the weighing.
				recipeList = recipeDAO.getRecipeDetailsByID( 1 ); // TODO VI KENDER IKKE RECIPE ID, MEN KUN NAVNET
			} catch (NumberFormatException e) {
				System.out.println("Couldn't parse the product batch number to an integer!");
				e.printStackTrace();
			} catch (DALException e) {
				System.out.println("Couldn't get the recipe details by ID!");
				e.printStackTrace();
			}


			try {
				// Shows the specific recipe name in secondary display, which will be weighed
				weightAdaptor.confirmRecipeName( productBatchCompOverviewList.get(0).getRecipeName() );
			} catch (AdaptorException e) { // TODO EXCEPTION SKAL HÅNDTERES
				e.printStackTrace();
			}


			ArrayList<String> alreadyWeighedProducesInProductBatch = new ArrayList<String>();
			ArrayList<String> producesNeededToBeWeighed = new ArrayList<String>();
			// Put all produces in the recipe into ArrayList
			for(RecipeListDTO dto : recipeList ) {
				producesNeededToBeWeighed.add(dto.getProduceName());
			}
			// Put all produces that has been weighed in the specific product batch into ArrayList
			for(ProductBatchCompOverviewDTO dto : productBatchCompOverviewList) {
				alreadyWeighedProducesInProductBatch.add(dto.getProduceName());
			}
			// Removes all the produces that has been weighed. Produces that needs to be weighed is left in the ArrayList
			for(int i = 0; i < alreadyWeighedProducesInProductBatch.size(); i++) {
				producesNeededToBeWeighed.remove(alreadyWeighedProducesInProductBatch.get(i));
			}

			// Start the weighing process
			double tara;
			double netto;
			double gross;
			for(int i = 0; i < producesNeededToBeWeighed.size(); i++) {
				try {
					do {
						weightAdaptor.startWeighingProcess(producesNeededToBeWeighed.get(i));
						tara = Double.parseDouble(weightAdaptor.placeTara());
						netto = Double.parseDouble(weightAdaptor.placeNetto());
						gross = Double.parseDouble(weightAdaptor.removeGross());
						weightAdaptor.grossCheck(tara + netto == Math.abs(gross));
					} while (tara + netto == Math.abs(gross));
				} catch (AdaptorException e) {
					e.printStackTrace(); // TODO MANGLER EXCEPTION HÅNDTERING
				}
			}
			
		}

	}
}
