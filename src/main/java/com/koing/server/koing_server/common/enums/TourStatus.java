package com.koing.server.koing_server.common.enums;

public enum TourStatus {

    CREATED("CREATED", 1),
    APPROVAL("APPROVAL", 2),
    REJECTION("REJECTION", 3),
    RECRUITMENT("RECRUITMENT", 4),
    STANDBY("STANDBY", 5),
    ONGOING("ONGOING", 6),
    FINISH("FINISH", 7)

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
