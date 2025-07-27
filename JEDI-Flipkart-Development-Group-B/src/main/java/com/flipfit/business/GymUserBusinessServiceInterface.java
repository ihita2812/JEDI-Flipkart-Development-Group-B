package com.flipfit.business;

import com.flipfit.bean.GymUser;
import com.flipfit.bean.Notification;

public interface GymUserBusinessServiceInterface {

    public int loginUser(String username, String password, int role);
    public boolean userNameExists(String userName);
    public Notification[] viewNotifications(GymUser user);
    boolean changePassword(String username, String oldPassword, String newPassword);
    GymUser createUserBean(String name, String password, int i, String userName);
    void addUser(GymUser newUser);
}
