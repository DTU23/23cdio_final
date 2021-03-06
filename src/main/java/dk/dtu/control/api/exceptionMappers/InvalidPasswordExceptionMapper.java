package dk.dtu.control.api.exceptionMappers;

import dk.dtu.model.exceptions.validation.InvalidPasswordException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidPasswordExceptionMapper implements ExceptionMapper<InvalidPasswordException> {
    public Response toResponse(InvalidPasswordException e){
        return Response.status(400)
                .entity(e.getMessage())
                .type("text/plain")
                .build();
    }
}
