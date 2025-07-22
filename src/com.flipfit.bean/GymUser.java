package com.flipkart.bean;

public class User {
    private int userId;
    private String userName;
    private int Role;       // 0 -> GymCustomer, 1-> GymOwner, 2-> GymAdmin

    public int getRole() {
        return Role;
    }

    public void setRole(int role) {
        Role = role;
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