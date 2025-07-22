package com.flipfit.bean;

public class Registration {
    private int userId;
    private String password;

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(GymUser.userId userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}