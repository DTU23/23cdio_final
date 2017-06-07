package dk.dtu.model.dto;

public class OperatorNoPWDTO
{
	/** Operatoer-identifikationsnummer (opr_id) i omraadet 1-99999999. Vaelges af brugerne */
	private int oprId;                     
	/** Operatoernavn (opr_navn) min. 2 max. 20 karakterer */
	private String oprName;                
	/** Operatoer-initialer min. 2 max. 3 karakterer */
	private String ini;                 
	/** Operatoer cpr-nr 10 karakterer */
	private String cpr;
	private boolean admin;
	private String role;
	
	public OperatorNoPWDTO(){}

	public OperatorNoPWDTO(int oprId, String oprName, String ini, String cpr, boolean admin, String role)
	{
		this.oprId = oprId;
		this.oprName = oprName;
		this.ini = ini;
		this.cpr = cpr;
		this.admin = admin;
		this.role = role;
	}
	
	/**
	 * Creates a DTO without password, from a DTO with password.
	 * @param opr
	 */
    public OperatorNoPWDTO(OperatorNoPWDTO opr)
    {
    	this.oprId = opr.getOprId();
    	this.oprName = opr.getOprName();
    	this.ini = opr.getIni();
    	this.cpr = opr.getCpr();
    	this.admin = opr.isAdmin();
    	this.role = opr.getRole();
    }

	public int getOprId() {
		return oprId;
	}

	public void setOprId(int oprId) {
		this.oprId = oprId;
	}

	public String getOprName() {
		return oprName;
	}

	public void setOprName(String oprName) {
		this.oprName = oprName;
	}

	public String getIni() {
		return ini;
	}

	public void setIni(String ini) {
		this.ini = ini;
	}

	public String getCpr() {
		return cpr;
	}

	public void setCpr(String cpr) {
		this.cpr = cpr;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
