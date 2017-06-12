package dk.dtu.model.interfaces;

import java.util.List;

import dk.dtu.model.dto.ProductBatchCompDTO;
import dk.dtu.model.dto.ProductBatchCompOverviewDTO;
import dk.dtu.model.dto.ProductBatchCompSupplierDetailsDTO;
import dk.dtu.model.exceptions.dal.ConnectivityException;
import dk.dtu.model.exceptions.dal.IntegrityConstraintViolationException;
import dk.dtu.model.exceptions.dal.NotFoundException;

public interface ProductBatchCompDAO {
	void createProductBatchComp(ProductBatchCompDTO productbatchcomponent) throws ConnectivityException, IntegrityConstraintViolationException;
	ProductBatchCompDTO readProductBatchComp(int pbId, int rbId) throws ConnectivityException, NotFoundException;
	void updateProductBatchComp(ProductBatchCompDTO productBatchComponent) throws ConnectivityException, NotFoundException, IntegrityConstraintViolationException;
	void deleteProductBatchComp(int pbId, int rbId) throws ConnectivityException, NotFoundException, IntegrityConstraintViolationException;
	List<ProductBatchCompDTO> getProductBatchCompList() throws ConnectivityException, NotFoundException;
	List<ProductBatchCompOverviewDTO> getProductBatchCompOverview() throws ConnectivityException, NotFoundException;
	List<ProductBatchCompSupplierDetailsDTO> getProductBatchComponentSupplierDetailsByPbId(int pbId) throws ConnectivityException, NotFoundException;
}
