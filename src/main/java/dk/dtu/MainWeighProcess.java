package main.java.dk.dtu;

import main.java.dk.dtu.control.IWeightProcessController;
import main.java.dk.dtu.control.WeightProcessController;

public class MainWeighProcess {

	public static void main(String[] args) {

		IWeightProcessController weightProcessController = new WeightProcessController("localhost", 8000);
		weightProcessController.run();
		
	}

}
