package com.flipfit.dao;

import com.flipfit.bean.*;

import java.util.List;

public interface GymOwnerDAO {
    void addOwner(GymOwner owner);
    void addGymCenter(GymCenter gymCenter);
    GymOwner getOwnerById(int ownerId);
    List<GymOwner> getAllOwners();
    List<GymCenter> getAllCentersByOwnerId(int ownerId);
    //GymCenter getCenterById(int centerId);
    void updateOwner(GymOwner owner);
    boolean removeOwner(int ownerId);
    List<Notification> getNotificationsByOwnerId(int ownerId);
    List<Payment> getAllPaymentsByCenterId(int centerId);
    void addGymSlot(Slot slot);
    void addNotification(Notification notification);
}