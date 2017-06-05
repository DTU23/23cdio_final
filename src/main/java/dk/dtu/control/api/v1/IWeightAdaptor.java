package dk.dtu.control.api.v1;

public interface IWeightAdaptor {
	
	public void establishConnection(String ip, int port) throws AdaptorException;
	public void setupUnit() throws AdaptorException;
	
	public String getOperatorId() throws AdaptorException;
	public String getOperatorPassword() throws AdaptorException;
	public void loginResult(boolean result) throws AdaptorException;
	
	public String getProductBatchNumber() throws AdaptorException;
	public void confirmRecipeName(String recipeName) throws AdaptorException;
	public void startWeighingProcess(String produceName) throws AdaptorException;
	
	public String placeTara() throws AdaptorException;
	public String placeNetto() throws AdaptorException;
	public String removeGross() throws AdaptorException;
	public void grossCheck(boolean result) throws AdaptorException;
	
}
