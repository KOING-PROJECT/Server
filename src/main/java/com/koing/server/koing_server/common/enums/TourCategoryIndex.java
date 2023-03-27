package com.koing.server.koing_server.common.enums;

public enum TourCategoryIndex {

    HISTORY("쇼핑", "0"),
    FOOD("먹방", "1"),
    TOUR("힐링", "2"),
    HEALING("관광명소", "3"),
    ACTIVITY("역사/전통", "4"),
    ENTERTAINMENT("엔터테인먼트", "5"),
    SHOPPING("액티비티", "6"),
    ALL("전체", "7"),
    ;

    private String categoryName;
    private String categoryIndex;

    TourCategoryIndex(String categoryName, String categoryIndex) {
        this.categoryName = categoryName;
        this.categoryIndex = categoryIndex;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryIndex() {
        return categoryIndex;
    }
}
