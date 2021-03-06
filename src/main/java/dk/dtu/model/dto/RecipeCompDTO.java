package dk.dtu.model.dto;

public class RecipeCompDTO
{
	int recipeId;                  // auto genereres fra 1..n   
	int produceId;             // i omraadet 1-99999999
	double nomNetto;            // skal vaere positiv og passende stor
	double tolerance;           // skal vaere positiv og passende stor
	
	public RecipeCompDTO(){}
	
	public RecipeCompDTO(int recipeId, int produceId, double nomNetto, double tolerance) {
		this.recipeId = recipeId;
		this.produceId = produceId;
		this.nomNetto = nomNetto;
		this.tolerance = tolerance;
	}

	public int getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(int recipeId) {
		this.recipeId = recipeId;
	}

	public int getProduceId() {
		return produceId;
	}

	public void setProduceId(int produceId) {
		this.produceId = produceId;
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

	@Override
	public String toString() {
		return "RecipeCompDTO [recipeId=" + recipeId + ", produceId=" + produceId + ", nomNetto=" + nomNetto
				+ ", tolerance=" + tolerance + "]";
	}
}
