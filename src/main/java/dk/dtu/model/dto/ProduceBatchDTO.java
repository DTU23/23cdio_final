package dk.dtu.model.dto;

public class ProduceBatchDTO
{
	private int rbId;                     // i omraadet 1-99999999
	private int produceId;
	private String produceName;             // i omraadet 1-99999999
	private String supplier;             // kan vaere negativ
	private double amount;
	
	public ProduceBatchDTO(){}

	public ProduceBatchDTO(int rbId, Integer produceId, String produceName, String supplier, double amount)
	{
		this.rbId = rbId;
		this.produceId = produceId;
		this.produceName = produceName;
		this.supplier = supplier;
		this.amount = amount;
	}

	public int getRbId() {
		return rbId;
	}

	public void setRbId(int rbId) {
		this.rbId = rbId;
	}

	public int getProduceId() {
		return produceId;
	}

	public void setProduceId(int produceId) {
		this.produceId = produceId;
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

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "ProduceBatchDTO [rbId=" + rbId + ", produceId=" + produceId + ", produceName=" + produceName
				+ ", supplier=" + supplier + ", amount=" + amount + "]";
	}
}