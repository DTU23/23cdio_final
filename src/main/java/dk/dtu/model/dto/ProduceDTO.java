package main.java.dk.dtu.model.dto;

/**
 * Produce Data Objekt
 *
 * @author mn/sh/tb
 * @version 1.2
 */

public class ProduceDTO
{
    /** i omraadet 1-99999999 vaelges af brugerne */
    private int produceId;
    /** min. 2 max. 20 karakterer */
    private String produceName;
    /** min. 2 max. 20 karakterer */
    private String supplier;

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
        return this.produceName;
    }
    public void setProduceName(String produceName) {
        this.produceName = produceName;
    }
    public String getSupplier() {
        return this.supplier;
    }
    public void setSupplier(String amount) {
        this.supplier = amount;
    }
    public String toString() {
        return this.produceId + "\t" + this.produceName +"\t" + this.supplier;
    }
}
