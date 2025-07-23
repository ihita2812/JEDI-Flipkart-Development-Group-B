package com.flipfit.business;

import com.flipfit.bean.*;

public class GymAdminBusinessService
{
    public void createAdmin(GymAdmin gymAdmin){
        System.out.println("Creating Admin");
    }
    public void viewRegisteredGyms(){
        System.out.println("[Viewed Registered Gyms]");
    }
    // public void approveGym(GymCenter gymCenter){
    //     System.out.println("Approving Gym");
    // }
    // public void removeGym(GymCenter gymCenter){
    //     System.out.println("Removing Gym");
    // }
    public void viewPayments(){
        System.out.println("[Viewed Payment Information]");
    }
    public void cancelbooking(Booking booking){
        System.out.println("Booking Cancelled");
    }
    public void viewPendingGymCenters() {
        System.out.println("[Here are the gym centers pending approval!]");
    }
    public boolean verifyGymCenter(int gymCenter) {
        // admin will view details of gym and approve or reject.
        System.out.println("[Did you approve or reject? IDK, cos there is no functionality yet.]");
        return true;
    }

    public void addNewRole(String roleName, String roleDesc) {
        // admin can create new role here
        System.out.println("[New role added to db!]");
    }
    public void addGymCustomer(String customerName, String customerEmail, String customerPhone) {
        // admin can add new gym customer here
        System.out.println("[New gym customer added to db!]");
    }
    public void removeGymCustomer(int customerId) {
        // admin can remove gym customer here
        System.out.println("[Gym customer with ID " + customerId + " removed from db!]");
    }
    public void addGymOwner(String ownerName, String ownerEmail, String ownerPhone) {
        // admin can add new gym owner here
        System.out.println("[New gym owner added to db!]");
    }
    public void removeGymOwner(int ownerId) {
        // admin can remove gym owner here
        System.out.println("[Gym owner with ID " + ownerId + " removed from db!]");
    }

}