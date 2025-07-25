package com.flipfit.bean;


/*
 *@author: Aryan, Ihita, Kashif
 *@ClassName: GymOwner
 *@Exceptions: No
 *@version: 1.0
 *@See :GymUser, Role, GymCenter
 */

public class GymOwner extends GymUser{
    private int ownerId;
    private int gender;
    private String email;
    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public int getOwnerId() {
        return this.ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getGender() {
        return this.gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

}