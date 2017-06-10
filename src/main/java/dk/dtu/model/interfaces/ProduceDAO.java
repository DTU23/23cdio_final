package dk.dtu.model.interfaces;

import java.util.List;

import dk.dtu.model.dto.ProduceDTO;
import dk.dtu.model.dto.ProduceOverviewDTO;
import dk.dtu.model.exceptions.DALException;

public interface ProduceDAO {
	void createProduce(ProduceDTO raavare) throws DALException;
	ProduceDTO readProduce(int raavareId) throws DALException;
	void updateProduce(ProduceDTO raavare) throws DALException;
	void deleteProduce(int raavareId) throws DALException;
	List<ProduceDTO> getProduceList() throws DALException;
	List<ProduceOverviewDTO> getProduceOverview() throws DALException;
}
