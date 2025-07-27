
package com.flipfit.business;

import com.flipfit.bean.*;

import java.util.List;

public interface GymCustomerBusinessServiceInterface {
    void registerCustomer(GymCustomer gymCustomer);

    List<GymCenter> viewGymCenter();

    List<Slot> viewSlotsFromCenter(int centerId);

    Slot viewSlotFromBooking(int bookingId);

    String viewCenterName(int centerId);

    int  bookSlot(int customerId, int slotId);

    void cancelBooking(int bookingId);

    void editBooking(int bookingId);

    boolean makePayment(int bookingId);
    
    List<Booking> viewBookings(int customerId);

    void editProfile(GymCustomer gymCustomer);

    GymCustomer createCustomerBean(String name, String password, int role, String userName, int age,
            String location, int gender, String email, int userId);

    List<Notification> viewNotificationsByCustomerId(int customerId);
}
