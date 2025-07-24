
package com.flipfit.business;

import java.util.*;
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
    public List <Notification> viewNotifications(int customerId);
    public void editProfile(GymCustomer gymCustomer);
}




