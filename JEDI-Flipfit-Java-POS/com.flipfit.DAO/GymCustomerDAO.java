package com.flipfit.dao;

import com.flipfit.bean.*;
import com.flipfit.dao.GymUserDAOImpl;

import java.util.*;

public interface GymCustomerDAO {
    // void registerCustomer(GymCustomer gymCustomer);
    // void viewGymCenter(String location);
    // void viewSlot(int gymCenterId);
    // void bookSlot(int slotId);
    // void cancelBooking(int bookingId);
    // void editBooking(int bookingId);
    // void makePayment(int bookingId);
    // void viewBookings(int customerId);
    // void editProfile(GymCustomer gymCustomer);
    // void addCustomer(GymCustomer gymCustomer);
    public List <Notification> getNotificationsByCustomerId(int customerId);
}