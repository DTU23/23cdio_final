package dk.dtu.model.dto;

public class ProductBatchCompSupplierDetailsDTO
{
	private int rbId;
	private String produceName;
	private String supplier;
	private double netto;
	private int OprId;
	
	public ProductBatchCompSupplierDetailsDTO(){}

	public ProductBatchCompSupplierDetailsDTO(int rbId, String supplier, String produceName, double netto, int operatorId){
		this.rbId = rbId;
		this.supplier = supplier;
		this.produceName = produceName;
		this.netto = netto;
		this.OprId = operatorId;
	}

	public int getrbId() {
		return rbId;
	}

	public void setrbId(int rbId) {
		this.rbId = rbId;
	}

	public String getProduceName() {
		return produceName;
	}

	public void setProduceName(String produceName) {
		this.produceName = produceName;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public double getNetto() {
		return netto;
	}

	public void setNetto(double netto) {
		this.netto = netto;
	}

	public int getOprId() {
		return OprId;
	}

	public void setOprId(int oprId) {
		OprId = oprId;
	}

	@Override
	public String toString() {
		return "ProductBatchCompSupplierDetailsDTO [rbId=" + rbId + ", produceName=" + produceName + ", supplier="
				+ supplier + ", netto=" + netto + ", OprId=" + OprId + "]";
	}
}
