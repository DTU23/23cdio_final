package dk.dtu.control.api.ExceptionMappers;

import dk.dtu.model.exceptions.validation.InvalidNameException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidNameExceptionMapper implements ExceptionMapper<InvalidNameException> {
    public Response toResponse(InvalidNameException e){
        return Response.status(400)
                .entity(e.getMessage())
                .type("text/plain")
                .build();
    }
}
