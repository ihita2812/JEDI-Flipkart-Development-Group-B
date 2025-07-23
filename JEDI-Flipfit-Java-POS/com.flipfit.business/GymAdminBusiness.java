package com.flipfit.business;

import com.flipfit.bean.GymAdmin;
import com.flipfit.bean.GymOwner;
import com.flipfit.bean.GymCenter;

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
}