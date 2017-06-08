package dk.dtu.model.dto;

public class StockDTO {

	private String produceName;
	private double currentStock;
	
	public StockDTO() {}

	public StockDTO(String produceName, double currentStock) {
		this.produceName = produceName;
		this.currentStock = currentStock;
	}

	public String getProduceName() {
		return produceName;
	}

	public void setProduceName(String produceName) {
		this.produceName = produceName;
	}

	public double getCurrentStock() {
		return currentStock;
	}

	public void setCurrentStock(double currentStock) {
		this.currentStock = currentStock;
	}

	@Override
	public String toString() {
		return "StockDTO [produceName=" + produceName + ", currentStock=" + currentStock + "]";
	}
}
