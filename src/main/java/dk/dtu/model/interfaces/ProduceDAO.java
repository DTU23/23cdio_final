package main.java.dk.dtu.model.interfaces;

import java.util.List;

import main.java.dk.dtu.model.dto.ProduceDTO;
import main.java.dk.dtu.model.dto.ProduceOverviewDTO;

public interface ProduceDAO {
	ProduceDTO getProduce(int raavareId) throws DALException;
	List<ProduceDTO> getProduceList() throws DALException;
	void createProduce(ProduceDTO raavare) throws DALException;
	void updateProduce(ProduceDTO raavare) throws DALException;
	List<ProduceOverviewDTO> getProduceOverview() throws DALException;
}