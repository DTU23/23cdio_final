package main.java.dk.dtu.control.api;

import main.java.dk.dtu.model.dto.OperatorDTO;
import main.java.dk.dtu.model.interfaces.DALException;

public interface ExtendedSecurityContext extends javax.ws.rs.core.SecurityContext {
    boolean isAdmin() throws DALException;
    OperatorDTO getUser() throws DALException;
}
