package dk.dtu.control;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import dk.dtu.control.api.Role;
import dk.dtu.control.api.v1.IWeightAdaptor;
import dk.dtu.control.api.v1.WeightAdaptor;
import dk.dtu.model.Validation;
import dk.dtu.model.dao.MySQLOperatorDAO;
import dk.dtu.model.dao.MySQLProduceBatchDAO;
import dk.dtu.model.dao.MySQLProductBatchCompDAO;
import dk.dtu.model.dao.MySQLProductBatchDAO;
import dk.dtu.model.dao.MySQLRecipeDAO;
import dk.dtu.model.dto.OperatorDTO;
import dk.dtu.model.dto.ProductBatchCompDTO;
import dk.dtu.model.dto.ProductBatchCompOverviewDTO;
import dk.dtu.model.dto.RecipeListDTO;
import dk.dtu.model.exceptions.AdaptorException;
import dk.dtu.model.exceptions.AuthException;
import dk.dtu.model.exceptions.DALException;
import dk.dtu.model.exceptions.dal.NotFoundException;
import dk.dtu.model.exceptions.validation.PositiveIntegerValidationException;
import dk.dtu.model.interfaces.OperatorDAO;
import dk.dtu.model.interfaces.ProduceBatchDAO;
import dk.dtu.model.interfaces.ProductBatchCompDAO;
import dk.dtu.model.interfaces.ProductBatchDAO;
import dk.dtu.model.interfaces.RecipeDAO;

public class WeightProcessController implements IWeightProcessController {

	private String ipAddress;
	private int portNumber;
	private OperatorDAO operatorDAO;
	private ProductBatchDAO productBatchDAO;
	private ProduceBatchDAO produceBatchDAO;
	private ProductBatchCompDAO productBatchCompDAO;
	private RecipeDAO recipeDAO;
	private IWeightAdaptor weightAdaptor;

	public WeightProcessController() {
		this("localhost", 8000);
	}

	public WeightProcessController(String ipAddress, int portNumber) {
		this.ipAddress = ipAddress;
		this.portNumber = portNumber;
	}

