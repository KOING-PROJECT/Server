package com.koing.server.koing_server.common.enums;

public enum TourCategoryIndex {

    HISTORY("역사/전통", 1),
    FOOD("먹방", 2),
    TOUR("관광명소", 3),
    HEALING("힐링", 4),
    ACTIVITY("액티비티", 5),
    ENTERTAINMENT("엔터테인먼트", 6),
    SHOPPING("쇼핑", 7),
    ;

    private String categoryName;
    private int categoryIndex;

    TourCategoryIndex(String categoryName, int categoryIndex) {
        this.categoryName = categoryName;
        this.categoryIndex = categoryIndex;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getCategoryIndex() {
        return categoryIndex;
    }
}
