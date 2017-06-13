package dk.dtu.control.api.exceptionMappers;

import dk.dtu.model.exceptions.dal.IntegrityConstraintViolationException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class IntegrityConstraintViolationExceptionMapper implements ExceptionMapper<IntegrityConstraintViolationException> {
    public Response toResponse(IntegrityConstraintViolationException e){
        return Response.status(405)
                .entity("Integrity constraint, action not allowed")
                .type("text/plain")
                .build();
    }
}
