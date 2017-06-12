package dk.dtu.control.api.ExceptionMappers;

import dk.dtu.model.exceptions.dal.DataResetException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DataResetExceptionMapper implements ExceptionMapper<DataResetException> {
    public Response toResponse(DataResetException e){
        return Response.status(401)
                .entity(e.getMessage())
                .type("text/plain")
                .build();
    }
}
