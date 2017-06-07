package dk.dtu.control.api;

import dk.dtu.model.dto.OperatorDTO;
import dk.dtu.model.interfaces.DALException;

public interface ExtendedSecurityContext extends javax.ws.rs.core.SecurityContext {
    boolean isAdmin() throws DALException;
    OperatorDTO getUser() throws DALException;
}
