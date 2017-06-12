package dk.dtu.control;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import dk.dtu.model.exceptions.DALException;
import dk.dtu.model.interfaces.OperatorDAO;
import dk.dtu.model.interfaces.ProduceBatchDAO;
import dk.dtu.model.interfaces.ProductBatchCompDAO;
import dk.dtu.model.interfaces.ProductBatchDAO;
import dk.dtu.model.interfaces.RecipeDAO;

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
		initLoop: while(true) {
			try {
				operatorDAO = new MySQLOperatorDAO();
				productBatchDAO = new MySQLProductBatchDAO();
				produceBatchDAO = new MySQLProduceBatchDAO();
				recipeDAO = new MySQLRecipeDAO();
				productBatchCompDAO = new MySQLProductBatchCompDAO();
			} catch (Exception e) {
				System.out.println("Error trying to reach database!");
				e.printStackTrace();
				continue initLoop;
			}
			weightAdaptor = new WeightAdaptor();

			//TODO Kan der laves et loop, hvis dette fejler??? Hvis der kan, hvor mange gange skal vi så køre det før vi terminerer
			try {
				weightAdaptor.establishConnection(ipAddress, portNumber);
				weightAdaptor.setupUnit();
			} catch (AdaptorException e) {
				System.out.println("Connection couldn't be established!");
				e.printStackTrace();
				continue initLoop;
			}
			break initLoop;
		}

		while(true) {

			String oprId = null;
			String oprPW = null;
			String productBatchNumber = null;
			OperatorDTO operatorDTO = null;
			List<RecipeListDTO> recipeList = null; // The theoretical recipe. List of the all the produces, which are supposed to be in the recipe. 
			List<ProductBatchCompOverviewDTO> productBatchCompOverviewList = null; // The actual product batch. List of the actual already weighed produces. 
			ProductBatchCompOverviewDTO productBatchRequest = null;

			loginLoop: while(true) {
				// Get operator identification number and validate it
				oprIdLoop: while(true) {
					try {
						oprId = weightAdaptor.getOperatorId();
						Validation.isPositiveInteger(oprId);
						operatorDTO = operatorDAO.readOperator(Integer.parseInt(oprId));
						weightAdaptor.confirmOperatorName( operatorDTO.getOprName() );
						break oprIdLoop;
					} catch (Exception e) {
						try {
							weightAdaptor.writeInSecondaryDisplay("Wrong operator id!");
							weightAdaptor.clearSecondaryDisplay();
						} catch (AdaptorException e1) {
							System.out.println("Error message couldn't be shown to user in display!");
							e1.printStackTrace();
							continue oprIdLoop;
						}
						continue oprIdLoop;
					}
				}

			if (operatorDTO.getRole().equals(Role.None.toString())) {
				try {
					weightAdaptor.writeInSecondaryDisplay("Operator must have a role!");
					weightAdaptor.clearSecondaryDisplay();
					continue loginLoop;
				} catch (AdaptorException e) {
					System.out.println("Operator is not allowed to login with role \"None\"!");
					e.printStackTrace();
					continue loginLoop;
				}
			}

			// Get the specific operators password and validate password
			try {
				oprPW = weightAdaptor.getOperatorPassword();
				ILoginController controller = new LoginController();
				controller.authenticateUser(oprId, oprPW);
			} catch (Exception e) {
				e.printStackTrace();
				try {
					weightAdaptor.loginResult(false);
				} catch (AdaptorException e1) {
					System.out.println("Login failed message couldn't be shown to user in display!");
					e1.printStackTrace();
				}
				continue loginLoop;
			}
			try {
				weightAdaptor.loginResult(true);
			} catch (AdaptorException e) {
				System.out.println("Login success message couldn't be shown to user in display!");
				e.printStackTrace();
			}
			break loginLoop;

			}

			// Asks for the product batch (number), which will be weighed
			pbLoop: while(true) {
				try {
					productBatchNumber = weightAdaptor.getProductBatchNumber();
					Validation.isPositiveInteger(productBatchNumber);
					productBatchRequest = productBatchDAO.getProductBatchListDetailsByPbId(Integer.parseInt(productBatchNumber));

					if(productBatchRequest.getStatus() == 2) {
						weightAdaptor.writeInSecondaryDisplay("All produces in this product batch is already weighed! Status 2");
						weightAdaptor.clearSecondaryDisplay();
						continue pbLoop;
					}

					recipeList = recipeDAO.getRecipeDetailsByID(productBatchRequest.getRecipeId());
					weightAdaptor.confirmRecipeName(productBatchRequest.getRecipeName());
				} catch (Exception e) {
					try {
						weightAdaptor.writeInSecondaryDisplay("Product batch number doesn't exist!");
						weightAdaptor.clearSecondaryDisplay();
					} catch (AdaptorException e1) {
						System.out.println("Error message couldn't be shown to user in display!");
						e1.printStackTrace();
					}
					e.printStackTrace();
					continue pbLoop;
				}

				// Saves list of ProductBatchCompOverviewDTO's specified by the product batch number
				try {
					productBatchCompOverviewList = productBatchDAO.getProductBatchDetailsByPbId(Integer.parseInt(productBatchNumber));
				} catch (NumberFormatException | DALException e) {
					productBatchCompOverviewList = null;
					e.printStackTrace();
				}


				break;
			}

			// Put all produces in the recipe into ArrayList
			ArrayList<String> producesAlreadyWeighed = new ArrayList<String>();
			for(ProductBatchCompOverviewDTO dto : productBatchCompOverviewList ) {
				producesAlreadyWeighed.add(dto.getProduceName());
			}

			// Removes all the produces that has been weighed. Produces that needs to be weighed is left in the ArrayList
			if(productBatchCompOverviewList != null) {
				Iterator<RecipeListDTO> iterator = recipeList.iterator();
				while(iterator.hasNext()) {
					RecipeListDTO dto = iterator.next();
					if( producesAlreadyWeighed.contains( dto.getProduceName()) ) {
						iterator.remove();
					}
				}
			}

			// Start the weighing process
			double tara;
			double netto;
			String rbId;
			double gross;
			ProductBatchCompDTO productBatchCompDTO;

			// For all produces that needs to be weighed
			weighingLoop: for(int i = 0; i < recipeList.size(); i++) {
				try {
					RecipeListDTO recipeDTO = recipeList.get(i);
					weightAdaptor.startWeighingProcess(recipeDTO.getProduceName());
					weightAdaptor.tara();
					tara = Double.parseDouble(weightAdaptor.placeTara());
					weightAdaptor.tara();
					double tolerance = recipeDTO.getTolerance();
					do {
						netto = Double.parseDouble(weightAdaptor.placeNetto(recipeDTO.getNomNetto()));
					} while (Math.abs(recipeDTO.getNomNetto() - netto) >= tolerance);
					weightAdaptor.clearSecondaryDisplay();

					do {
						rbId = weightAdaptor.getProduceBatchNumber();
						try {
							produceBatchDAO.readProduceBatch(Integer.parseInt(rbId));
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
						productBatchCompDTO = new ProductBatchCompDTO(productBatchRequest.getPbId(), Integer.parseInt(rbId), tara, netto, Integer.parseInt(oprId));
						try {
							productBatchCompDAO.createProductBatchComp(productBatchCompDTO);
						} catch (DALException e) {
							System.out.println("Product batch component couldn't be created in database!");
							e.printStackTrace();
							i--;
							continue weighingLoop;
						}	
					} else {
						i--;
						weightAdaptor.grossCheck(false);
						continue weighingLoop;
					}
					weightAdaptor.grossCheck(true);
				} catch (AdaptorException e) {
					System.out.println("Something went wrong when trying to weigh the produce!");
					e.printStackTrace();
				}
			}

			try {
				weightAdaptor.clearBothDisplays();
			} catch (AdaptorException e) {
				System.out.println("Error trying to clear primary and secondary display!");
				e.printStackTrace();
			}

		}

	}
}

