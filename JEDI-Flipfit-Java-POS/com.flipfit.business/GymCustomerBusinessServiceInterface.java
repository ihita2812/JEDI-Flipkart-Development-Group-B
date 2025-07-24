
package com.flipfit.business;

import java.util.*;
import java.util.*;
import com.flipfit.bean.*;

public interface GymCustomerBusinessServiceInterface {
    public void registerCustomer(GymCustomer gymCustomer);
    void viewGymCenter(String location);

    public List<Object> viewSlots(int centerId);
    public int  bookSlot(int customerId, int slotId);

    void cancelBooking(int bookingId);

    void editBooking(int bookingId);

    void makePayment(int bookingId);
    
   public List<Object> viewBookings(GymCustomer gymCustomer);

    public void editProfile(GymCustomer gymCustomer);
    public GymCustomer createCustomerBean(String name, String password, int role, String userName, int age,String location, int gender, String email);
    public List <Notification> viewNotificationsByCustomerId(int customerId);
}
