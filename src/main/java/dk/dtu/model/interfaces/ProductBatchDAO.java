package dk.dtu.model.interfaces;

import java.util.List;

import dk.dtu.model.dto.ProductBatchCompOverviewDTO;
import dk.dtu.model.dto.ProductBatchDTO;
import dk.dtu.model.dto.ProductBatchListDTO;
import dk.dtu.model.exceptions.dal.ConnectivityException;
import dk.dtu.model.exceptions.dal.IntegrityConstraintViolationException;
import dk.dtu.model.exceptions.dal.NotFoundException;

public interface ProductBatchDAO {
	void createProductBatch(int recipe_id) throws ConnectivityException, IntegrityConstraintViolationException;
	ProductBatchDTO readProductBatch(int pbId) throws ConnectivityException, NotFoundException;
	void updateProductBatch(ProductBatchDTO productbatch) throws ConnectivityException, NotFoundException, IntegrityConstraintViolationException;
	void deleteProductBatch(int pbId) throws ConnectivityException, NotFoundException, IntegrityConstraintViolationException;
	List<ProductBatchListDTO> getProductBatchList() throws ConnectivityException, NotFoundException;
	ProductBatchListDTO getProductBatchListDetailsByPbId(int pbId) throws ConnectivityException, NotFoundException;
	List<ProductBatchCompOverviewDTO> getProductBatchDetailsByPbId(int pbId) throws ConnectivityException, NotFoundException;
}