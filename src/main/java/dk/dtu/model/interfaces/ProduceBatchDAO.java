package dk.dtu.model.interfaces;

import java.util.List;

import dk.dtu.model.dto.ProduceBatchDTO;
import dk.dtu.model.dto.StockDTO;
import dk.dtu.model.exceptions.DALException;

public interface ProduceBatchDAO {
	void createProduceBatch(ProduceBatchDTO produceBatchDTO) throws DALException;
	ProduceBatchDTO readProduceBatch(int rbId) throws DALException;
	void updateProduceBatch(ProduceBatchDTO produceBatchDTO) throws DALException;
	void deleteProduceBatch(int rbId) throws DALException;
	List<ProduceBatchDTO> getProduceBatchList() throws DALException;
	ProduceBatchDTO getProduceBatchWithProduceName(int rbId) throws DALException;
	List<StockDTO> getProduceInStock() throws DALException;
}
