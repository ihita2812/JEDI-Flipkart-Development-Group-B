
package com.flipfit.business;

import com.flipfit.bean.*;

public interface GymCustomerBusinessServiceInterface {
    void registerCustomer(GymCustomer gymCustomer);
    void viewGymCenter(String location);
    void viewSlot(int gymCenterId);
    void bookSlot(int slotId);
    void cancelBooking(int bookingId);
    void editBooking(int bookingId);
    void makePayment(int bookingId);
    void viewBookings();
    public void editProfile(GymCustomer gymCustomer);
}




