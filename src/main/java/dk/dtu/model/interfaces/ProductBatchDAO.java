package dk.dtu.model.interfaces;

import java.util.List;

import dk.dtu.model.dto.ProductBatchCompOverviewDTO;
import dk.dtu.model.dto.ProductBatchDTO;

public interface ProductBatchDAO {
	ProductBatchDTO getProductBatch(int pbId) throws DALException;
	List<ProductBatchDTO> getProductBatchList() throws DALException;
	void createProductBatch(int recipe_id) throws DALException;
	void updateProductBatchStatus(ProductBatchDTO productbatch) throws DALException;
	List<ProductBatchCompOverviewDTO> get_product_batch_details_by_pb_id(int productBatchID) throws DALException;
}