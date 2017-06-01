package dk.dtu.model.dto;

public class ProductBatchDTO
{
	private int pbId;                     // i omraadet 1-99999999
	private int status;					// 0: ikke paabegyndt, 1: under produktion, 2: afsluttet
	private int recipeId;

	public ProductBatchDTO(int pbId, int status, int recipeId)
	{
		this.pbId = pbId;
		this.status = status;
		this.recipeId = recipeId;
	}

	public int getPbId() { return pbId; }
	public void setPbId(int pbId) { this.pbId = pbId; }
	public int getStatus() { return status; }
	public void setStatus(int status) { this.status = status; }
	public int getRecipeId() { return recipeId; }
	public void setRecipeId(int recipeId) { this.recipeId = recipeId; }
	public String toString() { return pbId + "\t" + status + "\t" + recipeId; }
}

