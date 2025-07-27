package com.flipfit.dao;

import com.flipfit.bean.*;
import java.util.*;

import  java.sql.Connection;
import java.sql.SQLException;
public interface GymUserDAO {
    void addUser(GymUser user);

   // GymUser getUserById(int userId);

    GymUser getUserByUsername(String username);

    List<GymUser> getAllUsers();

    void removeUser(int userId);

    Role getRole(int role);

    List<Slot> getSlotByCenterId(int gymCenterId);

    Slot getSlotByBookingId(int bookingId);

    String getCenterNameByCenterId(int gymCenterId);

    List<Booking> getBookingsByCustomerId(int customerId);

    boolean approvePayment(int bookingId);

    int addBooking(Booking booking);

    void cancelBookingById(int bookingId);

    List<GymCenter> getAllCenters();
    List<GymOwner> getAllOwners();
    List<GymCenter> getAllCentersByOwnerId(int ownerId);
    List<Booking> getBookingsBySlotId(int slotId);
    Payment getPaymentByBookingId(int bookingId);
    List<GymCenter> getAllValidCenters();
    List<GymCustomer> getAllCustomers();
    int getCustomerId(GymUser gymUser);
    public List<Payment> getAllPayments();
    int getAdminId(GymUser gymUser);

    void addNotification(Notification notification);

    int getOwnerId(GymUser gymUser);

    int getUserIdFromOwnerId(int ownerId);

    void updateUserPassword(String username, String newPassword);
    boolean updateGymCenterApprovalStatus(int gymCenterId, int approvalStatus);
    GymCenter getCenterById(int gymCenterId);

    void addPayment(Payment payment, Connection con) throws SQLException;
    void addNotification(Notification notification, Connection con) throws SQLException;
}