package com.koing.server.koing_server.common.enums;

public enum TourStatus {

    RECRUITMENT(1),
    STANDBY(2),
    ONGOING(3),
    FINISH(4),
    ;

    private int status;

    TourStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

}
