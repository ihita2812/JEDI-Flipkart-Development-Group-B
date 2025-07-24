package com.flipfit.dao;

import com.flipfit.bean.*;
import java.util.List;
import com.flipfit.dao.GymUserDAOImpl;

public interface GymOwnerDAO {
    void addOwner(GymOwner owner);

    void addGymCenter(GymCenter gymCenter);

    GymOwner getOwnerById(int ownerId);

    List<GymOwner> getAllOwners();

    List<GymCenter> getAllCentersByOwnerId(int ownerId);

    GymCenter getCenterById(int centerId);

    void updateOwner(GymOwner owner);

    void removeOwner(int ownerId);

    List<Booking> getBookingsBySlotId(int slotId);

    public List<Notification> getNotificationsByOwnerId(int ownerId);
}