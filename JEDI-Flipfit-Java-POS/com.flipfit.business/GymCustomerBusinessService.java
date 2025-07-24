package com.flipfit.business;

import com.flipfit.bean.*;
import java.util.*;
import com.flipfit.dao.GymCustomerDAOImpl;
import com.flipfit.dao.GymUserDAOImpl;

public class GymCustomerBusinessService implements GymCustomerBusinessServiceInterface {
    public void registerCustomer(GymCustomer gymCustomer) {
        GymCustomerDAOImpl.customerMap.put(gymCustomer.getCustomerId(), gymCustomer);
    }

    public int nextCustomerId() {
        int currMax = Collections.max(GymCustomerDAOImpl.customerMap.keySet());
        return (currMax + 1);
    }

    public GymCustomer createCustomerBean(String name, String password, int role, String userName, int age,
            String location, int gender, String email) {
        GymCustomer gymCustomer = new GymCustomer();
        gymCustomer.setCustomerId(nextCustomerId());
        gymCustomer.setName(name);
        gymCustomer.setPassword(password);
        gymCustomer.setRole(GymUserDAOImpl.roleMap.get(role));
        gymCustomer.setUserName(userName);
        gymCustomer.setAge(age);
        gymCustomer.setLocation(location);
        gymCustomer.setGender(gender);
        gymCustomer.setEmail(email);
        return gymCustomer;
    }

    public void viewGymCenter(String location) {
        System.out.println("[Gym Center Viewed]");
    }

    public List<Slot> viewSlot(int gymCenterId) {
        if (GymUserDAOImpl.slotMap.isEmpty()) {
            return Collections.emptyList();
        }
        List<Slot> newSlots = new ArrayList<>();
        for (Slot slot : GymUserDAOImpl.slotMap.values()) {
            if (slot.getCenterId() == gymCenterId) {
                newSlots.add(slot);
            }
        }
        return newSlots;
    }
    public List <Notification> viewNotificationsByCustomerId(int customerId){
        // iterate in notificationMap and store the Notification objects in a List which match the owner's ID
        List <Notification> notifications = getNotificationsByCustomerId(customerId);

        return notifications;
    }

    public void bookSlot(int slotId) {
        System.out.println("[Slot " + slotId + " Booked]");
    }
    public void cancelBooking(int bookingId){
        cancelBookingById(bookingId);
        System.out.println("[Booking " + bookingId + " Cancelled]");
    }

    public void editBooking(int bookingId) {
        System.out.println("[Booking " + bookingId + " Edited]");
    }

    public void makePayment(int bookingId) {
        System.out.println("[Payment made!]");
    }
    public List<Booking> viewBookings(GymCustomer gymCustomer) {
        return getBookingsByCustomerId(gymCustomer.getCustomerId());
    }

    public void editProfile(GymCustomer gymCustomer) {
        System.out.println("Profile Edited!");
    }
}