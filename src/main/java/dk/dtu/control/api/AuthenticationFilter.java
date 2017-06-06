package main.java.dk.dtu.control.api;

import java.io.IOException;
import java.security.Principal;

import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import main.java.dk.dtu.model.dao.MySQLOperatorDAO;
import main.java.dk.dtu.model.dto.OperatorDTO;
import main.java.dk.dtu.model.interfaces.DALException;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // Get the HTTP Authorization header from the request
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Check if the HTTP Authorization header is present and formatted correctly
        if (authorizationHeader == null) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }

        try {
            // Validate the token
            validateToken(authorizationHeader);
        } catch (Exception e) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED).build());
        }

        final SecurityContext currentSecurityContext = requestContext.getSecurityContext();
        requestContext.setSecurityContext(new ExtendedSecurityContext() {
            DecodedJWT decodedToken = JWT.decode(authorizationHeader);
            MySQLOperatorDAO oprDAO = new MySQLOperatorDAO();

            @Override
            public boolean isAdmin() throws DALException {
                return decodedToken.getClaim("admin").asBoolean();
            }

            @Override
            public OperatorDTO getUser() throws DALException {
                return oprDAO.getOperator(decodedToken.getClaim("oprId").asInt());
            }

            @Override
            public Principal getUserPrincipal() {
                return () -> decodedToken.getClaim("name").asString();
            }

            @Override
            public boolean isUserInRole(String role){
                return Role.valueOf(decodedToken.getClaim("role").asString()).hasPermissions(role);
            }

            @Override
            public boolean isSecure() {
                return currentSecurityContext.isSecure();
            }

            @Override
            public String getAuthenticationScheme() {
                return "Token";
            }
        });

    }

    private void validateToken(String token) throws Exception {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("auth0")
                .build(); //Reusable verifier instance
        verifier.verify(token);
    }
}