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

    public List<Object> getSlotByCenterId(int gymCenterId);

    public List<Object> getBookingsByCustomerId(int customerId);

    public void approvePayment(int bookingId);

    public int addBooking(Booking booking);

    public void cancelBookingById(int bookingId);

    // boolean validateLogin(String username, String password);
}