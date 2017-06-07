package dk.dtu.model.interfaces;

import java.util.List;

import dk.dtu.model.dto.ProductBatchCompOverviewDTO;
import dk.dtu.model.dto.ProductBatchDTO;

public interface ProductBatchDAO {
	void createProductBatch(int recipe_id) throws DALException;
	ProductBatchDTO readProductBatch(int pbId) throws DALException;
	void updateProductBatchStatus(ProductBatchDTO productbatch) throws DALException;
	void deleteProductBatch(int pbId) throws DALException;
	ProductBatchCompOverviewDTO getProductBatchListDetailsByPbId(int productBatchID) throws DALException;
	List<ProductBatchCompOverviewDTO> getProductBatchDetailsByPbId(int productBatchID) throws DALException;
	List<ProductBatchDTO> getProductBatchList() throws DALException;
	boolean exists(int pbId) throws DALException;
}