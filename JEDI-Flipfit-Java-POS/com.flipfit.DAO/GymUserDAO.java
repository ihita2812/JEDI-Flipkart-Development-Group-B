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

    void approvePayment(int bookingId);

    int addBooking(Booking booking);

    void cancelBookingById(int bookingId);

    List<GymCenter> getAllCenters();

    int getCustomerId(GymUser gymUser);

    int getAdminId(GymUser gymUser);

    int getOwnerId(GymUser gymUser);
}