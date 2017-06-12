package dk.dtu.control.api.ExceptionMappers;

import dk.dtu.model.exceptions.AuthException;
import dk.dtu.model.exceptions.ValidationException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AuthExceptionMapper implements ExceptionMapper<AuthException> {
    public Response toResponse(AuthException e){
        return Response.status(401)
                .entity(e.getMessage())
                .type("text/plain")
                .build();
    }
}
