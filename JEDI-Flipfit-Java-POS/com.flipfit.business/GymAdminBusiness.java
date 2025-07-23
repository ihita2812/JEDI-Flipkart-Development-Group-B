package com.flipfit.business;

import com.flipfit.bean.*;

public class GymAdminBusiness
{
    public void createAdmin(GymAdmin gymAdmin){
        System.out.println("Creating Admin");
    }
    public void viewRegisteredGyms(){
        System.out.println("Viewed Registered Gyms");
    }
    public void approveGym(GymCenter gymCenter){
        System.out.println("Approving Gym");
    }
    public void removeGym(GymCenter gymCenter){
        System.out.println("Removing Gym");
    }
    public void viewPaymentsInformation(){
        System.out.println("Viewed Payment Information");
    }
    public void cancelbooking(Booking booking){
        System.out.println("Booking Cancelled");
    }

    public void viewPendingGymCenters() {
        System.out.println("Here are the gym centers pending approval!")
    }

    public void verifyGymCenter(GymCenter gymCenter) {
        // admin will view details of gym and approve or reject.
        System.out.println("Did you approve or reject? IDK, cos there is no functionality yet.")
    }

}