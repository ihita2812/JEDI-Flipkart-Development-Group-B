package com.flipfit.bean;

public class GymUser {
    private int userId;
    private String userName;
    private int role;       // 0 -> GymCustomer, 1-> GymOwner, 2-> GymAdmin

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
}