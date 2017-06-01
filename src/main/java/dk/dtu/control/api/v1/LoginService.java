package dk.dtu.control.api.v1;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import dk.dtu.model.dto.OperatorDTO;

@Path("/v1/login")
@Consumes(MediaType.APPLICATION_JSON)
public class LoginService {

	@POST
	public boolean passwordCheck(OperatorDTO opr) {
//		if(opr.getOprName().equals("admin") && opr.getPassword().equals("admin"))
			return true;
//		return false;
	}
}
