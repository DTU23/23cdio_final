package dk.dtu.control.api.ExceptionMappers;

import dk.dtu.model.exceptions.dal.IntegrityConstraintViolationException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class IntegrityConstraintViolationExceptionMapper implements ExceptionMapper<IntegrityConstraintViolationException> {
    public Response toResponse(IntegrityConstraintViolationException e){
        return Response.status(401)
                .entity(e.getMessage())
                .type("text/plain")
                .build();
    }
}
