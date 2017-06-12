package dk.dtu.model.interfaces;

import java.util.List;

import dk.dtu.model.dto.OperatorDTO;
import dk.dtu.model.dto.OperatorNoPWDTO;
import dk.dtu.model.exceptions.dal.ConnectivityException;
import dk.dtu.model.exceptions.dal.IntegrityConstraintViolationException;
import dk.dtu.model.exceptions.dal.NotFoundException;

public interface OperatorDAO {
	void createOperator(OperatorDTO opr) throws ConnectivityException, IntegrityConstraintViolationException;
	OperatorDTO readOperator(int oprId) throws ConnectivityException, NotFoundException;
	OperatorNoPWDTO readOperatorNoPW(int oprId) throws ConnectivityException, NotFoundException;
	void updateOperator(OperatorDTO opr) throws ConnectivityException, NotFoundException, IntegrityConstraintViolationException;
	void deleteOperator(int oprId) throws ConnectivityException, NotFoundException, IntegrityConstraintViolationException;
	List<OperatorNoPWDTO> getOperatorList() throws ConnectivityException, NotFoundException;
}
