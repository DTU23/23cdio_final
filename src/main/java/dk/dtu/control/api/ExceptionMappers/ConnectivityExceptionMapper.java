package dk.dtu.control.api.ExceptionMappers;

import dk.dtu.model.exceptions.dal.ConnectivityException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ConnectivityExceptionMapper implements ExceptionMapper<ConnectivityException> {
    public Response toResponse(ConnectivityException e){
        return Response.status(503)
                .entity(e.getMessage())
                .type("text/plain")
                .build();
    }
}
