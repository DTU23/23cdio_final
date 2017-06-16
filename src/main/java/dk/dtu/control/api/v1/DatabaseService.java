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
		if (DataSource.getInstance().resetData() == 0) {
			throw new DALException("No rows affected!");
		}
		return "Database reset successfully!";
	}
	
	@GET
	@Path("/users")
	public String insertUsers() throws DALException {
		if (DataSource.getInstance().insertUsers() == 0) {
			throw new DALException("No rows affected!");
		}
		return "Users inserted successfully!";
	}
	
	@GET
	@Path("/produce")
	public String insertProduce() throws DALException {
		if (DataSource.getInstance().insertProduce() == 0) {
			throw new DALException("No rows affected!");
		}
		return "Produce inserted successfully!";
	}
	
	@GET
	@Path("/recipe")
	public String insertRecipe() throws DALException {
		if (DataSource.getInstance().insertRecipe() == 0) {
			throw new DALException("No rows affected!");
		}
		return "Recipe inserted successfully!";
	}
	
	@GET
	@Path("/producebatch")
	public String insertProduceBatch() throws DALException {
		if (DataSource.getInstance().insertProduceBatch() == 0) {
			throw new DALException("No rows affected!");
		}
		return "Produce batch inserted successfully!";
	}
	
	@GET
	@Path("/productbatch")
	public String insertProductBatch() throws DALException {
		if (DataSource.getInstance().insertProductBatch() == 0) {
			throw new DALException("No rows affected!");
		}
		return "Product batch inserted successfully!";
	}
	
	@GET
	@Path("/productbatchcomp")
	public String insertProductBatchComp() throws DALException {
		if (DataSource.getInstance().insertProductBatchComp() == 0) {
			throw new DALException("No rows affected!");
		}
		return "Product batch components inserted successfully!";
	}
}
