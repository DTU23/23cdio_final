package dk.dtu.control.api.exceptionMappers;

import dk.dtu.model.exceptions.validation.PositiveDoubleValidationException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class PositiveDoubleValidationExceptionMapper implements ExceptionMapper<PositiveDoubleValidationException> {
    public Response toResponse(PositiveDoubleValidationException e){
        return Response.status(400)
                .entity(e.getMessage())
                .type("text/plain")
                .build();
    }
}
