package com.flipfit.business;

import com.flipfit.bean.*;

public class GymCustomerBusiness
{
    public void registerCustomer(GymCustomer gymCustomer){
        System.out.println("Gym Customer Created");
    }
    public void viewGymCenter(String location){
        System.out.println("Gym Center Viewed");
    }
    public void viewSlot(int gymCenterId){
        System.out.println("Slots of gym center " + gymCenterId + " Viewed");
    }
    public void bookSlot(int slotId){
        System.out.println("Slot " + slotId + " Booked");
    }
    public void cancelbooking(Booking booking){
        System.out.println("Booking Cancelled");
    }
    public void makePayment(Booking booking){
        System.out.println("Make Payment");
    }

    // TODO : ADD VIEW BOOKINGS
}