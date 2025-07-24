package com.flipfit.dao;

import com.flipfit.bean.*;
import java.util.*;

public class GymUserDAOImpl implements GymUserDAO {

    public static Map<Integer, GymUser> userMap = new HashMap<>();
    public static Map<Integer, Role> roleMap = new HashMap<>();
    public static Map<Integer, GymCenter> centerMap = new HashMap<>();
    public static Map<Integer, Slot> slotMap = new HashMap<>();
    public static Map<Integer, Booking> bookingMap = new HashMap<>();
    public static Map<Integer, Payment> paymentMap = new HashMap<>();
    public static Map<Integer, Notification> notificationMap = new HashMap<>();

    public GymUserDAOImpl() {
        Role customerRole = new Role();
        customerRole.setRoleId(0);
        customerRole.setRoleName("Customer");
        customerRole.setRoleDesc("A customer of the gym");
        roleMap.put(0, customerRole);

        Role ownerRole = new Role();
        ownerRole.setRoleId(1);
        ownerRole.setRoleName("Owner");
        ownerRole.setRoleDesc("An owner of the gym");
        roleMap.put(1, ownerRole);

        Role adminRole = new Role();
        adminRole.setRoleId(2);
        adminRole.setRoleName("Admin");
        adminRole.setRoleDesc("An admin of the gym");
        roleMap.put(2, adminRole);

        GymUser adminUser = new GymUser();
        adminUser.setUserId(1);
        adminUser.setUserName("admin");
        adminUser.setPassword("admin");
        adminUser.setName("Admin User");
        adminUser.setRole(adminRole);
        userMap.put(1, adminUser);

        GymUser ownerUser = new GymUser();
        ownerUser.setUserId(2);
        ownerUser.setUserName("owner");
        ownerUser.setPassword("owner");
        ownerUser.setName("Gym Owner");
        ownerUser.setRole(ownerRole);
        userMap.put(2, ownerUser);

        GymUser customerUser = new GymUser();
        customerUser.setUserId(3);
        customerUser.setUserName("customer");
        customerUser.setPassword("customer");
        customerUser.setName("Gym Customer");
        customerUser.setRole(customerRole);
        userMap.put(3, customerUser);

        GymUser adminUser1 = new GymUser();
        adminUser1.setUserId(4);
        adminUser1.setUserName("admin1");
        adminUser1.setPassword("admin");
        adminUser1.setName("Admin User hu mai");
        adminUser1.setRole(adminRole);
        userMap.put(4, adminUser1);

        GymUser ownerUser1 = new GymUser();
        ownerUser1.setUserId(5);
        ownerUser1.setUserName("owner1");
        ownerUser1.setPassword("owner");
        ownerUser1.setName("Gym Owner hu mai");
        ownerUser1.setRole(ownerRole);
        userMap.put(5, ownerUser1);

        GymUser customerUser1 = new GymUser();
        customerUser1.setUserId(6);
        customerUser1.setUserName("customer1");
        customerUser1.setPassword("customer");
        customerUser1.setName("Gym Customer hu mai");
        customerUser1.setRole(customerRole);
        userMap.put(6, customerUser1);

    }

    public List<> getBookingsByCustomerId(int customerId) {
        List<Booking> bookings = new ArrayList<>();
        List<Booking> bookingIds = new ArrayList<>();
        List<Slot> slots = new ArrayList<>();
        for (Booking booking : bookingMap.values()) {
            if (booking.getCustomerId() == customerId) {
                bookings.add(booking);
                bookingIds.add(booking.getBookingId());
                slots.add(slotMap.get(booking.getSlotId()));
            }
        }
        List<> results = new ArrayList<>();
        results.add(bookingIds);
        results.add(bookings);
        results.add(slots);
        return results;
    }

    public void cancelBookingById(int bookingId) {
        bookingMap.remove(bookingId);
    }
    
    @Override
    public void addUser(GymUser user) {
        int newUserId = Collections.max(userMap.keySet()) + 1;
        user.setUserId(newUserId);
        userMap.put(newUserId, user);
        System.out.println("User added successfully with ID: " + newUserId);
    }

    @Override
    public GymUser getUserById(int userId) {
        return userMap.get(userId);
    }

    @Override
    public GymUser getUserByUsername(String username) {
        for (GymUser user : userMap.values()) {
            if (user.getUserName().equals(username)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<GymUser> getAllUsers() {
        return new ArrayList<>(userMap.values());
    }

    @Override
    public void removeUser(int userId) {
        userMap.remove(userId);
    }

    @Override
    public Role getRole(int role) {
        return roleMap.get(role);
    }

    // public boolean validateLogin(String username, String password) {
    // for (GymUser user : userMap.values()) {
    // if (user.getUserName().equals(username) &&
    // user.getPassword().equals(password)) {
    // return true;
    // }
    // }
    // return false;
    // }

}