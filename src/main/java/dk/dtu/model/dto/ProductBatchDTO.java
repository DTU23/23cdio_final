package dk.dtu.model.dto;

public class ProductBatchDTO
{
	private int pbId;                     // i omraadet 1-99999999
	private int recipeId;
	private int status;					// 0: ikke paabegyndt, 1: under produktion, 2: afsluttet

	public ProductBatchDTO(){}
	
	public ProductBatchDTO(int pbId, int recipeId, int status)
	{
		this.pbId = pbId;
		this.recipeId = recipeId;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}

