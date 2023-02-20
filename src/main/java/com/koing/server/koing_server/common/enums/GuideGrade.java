package com.koing.server.koing_server.common.enums;

public enum GuideGrade {

    BASIC("BASIC", 1),
    ;

    private String grade;
    private int gradeNumber;

    GuideGrade(String grade, int gradeNumber) {
        this.grade = grade;
        this.gradeNumber = gradeNumber;
    }

    public String getGrade() {
        return grade;
    }

}
