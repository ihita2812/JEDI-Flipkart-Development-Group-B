package com.flipfit.bean;


/*
 *@author: Eshwar, Ritesh, Kashif
 *@ClassName: Notification
 *@Exceptions: No
 *@version: 1.0
 *@See :GymCustomer, GymOwner
 */

public class Notification {
    private int notifId;
    private String message;
    private int userId;

    public int getNotifId() {
        return this.notifId;
    }

    public void setNotifId(int notifId) {
        this.notifId = notifId;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


}