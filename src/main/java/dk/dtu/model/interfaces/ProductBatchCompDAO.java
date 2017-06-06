package main.java.dk.dtu.model.interfaces;

import java.util.List;

import main.java.dk.dtu.model.dto.ProductBatchCompDTO;
import main.java.dk.dtu.model.dto.ProductBatchCompOverviewDTO;
import main.java.dk.dtu.model.dto.ProductBatchCompSupplierDetailsDTO;

public interface ProductBatchCompDAO {
	void createProductBatchComp(ProductBatchCompDTO productbatchcomponent) throws DALException;
	ProductBatchCompDTO readProductBatchComp(int pbId, int rbId) throws DALException;
	void updateProductBatchComp(ProductBatchCompDTO productBatchComponent) throws DALException;
	void deleteProductBatchComp(int pbId, int rbId) throws DALException;
	List<ProductBatchCompDTO> getProductBatchCompList() throws DALException;
	List<ProductBatchCompOverviewDTO> getProductBatchCompOverview() throws DALException;
	List<ProductBatchCompSupplierDetailsDTO> getSupplierDetailById(int pbId) throws DALException;
}
