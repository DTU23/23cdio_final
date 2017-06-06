package main.java.dk.dtu.model.interfaces;

import java.util.List;

import main.java.dk.dtu.model.dto.OperatorDTO;
import main.java.dk.dtu.model.dto.OperatorNoPWDTO;

public interface OperatorDAO {
	void createOperator(OperatorDTO opr) throws DALException;
	OperatorDTO readOperator(int oprId) throws DALException;
	void updateOperator(OperatorDTO opr) throws DALException;
	void deleteOperator(int oprId) throws DALException;
	List<OperatorNoPWDTO> getOperatorList() throws DALException;
}
