package dk.dtu.model.dto;

public class OperatorNewPWDTO
{
	/** Operatoer-identifikationsnummer (opr_id) i omraadet 1-99999999. Vaelges af brugerne */
	private int oprId;
	/** Operatoernavn (opr_navn) min. 2 max. 20 karakterer */
	private String oprName;
	/** Operatoer-initialer min. 2 max. 3 karakterer */
	private String ini;
	/** Operatoer cpr-nr 10 karakterer */
	private String cpr;
	/** Operatoer password min. 7 max. 8 karakterer */
	private String password;
	private String newPassword;
	private boolean admin;
	private String role;

	public OperatorNewPWDTO(){}

	public OperatorNewPWDTO(int oprId, String oprName, String ini, String cpr, String password, boolean admin, String role)
	{
		this.oprId = oprId;
		this.oprName = oprName;
		this.ini = ini;
		this.cpr = cpr;
		this.password = password;
		this.admin = admin;
		this.role = role;
	}

    public OperatorNewPWDTO(OperatorNewPWDTO opr)
    {
    	this.oprId = opr.getOprId();
    	this.oprName = opr.getOprName();
    	this.ini = opr.getIni();
    	this.cpr = opr.getCpr();
    	this.password = opr.getPassword();
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
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

	@Override
	public String toString() {
		return "OperatorDTO [oprId=" + oprId + ", oprName=" + oprName + ", ini=" + ini + ", cpr=" + cpr + ", password="
				+ password + ", admin=" + admin + ", role=" + role + "]";
	}
}