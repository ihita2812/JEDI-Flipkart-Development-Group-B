package com.flipfit.business;

import com.flipfit.bean.*;
import com.flipfit.DAO.GymUserDAOImpl.userMap;
import java.util.Collections;

public class GymUserBusinessService implements GymUserBusinessServiceInterface {

    public boolean loginUser(GymUser user) {
        System.out.println("User logged in!");
        return true;
    }
    public Notification[] viewNotifications(GymUser user) {
        System.out.println("Here are you notifications!");
        return null;
    }

    public boolean userNameExists(String userName) {
        for (GymUser existing : userMap.values()) {
            if (existing.userName().equals(userName)) {
                return true;
            }
        }
        return false;
    }
    
    public int nextUserId() {
        int currMax = Collections.max(userMap.keySet());
        return (currMax + 1);
    }

    public GymUser createUserBean(String name, String pass, Role role, String userName) {
        GymUser user = new GymUser();
        user.setUserName(userName);
        user.setRole(role);
        user.setName(name);
        user.setPassword(pass);
        user.setUserId(nextUserId());
        return user;
    }

    public void addUser(GymUser user) {
        userMap.put(user.getUserId(), user);
    }

}