package com.flipfit.dao;

import com.flipfit.bean.GymCustomer;
import com.flipfit.bean.Notification;

import java.util.List;

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
    void addCustomer(GymCustomer gymCustomer);
    public boolean removeCustomer(int customerId);
    public List<Notification> getNotificationsByCustomerId(int customerId);
}