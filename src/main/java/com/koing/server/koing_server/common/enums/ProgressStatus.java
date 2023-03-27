package com.koing.server.koing_server.common.enums;

public enum ProgressStatus {

    READY("READY", 1),
    PRESS_START("PRESS_START", 2),
    PRESS_END("PRESS_END", 3)
    ;

    private String status;
    private int statusNumber;

    ProgressStatus(String status, int statusNumber) {
        this.status = status;
        this.statusNumber = statusNumber;
    }

    public String getStatus() {
        return status;
    }

}
