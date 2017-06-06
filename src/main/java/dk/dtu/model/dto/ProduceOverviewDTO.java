package main.java.dk.dtu.model.dto;

/**
 * Produce Data Objekt
 * 
 * @author mn/sh/tb
 * @version 1.2
 */
public class ProduceOverviewDTO
{
    private int produceId;
    private String produceName;
    private double amount;

	public ProduceOverviewDTO(int produceId, String produceName, double amount)
	{
		this.produceId = produceId;
		this.produceName = produceName;
		this.amount = amount;
	}

    public int getProduceId() {
	    return produceId;
	}
    public void setProduceId(int produceId) {
	    this.produceId = produceId;
	}
    public String getProduceName() {
	    return this.produceName;
	}
    public void setProduceName(String produceName) {
	    this.produceName = produceName;
	}
    public double getAmount() {
	    return this.amount;
	}
    public void setAmount(double amount) {
	    this.amount = amount;
	}
    public String toString() { 
		return this.produceId + "\t" + this.produceName +"\t" + this.amount;
	}
}
