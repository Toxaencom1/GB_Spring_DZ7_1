package com.taxah.springdz7.security;

public enum Roles {
    USER("USER"),
    ADMIN("ADMIN");

    private final String roleName;

    Roles(String roleName) {
        this.roleName = roleName;
    }

    public String get() {
        return roleName;
    }
}
