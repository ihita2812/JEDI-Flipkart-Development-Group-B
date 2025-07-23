package com.flipfit.business;

import com.flipfit.bean.*;
import java.util.Collections;
import com.flipfit.DAO.GymCustomerDAOImpl.customerMap;

public class GymCustomerBusinessService implements GymCustomerBusinessServiceInterface
{
    public void registerCustomer(GymCustomer gymCustomer){
        customerMap.put(gymCustomer.getCustomerId(), gymCustomer);
    }

    public int nextCustomerId() {
        int currMax = Collections.max(customerMap.keySet());
        return (currMax + 1);
    }

    public GymCustomer createCustomerBean(String name, String password, int role, String userName, int age, String location, String gender, String email) {
        GymCustomer gymCustomer = new GymCustomer();
        gymCustomer.setCustomerId(nextCustomerId());
        gymCustomer.setName(name);
        gymCustomer.setPassword(password);
        gymCustomer.setRole(role);
        gymCustomer.setUserName(userName);
        gymCustomer.setAge(age);
        gymCustomer.setLocation(location);
        gymCustomer.setGender(gender);
        gymCustomer.setEmail(email);
        return gymCustomer;
    }

    public void viewGymCenter(String location){
        System.out.println("[Gym Center Viewed]");
    }
    public void viewSlot(int gymCenterId){
        System.out.println("[Slots of gym center " + gymCenterId + " Viewed]");
    }
    public void bookSlot(int slotId){
        System.out.println("[Slot " + slotId + " Booked]");
    }
    public void cancelBooking(int bookingId){
        System.out.println("[Booking " + bookingId + " Cancelled]");
    }
    public void editBooking(int bookingId){
        System.out.println("[Booking " + bookingId + " Edited]");
    }
    public void makePayment(int bookingId){
        System.out.println("[Payment made!]");
    }
    public void viewBookings() {
        System.out.println("[Bookings viewed]");
    }
    public void editProfile(GymCustomer gymCustomer){
        System.out.println("Profile Edited!");
    }
}