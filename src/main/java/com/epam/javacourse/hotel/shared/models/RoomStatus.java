package com.epam.javacourse.hotel.shared.models;

public enum RoomStatus {
    None("none"),
    Available("available"),
    Reserved("reserved"),
    Booked("booked"),
    Unavailable("unavailable");

    private String text;

    RoomStatus(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static RoomStatus fromString(String text) {
        for (RoomStatus b : RoomStatus.values()) {
            if (b.text.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return RoomStatus.None;
    }
}
