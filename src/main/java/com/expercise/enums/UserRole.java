package com.expercise.enums;

public enum UserRole {

    User("ROLE_USER"),
    Admin("ROLE_USER", "ROLE_ADMIN");

    private String[] authorities;

    private UserRole(String... authorities) {
        this.authorities = authorities;
    }

    public String[] getAuthorities() {
        return authorities;
    }

}
