package com.epam.javacourse.hotel.shared.models;

public enum SortBy {
    None("none"),
    Price("price"),
    Class("class");

    private final String text;

    SortBy(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static SortBy fromString(String text) {
        for (SortBy b : SortBy.values()) {
            if (b.text.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return SortBy.None;
    }
}
