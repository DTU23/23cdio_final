package dk.dtu.model.interfaces;

import java.util.List;

import dk.dtu.model.dto.OperatorDTO;
import dk.dtu.model.dto.OperatorNoPWDTO;

public interface OperatorDAO {
	OperatorDTO getOperator(int oprId) throws DALException;
	List<OperatorNoPWDTO> getOperatorList() throws DALException;
	void createOperator(OperatorDTO opr) throws DALException;
	void updateOperator(OperatorDTO opr) throws DALException;
}
