package com.flipfit.business;
import com.flipfit.bean.*;
import java.util.*;

public interface GymOwnerBusinessServiceInterface {
    public void registerOwner(GymOwner gymOwner);
    public void viewGymCenters(GymOwner owner);
    public void viewBookingDetails(Slot slot);
    public void addSlotsAndCapacity(GymCenter center, int numSlots, int capacity);
    public List <Notification> viewNotifications();
    public void viewPayment(GymCenter center);
    public void viewSlot(GymCenter gymCenter);
    public int registerGymCenter(String centerName, String centerLocation);
    public void editSlot(int slotID, String centerName);
}