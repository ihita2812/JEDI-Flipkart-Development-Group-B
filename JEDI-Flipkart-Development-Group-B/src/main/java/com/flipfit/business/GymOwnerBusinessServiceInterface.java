package com.flipfit.business;

import com.flipfit.bean.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


public interface GymOwnerBusinessServiceInterface {
    public void registerOwner(GymOwner gymOwner);

    public List<GymCenter> viewGymCenters(int ownerId);

    public List<Booking> viewBookingDetails(int slotId);

    public void addSlotsAndCapacity(int centerId, int numSlots, int capacity);

    public List<Notification> viewNotificationsByOwnerId(int ownerId);

    public List<Payment> viewAllPayments(int centerId);

    public Payment viewPaymentDetails(int bookingId);

    public List<Slot> viewSlots(int centerId);

    public void registerGymCenter(GymCenter gymCenter);

    public GymCenter createGymCenterBean(String centerName, String centerLocation, int capacity, int numSlots,
            int ownerId);

    public void editSlot(int slotID, String centerName);

    public GymOwner createOwnerBean(String name, String password, int role, String userName, int gender, String email, int userId);

    public Slot createSlotBean(LocalTime startTime,LocalDate date, int centerId);

    public void registerGymSlot(Slot slot);
}