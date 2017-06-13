package dk.dtu.control.api.exceptionMappers;

import dk.dtu.model.exceptions.validation.InvalidInitialsException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidInitialsExceptionMapper implements ExceptionMapper<InvalidInitialsException> {
    public Response toResponse(InvalidInitialsException e){
        return Response.status(400)
                .entity(e.getMessage())
                .type("text/plain")
                .build();
    }
}
