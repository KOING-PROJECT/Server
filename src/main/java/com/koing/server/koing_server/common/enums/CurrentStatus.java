package com.koing.server.koing_server.common.enums;

public enum CurrentStatus {

    CREATING("CREATING", 1),
    CREATED("CREATED", 2),
    APPROVAL("APPROVAL", 3),
    REJECTION("REJECTION", 4),
    RECRUITMENT("RECRUITMENT", 5),
    DE_ACTIVATE("DE_ACTIVATE", 6),
    PASSED("PASSED", 7),
    NOT_TODAY("NOT_TODAY", 8),
    TODAY("TODAY", 9),
    GUIDE_START("GUIDE_START", 10),
    TOURIST_START("TOURIST_START", 11),
    ONGOING("ONGOING", 12),
    GUIDE_END("GUIDE_END", 13),
    TOURIST_END("TOURIST_END", 14),
    NO_REVIEW("NO_REVIEWED", 15),
    GUIDE_REVIEWED("GUIDE_REVIEWED", 16),
    TOURIST_REVIEWED("TOURIST_REVIEWED", 17),
    REVIEWED("REVIEWED", 18),
    FINISH("FINISH", 19),
    UNKNOWN("UNKNOWN", 20),
    ;

    private String status;
    private int statusNumber;

    CurrentStatus(String status, int statusNumber) {
        this.status = status;
        this.statusNumber = statusNumber;
    }

    public String getStatus() {
        return status;
    }

}
