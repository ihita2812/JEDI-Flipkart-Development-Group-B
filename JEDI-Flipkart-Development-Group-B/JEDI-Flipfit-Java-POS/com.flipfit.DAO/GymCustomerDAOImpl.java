package com.flipfit.dao;

import com.flipfit.bean.*;
import com.flipfit.dao.GymUserDAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.sql.ResultSet;
import com.flipfit.dao.GymUserDAOImpl;

/*
 *@author:Ihita, Kashif, Zaid
 *@ClassName:GymCustomerDAOImpl
 *@Exceptions: SQL Exception
 *@version:1.0
 *@See :GymCustomerDAO
 */
public class GymCustomerDAOImpl implements GymCustomerDAO {

    public static Map<Integer, GymCustomer> customerMap = new HashMap<>();
    GymUserDAOImpl gymUserDAOImpl = new GymUserDAOImpl();

    public GymCustomerDAOImpl() {

        //Role customerRole = GymUserDAOImpl.roleMap.get(0);
    }

    public List<Notification> getNotificationsByCustomerId(int customerId) {
        List<Notification> notifications = new ArrayList<>();

        String sql = "SELECT * FROM Flipfit.Notification WHERE userId = ?";

        try (Connection db = DBConnection.getConnection();
             PreparedStatement preparedStatement = db.prepareStatement(sql)) {

            // We need to get the main userId from the customerId to query the notification table.
            int userId = -1;
            String getUserIdSql = "SELECT userId FROM Flipfit.GymCustomer WHERE customerId = ?";
            try(PreparedStatement ps = db.prepareStatement(getUserIdSql)) {
                ps.setInt(1, customerId);
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    userId = rs.getInt("userId");
                }
            }

            if (userId != -1) {
                preparedStatement.setInt(1, userId);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        Notification notification = new Notification();
                        notification.setNotifId(rs.getInt("notifiId"));
                        notification.setMessage(rs.getString("message"));
                        notification.setUserId(rs.getInt("userId"));
                        notifications.add(notification);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching notifications for customer ID: " + customerId);
            e.printStackTrace();
        }
        return notifications;
    }


