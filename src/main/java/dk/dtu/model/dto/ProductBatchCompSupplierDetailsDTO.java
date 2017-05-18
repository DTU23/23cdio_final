package main.java.dk.dtu.model.dto;

public class ProductBatchCompSupplierDetailsDTO
{
	private int RbId;
	private String produceName;
	private String supplier;
	private double netto;
	private int OprId;


	public ProductBatchCompSupplierDetailsDTO(int RbId, String supplier, String produceName, double netto, int operatorId){
		this.RbId = RbId;
		this.supplier = supplier;
		this.produceName = produceName;
		this.netto = netto;
		this.OprId = operatorId;
	}
	
	public int getRbId() { return this.RbId; }
	public void setRbId(int RbId) { this.RbId = RbId; }
	public String getSupplier(){ return this.supplier; }
	public void setSupplier(String supplier){ this.supplier = supplier;}
	public String getProduceName(){ return this.produceName; }
	public void setProduceName(String produceName){ this.produceName = produceName; }
	public double getNetto() { return netto; }
	public void setNetto(double netto) { this.netto = netto; }
	public int getOprId() { return this.OprId; }
	public void setOprId(int oprId) { this.OprId = oprId; }
	public String toString() { 
		return RbId + "\t" + supplier +"\t" + produceName + "\t" + netto + "\t" + OprId;
	}
}
