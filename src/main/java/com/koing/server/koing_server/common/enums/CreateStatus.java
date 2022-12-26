package com.koing.server.koing_server.common.enums;

public enum CreateStatus {

    COMPLETE("COMPLETE", 1),
    CREATING("CREATING", 2),
    ;

    private String status;
    private int statusNumber;

    CreateStatus(String status, int statusNumber) {
        this.status = status;
        this.statusNumber = statusNumber;
    }

    public String getStatus() {
        return status;
    }

}
