package dk.dtu.model.dto;

public class ProductBatchCompOverviewDTO
{
	private int pbId;
	private int rbId;
	private int recipeId;
	private String recipeName;
	private int status;
	private String produceName;
	private double netto;
	private int oprId;

	public ProductBatchCompOverviewDTO(){}

	public ProductBatchCompOverviewDTO(int pbId, int rbId, int recipeId, String recipeName, int status, String produceName, double netto, int operatorId){
		this.pbId = pbId;
		this.rbId = rbId;
		this.recipeId = recipeId;
		this.recipeName = recipeName;
		this.status = status;
		this.produceName = produceName;
		this.netto = netto;
		this.oprId = operatorId;
	}

	public int getPbId() {
		return pbId;
	}

	public void setPbId(int pbId) {
		this.pbId = pbId;
	}

	public int getRbId() {
		return rbId;
	}

	public void setRbId(int rbId) {
		this.rbId = rbId;
	}

	public int getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(int recipeId) {
		this.recipeId = recipeId;
	}

	public String getRecipeName() {
		return recipeName;
	}

	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getProduceName() {
		return produceName;
	}

	public void setProduceName(String produceName) {
		this.produceName = produceName;
	}

	public double getNetto() {
		return netto;
	}

	public void setNetto(double netto) {
		this.netto = netto;
	}

	public int getOprId() {
		return oprId;
	}

	public void setOprId(int oprId) {
		this.oprId = oprId;
	}
}