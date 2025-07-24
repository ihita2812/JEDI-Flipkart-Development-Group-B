package com.flipfit.dao;

import com.flipfit.bean.*;
import java.util.*;
import com.flipfit.dao.GymUserDAOImpl;

public class GymOwnerDAOImpl implements GymOwnerDAO {

    private Map<Integer, GymOwner> ownerMap = new HashMap<>();

    public GymOwnerDAOImpl() {

        Role ownerRole = roleMap.get(1);

        GymOwner owner1 = new GymOwner();
        owner1.setUserId(2);
        owner1.setUserName("owner");
        owner1.setPassword("owner");
        owner1.setName("Gym Owner");
        owner1.setRole(ownerRole);
        owner1.setOwnerId(1);
        owner1.setGender('M');
        owner1.setEmail("owner@gaanmara.com");
        ownerMap.put(owner1.getOwnerId(), owner1);

        GymOwner owner2 = new GymOwner();
        owner2.setUserId(5);
        owner2.setUserName("owner1");
        owner2.setPassword("owner");
        owner2.setName("Gym Owner hu mai");
        owner2.setRole(ownerRole);
        owner2.setOwnerId(2);
        owner2.setGender('F');
        owner2.setEmail("owner1@gaanmara.com");
        ownerMap.put(owner2.getOwnerId(), owner2);
    }

    public List <Notification> viewOwnerNotifications(int ownerId) {
        List <Notification> notifications = new ArrayList<>();
        for (Notification notification : notificationMap.values()) {
            if (notification.getUserId() == ownerId) {
                notifications.add(notification);
            }
        }
        return notifications;
    }
    /*
    @Override
    public void addOwner(GymOwner owner) {
        // Check if username already exists
        for (GymOwner existing : ownerMap.values()) {
            if (existing.getUserName().equals(owner.getUserName())) {
                System.out.println("Username already exists!");
                return;
            }
        }

        // Auto-generate owner ID
        int newOwnerId = ownerMap.keySet().stream()
                                 .max(Integer::compareTo)
                                 .orElse(0) + 1;
        owner.setOwnerId(newOwnerId);
        owner.setUserId(newOwnerId); // For compatibility with GymUser
        ownerMap.put(newOwnerId, owner);
        System.out.println("Owner added successfully with ID: " + newOwnerId);
    }

    @Override
    public GymOwner getOwnerById(int ownerId) {
        return ownerMap.get(ownerId);
    }

    @Override
    public List<GymOwner> getAllOwners() {
        return new ArrayList<>(ownerMap.values());
    }

    @Override
    public void updateOwner(GymOwner owner) {
        ownerMap.put(owner.getOwnerId(), owner);
    }

    @Override
    public void removeOwner(int ownerId) {
        ownerMap.remove(ownerId);
    }
    */
    
}