package main.java.dk.dtu.control.api.v1;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import main.java.dk.dtu.model.dao.MySQLOperatorDAO;
import main.java.dk.dtu.model.dto.OperatorDTO;
import main.java.dk.dtu.model.interfaces.DALException;
import main.java.dk.dtu.model.interfaces.OperatorDAO;

@Path("/v1/login")
public class LoginService {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response passwordCheck(OperatorDTO opr) {
		try {

			// Authenticate the user using the credentials provided
			authenticate(opr.getOprId(), opr.getPassword());

			// Issue a token for the user
			String token = issueToken(opr.getOprId());

			// Return the token on the response
			return Response.ok(token).build();

		} catch (Exception e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}

	private void authenticate(int oprId, String password) throws Exception {
		OperatorDAO oprDao = new MySQLOperatorDAO();
		OperatorDTO sysOpr = oprDao.readOperator(oprId);
		if (!sysOpr.getPassword().equals(password)) {
			throw new DALException("Wrong Password!");
		}
	}

	private String issueToken(int oprId) throws DALException {
		OperatorDAO oprDAO = new MySQLOperatorDAO();
		try {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, 7);
			Algorithm algorithm = Algorithm.HMAC256("secret");
			return JWT.create()
					.withIssuer("auth0")
					.withClaim("oprId", oprId)
					.withClaim("name", oprDAO.readOperator(oprId).getOprName())
					.withClaim("role", oprDAO.readOperator(oprId).getRole())
					.withClaim("admin", oprDAO.readOperator(oprId).getAdmin())
					.withExpiresAt(cal.getTime())
					.sign(algorithm);
		} catch (UnsupportedEncodingException | JWTCreationException ignored){

		}
		return null;
	}
}
