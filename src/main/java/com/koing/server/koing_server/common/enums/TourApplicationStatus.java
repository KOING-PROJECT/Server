package com.koing.server.koing_server.common.enums;

public enum TourApplicationStatus {

    RECRUITMENT("RECRUITMENT", 1),
    DE_ACTIVATE("DE_ACTIVATE", 2),
    STANDBY("STANDBY", 3),
    GUIDE_START("GUIDE_START", 4),
    TOURIST_START("TOURIST_START", 5),
    ONGOING("ONGOING", 6),
    GUIDE_END("GUIDE_END", 7),
    TOURIST_END("TOURIST_END", 8),
    NO_REVIEW("NO_REVIEW", 9),
    GUIDE_REVIEWED("GUIDE_REVIEWED", 10),
    TOURIST_REVIEWED("TOURIST_REVIEWED", 11),
    REVIEWED("REVIEWED", 12),
    NONE("NONE", 13)
    ;

    private String status;
    private int statusNumber;

    TourApplicationStatus(String status, int statusNumber) {
        this.status = status;
        this.statusNumber = statusNumber;
    }

    public String getStatus() {
        return status;
    }

}
