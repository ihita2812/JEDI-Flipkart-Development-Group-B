package com.flipfit.business;
import com.flipfit.bean.*;
public class GymOwnerBusiness {
    
    public void viewGymCenters(GymOwner owner) {
        int ownerId = owner.getOwnerId();
        // query a database to get all GymCenter objects where ownerId
        // matches the owner's ID.
    }
    public void viewBookingDetails(GymOwner owner, Slot slot) {
        System.out.println("Booking Details Viewed");
    }
    public void addSlotsAndCapacity(GymOwner owner, GymCenter center, int numSlots, int capacity) {
        // updating the database with the new slot information.
        center.setNumSlots(numSlots);
        center.setCapacity(capacity);
        System.out.println("Slots and capacity added for center: " + center.getName());
    }
    public void viewNotifications(){
        //fetching notifications from a database or a notification service.
        System.out.println("Notifications viewed"); 
    }
}
