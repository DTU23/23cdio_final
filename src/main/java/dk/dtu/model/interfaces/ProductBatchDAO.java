package main.java.dk.dtu.model.interfaces;

import java.util.List;

import main.java.dk.dtu.model.dto.ProductBatchDTO;

public interface ProductBatchDAO {
	ProductBatchDTO getProductBatch(int pbId) throws DALException;
	List<ProductBatchDTO> getProductBatchList() throws DALException;
	void createProductBatch(int recipe_id) throws DALException;
	void updateProductBatchStatus(ProductBatchDTO productbatch) throws DALException;
}