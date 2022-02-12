package com.epam.javacourse.hotel.shared.models;

public enum RoomSeats {
    NONE("none"),
    SINGLE("single"),
    DOUBLE("double"),
    TWIN("twin"),
    TRIPLE("triple");

    private final String text;

    RoomSeats(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static RoomSeats fromString(String text) {
        for (RoomSeats b : RoomSeats.values()) {
            if (b.text.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return RoomSeats.NONE;
    }
}
