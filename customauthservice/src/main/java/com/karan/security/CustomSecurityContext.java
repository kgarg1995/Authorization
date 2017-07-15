package com.karan.security;

import java.security.Principal;
import java.util.List;

import javax.ws.rs.core.SecurityContext;

import entities.Role;

public class CustomSecurityContext implements SecurityContext {
    private final CustomAuthUser principal;
    private final SecurityContext securityContext;

    public CustomSecurityContext(CustomAuthUser principal, SecurityContext securityContext) {
        this.principal = principal;
        this.securityContext = securityContext;
    }

    @Override
    public Principal getUserPrincipal() {
        return principal;
    }

    @Override
    public boolean isUserInRole(String allowedRole) {
        List<Role> roles = principal.getRole();
        for(Role role : roles) {
            if(role.name().equals(allowedRole))
                return true;
        }
        return false;
    }

    @Override
    public boolean isSecure() {
        return securityContext.isSecure();
    }

    @Override
    public String getAuthenticationScheme() {
        return null;
    }
}
