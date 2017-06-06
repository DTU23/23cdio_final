package main.java.dk.dtu.control;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import main.java.dk.dtu.control.api.v1.AdaptorException;
import main.java.dk.dtu.control.api.v1.IWeightAdaptor;
import main.java.dk.dtu.control.api.v1.WeightAdaptor;
import main.java.dk.dtu.model.Validation;
import main.java.dk.dtu.model.connector.Connector;
import main.java.dk.dtu.model.dao.MySQLOperatorDAO;
import main.java.dk.dtu.model.dao.MySQLProduceBatchDAO;
import main.java.dk.dtu.model.dao.MySQLProductBatchCompDAO;
import main.java.dk.dtu.model.dao.MySQLProductBatchDAO;
import main.java.dk.dtu.model.dao.MySQLRecipeDAO;
import main.java.dk.dtu.model.dto.ProductBatchCompDTO;
import main.java.dk.dtu.model.dto.ProductBatchCompOverviewDTO;
import main.java.dk.dtu.model.dto.RecipeListDTO;
import main.java.dk.dtu.model.interfaces.DALException;
import main.java.dk.dtu.model.interfaces.OperatorDAO;
import main.java.dk.dtu.model.interfaces.ProduceBatchDAO;
import main.java.dk.dtu.model.interfaces.ProductBatchCompDAO;
import main.java.dk.dtu.model.interfaces.ProductBatchDAO;
import main.java.dk.dtu.model.interfaces.RecipeDAO;

public class WeightProcessController implements IWeightProcessController {

	private String ipAddress;
	private int portNumber;
	private IWeightAdaptor weightAdaptor;
	private OperatorDAO operatorDAO;
	private ProductBatchDAO productBatchDAO;
	private ProduceBatchDAO produceBatchDAO;
	private RecipeDAO recipeDAO;
	private ProductBatchCompDAO productBatchCompDAO;

	public WeightProcessController() {
		this("localhost", 8000);
	}

	public WeightProcessController(String ipAddress, int portNumber) {
		this.ipAddress = ipAddress;
		this.portNumber = portNumber;
	}

