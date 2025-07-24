package com.flipfit.business;

import com.flipfit.bean.*;
import java.util.*;
import com.flipfit.dao.*;

public class GymCustomerBusinessService implements GymCustomerBusinessServiceInterface {
    private GymCustomerDAO customerDAO = new GymCustomerDAOImpl();
    private GymUserDAO userDAO = new GymUserDAOImpl();

    public void registerCustomer(GymCustomer gymCustomer) {
        GymCustomerDAOImpl.customerMap.put(gymCustomer.getCustomerId(), gymCustomer);
    }

    public int nextCustomerId() {
        int currMax = Collections.max(GymCustomerDAOImpl.customerMap.keySet());
        return (currMax + 1);
    }

    public GymCustomer createCustomerBean(String name, String password, int role, String userName, int age, String location, int gender, String email) {
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

    public List<Object> viewSlots(int centerId) {
        return userDAO.getSlotByCenterId(centerId);
    }
    public List <Notification> viewNotificationsByCustomerId(int customerId){
        // iterate in notificationMap and store the Notification objects in a List which match the owner's ID
        List <Notification> notifications = customerDAO.getNotificationsByCustomerId(customerId);

        return notifications;
    }

    public int bookSlot(int customerId, int slotId) {
        Booking booking = new Booking();
        booking.setSlotId(slotId);
        booking.setCustomerId(customerId);
        booking.setStatus(0); // 0 for payment pending
        return userDAO.addBooking(booking);
    }
    public void cancelBooking(int bookingId){
        userDAO.cancelBookingById(bookingId);
    }

    public void editBooking(int bookingId) {
        System.out.println("[Booking " + bookingId + " Edited]");
    }

    public void makePayment(int bookingId) {
        userDAO.approvePayment(bookingId);
    }
    
    public List<Object> viewBookings(GymCustomer gymCustomer) {
        return userDAO.getBookingsByCustomerId(gymCustomer.getCustomerId());
    }

    public void editProfile(GymCustomer gymCustomer) {
        System.out.println("Profile Edited!");
    }
}