package com.karan.resources;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;

import dao.UserDao;

import entities.Role;
import entities.User;
import io.dropwizard.jersey.PATCH;

@Path("/users/")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private UserDao userDao;

    public UserResource(UserDao userDao) {
        this.userDao = userDao;
    }

    @PATCH
    @Path("{email}")
    @RolesAllowed({ "MANAGER" })
    /**
     * it accept email as path param,
     * roles as query param, roles here are comma
     * seperated value of roles that are to be added to a user
     *
     * @returns : new User object with updated roles
     */
    public User addRoles(
        @PathParam("email") @NotEmpty String userEmail,
        @QueryParam("roles") String roles) {
        List<Role> roleList = new ArrayList<>();
        if (StringUtils.isNotBlank(roles)) {
            for (String role : StringUtils.split(roles, ",")) {
                roleList.add(Role.valueOf(role));
            }
        }
        return userDao.addRoles(userEmail, roleList);
    }

    @DELETE
    @Path("{email}")
    @RolesAllowed({ "MANAGER", "TEAM_LEAD" })
    /**
     * it accept email as path param,
     * roles as query param, roles here are comma
     * seperated value of roles that are to be removed from a user
     *
     * @returns : new User object with updated roles
     */
    public User removeRoles(
        @PathParam("email") @NotEmpty String userEmail,
        @QueryParam("roles") String roles) {
        List<Role> roleList = new ArrayList<>();
        if (StringUtils.isNotBlank(roles)) {
            for (String role : StringUtils.split(roles, ",")) {
                roleList.add(Role.valueOf(role));
            }
        }
        return userDao.removeRoles(userEmail, roleList);
    }

}
