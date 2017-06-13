package dk.dtu.model.interfaces;

import java.util.List;

import dk.dtu.model.dto.ProductBatchCompOverviewDTO;
import dk.dtu.model.dto.ProductBatchDTO;
import dk.dtu.model.dto.ProductBatchListDTO;
import dk.dtu.model.exceptions.DALException;

public interface ProductBatchDAO {
	void createProductBatch(ProductBatchDTO producBatchDTO) throws DALException;
	ProductBatchDTO readProductBatch(int pbId) throws DALException;
	void updateProductBatch(ProductBatchDTO producBatchDTO) throws DALException;
	void deleteProductBatch(int pbId) throws DALException;
	List<ProductBatchListDTO> getProductBatchList() throws DALException;
	ProductBatchListDTO getProductBatchListDetailsByPbId(int pbId) throws DALException;
	List<ProductBatchCompOverviewDTO> getProductBatchDetailsByPbId(int pbId) throws DALException;
}