package com.flipfit.dao;

import com.flipfit.bean.*;
import java.util.*;

public class GymUserDAOImpl implements GymUserDAO {

    public Map<Integer, GymUser> userMap = new HashMap<>();
    public Map<Integer, GymCenter> centerMap = new HashMap<>();
    public Map<Integer, Slot> slotMap = new HashMap<>();
    public Map<Integer, Booking> bookingMap = new HashMap<>();
    public Map<Integer, Payment> paymentMap = new HashMap<>();
    public Map<Integer, Notification> notificationMap = new HashMap<>();


    

    

    /*
    @Override
    public void addUser(GymUser user) {
        for (GymUser existing : userMap.values()) {
            if (existing.getUserName().equals(user.getUserName())) {
                System.out.println("Username already exists!");
                return;
            }
        }
        
        int newUserId = userMap.keySet().stream()
                               .max(Integer::compareTo)
                               .orElse(0) + 1;
        user.setUserId(newUserId);
        userMap.put(newUserId, user);
        System.out.println("User added successfully with ID: " + newUserId);
    }

    @Override
    public GymUser getUserById(int userId) {
        return userMap.get(userId);
    }

    @Override
    public GymUser getUserByUsername(String username) {
        for (GymUser user : userMap.values()) {
            if (user.getUserName().equals(username)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<GymUser> getAllUsers() {
        return new ArrayList<>(userMap.values());
    }

    @Override
    public void removeUser(int userId) {
        userMap.remove(userId);
    }

    @Override
    public boolean validateLogin(String username, String password) {
        for (GymUser user : userMap.values()) {
            if (user.getUserName().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
     */
}