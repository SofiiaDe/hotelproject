package com.epam.javacourse.hotel.shared.models;

public enum SortType {
    None("none"),
    Asc("asc"),
    Desc("desc");

    private final String text;

    SortType(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static SortType fromString(String text) {
        for (SortType b : SortType.values()) {
            if (b.text.equalsIgnoreCase(text)) {
                return b;
            }
        }

        return SortType.None;
    }
}
