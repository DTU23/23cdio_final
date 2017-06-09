package dk.dtu.control.api.v1;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import dk.dtu.control.api.Secured;
import dk.dtu.model.connector.Connector;
import dk.dtu.model.interfaces.DALException;
import dk.dtu.control.api.Role;

@Path("/v1/database")
@Secured(roles = {Role.Pharmacist}, admin = true)
public class DatabaseService {

	@GET
	@Path("/reset")
	public void resetDatabase() throws DALException {
		if (Connector.getInstance().resetData() == 0) {
			throw new DALException("No rows affected!");
		}
	}
	
}
