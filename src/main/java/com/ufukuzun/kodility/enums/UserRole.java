package com.ufukuzun.kodility.enums;

public enum UserRole {

    User("ROLE_USER"),
    Admin("ROLE_ADMIN");

    private String role;

    private UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

}
