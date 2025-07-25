package com.flipfit.bean;


/*
 *@author: Aryan, Zaid
 *@ClassName: GymUser
 *@Exceptions: No
 *@version: 1.0
 *@See :GymCustomer, GymOwner, GymAdmin
 */

public class GymUser {
    private int userId;
    private String userName;
    private Role role;       // 0 -> GymCustomer, 1-> GymOwner, 2-> GymAdmin
    private String password;
    private String name;

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
}