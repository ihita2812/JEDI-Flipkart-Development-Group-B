package com.flipfit.business;

import com.flipfit.bean.*;

public interface GymUserBusinessServiceInterface {
	
	public boolean loginUser(GymUser user);
    public Notification[] viewNotifications(GymUser user);
}
