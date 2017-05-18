package main.java.dk.dtu.model.interfaces;

import java.util.List;

import main.java.dk.dtu.model.dto.ProduceBatchDTO;

public interface ProduceBatchDAO {
	ProduceBatchDTO getProduceBatch(int rbId) throws DALException;
	List<ProduceBatchDTO> getProduceBatchList() throws DALException;
	void createProduceBatch(int produce_id, double amount) throws DALException;
	void updateProduceBatch(int produce_id, double amount) throws DALException;
}
