package com.flipfit.business;

import com.flipfit.bean.GymCustomer;
import com.flipfit.bean.GymCenter;
import com.flipfit.bean.Booking;

public class GymCustomerBusiness
{
    public void createGymCustomer(GymCustomer gymCustomer){
        System.out.println("Gym Customer Created");
    }
    public void viewGymCenter(){
        System.out.println("Gym Center Viewed");
    }
    public void bookSlot(GymCenter gymCenter){
        System.out.println("Slot Booked");
    }
    public void cancelSlot(){
        System.out.println("Slot Cancelled");
    }
    public void makePayment(Booking booking){
        System.out.println("Make Payment");
    }
}