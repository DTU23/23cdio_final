package dk.dtu.control.api.exceptionMappers;

import dk.dtu.model.exceptions.validation.InvalidStatusException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidStatusExceptionMapper implements ExceptionMapper<InvalidStatusException> {
    public Response toResponse(InvalidStatusException e){
        return Response.status(400)
                .entity(e.getMessage())
                .type("text/plain")
                .build();
    }
}
