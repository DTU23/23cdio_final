package dk.dtu.model.dto;

public class RecipeDTO
{
	int recipeId;
	String recipeName;

	public RecipeDTO(){}
	
	public RecipeDTO(int recipeId, String recipeName) {
		this.recipeId = recipeId;
		this.recipeName = recipeName;
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

	@Override
	public String toString() {
		return "RecipeDTO [recipeId=" + recipeId + ", recipeName=" + recipeName + "]";
	}
}
