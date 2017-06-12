package dk.dtu.control.api.ExceptionMappers;

import dk.dtu.model.exceptions.validation.NotLettersException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotLettersExceptionMapper implements ExceptionMapper<NotLettersException> {
    public Response toResponse(NotLettersException e){
        return Response.status(400)
                .entity(e.getMessage())
                .type("text/plain")
                .build();
    }
}
