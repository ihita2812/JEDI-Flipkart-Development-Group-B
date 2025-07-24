package com.flipfit.business;

import com.flipfit.bean.*;
import java.util.*;
import com.flipfit.DAO.*;

public class GymOwnerBusinessService implements GymOwnerBusinessServiceInterface{
    
    @Override
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
    @Override
    public void viewGymCenters(GymOwner owner) {
        // fetching gym centers from a database or service.
        System.out.println("Gym Centers Viewed");
    }
    @Override
    public void viewBookingDetails(Slot slot) {
        System.out.println("Booking Details Viewed");
    }
    @Override
    public void addSlotsAndCapacity(GymCenter center, int numSlots, int capacity) {
        // updating the database with the new slot information.
        center.setNumSlots(numSlots);
        center.setCapacity(capacity);
        System.out.println("Slots and capacity added for center: " + center.getName());
    }
    public List <Notification> viewNotifications(int ownerId){
        // iterate in notificationMap and store the Notification objects in a List which match the owner's ID
        List <Notification> notifications = viewOwnerNotifications(ownerId);

        return notifications;
    }
    @Override
    public void viewPayment(GymCenter center) {
        // fetching payment details from a database or payment service.
        System.out.println("Payment details viewed");
    }

     @Override
     public void viewSlot(GymCenter gymCenter){
        //fetching all the slots of the gym 
        System.out.println("Slot Viewed");
     }
    @Override
    public int registerGymCenter(String centerName, String centerLocation) {
        // give GSTIN etc number of self to register gym
        // send to admin for approval
        System.out.println("Yout gym has been sent for approval!");
        return 0;
    }

    @Override
    public void editSlot(int slotID, String centerName){
        System.out.println("Your slot has been edited");
    }
}
