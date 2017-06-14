package dk.dtu;

import dk.dtu.control.IWeightProcessController;
import dk.dtu.control.WeightProcessController;

public class MainWeighProcess {
	
	public static void main(String[] args) {

		IWeightProcessController weightProcessController = new WeightProcessController("localhost", 8000);
		weightProcessController.run();
		
	}

}
