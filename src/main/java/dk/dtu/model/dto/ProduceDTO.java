package dk.dtu.model.dto;

public class ProduceDTO
{
    /** i omraadet 1-99999999 vaelges af brugerne */
    private int produceId;
    /** min. 2 max. 20 karakterer */
    private String produceName;
    /** min. 2 max. 20 karakterer */
    private String supplier;

    public ProduceDTO(){}
    
    public ProduceDTO(int produceId, String produceName, String supplier)
    {
        this.produceId = produceId;
        this.produceName = produceName;
        this.supplier = supplier;
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
}
