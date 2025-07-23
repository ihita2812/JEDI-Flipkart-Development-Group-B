package com.flipfit.business;
import com.flipfit.bean.*;
public class GymOwnerBusiness {
    GymOwner owner = new GymOwner();
    public void viewGymCenters() {
        int ownerId = this.owner.getOwnerId();
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
     public void viewSlot(GymCenter gymCenter){
        //fetching all the slots of the gym 
        System.out.println("Slot Viewed");
    }
}
