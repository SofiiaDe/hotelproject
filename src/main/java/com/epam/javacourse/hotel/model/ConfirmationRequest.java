package com.epam.javacourse.hotel.model;

public class ConfirmationRequest extends Entity{

    private int id;
    private User user;
    private Application application;
    private Room room;
    private ConfirmRequestStatus status;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public ConfirmRequestStatus getStatus() {
        return status;
    }

    public void setStatus(ConfirmRequestStatus status) {
        this.status = status;
    }
}

enum ConfirmRequestStatus{
    NEW,
    CONFIRMED,
}
