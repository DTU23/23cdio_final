package dk.dtu.control.api.exceptionMappers;

import dk.dtu.model.exceptions.validation.PositiveIntegerValidationException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class PositiveIntegerValidationExceptionMapper implements ExceptionMapper<PositiveIntegerValidationException> {
    public Response toResponse(PositiveIntegerValidationException e){
        return Response.status(400)
                .entity(e.getMessage())
                .type("text/plain")
                .build();
    }
}
