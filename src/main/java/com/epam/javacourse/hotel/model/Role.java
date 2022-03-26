package com.epam.javacourse.hotel.model;

public enum Role {

    MANAGER("manager"),
    CLIENT("client");


    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public static Role getRoleByType(String type) {
        for (Role role : values()) {
            if (role.getRole().equals(type)) {
                return role;
            }
        }
        throw new IllegalArgumentException("No role found with such input data: [" + type + "]");
    }

}
