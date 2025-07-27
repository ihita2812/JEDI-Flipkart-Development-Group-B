package com.flipfit.business;

import com.flipfit.bean.*;
import java.util.*;
import com.flipfit.dao.*;


/*
 *@author: Ritesh, Ritesh, Zaid
 *@ClassName:GysCustomerBusinessService
 *@version:1.0
 *@See :GymCustomerBusinessServiceInterface
 */
public class GymCustomerBusinessService implements GymCustomerBusinessServiceInterface {
    private final GymCustomerDAO customerDAO = new GymCustomerDAOImpl();
    private final GymUserDAO userDAO = new GymUserDAOImpl();

    public void registerCustomer(GymCustomer gymCustomer) {
        customerDAO.addCustomer(gymCustomer);
    }

    public GymCustomer createCustomerBean(String name, String password, int role, String userName, int age,
            String location, int gender, String email, int userId) {
        GymCustomer gymCustomer = new GymCustomer();
        gymCustomer.setName(name);
        gymCustomer.setPassword(password);
        gymCustomer.setRole(userDAO.getRole(role));
        gymCustomer.setUserName(userName);
        gymCustomer.setAge(age);
        gymCustomer.setLocation(location);
        gymCustomer.setGender(gender);
        gymCustomer.setEmail(email);
        gymCustomer.setUserId(userId);
        return gymCustomer;
    }

    public List<GymCenter> viewGymCenter() {
        return userDAO.getAllValidCenters();
    }

    public List<Slot> viewSlotsFromCenter(int centerId) {
        return userDAO.getSlotByCenterId(centerId);
    }

    public Slot viewSlotFromBooking(int bookingId) {
        return userDAO.getSlotByBookingId(bookingId);
    }

    public String viewCenterName(int centerId) {
        return userDAO.getCenterNameByCenterId(centerId);
    }

    public List<Notification> viewNotificationsByCustomerId(int customerId) {
        // iterate in notificationMap and store the Notification objects in a List which
        // match the owner's ID
        return customerDAO.getNotificationsByCustomerId(customerId);
    }

    public int bookSlot(int customerId, int slotId) {
        Booking booking = new Booking();
        booking.setSlotId(slotId);
        booking.setCustomerId(customerId);
        booking.setStatus(0); // 0 for payment pending
        return userDAO.addBooking(booking);
    }

    public void cancelBooking(int bookingId) {
        userDAO.cancelBookingById(bookingId);
    }

    public void editBooking(int bookingId) {
        System.out.println("[Booking " + bookingId + " Edited]");
    }

    public boolean makePayment(int bookingId) {
        return userDAO.approvePayment(bookingId);
    }

    public List<Booking> viewBookings(int customerId) {
        return userDAO.getBookingsByCustomerId(customerId);
    }

    public void editProfile(GymCustomer gymCustomer) {
        System.out.println("Profile Edited!");
    }
}