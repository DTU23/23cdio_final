package dk.dtu.model.interfaces;

import java.util.List;

import dk.dtu.model.dto.ProductBatchCompOverviewDTO;
import dk.dtu.model.dto.ProductBatchDTO;
import dk.dtu.model.dto.ProductBatchListDTO;

public interface ProductBatchDAO {
	void createProductBatch(int recipe_id) throws DALException;
	ProductBatchDTO readProductBatch(int pbId) throws DALException;
	void updateProductBatch(ProductBatchDTO productbatch) throws DALException;
	void deleteProductBatch(int pbId) throws DALException;
	List<ProductBatchListDTO> getProductBatchList() throws DALException;
	ProductBatchListDTO getProductBatchListDetailsByPbId(int pbId) throws DALException;
	List<ProductBatchCompOverviewDTO> getProductBatchDetailsByPbId(int pbId) throws DALException;
}