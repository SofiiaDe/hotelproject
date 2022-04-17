package com.epam.javacourse.hotel.model;

public enum Role {

    MANAGER("manager"),
    CLIENT("client");


    private final String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public static Role getRoleByType(String type) {
        for (Role role : values()) {
            if (role.getRoleName().equals(type)) {
                return role;
            }
        }
        throw new IllegalArgumentException("No role found with such input data: [" + type + "]");
    }

}
