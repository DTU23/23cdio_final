package dk.dtu.model.interfaces;

import java.util.List;

import dk.dtu.model.dto.ProduceBatchDTO;

public interface ProduceBatchDAO {
	ProduceBatchDTO getProduceBatch(int rbId) throws DALException;
	List<ProduceBatchDTO> getProduceBatchList() throws DALException;
	void createProduceBatch(int produce_id, double amount) throws DALException;
	void updateProduceBatch(int produce_id, double amount) throws DALException;
	void deleteProduceBatch(int rbId) throws DALException;
}
