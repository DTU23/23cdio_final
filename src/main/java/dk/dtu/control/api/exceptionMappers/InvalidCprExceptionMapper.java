package dk.dtu.control.api.exceptionMappers;

import dk.dtu.model.exceptions.validation.InvalidCprException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidCprExceptionMapper implements ExceptionMapper<InvalidCprException> {
    public Response toResponse(InvalidCprException e){
        return Response.status(400)
                .entity(e.getMessage())
                .type("text/plain")
                .build();
    }
}
