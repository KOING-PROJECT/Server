package com.koing.server.koing_server.common.enums;

public enum GuideGrade {

    BEGINNER("BEGINNER", 1),
    NOVICE("NOVICE", 2),
    INTERMEDIATE("INTERMEDIATE", 3),
    EXPERT("EXPERT", 4),
    PRO("PRO", 5)
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
