package com.flipfit.dao;

import com.flipfit.bean.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Date;
import java.util.*;
import com.flipfit.dao.GymUserDAOImpl;


/*
 *@author:Kashif, Ritesh, Zaid
 *@ClassName:GymOwnerDAOImpl
 *@Exceptions:SQL Exception
 *@version:1.0
 *@See :GymOwnerDAO
 */
public class GymOwnerDAOImpl implements GymOwnerDAO {

    public static Map<Integer, GymOwner> ownerMap = new HashMap<>();
    GymUserDAOImpl gymUserDAOImpl = new GymUserDAOImpl();
    public GymOwnerDAOImpl() {
        Role ownerRole = GymUserDAOImpl.roleMap.get(1);
        // Role ownerRole = roleMap.get(1);

//        GymOwner owner1 = new GymOwner();
//        owner1.setUserId(2);
//        owner1.setUserName("owner");
//        owner1.setPassword("owner");
//        owner1.setName("Gym Owner");
//        owner1.setRole(ownerRole);
//        owner1.setOwnerId(1);
//        owner1.setGender('M');
//        owner1.setEmail("owner@gaanmara.com");
//        ownerMap.put(owner1.getOwnerId(), owner1);
//
//        GymOwner owner2 = new GymOwner();
//        owner2.setUserId(5);
//        owner2.setUserName("owner1");
//        owner2.setPassword("owner");
//        owner2.setName("Gym Owner hu mai");
//        owner2.setRole(ownerRole);
//        owner2.setOwnerId(2);
//        owner2.setGender('F');
//        owner2.setEmail("owner1@gaanmara.com");
//        ownerMap.put(owner2.getOwnerId(), owner2);
    }

    public List<Payment> getAllPaymentsByCenterId(int centerId)
    {

        List<Payment> payments = new ArrayList<>();
        // This SQL query joins three tables to find all payments
        // associated with a specific gym center.
        String sql = "SELECT p.* " +
                "FROM Flipfit.Payment p " +
                "JOIN Flipfit.Booking b ON p.bookingId = b.bookingId " +
                "JOIN Flipfit.Slot s ON b.slotId = s.slotId " +
                "WHERE s.centerId = ?";

        try (Connection db = DBConnection.getConnection();
             PreparedStatement preparedStatement = db.prepareStatement(sql)) {

            preparedStatement.setInt(1, centerId);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Payment payment = new Payment();
                    payment.setPaymentId(rs.getInt("paymentId"));
                    payment.setBookingId(rs.getInt("bookingId"));
                    payment.setCustomerId(rs.getInt("customerId"));
                    payment.setAmount(rs.getFloat("amount"));
                    payment.setPaymentDateTime(rs.getTimestamp("paymentDateTime").toLocalDateTime());

                    payments.add(payment);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching payments for center ID: " + centerId);
            e.printStackTrace();
        }
        return payments;
    }
    public List<Notification> getNotificationsByOwnerId(int ownerId) {
//        List<Notification> notifications = new ArrayList<>();
//        for (Notification notification : notificationMap.values()) {
//            if (notification.getUserId() == ownerId) {
//                notifications.add(notification);
//            }
//        }
            List<Notification> notifications = new ArrayList<>();
            // The Notification table uses the main userId, so first we must find the
            // userId that corresponds to the given ownerId.
            int userId = -1;
            try {
                userId = gymUserDAOImpl.getUserIdFromOwnerId(ownerId);
            } catch (Exception e) {
                System.err.println("Could not retrieve userId for ownerId: " + ownerId);
                e.printStackTrace();
                return notifications; // Return empty list if userId can't be found
            }

            if (userId == -1) {
                System.err.println("No user found for ownerId: " + ownerId);
                return notifications; // Return empty list
            }

            // Now, fetch notifications for the retrieved userId
            String sql = "SELECT * FROM Flipfit.Notification WHERE userId = ?";

            try (Connection db = DBConnection.getConnection();
                 PreparedStatement preparedStatement = db.prepareStatement(sql)) {

                preparedStatement.setInt(1, userId);

                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        Notification notification = new Notification();
                        notification.setNotifId(rs.getInt("notifiId")); // Note column name
                        notification.setMessage(rs.getString("message"));
                        notification.setUserId(rs.getInt("userId"));
                        notifications.add(notification);
                    }
                }
            } catch (SQLException e) {
                System.err.println("Error fetching notifications for owner ID: " + ownerId);
                e.printStackTrace();
            }
        return notifications;
    }

    public List<GymCenter> getAllCentersByOwnerId(int ownerId) {
        List<GymCenter> gymCenters = new ArrayList<>();
        List<GymCenter> allCenters = gymUserDAOImpl.getAllCenters();
        for (GymCenter gymCenter : allCenters) {
            if (gymCenter.getOwnerId() == ownerId) {
                gymCenters.add(gymCenter);
            }
        }
        return gymCenters;
    }

