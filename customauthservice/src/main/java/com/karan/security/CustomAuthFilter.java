package com.karan.security;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import dao.UserDao;

import entities.User;
import io.dropwizard.auth.AuthFilter;

@PreMatching
@Priority(Priorities.AUTHENTICATION)
public class CustomAuthFilter extends AuthFilter<CustomCredentials, CustomAuthUser> {
    private UserDao userDao;

    public CustomAuthFilter(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        CustomCredentials credentials = getCredentials(requestContext);
        User user = userDao.getUser(credentials.getUserEmail());
        CustomAuthUser customAuthUser = new CustomAuthUser(user.getUserName(), user.getRole(), user.getUserEmail());
        SecurityContext securityContext = new CustomSecurityContext(customAuthUser,
            requestContext.getSecurityContext());
        requestContext.setSecurityContext(securityContext);
    }

    /**
     * This function assumes that we are setting email in cookie
     * @param requestContext
     * @return : object off a customcredential class if email is present in cookie or throws exception if not
     */
    private CustomCredentials getCredentials(ContainerRequestContext requestContext) {
        CustomCredentials credentials = new CustomCredentials();
        try {
            String rawUserEmail = requestContext.getCookies().get("auth_user_email").getValue();
            credentials.setUserEmail(rawUserEmail);
        }
        catch (Exception e) {
            throw new WebApplicationException("Unable to parse credentials", Response.Status.UNAUTHORIZED);
        }
        return credentials;
    }
}
