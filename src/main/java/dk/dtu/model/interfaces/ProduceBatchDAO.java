package main.java.dk.dtu.model.interfaces;

import java.util.List;

import main.java.dk.dtu.model.dto.ProduceBatchDTO;

public interface ProduceBatchDAO {
	void createProduceBatch(int produce_id, double amount) throws DALException;
	ProduceBatchDTO readProduceBatch(int rbId) throws DALException;
	void updateProduceBatch(int produce_id, double amount) throws DALException;
	void deleteProduceBatch(int rbId) throws DALException;
	ProduceBatchDTO getProduceBatchWithProduceName(int rbId) throws DALException;
	List<ProduceBatchDTO> getProduceBatchList() throws DALException;
}
