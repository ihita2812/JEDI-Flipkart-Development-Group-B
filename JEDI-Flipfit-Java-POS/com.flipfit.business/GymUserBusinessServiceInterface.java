package com.flipfit.business;

import com.flipfit.bean.*;

public interface GymUserBusinessServiceInterface {

    public int loginUser(String username, String password, int role);

    public Notification[] viewNotifications(GymUser user);
}
