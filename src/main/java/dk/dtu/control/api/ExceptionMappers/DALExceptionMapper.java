package dk.dtu.control.api.ExceptionMappers;

import dk.dtu.model.exceptions.DALException;
import dk.dtu.model.exceptions.ValidationException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DALExceptionMapper implements ExceptionMapper<DALException> {
    public Response toResponse(DALException e){
        return Response.status(404)
                .entity(e.getMessage())
                .type("text/plain")
                .build();
    }
}