	@Override
	public void run() {
		try {
			// TODO Kig på database forbindelse!!!
			new Connector();
			operatorDAO = new MySQLOperatorDAO();
			productBatchDAO = new MySQLProductBatchDAO();
			produceBatchDAO = new MySQLProduceBatchDAO();
			recipeDAO = new MySQLRecipeDAO();
			productBatchCompDAO = new MySQLProductBatchCompDAO();
		} catch (Exception e) {
			System.out.println("Error trying to reach database!");
			e.printStackTrace();
		}
		weightAdaptor = new WeightAdaptor();

		//TODO Kan der laves et loop, hvis dette fejler??? Hvis der kan, hvor mange gange skal vi så køre det før vi terminerer
		try {
			weightAdaptor.establishConnection(ipAddress, portNumber);
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
			List<RecipeListDTO> recipeList = null; // The theoretical recipe. List of the all the produces, which are supposed to be in the recipe. 
			List<ProductBatchCompOverviewDTO> productBatchCompOverviewList = null; // The actual product batch. List of the actual already weighed produces. 

			loginLoop: while(true) {
				// Get operator identification number and validate it
				oprIdLoop: while(true) {
					try {
						oprId = weightAdaptor.getOperatorId();
						Validation.isPositiveInteger(oprId);
						weightAdaptor.confirmOperatorName( operatorDAO.readOperator(Integer.parseInt(oprId)).getOprName() );
						break oprIdLoop;
					} catch (Exception e) {
						try {
							weightAdaptor.writeInSecondaryDisplay("Wrong operator id!");
							weightAdaptor.clearSecondaryDisplay();
						} catch (AdaptorException e1) {
							e1.printStackTrace();
						}
						continue oprIdLoop;
					}
				}	
			// Get the specific operators password and validate password
			try {
				oprPW = weightAdaptor.getOperatorPassword();
				Validation.authenticateUser(oprId, oprPW);
			} catch (Exception e) {
				try {
					weightAdaptor.loginResult(false);
				} catch (AdaptorException e1) {}
				continue loginLoop;
			}
			try {
				weightAdaptor.loginResult(true);
			} catch (AdaptorException e) {}
			break loginLoop;

			}

			// Asks for the product batch (number), which will be weighed
			pbLoop: while(true) {
				try {
					productBatchNumber = weightAdaptor.getProductBatchNumber();
					Validation.isPositiveInteger(productBatchNumber);
					// Saves list of ProductBatchCompOverviewDTO's specified by the product batch number
					productBatchCompOverviewList = productBatchDAO.getProductBatchDetailsByPbId(Integer.parseInt(productBatchNumber));
					recipeList = recipeDAO.getRecipeDetailsByID(productBatchCompOverviewList.get(0).getRecipeId());
				} catch (Exception e) {
					try {
						weightAdaptor.writeInSecondaryDisplay("Product batch number doesn't exist!");
						weightAdaptor.clearSecondaryDisplay();
					} catch (AdaptorException e1) {
						e1.printStackTrace();
					}
					//e.printStackTrace();
					continue pbLoop;
				}
				
				// Shows the specific recipe name in secondary display, which will be weighed
				try {
					if(productBatchCompOverviewList.get(0).getStatus() == 2) {
						weightAdaptor.writeInSecondaryDisplay("All produces in this product batch is already weighed! Status 2");
						weightAdaptor.clearSecondaryDisplay();
						continue pbLoop;
					}
					weightAdaptor.confirmRecipeName(recipeList.get(0).getRecipeName());
				} catch (AdaptorException e) {}
				break;
			}

			ArrayList<String> producesAlreadyWeighed = new ArrayList<String>();
			// Put all produces in the recipe into ArrayList
			for(ProductBatchCompOverviewDTO dto : productBatchCompOverviewList ) {
				producesAlreadyWeighed.add(dto.getProduceName());
			}
			
			// Removes all the produces that has been weighed. Produces that needs to be weighed is left in the ArrayList
			Iterator<RecipeListDTO> iterator = recipeList.iterator();
			while(iterator.hasNext()) {
				RecipeListDTO dto = iterator.next();
				if( producesAlreadyWeighed.contains( dto.getProduceName()) ) {
					iterator.remove();
				}
			}

			// Start the weighing process
			double tara;
			double netto;
			String rbId;
			double gross;
			ProductBatchCompDTO productBatchCompDTO;

			for(int i = 0; i < recipeList.size(); i++) {
				try {
					RecipeListDTO recipeDTO = recipeList.get(i);
					weightAdaptor.startWeighingProcess(recipeDTO.getProduceName());
					weightAdaptor.tara();
					tara = Double.parseDouble(weightAdaptor.placeTara());
					weightAdaptor.tara();
					double tolerance = recipeDTO.getTolerance();
					do {
						netto = Double.parseDouble(weightAdaptor.placeNetto());
					} while (Math.abs(recipeDTO.getNomNetto() - netto) > tolerance);
					weightAdaptor.clearSecondaryDisplay();
					
					do {
						rbId = weightAdaptor.getProduceBatchNumber();
						try {
							produceBatchDAO.getProduceBatch(Integer.parseInt(rbId));
						} catch (Exception e) {
							weightAdaptor.writeInSecondaryDisplay("Wrong produce batch id!");
							weightAdaptor.clearSecondaryDisplay();
							continue;
						}
						break;
					} while(true);
					
					weightAdaptor.tara();
					gross = Double.parseDouble(weightAdaptor.removeGross());
					if(tara + netto == Math.abs(gross)) {
						productBatchCompDTO = new ProductBatchCompDTO(productBatchCompOverviewList.get(0).getPbId(), Integer.parseInt(rbId), tara, netto, Integer.parseInt(oprId));
						try {
							productBatchCompDAO.createProductBatchComp(productBatchCompDTO);
						} catch (DALException e) {
							//TODO
						}
						weightAdaptor.grossCheck(true);
					} else {
						i--;
						weightAdaptor.grossCheck(false);
					}
				} catch (AdaptorException e) {
					e.printStackTrace(); // TODO MANGLER EXCEPTION HÅNDTERING
				}
			}
			
			try {
				weightAdaptor.clearBothDisplays();
			} catch (AdaptorException e) {
				e.printStackTrace();
			}

		}

	}
}

