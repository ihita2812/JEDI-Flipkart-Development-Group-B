package com.flipfit.bean;


/*
 * @author: Ihita, Zaid
 * @ClassName: GymAdmin
 * @Exceptions: No
 * @Version: 1.0
 * @See:GymUser
 */
public class GymAdmin extends GymUser {
    private int adminId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    int userId;

    public int getAdminId() {
        return this.adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }
}

