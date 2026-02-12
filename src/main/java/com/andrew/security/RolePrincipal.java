package com.andrew.security;

import java.security.Principal;

public class RolePrincipal implements Principal {
    private final String role;

    public RolePrincipal(String role) {
        this.role = role;
    }

    @Override
    public String getName() {
        return role;
    }
}
