package com.flipfit.business;

import com.flipfit.bean.*;

public class GymCustomerBusinessService
{
    public void registerCustomer(GymCustomer gymCustomer){
        System.out.println("[Gym Customer Created]");
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
}