package main.java.dk.dtu.model.dto;

public class ProduceBatchDTO
{
	private int rbId;                     // i omraadet 1-99999999
	private String produceName;             // i omraadet 1-99999999
	private String supplier;             // kan vaere negativ
	private double amount;

	public ProduceBatchDTO(int rbId, String produceName, String supplier, double amount)
	{
		this.rbId = rbId;
		this.produceName = produceName;
		this.supplier = supplier;
		this.amount = amount;
	}

	public int getId() { return this.rbId; }
	public void setId(int rbId) { this.rbId = rbId; }
	public String getProduceName() { return this.produceName; }
	public void setProduceName(String produceName) { this.produceName = produceName; }
	public double getAmount() { return this.amount; }
	public void setAmount(double amount) { this.amount = amount; }
	public String toString() {
		return this.rbId + "\t" + this.produceName +"\t" + this.supplier +"\t" + this.amount;
	}
}
