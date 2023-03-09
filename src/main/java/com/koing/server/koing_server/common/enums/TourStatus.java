package com.koing.server.koing_server.common.enums;

public enum TourStatus {

    CREATED("CREATED", 1),
    APPROVAL("APPROVAL", 2),
    REJECTION("REJECTION", 3),
    RECRUITMENT("RECRUITMENT", 4),
    DE_ACTIVATE("DE_ACTIVATE", 5),
    FINISH("FINISH", 6)
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