    public void addCustomer(GymCustomer customer) {
        int newCustomerId = -1;
        try (Connection db = DBConnection.getConnection();
             PreparedStatement preparedStatement = db.prepareStatement("SELECT IFNULL(MAX(customerId), 0) + 1 FROM Flipfit.GymCustomer")) {
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                newCustomerId = rs.getInt(1);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        customer.setCustomerId(newCustomerId);

        try (Connection db = DBConnection.getConnection();
             PreparedStatement preparedStatement = db.prepareStatement("INSERT INTO Flipfit.GymCustomer (customerId, age, location, gender, email, userId) VALUES (?, ?, ?, ?, ?, ?);")) {

            preparedStatement.setInt(1, customer.getCustomerId());
            preparedStatement.setInt(2, customer.getAge());
            preparedStatement.setString(3, customer.getLocation());
            preparedStatement.setInt(4, customer.getGender());
            preparedStatement.setString(5, customer.getEmail());
            preparedStatement.setInt(6, customer.getUserId());

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean removeCustomer(int customerId){

        int userId1 = -1;
        try (Connection db = DBConnection.getConnection();
             PreparedStatement getAdminIdStatement = db.prepareStatement("SELECT userId FROM Flipfit.GymCustomer WHERE CustomerId = ?")) {

            getAdminIdStatement.setInt(1, customerId);
            ResultSet customerResult = getAdminIdStatement.executeQuery();

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
             PreparedStatement preparedStatement = db.prepareStatement("DELETE FROM Flipfit.GymCustomer WHERE customerId = ?")) {

            preparedStatement.setInt(1, customerId);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) deleted.");
            gymUserDAOImpl.removeUser(userId1);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
     * private GymUserDAO userDAO = new GymUserDAOImpl();
     * 
     * private int bookingIdCounter = 1;
     * 
     * public void addCustomer(GymCustomer customer) {
     * if (userDAO.getUserByUsername(customer.getUserName()) != null) {
     * System.out.println("Username already exists!");
     * return;
     * }
     * 
     * int newId = userDAO.getAllUsers().stream()
     * .mapToInt(GymUser::getUserId)
     * .max().orElse(0) + 1;
     * 
     * customer.setCustomerId(newId);
     * customer.setUserId(newId);
     * userDAO.addUser(customer);
     * customerMap.put(newId, customer);
     * 
     * System.out.println("Customer registered with ID: " + newId);
     * }
     * 
     * public void registerCustomer(GymCustomer gymCustomer) {
     * addCustomer(gymCustomer);
     * }
     * 
     * 
     * public void viewGymCenter(String location) {
     * System.out.println("Gyms in location: " + location);
     * for (GymCenter center : gymCenterMap.values()) {
     * if (center.getLocation().equalsIgnoreCase(location)) {
     * System.out.println("Gym ID: " + center.getCenterId() + " - " +
     * center.getName());
     * }
     * }
     * }
     * 
     * 
     * public void viewSlot(int gymCenterId) {
     * System.out.println("Slots at center ID: " + gymCenterId);
     * for (Slot slot : slotMap.values()) {
     * if (slot.getCenterId() == gymCenterId) {
     * System.out.println("Slot ID: " + slot.getSlotId() + ", Time: " +
     * slot.getStartTime() + ", Date: " + slot.getDate());
     * }
     * }
     * }
     * 
     * 
     * public void bookSlot(int slotId) {
     * Slot slot = slotMap.get(slotId);
     * if (slot == null) {
     * System.out.println("Invalid Slot ID.");
     * return;
     * }
     * 
     * Booking booking = new Booking();
     * booking.setBookingId(bookingIdCounter++);
     * booking.setSlotId(slotId);
     * booking.setStatus(0); // payment pending
     * 
     * // Dummy customer for now (in real use, pass customerId)
     * int customerId = 1;
     * booking.setCustomerId(customerId);
     * bookingMap.put(booking.getBookingId(), booking);
     * 
     * customerBookings.computeIfAbsent(customerId, k -> new
     * ArrayList<>()).add(booking.getBookingId());
     * 
     * System.out.println("Slot booked. Booking ID: " + booking.getBookingId() +
     * " (Payment Pending)");
     * }
     * 
     * 
     * public void cancelBooking(int bookingId) {
     * Booking booking = bookingMap.get(bookingId);
     * if (booking != null) {
     * bookingMap.remove(bookingId);
     * customerBookings.getOrDefault(booking.getCustomerId(), new
     * ArrayList<>()).remove(Integer.valueOf(bookingId));
     * System.out.println("Booking " + bookingId + " cancelled.");
     * } else {
     * System.out.println("Invalid Booking ID.");
     * }
     * }
     * 
     * 
     * public void editBooking(int bookingId) {
     * Booking booking = bookingMap.get(bookingId);
     * if (booking != null) {
     * // dummy edit operation
     * booking.setStatus(1); // confirmed
     * System.out.println("Booking " + bookingId +
     * " updated (status set to confirmed).");
     * } else {
     * System.out.println("Invalid Booking ID.");
     * }
     * }
     * 
     * 
     * public void makePayment(int bookingId) {
     * Booking booking = bookingMap.get(bookingId);
     * if (booking != null && booking.getStatus() == 0) {
     * booking.setStatus(1); // Confirmed
     * System.out.println("Payment done for booking ID " + bookingId);
     * } else {
     * System.out.println("Booking already paid or invalid.");
     * }
     * }
     * 
     * 
     * public void viewBookings(int customerId) {
     * List<Integer> bookings = customerBookings.get(customerId);
     * if (bookings == null || bookings.isEmpty()) {
     * System.out.println("No bookings for customer " + customerId);
     * return;
     * }
     * for (int id : bookings) {
     * Booking booking = bookingMap.get(id);
     * System.out.println("Booking ID: " + booking.getBookingId() + ", Slot ID: " +
     * booking.getSlotId() + ", Status: " + booking.getStatus());
     * }
     * }
     * 
     * 
     * public void editProfile(GymCustomer gymCustomer) {
     * int id = gymCustomer.getCustomerId();
     * if (customerMap.containsKey(id)) {
     * customerMap.put(id, gymCustomer);
     * System.out.println("Profile updated for customer ID: " + id);
     * } else {
     * System.out.println("Customer not found.");
     * }
     * }
     * 
     * // Utility methods to populate gyms/slots (for simulation)
     * public void addGymCenter(GymCenter center) {
     * gymCenterMap.put(center.getCenterId(), center);
     * }
     * 
     * public void addSlot(Slot slot) {
     * slotMap.put(slot.getSlotId(), slot);
     * }
     */
}