package dk.dtu.control.api.v1;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import dk.dtu.control.api.Secured;
import dk.dtu.model.connector.DataSource;
import dk.dtu.model.exceptions.DALException;
import dk.dtu.control.api.Role;

@Path("/v1/database")
@Secured(roles = {Role.None})
public class DatabaseService {

	@GET
	@Path("/reset")
	public String resetDatabase() throws DALException {
		DataSource.getInstance().resetData();
		return "Database reset successfully!";
	}
	
	@GET
	@Path("/users")
	public String insertUsers() throws DALException {
		DataSource.getInstance().insertUsers();
		return "Users inserted successfully!";
	}
	
	@GET
	@Path("/produce")
	public String insertProduce() throws DALException {
		DataSource.getInstance().insertProduce();
		return "Produce inserted successfully!";
	}
	
	@GET
	@Path("/recipe")
	public String insertRecipe() throws DALException {
		DataSource.getInstance().insertRecipe();
		return "Recipe inserted successfully!";
	}
	
	@GET
	@Path("/producebatch")
	public String insertProduceBatch() throws DALException {
		DataSource.getInstance().insertProduceBatch();
		return "Produce batch inserted successfully!";
	}
	
	@GET
	@Path("/productbatch")
	public String insertProductBatch() throws DALException {
		DataSource.getInstance().insertProductBatch();
		return "Product batch inserted successfully!";
	}
	
	@GET
	@Path("/productbatchcomp")
	public String insertProductBatchComp() throws DALException {
		DataSource.getInstance().insertProductBatchComp();
		return "Product batch components inserted successfully!";
	}
}
