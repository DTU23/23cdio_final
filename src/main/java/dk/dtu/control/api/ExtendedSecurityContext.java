package dk.dtu.control.api;

import dk.dtu.model.dto.OperatorDTO;
import dk.dtu.model.interfaces.DALException;

import java.security.Principal;

public interface ExtendedSecurityContext extends javax.ws.rs.core.SecurityContext {
    public boolean isAdmin();
    public OperatorDTO getUser() throws DALException;
}
