package dk.dtu.control.api.v1;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import dk.dtu.control.api.Secured;
import dk.dtu.model.connector.DataSource;
import dk.dtu.model.exceptions.DALException;
import dk.dtu.control.api.Role;

@Path("/v1/database")
@Secured(roles = {Role.Pharmacist}, admin = true)
public class DatabaseService {

	@GET
	@Path("/reset")
	public void resetDatabase() throws DALException {
		if (DataSource.getInstance().resetData() == 0) {
			throw new DALException("No rows affected!");
		}
	}
	
}