	@Override
	public void run() {
		initLoop: for(int i = 0; i < 60; i++) {
			operatorDAO = new MySQLOperatorDAO();
			productBatchDAO = new MySQLProductBatchDAO();
			produceBatchDAO = new MySQLProduceBatchDAO();
			productBatchCompDAO = new MySQLProductBatchCompDAO();
			recipeDAO = new MySQLRecipeDAO();
			weightAdaptor = new WeightAdaptor();

			try {
				weightAdaptor.establishConnection(ipAddress, portNumber);
				weightAdaptor.setupUnit();
			} catch (AdaptorException e) {
				System.out.println("Connection couldn't be established!");
				e.printStackTrace();
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				if (i == 59) {
					System.exit(1);
				}
				continue initLoop;
			}

			break initLoop;
		}

	while(true) {
		String oprId = null;
		String oprPW = null;
		String productBatchNumber = null;
		OperatorDTO operatorDTO = null;
		ProductBatchCompOverviewDTO productBatchRequest = null;
		List<RecipeListDTO> recipeList = null; // The theoretical recipe. List of the all the produces, which are supposed to be in the recipe. 
		List<ProductBatchCompOverviewDTO> productBatchCompOverviewList = null; // The actual product batch. List of the actual already weighed produces. 

		loginLoop: while(true) {
			// Get operator ID and confirm it in display
			oprIdLoop: while(true) {
				try {
					oprId = weightAdaptor.getOperatorId();
					Validation.isPositiveInteger(oprId);
					operatorDTO = operatorDAO.readOperator(Integer.parseInt(oprId));
					weightAdaptor.confirmOperatorName(operatorDTO.getOprName());
					break oprIdLoop;
				} catch (AdaptorException e) {
					errorMessageInSecondaryDisplay("Problem trying to send or recieve message from weight!");
					e.printStackTrace();
					continue oprIdLoop;
				} catch (NotFoundException e) {
					errorMessageInSecondaryDisplay("Operator was not found!");
					e.printStackTrace();
					continue oprIdLoop;
				} catch (PositiveIntegerValidationException e) {
					errorMessageInSecondaryDisplay("The ID was not valid!");
					e.printStackTrace();
					continue oprIdLoop;
				} catch (DALException e) {
					errorMessageInSecondaryDisplay("Problem trying to read operator from database!");
					e.printStackTrace();
					continue oprIdLoop;
				}
			}

		// Get the specific operators password and validate password
		try {
			oprPW = weightAdaptor.getOperatorPassword();
			ILoginController controller = new LoginController();
			controller.authenticateUser(oprId, oprPW);
		} catch (AuthException e) {
			loginResult(false);
			e.printStackTrace();
			continue loginLoop;
		} catch (AdaptorException e) {
			errorMessageInSecondaryDisplay("Problem trying to send or recieve message from weight!");
			e.printStackTrace();
			continue loginLoop;
		}  catch (DALException e) {
			errorMessageInSecondaryDisplay("Problem trying to reach database!");
			e.printStackTrace();
			continue loginLoop;
		}
		loginResult(true);
		// Check operator has a role
		if (operatorDTO.getRole().equals(Role.None.toString())) {
			errorMessageInSecondaryDisplay("This operator is disabled!");
			continue loginLoop;
		}
		break loginLoop;
		}

		// Asks for the product batch (number), which needs to be weighed
		pbLoop: while(true) {
			try {
				productBatchNumber = weightAdaptor.getProductBatchNumber();
				Validation.isPositiveInteger(productBatchNumber);
				productBatchRequest = productBatchDAO.getProductBatchListDetailsByPbId(Integer.parseInt(productBatchNumber));
				if(productBatchRequest.getStatus() == 2) {
					weightAdaptor.writeInSecondaryDisplay("All produces in this product batch is already weighed!");
					weightAdaptor.clearSecondaryDisplay();
					continue pbLoop;
				}
				recipeList = recipeDAO.getRecipeDetailsByID(productBatchRequest.getRecipeId());
				weightAdaptor.confirmRecipeName(productBatchRequest.getRecipeName());
			} catch (AdaptorException e) {
				errorMessageInSecondaryDisplay("Problem trying to send or recieve message from weight!");
				e.printStackTrace();
				continue pbLoop;
			} catch (DALException e) {
				errorMessageInSecondaryDisplay("Product batch number doesn't exist!");
				e.printStackTrace();
				continue pbLoop;
			} catch (PositiveIntegerValidationException e) {
				errorMessageInSecondaryDisplay("Product batch number is not valid!");
				e.printStackTrace();
				continue pbLoop;
			}

			// Saves list of ProductBatchCompOverviewDTO's (already weighed produces in product batch) specified by the product batch number
			try {
				productBatchCompOverviewList = productBatchDAO.getProductBatchDetailsByPbId(Integer.parseInt(productBatchNumber));
			} catch (DALException e) {
				// Fine - status 0. No weighed produces in product batch
			}
			break;
		}

		// Only if there's any already weighed produces in product batch
		if(productBatchCompOverviewList != null) {
			// Put all produces in the recipe (theoretical) into ArrayList
			ArrayList<String> producesAlreadyWeighed = new ArrayList<String>();
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
		}

		double tara;
		double netto;
		String rbId;
		double gross;
		ProductBatchCompDTO productBatchCompDTO;
		// Start the weighing process. For all produces that needs to be weighed
		weighingLoop: for(int i = 0; i < recipeList.size(); i++) {
			try {
				RecipeListDTO recipeDTO = recipeList.get(i);
				weightAdaptor.startWeighingProcess(recipeDTO.getProduceName());
				weightAdaptor.tara();
				tara = Double.parseDouble(weightAdaptor.placeTara());
				weightAdaptor.tara();
				double tolerance = recipeDTO.getTolerance();
				weighNettoLoop: do {
					netto = Double.parseDouble(weightAdaptor.placeNetto(recipeDTO.getNomNetto()));
					if(Math.abs(recipeDTO.getNomNetto() - netto) > tolerance) {
						weightAdaptor.writeInSecondaryDisplay("Netto too high or too low! Please place "+recipeDTO.getNomNetto()+" kg");
						weightAdaptor.clearSecondaryDisplay();
						continue weighNettoLoop;
					} else {
						break weighNettoLoop;
					}
				} while (true);
				weightAdaptor.clearSecondaryDisplay();

				rbIdLoop: do {
					rbId = weightAdaptor.getProduceBatchNumber();
					try {
						produceBatchDAO.readProduceBatch(Integer.parseInt(rbId));
					} catch (NumberFormatException e) {
						errorMessageInSecondaryDisplay("Wrong produce batch ID!");
						e.printStackTrace();
						continue rbIdLoop;
					} catch (DALException e) {
						errorMessageInSecondaryDisplay("Produce batch does not exist!");
						e.printStackTrace();
						continue rbIdLoop;
					}	
					break;
				} while(true);

				weightAdaptor.tara();
				gross = Double.parseDouble(weightAdaptor.removeGross());

				if(tara + netto == Math.abs(gross)) {
					productBatchCompDTO = new ProductBatchCompDTO(productBatchRequest.getPbId(), Integer.parseInt(rbId), tara, netto, Integer.parseInt(oprId));
					try {
						productBatchCompDAO.createProductBatchComp(productBatchCompDTO);
					} catch (DALException e) {
						errorMessageInSecondaryDisplay("Product batch component couldn't be created in database!");
						e.printStackTrace();
						i--;
						continue weighingLoop;
					}	
				} else {
					weightAdaptor.grossCheck(false);
					i--;
					continue weighingLoop;
				}				
			} catch (AdaptorException e) {
				errorMessageInSecondaryDisplay("A connectivity problem occured!");
				e.printStackTrace();
				i--;
				continue weighingLoop;
			}
			try {
				weightAdaptor.grossCheck(true);
				weightAdaptor.clearBothDisplays();
			} catch (AdaptorException e) {
				errorMessageInSecondaryDisplay("A connectivity problem occured!");
				e.printStackTrace();
			}
		}
	}
	}

	private void loginResult(boolean bool) {
		try {
			weightAdaptor.loginResult(bool);
		} catch (AdaptorException e) {
			System.out.println("Login success/fail message couldn't be shown to user in display!");
			e.printStackTrace();
		}
	}

	private void errorMessageInSecondaryDisplay(String msg) {
		try {
			weightAdaptor.clearInputBuffer();
			weightAdaptor.writeInSecondaryDisplay(msg);
			weightAdaptor.clearSecondaryDisplay();
		} catch (AdaptorException e1) {
			System.out.println("Error message couldn't be shown to user in display!");
			e1.printStackTrace();
		}
		System.out.println(msg);
	}
}