//    public GymCenter getCenterById(int centerId) {
//        return GymUserDAOImpl.centerMap.get(centerId);
//    }

    @Override
    public void addOwner(GymOwner gymOwner) {
        int newOwnerId = -1;
        try (Connection db = DBConnection.getConnection();
             PreparedStatement preparedStatement = db.prepareStatement("SELECT IFNULL(MAX(ownerId), 0) + 1 FROM Flipfit.GymOwner")) {
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                newOwnerId = rs.getInt(1);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        gymOwner.setOwnerId(newOwnerId);
        ownerMap.put(newOwnerId, gymOwner);

        try (Connection db = DBConnection.getConnection();
             PreparedStatement preparedStatement = db.prepareStatement("INSERT INTO Flipfit.GymOwner (ownerId, gender, email, userId) VALUES (?, ?, ?, ?);")) {

            preparedStatement.setInt(1, gymOwner.getOwnerId());
            preparedStatement.setInt(2, gymOwner.getGender());
            preparedStatement.setString(3, gymOwner.getEmail());
            preparedStatement.setInt(4, gymOwner.getUserId());

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        }
        int userId1=-1;
        try (Connection db = DBConnection.getConnection();
             PreparedStatement getAdminIdStatement = db.prepareStatement("SELECT userId FROM Flipfit.GymOwner WHERE ownerId = ?")) {

            getAdminIdStatement.setInt(1, ownerId);
            ResultSet customerResult = getAdminIdStatement.executeQuery();

            // *** THE FIX IS HERE: Call next() before accessing data ***
            if (customerResult.next()) { // Move the cursor to the first (and likely only) row
                userId1 = customerResult.getInt("userId");
            }
        } catch (SQLException e) {
            // Handle SQL exceptions
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println(userId1);

        try (Connection db = DBConnection.getConnection();
             PreparedStatement preparedStatement = db.prepareStatement("DELETE FROM Flipfit.GymOwner WHERE ownerId = ?")) {

            preparedStatement.setInt(1, ownerId);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) deleted.");
            gymUserDAOImpl.removeUser(userId1);
            return  true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void addGymCenter(GymCenter gymCenter) {
        // This method would typically save the gym center to a database.
        // For now, we will just add it to the map.
        // GymUserDAOImpl.centermap.put(gymCenter.getCenterId(), gymCenter);
        // System.out.println("Gym Center Added: " + gymCenter.getName());

        int newGymCenterId = -1;
        try (Connection db = DBConnection.getConnection();
             PreparedStatement preparedStatement = db.prepareStatement("SELECT IFNULL(MAX(centerId), 0) + 1 FROM Flipfit.GymCenter")) {
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                newGymCenterId = rs.getInt(1);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        gymCenter.setCenterId(newGymCenterId);
       // GymUserDAOImpl.centerMap.put(newGymCenterId, gymCenter);

        try (Connection db = DBConnection.getConnection();
             PreparedStatement preparedStatement = db.prepareStatement("INSERT INTO Flipfit.GymCenter (centerId, name, location, capacity, numSlots, ownerId, approvalStatus) VALUES (?, ?, ?, ?, ?, ?, ?);")) {

            preparedStatement.setInt(1, gymCenter.getCenterId());
            preparedStatement.setString(2, gymCenter.getName());
            preparedStatement.setString(3, gymCenter.getLocation());
            preparedStatement.setInt(4, gymCenter.getCapacity());
            preparedStatement.setInt(5, gymCenter.getNumSlots());
            preparedStatement.setInt(6, gymCenter.getOwnerId());
            preparedStatement.setInt(7, gymCenter.getApprovalStatus());

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        for (GymAdmin admin : adminMap.values()) {
//            int userId = admin.getUserId();
//            Notification newCenterNotification = new Notification();
//            newCenterNotification.setMessage("New gym center " + newGymCenterId + " is waiting for approval!");
//            newCenterNotification.setUserId(userId);
//            addNotification(newCenterNotification);
//        }


        // System.out.println("User added successfully with ID: " + newGymCenterId);
    }

    public void addNotification(Notification notification) {
        int newNotificationId = -1;
        try (Connection db = DBConnection.getConnection();
             PreparedStatement preparedStatement = db.prepareStatement("SELECT IFNULL(MAX(notifiId), 0) + 1 FROM Flipfit.Notification")) {
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                newNotificationId = rs.getInt(1);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        notification.setNotifId(newNotificationId);
        //notificationMap.put(newNotificationId, notification);

        try (Connection db = DBConnection.getConnection();
             PreparedStatement preparedStatement = db.prepareStatement("INSERT INTO Flipfit.Notification (notifiId, message, userId) VALUES (?, ?, ?);")) {

            preparedStatement.setInt(1, notification.getNotifId());
            preparedStatement.setString(2, notification.getMessage());
            preparedStatement.setInt(3, notification.getUserId());

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addGymSlot(Slot slot) {
        int newSlotId = -1;
        try (Connection db = DBConnection.getConnection();
             PreparedStatement preparedStatement = db.prepareStatement("SELECT IFNULL(MAX(slotId), 0) + 1 FROM Flipfit.Slot")) {
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                newSlotId = rs.getInt(1);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        slot.setSlotId(newSlotId);
        //GymUserDAOImpl.slotMap.put(newSlotId, slot);

        try (Connection db = DBConnection.getConnection();
             PreparedStatement preparedStatement = db.prepareStatement("INSERT INTO Flipfit.Slot (slotId, startTime, date, bookedSeats, centerId) VALUES (?, ?, ?, ?, ?);")) {

            preparedStatement.setInt(1, slot.getSlotId());
            preparedStatement.setTime(2, Time.valueOf(slot.getStartTime()));
            preparedStatement.setDate(3, Date.valueOf(slot.getDate()));
            preparedStatement.setInt(4, slot.getBookedSeats());
            preparedStatement.setInt(5, slot.getCenterId());

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}