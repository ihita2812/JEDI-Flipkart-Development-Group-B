package com.flipfit.business;

import com.flipfit.bean.*;

public interface GymUserBusinessServiceInterface {

    public int loginUser(String username, String password, int role);
    public boolean userNameExists(String userName);
    public Notification[] viewNotifications(GymUser user);
    boolean changePassword(String username, String oldPassword, String newPassword);
}
