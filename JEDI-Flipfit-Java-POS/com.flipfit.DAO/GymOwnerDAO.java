package com.flipfit.dao;

import com.flipfit.bean.*;
import java.util.List;
import com.flipfit.dao.GymUserDAOImpl;


public interface GymOwnerDAO {
    /*void addOwner(GymOwner owner);
    GymOwner getOwnerById(int ownerId);
    List<GymOwner> getAllOwners();
    void updateOwner(GymOwner owner);
    void removeOwner(int ownerId);*/
    public List <Notification> getNotificationsByOwnerId(int ownerId);
}