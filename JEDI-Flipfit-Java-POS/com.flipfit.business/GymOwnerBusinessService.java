package com.flipfit.business;

import com.flipfit.bean.*;
import com.flipfit.dao.*;

import java.util.Collections;
import java.util.List;

import java.util.*;

public class GymOwnerBusinessService implements GymOwnerBusinessServiceInterface{
    private GymOwnerDAO ownerDAO = new GymOwnerDAOImpl();
    private GymUserDAO userDAO = new GymUserDAOImpl();

    
    public void registerOwner(GymOwner gymOwner) {
        ownerDAO.addOwner(gymOwner);
        
    }


    public GymOwner createOwnerBean(String name, String password, int role, String userName, int gender, String email) {
        GymOwner gymOwner = new GymOwner();
        gymOwner.setName(name);
        gymOwner.setPassword(password);
        gymOwner.setRole(userDAO.getRole(role));
        gymOwner.setUserName(userName);
        gymOwner.setGender(gender);
        gymOwner.setEmail(email);
        return gymOwner;
    }

    public List <GymCenter>  viewGymCenters(int ownerId) {
        List<GymCenter> gymCenters = ownerDAO.getAllCentersByOwnerId(ownerId);
        return gymCenters;
        // This method would typically return a list of GymCenter objects
        // fetching gym centers from a database or service.
        // System.out.println("Gym Centers Viewed");
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
    public List <Notification> viewNotificationsByOwnerId(int ownerId){
        // iterate in notificationMap and store the Notification objects in a List which match the owner's ID
        List <Notification> notifications = ownerDAO.getNotificationsByOwnerId(ownerId);

        return notifications;
    }
  
    public void viewPayment(GymCenter center) {
        // fetching payment details from a database or payment service.
        System.out.println("Payment details viewed");
    }

  
     public void viewSlot(GymCenter gymCenter){
        //fetching all the slots of the gym 
        System.out.println("Slot Viewed");
     }
    
    public GymCenter createGymCenterBean(String centerName, String centerLocation, int capacity, int numSlots, int ownerId) {
        GymCenter gymCenter = new GymCenter();
        gymCenter.setName(centerName);
        gymCenter.setLocation(centerLocation);
        gymCenter.setCapacity(capacity);
        gymCenter.setNumSlots(numSlots);
        gymCenter.setOwnerId(ownerId);
        gymCenter.setApprovalStatus(2); // pending approval
        return gymCenter;  
        // give GSTIN etc number of self to register gym
        // send to admin for approval
    }

    public void registerGymCenter(GymCenter gymCenter) {
        // This method would typically save the gym center to a database.
        ownerDAO.addGymCenter(gymCenter);
        // System.out.println("Gym Center Registered: " + gymCenter.getName());
    }
    public void editSlot(int slotID, String centerName){
        System.out.println("Your slot has been edited");
    }
}
