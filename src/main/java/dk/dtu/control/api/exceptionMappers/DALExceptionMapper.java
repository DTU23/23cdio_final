package dk.dtu.control.api.exceptionMappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import dk.dtu.model.exceptions.DALException;

@Provider
public class DALExceptionMapper implements ExceptionMapper<DALException> {
    public Response toResponse(DALException e){
        return Response.status(500)
                .entity(e.getMessage())
                .type("text/plain")
                .build();
    }
}
