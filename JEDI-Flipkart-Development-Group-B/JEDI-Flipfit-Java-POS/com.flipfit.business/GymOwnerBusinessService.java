package com.flipfit.business;

import com.flipfit.bean.*;
import com.flipfit.dao.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

/*
 *@author:Ihita, Ritesh, Kashif
 *@ClassName:GymOwnerBusinessService
 *@version:1.0
 *@See :GymOwnerBusinessServiceInterface
 */
public class GymOwnerBusinessService implements GymOwnerBusinessServiceInterface {
    private GymOwnerDAO ownerDAO = new GymOwnerDAOImpl();
    private GymUserDAO userDAO = new GymUserDAOImpl();

    public void registerOwner(GymOwner gymOwner) {
        ownerDAO.addOwner(gymOwner);
    }

    public GymOwner createOwnerBean(String name, String password, int role, String userName, int gender, String email, int userId) {
        GymOwner gymOwner = new GymOwner();
        gymOwner.setName(name);
        gymOwner.setPassword(password);
        gymOwner.setRole(userDAO.getRole(role));
        gymOwner.setUserName(userName);
        gymOwner.setGender(gender);
        gymOwner.setEmail(email);
        gymOwner.setUserId(userId);
        return gymOwner;
    }

    public List<GymCenter> viewGymCenters(int ownerId) {
        List<GymCenter> gymCenters = userDAO.getAllCentersByOwnerId(ownerId);
        return gymCenters;
        // This method would typically return a list of GymCenter objects
        // fetching gym centers from a database or service.
        // System.out.println("Gym Centers Viewed");
    }

    public List<Booking> viewBookingDetails(int slotId) {
        List<Booking> bookings = userDAO.getBookingsBySlotId(slotId);
        return bookings;
    }

    public void addSlotsAndCapacity(int centerId, int numSlots, int capacity) {
        // updating the database with the new slot information.
        GymCenter center = userDAO.getCenterById(centerId);
        center.setNumSlots(numSlots);
        center.setCapacity(capacity);
    }

    public List<Notification> viewNotificationsByOwnerId(int ownerId) {
        // iterate in notificationMap and store the Notification objects in a List which
        // match the owner's ID
        List<Notification> notifications = ownerDAO.getNotificationsByOwnerId(ownerId);

        return notifications;
    }

    public void viewPayment(GymCenter center) {
        // fetching payment details from a database or payment service.
        System.out.println("Payment details viewed");
    }

    public List<Slot> viewSlots(int centerId) {
        return userDAO.getSlotByCenterId(centerId);
    }

    public GymCenter createGymCenterBean(String centerName, String centerLocation, int capacity, int numSlots,
            int ownerId) {
        GymCenter gymCenter = new GymCenter();
        gymCenter.setName(centerName);
        gymCenter.setLocation(centerLocation);
        gymCenter.setCapacity(capacity);
        gymCenter.setNumSlots(numSlots);
        gymCenter.setOwnerId(ownerId);
        gymCenter.setApprovalStatus(2); // pending approval
        return gymCenter;
        // give GSTIN etc number of self to register gym
        // send to admin for approval
    }

    public void registerGymCenter(GymCenter gymCenter) {
        // This method would typically save the gym center to a database.
        ownerDAO.addGymCenter(gymCenter);
        // System.out.println("Gym Center Registered: " + gymCenter.getName());
    }

    public void editSlot(int slotID, String centerName) {
        System.out.println("Your slot has been edited");
    }

    @Override
    public List<Payment> viewAllPayments(int centerId) {
        return ownerDAO.getAllPaymentsByCenterId(centerId);
    }

    public Payment viewPaymentDetails(int bookingId)
    {
        return userDAO.getPaymentByBookingId(bookingId);
    }
    public Slot createSlotBean(LocalTime startTime, LocalDate date, int centerId)
    {
        Slot slot = new Slot();
        slot.setStartTime(startTime);
        slot.setDate(date);
        slot.setCenterId(centerId);
        slot.setBookedSeats(0);
        // The DAO will set the user ID when the user is added to the database.
        return slot;
    }

    public void registerGymSlot(Slot slot) {
        ownerDAO.addGymSlot(slot);
    }
}
