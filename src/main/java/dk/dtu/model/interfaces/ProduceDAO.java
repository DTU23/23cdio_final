package dk.dtu.model.interfaces;

import java.util.List;

import dk.dtu.model.dto.ProduceDTO;
import dk.dtu.model.dto.ProduceOverviewDTO;
import dk.dtu.model.exceptions.DALException;

public interface ProduceDAO {
	void createProduce(ProduceDTO produceDTO) throws DALException;
	ProduceDTO readProduce(int produce_id) throws DALException;
	void updateProduce(ProduceDTO produceDTO) throws DALException;
	void deleteProduce(int produce_id) throws DALException;
	List<ProduceDTO> getProduceList() throws DALException;
	List<ProduceOverviewDTO> getProduceOverview() throws DALException;
}
