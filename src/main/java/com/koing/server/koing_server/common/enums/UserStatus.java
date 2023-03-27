package com.koing.server.koing_server.common.enums;

public enum UserStatus {

    ACTIVATE("ACTIVATE", 1),
    BAN("BAN", 2),
    REQUEST_WITHDRAWAL("WITHDRAWAL_REQUEST", 3),
    WITHDRAWAL("WITHDRAWAL", 4),
    ;

    private String status;
    private int statusNumber;

    UserStatus(String status, int statusNumber) {
        this.status = status;
        this.statusNumber = statusNumber;
    }

    public String getStatus() {
        return status;
    }

}
