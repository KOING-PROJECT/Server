package com.koing.server.koing_server.common.enums;

public enum UserReviewGrade {

    BEST("0", 0.2, 0),
    KIND("1", 0.1, 1),
    PROFESSIONAL("2", 0.2, 2),
    BLUNT("3", -0.1, 3),
    NOT_GOOD("4", -0.2, 4),
    UNPLEASANT("5", -0.2, 5),
    ;

    private String status;
    private double score;
    private int statusNumber;

    UserReviewGrade(String status, double score, int statusNumber) {
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
