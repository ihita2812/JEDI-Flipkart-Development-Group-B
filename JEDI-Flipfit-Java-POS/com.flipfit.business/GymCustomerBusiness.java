package com.flipfit.business;

import com.flipfit.bean.*;

public class GymCustomerBusiness
{
    public void createGymCustomer(GymCustomer gymCustomer){
        System.out.println("Gym Customer Created");
    }
    public void viewGymCenter(String location){
        System.out.println("Gym Center Viewed");
    }
    public void viewSlot(GymCenter gymCenter){
        System.out.println("Slot Viewed");
    }
    public void bookSlot(Slot slot){
        System.out.println("Slot Booked");
    }
    public void cancelbooking(Booking booking){
        System.out.println("Booking Cancelled");
    }
    public void makePayment(Booking booking){
        System.out.println("Make Payment");
    }
}