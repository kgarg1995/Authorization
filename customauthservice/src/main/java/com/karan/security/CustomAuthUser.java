package com.karan.security;

import java.security.Principal;
import java.util.List;

import entities.Role;

public class CustomAuthUser implements Principal {
    private final String name;
    private final String userEmail;
    private final List<Role> role;

    public CustomAuthUser(String name, List<Role> role, String userEmail) {
        this.name = name;
        this.userEmail = userEmail;
        this.role = role;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public List<Role> getRole() {
        return role;
    }
}
