package dk.dtu.model.dto;

public class ProductBatchListDTO extends ProductBatchCompOverviewDTO {
	private int pbId;                     // i omraadet 1-99999999
	private int recipeId;
	String recipeName;
	private int status;					// 0: ikke paabegyndt, 1: under produktion, 2: afsluttet

	public ProductBatchListDTO(){}
	
	public ProductBatchListDTO(int pbId, int recipeId, String recipeName, int status)
	{
		this.pbId = pbId;
		this.recipeId = recipeId;
		this.recipeName = recipeName;
		this.status = status;
	}

	public int getPbId() {
		return pbId;
	}

	public void setPbId(int pbId) {
		this.pbId = pbId;
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

	@Override
	public String toString() {
		return "ProductBatchListDTO [pbId=" + pbId + ", recipeId=" + recipeId + ", recipeName=" + recipeName
				+ ", status=" + status + "]";
	}
}

