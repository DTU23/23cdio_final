package dk.dtu.control;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import javax.ws.rs.core.Response;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import dk.dtu.model.Validation;
import dk.dtu.model.dao.MySQLOperatorDAO;
import dk.dtu.model.dto.OperatorDTO;
import dk.dtu.model.exceptions.AuthException;
import dk.dtu.model.exceptions.DALException;
import dk.dtu.model.exceptions.ValidationException;
import dk.dtu.model.interfaces.OperatorDAO;

public class LoginController implements ILoginController {

	@Override
	public Response authenticateUser(int oprId, String password) throws AuthException, DALException {
		try {
			// Authenticate the user using the credentials provided
			passwordCheck(oprId, password);

			// Issue a token for the user
			String token = issueToken(oprId);

			// Return the token on the response
			return Response.ok(token).build();

		} catch (Exception e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}

	@Override
	public void authenticateUser(String oprId, String password) throws ValidationException, AuthException, DALException {
		Validation.isPositiveInteger(oprId);
		passwordCheck(Integer.parseInt(oprId), password);
	}

	private void passwordCheck(int oprId, String password) throws AuthException, DALException {
		OperatorDAO dao = new MySQLOperatorDAO();
		OperatorDTO sysOpr = dao.readOperator(oprId);
		if (!sysOpr.getPassword().equals(password)) {
			throw new AuthException("Wrong Password!");
		}
	}

	private String issueToken(int oprId) throws AuthException, DALException {
		OperatorDAO dao = new MySQLOperatorDAO();
		OperatorDTO opr = dao.readOperator(oprId);
		try {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, 7);
			Algorithm algorithm = Algorithm.HMAC256("secret");
			return JWT.create()
					.withIssuer("auth0")
					.withClaim("oprId", oprId)
					.withClaim("name", opr.getOprName())
					.withClaim("role", opr.getRole())
					.withClaim("admin", opr.isAdmin())
					.withExpiresAt(cal.getTime())
					.sign(algorithm);
		} catch (UnsupportedEncodingException | JWTCreationException e){
			throw new AuthException(e);
		}
	}
}

