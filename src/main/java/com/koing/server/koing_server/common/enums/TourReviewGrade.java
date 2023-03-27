package com.koing.server.koing_server.common.enums;

public enum TourReviewGrade {

    FUNNY("0", 0.2, 0),
    BENEFICIAL("1", 0.1, 1),
    HEALING("2", 0.1, 2),
    FRESHNESS("3", 0.2, 3),
    HARD("4", -0.2, 4),
    BORING("5", -0.3, 5),
    UNCOMFORTABLE("6", -0.3, 6),
    WORST("7", -0.5, 7),
    ;

    private String status;
    private double score;
    private int statusNumber;

    TourReviewGrade(String status, double score, int statusNumber) {
        this.status = status;
        this.score = score;
        this.statusNumber = statusNumber;
    }

    public String getStatus() {
        return status;
    }
    public double getScore() {
        return score;
    }

}
