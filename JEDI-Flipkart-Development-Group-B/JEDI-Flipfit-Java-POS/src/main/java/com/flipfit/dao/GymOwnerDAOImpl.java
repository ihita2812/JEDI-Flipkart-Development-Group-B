package com.flipfit.dao;

import com.flipfit.bean.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
        int userIdToRemove = -1;

        // Step 1: Find the userId associated with the ownerId.
        // This also serves as a check to see if the owner exists.
        String findUserSql = "SELECT userId FROM Flipfit.GymOwner WHERE ownerId = ?";
        try (Connection db = DBConnection.getConnection();
             PreparedStatement findUserStmt = db.prepareStatement(findUserSql)) {

            findUserStmt.setInt(1, ownerId);
            ResultSet rs = findUserStmt.executeQuery();

            if (rs.next()) {
                userIdToRemove = rs.getInt("userId");
            } else {
                // If no row is found, the ownerId was invalid. Return false.
                System.out.println("DAO: No owner found with ID: " + ownerId);
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        // Step 2: Delete the user from the main GymUser table.
        // The database's "ON DELETE CASCADE" will handle deleting the GymOwner entry.
        String deleteUserSql = "DELETE FROM Flipfit.GymUser WHERE userId = ?";
        try (Connection db = DBConnection.getConnection();
             PreparedStatement deleteUserStmt = db.prepareStatement(deleteUserSql)) {

            deleteUserStmt.setInt(1, userIdToRemove);
            int rowsAffected = deleteUserStmt.executeUpdate();

            // Step 3: Return true ONLY if the DELETE operation actually affected a row.
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void addGymCenter(GymCenter gymCenter) {

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