package dk.dtu.control.api;

import dk.dtu.model.dto.OperatorDTO;
import dk.dtu.model.interfaces.DALException;

public interface ExtendedSecurityContext extends javax.ws.rs.core.SecurityContext {
    boolean isAdmin();
    OperatorDTO getUser() throws DALException;
}
