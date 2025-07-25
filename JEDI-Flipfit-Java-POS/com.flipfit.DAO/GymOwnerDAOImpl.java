package com.flipfit.dao;

import com.flipfit.bean.*;
import java.util.*;
import com.flipfit.dao.GymUserDAOImpl;

import static com.flipfit.dao.GymAdminDAOImpl.adminMap;
import static com.flipfit.dao.GymUserDAOImpl.notificationMap;

public class GymOwnerDAOImpl implements GymOwnerDAO {

    public static Map<Integer, GymOwner> ownerMap = new HashMap<>();

    public GymOwnerDAOImpl() {
        Role ownerRole = GymUserDAOImpl.roleMap.get(1);
        // Role ownerRole = roleMap.get(1);

        GymOwner owner1 = new GymOwner();
        owner1.setUserId(2);
        owner1.setUserName("owner");
        owner1.setPassword("owner");
        owner1.setName("Gym Owner");
        owner1.setRole(ownerRole);
        owner1.setOwnerId(1);
        owner1.setGender('M');
        owner1.setEmail("owner@gaanmara.com");
        ownerMap.put(owner1.getOwnerId(), owner1);

        GymOwner owner2 = new GymOwner();
        owner2.setUserId(5);
        owner2.setUserName("owner1");
        owner2.setPassword("owner");
        owner2.setName("Gym Owner hu mai");
        owner2.setRole(ownerRole);
        owner2.setOwnerId(2);
        owner2.setGender('F');
        owner2.setEmail("owner1@gaanmara.com");
        ownerMap.put(owner2.getOwnerId(), owner2);
    }

    public Payment getPaymentByBookingId(int bookingId)
    {
        for(Payment payment: GymUserDAOImpl.paymentMap.values())
        {
            if(payment.getBookingId() == bookingId)
            {
                return payment;
            }
        }
        return null;
    }

    public List<Payment> getAllPaymentsByCenterId(int centerId)
    {
        List<Payment> payments = new ArrayList<>();
        for(Slot s: GymUserDAOImpl.slotMap.values())
        {
            if(s.getCenterId() == centerId)
            {
                for (Booking booking : GymUserDAOImpl.bookingMap.values()) {
                    if (booking.getSlotId() == s.getSlotId()) {
                        for(Payment payment: GymUserDAOImpl.paymentMap.values())
                        {
                            if(payment.getBookingId() == booking.getBookingId())
                            {
                                 payments.add(payment);
                            }
                        }
                    }
                }
            }
        }
        return payments;
    }
    public List<Notification> getNotificationsByOwnerId(int ownerId) {
        List<Notification> notifications = new ArrayList<>();
        for (Notification notification : notificationMap.values()) {
            if (notification.getUserId() == ownerId) {
                notifications.add(notification);
            }
        }
        return notifications;
    }

    public List<GymCenter> getAllCentersByOwnerId(int ownerId) {
        List<GymCenter> gymCenters = new ArrayList<>();
        for (GymCenter gymCenter : GymUserDAOImpl.centerMap.values()) {
            if (gymCenter.getOwnerId() == ownerId) {
                gymCenters.add(gymCenter);
            }
        }
        return gymCenters;
    }

    public GymCenter getCenterById(int centerId) {
        return GymUserDAOImpl.centerMap.get(centerId);
    }

    public List<Booking> getBookingsBySlotId(int slotId) {
        List<Booking> bookings = new ArrayList<>();
        for (Booking booking : GymUserDAOImpl.bookingMap.values()) {
            if (booking.getSlotId() == slotId) {
                bookings.add(booking);
            }
        }
        return bookings;
    }

    @Override
    public void addOwner(GymOwner gymOwner) {
        // Check if username already exists
//        for (GymOwner existing : ownerMap.values()) {
//            if (existing.getUserName().equals(gymOwner.getUserName())) {
//                System.out.println("Username already exists!");
//                return;
//            }
//        }

        int newOwnerId = Collections.max(ownerMap.keySet()) + 1;
        gymOwner.setOwnerId(newOwnerId);
        ownerMap.put(newOwnerId, gymOwner);
        // System.out.println("Owner added successfully with ID: " + newOwnerId);
    }

    @Override
    public GymOwner getOwnerById(int ownerId) {
        return ownerMap.get(ownerId);
    }

    @Override
    public List<GymOwner> getAllOwners() {
        return new ArrayList<>(ownerMap.values());
    }

    @Override
    public void updateOwner(GymOwner owner) {
        ownerMap.put(owner.getOwnerId(), owner);
    }

    @Override
    public boolean removeOwner(int ownerId) {
        if(ownerMap.containsKey(ownerId)){
            ownerMap.remove(ownerId);
            return  true;
        }
        return false;
    }

    public void addGymCenter(GymCenter gymCenter) {
        // This method would typically save the gym center to a database.
        // For now, we will just add it to the map.
        // GymUserDAOImpl.centermap.put(gymCenter.getCenterId(), gymCenter);
        // System.out.println("Gym Center Added: " + gymCenter.getName());

        int newGymCenterId =  GymUserDAOImpl.centerMap.isEmpty() ? 1 : Collections.max(GymUserDAOImpl.centerMap.keySet()) + 1;
        gymCenter.setCenterId(newGymCenterId);
        GymUserDAOImpl.centerMap.put(newGymCenterId, gymCenter);

        for (GymAdmin admin : adminMap.values()) {
            int userId = admin.getUserId();
            Notification newCenterNotification = new Notification();
            newCenterNotification.setMessage("New gym center " + newGymCenterId + " is waiting for approval!");
            newCenterNotification.setUserId(userId);
            addNotification(newCenterNotification);
        }


        // System.out.println("User added successfully with ID: " + newGymCenterId);
    }

    public void addNotification(Notification notification) {
        int newNotificationId = notificationMap.isEmpty() ? 1 : Collections.max(notificationMap.keySet()) + 1;
        notification.setNotifId(newNotificationId);
        notificationMap.put(newNotificationId, notification);
    }

    public void addGymSlot(Slot slot) {
        int newSlotId =  GymUserDAOImpl.slotMap.isEmpty() ? 1 : Collections.max(GymUserDAOImpl.slotMap.keySet()) + 1;
        slot.setSlotId(newSlotId);
        GymUserDAOImpl.slotMap.put(newSlotId, slot);
    }
}