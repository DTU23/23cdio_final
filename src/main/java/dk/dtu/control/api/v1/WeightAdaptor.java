package dk.dtu.control.api.v1;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

import dk.dtu.model.exceptions.AdaptorException;

public class WeightAdaptor implements IWeightAdaptor {

	private Socket clientSocket; 
	private BufferedReader input;
	private DataOutputStream output;

	public WeightAdaptor() {}

	// Sends a command to the simulator
	private void sendCommand(String command) throws IOException {
		output.writeBytes(command + "\r\n");
		output.flush();
		System.out.println("Bruger sendte kommandoen: " + command);
	}

	// Reads the response from the simulator
	private String nextResponse() throws IOException {
		String nextLine = input.readLine();
		if(nextLine != null) {
			System.out.println(nextLine);
		}
		return nextLine;
	}

	// Takes our nextResponse method and adds a wait time of 1 second
	private String waitResponse() throws IOException, InterruptedException {
		String response = nextResponse();
		while(response == null) {
			TimeUnit.MILLISECONDS.sleep(1);
			response = nextResponse();
		}
		return response;
	}
	
	private String getNumberFromResponse(String response) {
		String[] arr = response.split("\\.");
		return arr[0].split(" ")[arr[0].split(" ").length-1] + "." +  arr[1].split(" ")[0];
	}

	@Override
	public void clearInputBuffer() throws AdaptorException {
		try {
			TimeUnit.SECONDS.sleep(1);
			// If there's any data buffered in the socket it is cleared
			while(input.ready()) {
				input.readLine();
			}
		} catch (IOException | InterruptedException e) {
			throw new AdaptorException(e);
		}		
	}
	
	@Override
	public void establishConnection(String ip, int port) throws AdaptorException {
		try {
			clientSocket = new Socket(ip, port);
			input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); 
			output = new DataOutputStream(clientSocket.getOutputStream());
			clearInputBuffer();
		} catch (IOException e) {
			throw new AdaptorException(e);
		}
	}

	@Override
	public void setupUnit() throws AdaptorException {
		try {
			sendCommand("K 3");
			waitResponse();
			sendCommand("K 3");
			waitResponse();
		} catch (Exception e) {
			throw new AdaptorException(e);
		}
	}

	@Override
	public String getOperatorId() throws AdaptorException {
		try {
			sendCommand("RM20 8 \"OPR NR?\" \"\" \"&3\"");
			waitResponse();
			return waitResponse().split("\"")[1];
		} catch (Exception e) {
			throw new AdaptorException(e);
		}	
	}
	
	@Override
	public void confirmOperatorName(String operatorName) throws AdaptorException {
		try {
			sendCommand("P111 \""+operatorName+" [->\"");
			waitResponse();
			waitResponse();
			sendCommand("P111 \"\"");
			waitResponse();
		} catch (Exception e) {
			throw new AdaptorException(e);
		}	
	}

	@Override
	public String getOperatorPassword() throws AdaptorException {
		try {
			sendCommand("RM20 8 \"OPR PW?\" \"\" \"&3\"");
			waitResponse();
			return waitResponse().split("\"")[1];
		} catch (Exception e) {
			throw new AdaptorException(e);
		}
	}

	@Override
	public void loginResult(boolean result) throws AdaptorException {
		try {
			if(result) {
				sendCommand("P111 \"Login OK! [->\"");
				waitResponse();
				waitResponse();
				sendCommand("P111 \"\"");
				waitResponse();
			} else {
				sendCommand("P111 \"Login failed [->\"");
				waitResponse();
				waitResponse();
				sendCommand("P111 \"\"");
				waitResponse();
			}
		} catch (Exception e) {
			throw new AdaptorException(e);
		}
	}

	@Override
	public String getProductBatchNumber() throws AdaptorException {
		try {
			sendCommand("RM20 8 \"PB ID?\" \"\" \"&3\"");
			waitResponse();
			return waitResponse().split("\"")[1];
		} catch (Exception e) {
			throw new AdaptorException(e);
		}
	}

	@Override
	public void confirmRecipeName(String recipeName) throws AdaptorException {
		try {
			sendCommand("P111 \"" + recipeName + " [->\"");
			waitResponse();
			waitResponse();
		} catch (Exception e) {
			throw new AdaptorException(e);
		}
	}

	@Override
	public void startWeighingProcess(String produceName) throws AdaptorException {
		try {
			sendCommand("P111 \"" + produceName + " [->\"");
			waitResponse();
			waitResponse();
		} catch (Exception e) {
			throw new AdaptorException(e);
		}
	}

	@Override
	public void tara() throws AdaptorException {
		try {
			sendCommand("T");
			waitResponse();
		} catch (Exception e) {
			throw new AdaptorException(e);
		}
	}
	
	@Override
	public String placeTara() throws AdaptorException {
		try {
			sendCommand("P111 \"Place tara [->\"");
			waitResponse();
			waitResponse();
			sendCommand("S");
			return getNumberFromResponse(waitResponse());
		} catch (Exception e) {
			throw new AdaptorException(e);
		}
	}

	@Override
	public String placeNetto(double nomNetto) throws AdaptorException {
		try {
			sendCommand("P111 \"Place netto, " + new DecimalFormat("0.000").format(nomNetto).replace(',', '.') +" kg [->\"");
			waitResponse();
			waitResponse();
			sendCommand("S");
			return getNumberFromResponse(waitResponse());
		} catch (Exception e) {
			throw new AdaptorException(e);
		}
	}
	
	@Override
	public void clearSecondaryDisplay() throws AdaptorException {
		try {
			sendCommand("P111 \"\"");
			waitResponse();
		} catch (Exception e) {
			throw new AdaptorException(e);
		}
	}
	
	@Override
	public String getProduceBatchNumber() throws AdaptorException {
		try {
			sendCommand("RM20 8 \"RB ID?\" \"\" \"&3\"");
			waitResponse();
			return waitResponse().split("\"")[1];
		} catch (Exception e) {
			throw new AdaptorException(e);
		}
	}

	@Override
	public String removeGross() throws AdaptorException {
		try {
			sendCommand("P111 \"Remove gross [->\"");
			waitResponse();
			waitResponse();
			sendCommand("S");
			return getNumberFromResponse(waitResponse());
		} catch (Exception e) {
			throw new AdaptorException(e);
		}
	}

	@Override
	public void grossCheck(boolean result) throws AdaptorException {
		try {
			if(result) {
				sendCommand("P111 \"Weighing OK! [->");
				waitResponse();
				waitResponse();
				sendCommand("T");
				waitResponse();
			} else {
				sendCommand("P111 \"Weighing not OK! [->");
				waitResponse();
				waitResponse();
				sendCommand("T");
				waitResponse();
			}
		} catch (Exception e) {
			throw new AdaptorException(e);
		}
	}
	
	@Override
	public void clearBothDisplays() throws AdaptorException {
		try {
			sendCommand("T");
			waitResponse();
			sendCommand("P111 \"\"");
			waitResponse();
		} catch (Exception e) {
			throw new AdaptorException(e);
		}
	}
	
	@Override
	public void writeInSecondaryDisplay(String msg) throws AdaptorException {
		try {
			sendCommand("P111 \"" + msg + " [->\"");
			waitResponse();
			waitResponse();
		} catch (Exception e) {
			throw new AdaptorException(e);
		}
	}

}
