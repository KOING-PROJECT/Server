package com.koing.server.koing_server.common.enums;

public enum TouristGrade {

    BASIC("BASIC", 1),
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
