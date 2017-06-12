package dk.dtu.control.api.v1;

import dk.dtu.model.exceptions.AdaptorException;

public interface IWeightAdaptor {
	
	public void establishConnection(String ip, int port) throws AdaptorException;
	public void setupUnit() throws AdaptorException;
	
	public String getOperatorId() throws AdaptorException;
	public void confirmOperatorName(String operatorName) throws AdaptorException;
	public String getOperatorPassword() throws AdaptorException;
	public void loginResult(boolean result) throws AdaptorException;
	
	public String getProductBatchNumber() throws AdaptorException;
	public void confirmRecipeName(String recipeName) throws AdaptorException;
	public void startWeighingProcess(String produceName) throws AdaptorException;
	
	public void tara() throws AdaptorException;
	public String placeTara() throws AdaptorException;
	public String placeNetto(double nomNetto) throws AdaptorException;
	public void clearSecondaryDisplay() throws AdaptorException;
	public String getProduceBatchNumber() throws AdaptorException;
	public String removeGross() throws AdaptorException;
	public void grossCheck(boolean result) throws AdaptorException;
	void clearBothDisplays() throws AdaptorException;
	void writeInSecondaryDisplay(String msg) throws AdaptorException;
	

	
	
}
