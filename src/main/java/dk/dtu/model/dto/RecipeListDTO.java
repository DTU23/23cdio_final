package dk.dtu.model.dto;

public class RecipeListDTO
{
	private int recipeId;
	private String recipeName;
	private int produceId;
	private String produceName;
	private double nomNetto;
	private double tolerance;
	
	public RecipeListDTO(){}
	
	public RecipeListDTO(int recipeId, String recipeName, int produceId, String produceName, double nomNetto, double tolerance) {
		this.recipeId = recipeId;
		this.recipeName = recipeName;
		this.produceId = produceId;
		this.produceName = produceName;
		this.nomNetto = nomNetto;
		this.tolerance = tolerance;
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

	public double getNomNetto() {
		return nomNetto;
	}

	public void setNomNetto(double nomNetto) {
		this.nomNetto = nomNetto;
	}

	public double getTolerance() {
		return tolerance;
	}

	public void setTolerance(double tolerance) {
		this.tolerance = tolerance;
	}
}
