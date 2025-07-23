package com.flipfit.business;

import com.flipfit.bean.*;

public class GymUserBusiness {
    public void createUser(GymUser user) {
        System.out.println("User created!");
    }
    
    public boolean loginUser(GymUser user) {
        System.out.println("User logged in!");
        return true;
    }

}