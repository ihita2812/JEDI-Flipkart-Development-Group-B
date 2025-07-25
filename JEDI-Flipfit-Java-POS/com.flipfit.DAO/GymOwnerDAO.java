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

    boolean removeOwner(int ownerId);

    List<Booking> getBookingsBySlotId(int slotId);

    List<Notification> getNotificationsByOwnerId(int ownerId);

    Payment getPaymentByBookingId(int bookingId);

    List<Payment> getAllPaymentsByCenterId(int centerId);

    void addGymSlot(Slot slot);

    void addNotification(Notification notification);
}