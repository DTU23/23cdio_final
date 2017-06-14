package dk.dtu.control.api.exceptionMappers;

import dk.dtu.model.exceptions.validation.InvalidIDException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidIDExceptionMapper implements ExceptionMapper<InvalidIDException> {
    public Response toResponse(InvalidIDException e){
        return Response.status(400)
                .entity(e.getMessage())
                .type("text/plain")
                .build();
    }
}
