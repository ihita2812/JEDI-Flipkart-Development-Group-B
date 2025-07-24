package com.flipfit.business;

import com.flipfit.bean.*;
import com.flipfit.DAO.*;
import java.util.Collections;
import java.util.List;


public class GymOwnerBusinessService implements GymOwnerBusinessServiceInterface{
    
    public void registerOwner(GymOwner gymOwner) {
        ownerMap.put(gymOwner.getOwnerId(), gymOwner);
    }

    public int nextOwnerId() {
        int currMax = Collections.max(ownerMap.keySet());
        return (currMax + 1);
    }

    public GymOwner createOwnerBean(String name, String password, int role, String userName) {
        GymOwner gymOwner = new GymOwner();
        gymOwner.setOwnerId(nextOwnerId());
        gymOwner.setName(name);
        gymOwner.setPassword(password);
        gymOwner.setRole(role);
        gymOwner.setUserName(userName);
        return gymOwner;
    }
    public List viewGymCenters(GymOwner owner) {
        int ownerId = owner.getOwnerId();
        List<GymCenter> gymCenters = GymOwnerDAOImpl.getAllCenters(ownerId);
        return gymCenters;
        // This method would typically return a list of GymCenter objects
        // query a database to get all GymCenter objects where ownerId
        // matches the owner's ID.
    }
    public void viewBookingDetails(Slot slot) {
        System.out.println("Booking Details Viewed");
    }
    public void addSlotsAndCapacity(GymCenter center, int numSlots, int capacity) {
        // updating the database with the new slot information.
        center.setNumSlots(numSlots);
        center.setCapacity(capacity);
        System.out.println("Slots and capacity added for center: " + center.getName());
    }
    public void viewNotifications(){
        //fetching notifications from a database or a notification service.
        System.out.println("Notifications viewed"); 
    }
    public void viewPayment(GymCenter center) {
        // fetching payment details from a database or payment service.
        System.out.println("Payment details viewed");
    }

     public void viewSlot(GymCenter gymCenter){
        //fetching all the slots of the gym 
        System.out.println("Slot Viewed");
     }
    public int registerGymCenter(String centerName, String centerLocation) {
        // give GSTIN etc number of self to register gym
        // send to admin for approval
        System.out.println("Yout gym has been sent for approval!");
        return 0;
    }

    public void editSlot(int slotID, String centerName){
        System.out.println("Your slot has been edited");
    }
}
