package dk.dtu.control.api.v1;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import dk.dtu.model.connector.Connector;
import dk.dtu.model.interfaces.DALException;

@Path("/v1/database")
public class DatabaseService {

	@GET
	@Path("/reset")
	public void resetDatabase() throws DALException {
		if (Connector.getInstance().resetData() == 0) {
			throw new DALException("No rows affected!");
		}
	}
	
}
