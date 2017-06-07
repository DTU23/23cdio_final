package dk.dtu.model.dto;

public class RecipeDTO {
	
	int recipeId;
	String recipeName;

	public RecipeDTO(int recipeId, String recipeName) {
		this.recipeId = recipeId;
		this.recipeName = recipeName;
	}

	public int getRecipeId() { return recipeId; }
	public void setRecipeId(int receptId) { this.recipeId = receptId; }
	public String getRecipeName() { return recipeName; }
	public void setRecipeName(String recipeName) { this.recipeName = recipeName; }
	public String toString() { return recipeId + "\t" + recipeName; }
	
}
