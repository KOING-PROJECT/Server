package com.koing.server.koing_server.common.enums;

public enum UserRole {

    ROLE_GUIDE("ROLE_GUIDE", 1),
    ROLE_TOURIST("ROLE_TOURIST", 2),
    ROLE_ADMIN("ROLE_ADMIN", 3);

    private String role;
    private int roleNumber;

    UserRole(String role, int roleNumber) {
        this.role = role;
        this.roleNumber = roleNumber;
    }

    public String getRole() {
        return role;
    }

}
