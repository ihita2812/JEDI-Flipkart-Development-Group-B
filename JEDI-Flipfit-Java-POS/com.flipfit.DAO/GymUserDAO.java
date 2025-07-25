package com.flipfit.dao;

import com.flipfit.bean.*;
import java.util.*;

public interface GymUserDAO {
    void addUser(GymUser user);

    GymUser getUserById(int userId);

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

    public List<GymCenter> getAllCenters();
    public List<GymOwner> getAllOwners();
    public List<GymCenter> getAllCentersByOwnerId(int ownerId);
    public List<Booking> getBookingsBySlotId(int slotId);
    public Payment getPaymentByBookingId(int bookingId);
    public List<GymCenter> getAllValidCenters();
    int getCustomerId(GymUser gymUser);

    int getAdminId(GymUser gymUser);

    int getOwnerId(GymUser gymUser);


}