package com.flipfit.business;

import com.flipfit.bean.*;

public interface GymCustomerBusinessInterface {
    void registerCustomer(GymCustomer gymCustomer);
    void viewGymCenter(String location);
    void viewSlot(int gymCenterId);
    void bookSlot(int slotId);
    void cancelbooking(int bookingId);
    void makePayment(int bookingId);
    void viewBookings();
}
