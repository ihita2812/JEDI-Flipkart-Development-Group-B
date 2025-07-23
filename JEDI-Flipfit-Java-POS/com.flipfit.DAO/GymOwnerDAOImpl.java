package com.flipfit.dao;

import com.flipfit.bean.GymOwner;
import java.util.*;

public class GymOwnerDAOImpl implements GymOwnerDAO {

    private Map<Integer, GymOwner> ownerMap = new HashMap<>();

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