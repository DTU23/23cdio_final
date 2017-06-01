package dk.dtu.control.api.v1;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import dk.dtu.model.connector.Connector;

@Path("/v1/database")
public class DatabaseService {

	@GET
	@Path("/init")
	public void initDatabase(){
		try {
			new Connector();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
}
