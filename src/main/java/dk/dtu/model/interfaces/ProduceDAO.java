package dk.dtu.model.interfaces;

import java.util.List;

import dk.dtu.model.dto.ProduceDTO;
import dk.dtu.model.dto.ProduceOverviewDTO;

public interface ProduceDAO {
	ProduceDTO getProduce(int raavareId) throws DALException;
	List<ProduceDTO> getProduceList() throws DALException;
	void createProduce(ProduceDTO raavare) throws DALException;
	void updateProduce(ProduceDTO raavare) throws DALException;
	void deleteProduce(int raavareId) throws DALException;
	List<ProduceOverviewDTO> getProduceOverview() throws DALException;
}
