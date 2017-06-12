package dk.dtu.model.interfaces;

import java.util.List;

import dk.dtu.model.dto.ProduceDTO;
import dk.dtu.model.dto.ProduceOverviewDTO;
import dk.dtu.model.exceptions.dal.ConnectivityException;
import dk.dtu.model.exceptions.dal.IntegrityConstraintViolationException;
import dk.dtu.model.exceptions.dal.NotFoundException;

public interface ProduceDAO {
	void createProduce(ProduceDTO raavare) throws ConnectivityException, IntegrityConstraintViolationException;
	ProduceDTO readProduce(int raavareId) throws ConnectivityException, NotFoundException;
	void updateProduce(ProduceDTO raavare) throws ConnectivityException, NotFoundException, IntegrityConstraintViolationException;
	void deleteProduce(int raavareId) throws ConnectivityException, NotFoundException, IntegrityConstraintViolationException;
	List<ProduceDTO> getProduceList() throws ConnectivityException, NotFoundException;
	List<ProduceOverviewDTO> getProduceOverview() throws ConnectivityException, NotFoundException;
}
