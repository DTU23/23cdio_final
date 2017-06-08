package dk.dtu.model.dto;

public class ProduceOverviewDTO
{
    private int produceId;
    private String produceName;
    private String supplier;
    private double amount;
    
    public ProduceOverviewDTO(){}

	public ProduceOverviewDTO(int produceId, String produceName, String supplier, double amount)
	{
		this.produceId = produceId;
		this.produceName = produceName;
		this.supplier = supplier;
		this.amount = amount;
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
}
