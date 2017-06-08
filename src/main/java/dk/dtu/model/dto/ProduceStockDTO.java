package dk.dtu.model.dto;

public class ProduceStockDTO
{

	private String produceName;
    private double stock;
    
	public ProduceStockDTO() {}

	public ProduceStockDTO(String produceName, double stock) {
		this.produceName = produceName;
		this.stock = stock;
	}

	public String getProduceName() {
		return produceName;
	}

	public void setProduceName(String produceName) {
		this.produceName = produceName;
	}

	public double getStock() {
		return stock;
	}

	public void setStock(double stock) {
		this.stock = stock;
	}

	@Override
	public String toString() {
		return "ProduceStockDTO [produceName=" + produceName + ", stock=" + stock + "]";
	}
}
