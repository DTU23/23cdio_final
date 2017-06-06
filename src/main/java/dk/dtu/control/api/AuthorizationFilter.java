package main.java.dk.dtu.control.api;

import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import main.java.dk.dtu.model.interfaces.DALException;

@Secured
@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthorizationFilter implements ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        // Get the resource class which matches with the requested URL
        // Extract the roles declared by it
        Class<?> resourceClass = resourceInfo.getResourceClass();
        List<Role> classRoles = extractRoles(resourceClass);
        boolean classAdmin = extractAdmin(resourceClass);

        SecurityContext sc = requestContext.getSecurityContext();

        // Get the resource method which matches with the requested URL
        // Extract the roles declared by it
        Method resourceMethod = resourceInfo.getResourceMethod();
        List<Role> methodRoles = extractRoles(resourceMethod);
        boolean methodAdmin = extractAdmin(resourceMethod);

        try {

            // Check if the user is allowed to execute the method
            // The method annotations override the class annotations
            if (methodRoles.isEmpty()) {
                checkPermissions(classRoles, classAdmin, (ExtendedSecurityContext) sc);
            } else {
                checkPermissions(methodRoles, methodAdmin, (ExtendedSecurityContext) sc);
            }

        } catch (Exception e) {
            requestContext.abortWith(
                    Response.status(Response.Status.FORBIDDEN).build());
        }
    }

    // Extract the roles from the annotated element
    private boolean extractAdmin(AnnotatedElement annotatedElement) {
        if (annotatedElement == null) {
            return false;
        } else {
            Secured secured = annotatedElement.getAnnotation(Secured.class);
            return secured != null && secured.admin();
        }
    }

    // Extract the roles from the annotated element
    private List<Role> extractRoles(AnnotatedElement annotatedElement) {
        if (annotatedElement == null) {
            return new ArrayList<>();
        } else {
            Secured secured = annotatedElement.getAnnotation(Secured.class);
            if (secured == null) {
                return new ArrayList<>();
            } else {
                Role[] allowedRoles = secured.roles();
                return Arrays.asList(allowedRoles);
            }
        }
    }

    private void checkPermissions(List<Role> allowedRoles, boolean requiredAdmin, ExtendedSecurityContext sc) throws Exception {
        boolean roleSatisfied = false;
        boolean adminSatisfied;
        if (allowedRoles.size() == 0) {
            roleSatisfied = true;
        } else {
            for (Role role : allowedRoles) {
                if (sc.isUserInRole(String.valueOf(role))) {
                    roleSatisfied = true;
                }
            }
        }
        adminSatisfied = !requiredAdmin || sc.isAdmin();
        if (!(roleSatisfied && adminSatisfied)) {
            throw new DALException("Permission denied!");
        }
    }
}