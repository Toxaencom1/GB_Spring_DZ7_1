package com.taxah.springdz7.security;


/**
 * The Roles enum defines the roles available in the application.
 */
public enum Roles {
    USER("USER"),
    ADMIN("ADMIN");

    private final String roleName;

    /**
     * Constructs a new Roles enum value with the specified role name.
     *
     * @param roleName The name of the role.
     */
    Roles(String roleName) {
        this.roleName = roleName;
    }

    /**
     * Retrieves the name of the role.
     *
     * @return The name of the role.
     */
    public String get() {
        return roleName;
    }
}
