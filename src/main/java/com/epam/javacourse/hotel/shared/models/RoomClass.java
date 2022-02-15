package com.epam.javacourse.hotel.shared.models;

public enum RoomClass {

    NONE("none"),
    STANDARD("standard"),
    BUSINESS("business"),
    LUX("lux");

    String text;

    RoomClass(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static RoomClass fromString(String text) {
        for (RoomClass roomClass : RoomClass.values()) {
            if (roomClass.text.equalsIgnoreCase(text)) {
                return roomClass;
            }
        }
        return RoomClass.NONE;
    }


}
