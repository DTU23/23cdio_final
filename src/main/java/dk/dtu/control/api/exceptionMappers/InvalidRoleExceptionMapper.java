package dk.dtu.control.api.exceptionMappers;

import dk.dtu.model.exceptions.validation.InvalidRoleException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidRoleExceptionMapper implements ExceptionMapper<InvalidRoleException> {
    public Response toResponse(InvalidRoleException e){
        return Response.status(400)
                .entity(e.getMessage())
                .type("text/plain")
                .build();
    }
}
