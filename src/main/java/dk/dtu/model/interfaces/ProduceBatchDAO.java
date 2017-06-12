package dk.dtu.model.interfaces;

import java.util.List;

import dk.dtu.model.dto.ProduceBatchDTO;
import dk.dtu.model.dto.StockDTO;
import dk.dtu.model.exceptions.dal.ConnectivityException;
import dk.dtu.model.exceptions.dal.IntegrityConstraintViolationException;
import dk.dtu.model.exceptions.dal.NotFoundException;

public interface ProduceBatchDAO {
	void createProduceBatch(int produce_id, double amount) throws ConnectivityException, IntegrityConstraintViolationException;
	ProduceBatchDTO readProduceBatch(int rbId) throws ConnectivityException, NotFoundException;
	void updateProduceBatch(int produce_id, double amount) throws ConnectivityException, NotFoundException, IntegrityConstraintViolationException;
	void deleteProduceBatch(int rbId) throws ConnectivityException, NotFoundException, IntegrityConstraintViolationException;
	List<ProduceBatchDTO> getProduceBatchList() throws ConnectivityException, NotFoundException;
	ProduceBatchDTO getProduceBatchWithProduceName(int rbId) throws ConnectivityException, NotFoundException;
	List<StockDTO> getProduceInStock() throws ConnectivityException, NotFoundException;
}
