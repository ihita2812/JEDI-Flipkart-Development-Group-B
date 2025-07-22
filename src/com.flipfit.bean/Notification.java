package com.flipfit.bean;

public Class Notification {
    private int notifId;
    private String message;
    private GymUser.userId userId;


    public Class getNotification() {
        return Notification;
    }

    public void setNotification(Class notification) {
        Notification = notification;
    }

    public int getNotifId() {
        return notifId;
    }

    public void setNotifId(int notifId) {
        this.notifId = notifId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public GymUser.userId getUserId() {
        return userId;
    }

    public void setUserId(GymUser.userId userId) {
        this.userId = userId;
    }
}