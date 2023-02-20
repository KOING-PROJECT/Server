package com.koing.server.koing_server.common.enums;

public enum TouristGrade {

    BEGINNER("BEGINNER", 1),
    TRAVELER("TRAVELER", 2),
    INFLUENCER("INFLUENCER", 3),
    NATIVE("NATIVE", 4),
    ;

    private String grade;
    private int gradeNumber;

    TouristGrade(String grade, int gradeNumber) {
        this.grade = grade;
        this.gradeNumber = gradeNumber;
    }

    public String getGrade() {
        return grade;
    }

}
