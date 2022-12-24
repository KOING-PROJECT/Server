package com.koing.server.koing_server.common.enums;

public enum TourStatus {

    RECRUITMENT("RECRUITMENT", 1),
    STANDBY("STANDBY", 2),
    ONGOING("ONGOING", 3),
    FINISH("FINISH", 4),
    ;

    private String status;
    private int statusNumber;

    TourStatus(String status, int statusNumber) {
        this.status = status;
        this.statusNumber = statusNumber;
    }

    public String getStatus() {
        return status;
    }

}
