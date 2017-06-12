package dk.dtu.control.api.ExceptionMappers;

import dk.dtu.model.exceptions.dal.NotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {
    public Response toResponse(NotFoundException e){
        return Response.status(401)
                .entity(e.getMessage())
                .type("text/plain")
                .build();
    }
}
