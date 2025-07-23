package com.flipfit.business;

import com.flipfit.bean.*;
import com.flipfit.bean.*;

public class GymUserBusiness {

    public boolean loginUser(GymUser user) {
        System.out.println("User logged in!");
        return true;
    }
    public Notification[] viewNotifications(GymUser user) {
        System.out.println("Here are you notifications!");
        return null;
    }

}