package dk.dtu.control.api.v1;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import dk.dtu.model.dao.MySQLOperatorDAO;
import dk.dtu.model.dto.OperatorDTO;
import dk.dtu.model.interfaces.DALException;
import dk.dtu.model.interfaces.OperatorDAO;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;

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
		OperatorDTO sysOpr = oprDao.getOperator(oprId);
		if (!sysOpr.getPassword().equals(password)) {
			throw new DALException("Wrong Password!");
		}
	}

	private String issueToken(int oprId) {
		try {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, 7);
			Algorithm algorithm = Algorithm.HMAC256("secret");
			return JWT.create()
					.withIssuer("auth0")
					.withClaim("oprId", oprId)
					.withExpiresAt(cal.getTime())
					.sign(algorithm);
		} catch (UnsupportedEncodingException exception){
			//UTF-8 encoding not supported
		} catch (JWTCreationException exception){
			//Invalid Signing configuration / Couldn't convert Claims.
		}
		return null;
	}

	public static boolean validateToken(String token){
		try {
			Algorithm algorithm = Algorithm.HMAC256("secret");
			JWTVerifier verifier = JWT.require(algorithm)
					.withIssuer("auth0")
					.build(); //Reusable verifier instance
			DecodedJWT jwt = verifier.verify(token);
			Claim usernameClaim = jwt.getClaim("oprId");
			String oprId = usernameClaim.asString();
			System.out.println(oprId);
			return true;
		} catch (UnsupportedEncodingException exception){
			//UTF-8 encoding not supported
		} catch (JWTVerificationException exception){
			//Invalid signature/claims
		}
		return true;
	}
}
