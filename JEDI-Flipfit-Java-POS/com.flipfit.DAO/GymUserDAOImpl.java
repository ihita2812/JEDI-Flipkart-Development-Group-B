package com.flipfit.dao;

import com.flipfit.bean.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.flipfit.dao.GymCustomerDAOImpl;
import com.flipfit.dao.GymOwnerDAOImpl;
import com.flipfit.dao.GymAdminDAOImpl;

import static com.flipfit.dao.GymOwnerDAOImpl.ownerMap;

public class GymUserDAOImpl implements GymUserDAO {

    public static Map<Integer, GymUser> userMap = new HashMap<>();
    public static Map<Integer, Role> roleMap = new HashMap<>();
    public static Map<Integer, GymCenter> centerMap = new HashMap<>();
    public static Map<Integer, Slot> slotMap = new HashMap<>();
    public static Map<Integer, Booking> bookingMap = new HashMap<>();
    public static Map<Integer, Payment> paymentMap = new HashMap<>();
    public static Map<Integer, Notification> notificationMap = new HashMap<>();

    public void initializeAdmin() {
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
        GymAdmin adminGym = new GymAdmin();
        adminGym.setUserId(1);
        GymAdminDAOImpl.adminMap.put(1, adminGym);

        GymUser ownerUser = new GymUser();
        ownerUser.setUserId(2);
        ownerUser.setUserName("owner");
        ownerUser.setPassword("owner");
        ownerUser.setName("Gym Owner");
        ownerUser.setRole(ownerRole);
        userMap.put(2, ownerUser);
        GymOwner ownerGym = new GymOwner();
        ownerGym.setUserId(2);
        ownerGym.setOwnerId(1);
        ownerGym.setEmail("owner");
        ownerGym.setGender(0);
        GymOwnerDAOImpl.ownerMap.put(1, ownerGym);

        GymUser customerUser = new GymUser();
        customerUser.setUserId(3);
        customerUser.setUserName("customer");
        customerUser.setPassword("customer");
        customerUser.setName("Gym Customer");
        customerUser.setRole(customerRole);
        userMap.put(3, customerUser);
        GymCustomer customerGym = new GymCustomer();
        customerGym.setUserId(3);
        customerGym.setCustomerId(1);
        customerGym.setEmail("customer");
        customerGym.setGender(0);
        customerGym.setAge(21);
        customerGym.setLocation("Kol");
        GymCustomerDAOImpl.customerMap.put(3, customerGym);


        GymCenter dummyCenter = new GymCenter();
        dummyCenter.setCenterId(1);
        dummyCenter.setName("Dummy Gym Center");
        dummyCenter.setOwnerId(1);
        dummyCenter.setCapacity(100);
        dummyCenter.setApprovalStatus(1); // Approved
        centerMap.put(1, dummyCenter);

        GymCenter dummyCenter2 = new GymCenter();
        dummyCenter2.setCenterId(2);
        dummyCenter2.setName("Dummy Gym Center 2");
        dummyCenter2.setOwnerId(1);
        dummyCenter2.setCapacity(50);
        dummyCenter2.setApprovalStatus(2); //pending
        centerMap.put(2, dummyCenter2);

        GymCenter dummyCenter3 = new GymCenter();
        dummyCenter3.setCenterId(3);
        dummyCenter3.setName("Dummy Gym Center 3");
        dummyCenter3.setOwnerId(1);
        dummyCenter3.setCapacity(75);
        dummyCenter3.setApprovalStatus(2); //pending
        centerMap.put(3, dummyCenter3);

        GymCenter dummyCenter4 = new GymCenter();
        dummyCenter4.setCenterId(4);
        dummyCenter4.setName("Dummy Gym Center 4");
        dummyCenter4.setOwnerId(1);
        dummyCenter4.setCapacity(120);
        dummyCenter4.setApprovalStatus(0); // rejected
        centerMap.put(4, dummyCenter4);


        // ... after dummyCenter4 is added to centerMap
        // ... after dummyCenter4 is added to centerMap

        // Dummy Slot Data
        Slot slot1 = new Slot();
        slot1.setSlotId(1);
        slot1.setCenterId(1); // For the approved "Dummy Gym Center"
        slot1.setStartTime(LocalTime.parse("09:00"));
        slot1.setDate(java.time.LocalDate.now()); // Added for data completeness
        slot1.setBookedSeats(1);
        slotMap.put(1, slot1);

        Slot slot2 = new Slot();
        slot2.setSlotId(2);
        slot2.setCenterId(1); // For the approved "Dummy Gym Center"
        slot2.setStartTime(LocalTime.parse("10:00"));
        slot2.setDate(java.time.LocalDate.now()); // Added for data completeness
        slot2.setBookedSeats(1);
        slotMap.put(2, slot2);

        // Dummy Booking Data
        Booking booking1 = new Booking();
        booking1.setBookingId(1);
        booking1.setCustomerId(1); // For "Gym Customer" (customerId is 1)
        booking1.setSlotId(1);     // For slot 1
        booking1.setStatus(1);     // 1 for confirmed
        bookingMap.put(1, booking1);

        Booking booking2 = new Booking();
        booking2.setBookingId(2);
        booking2.setCustomerId(1); // For "Gym Customer"
        booking2.setSlotId(2);     // For slot 2
        booking2.setStatus(1);     // 1 for confirmed
        bookingMap.put(2, booking2);

        // Dummy Payment Data
        Payment payment1 = new Payment();
        payment1.setPaymentId(1);
        payment1.setBookingId(1);
        payment1.setCustomerId(1);
        payment1.setAmount(500.00f);
        // FIX: Corrected syntax error and used the direct method
        payment1.setPaymentDateTime(LocalDateTime.now());
        paymentMap.put(1, payment1);

        Payment payment2 = new Payment();
        payment2.setPaymentId(2);
        payment2.setBookingId(2);
        payment2.setCustomerId(1);
        payment2.setAmount(550.00f);
        // FIX: Replaced the parsing logic with the direct, correct method
        payment2.setPaymentDateTime(LocalDateTime.now());
        paymentMap.put(2, payment2);
    }

    public int getCustomerId(GymUser gymUser) {
        for(GymCustomer customer : GymCustomerDAOImpl.customerMap.values()){
            if(gymUser.getUserId() == customer.getUserId()){
                return customer.getCustomerId();
            }
        }
        return -1;
    }

    public int getOwnerId(GymUser gymUser) {
        for(GymOwner owner : ownerMap.values()){
            if(gymUser.getUserId() == owner.getUserId()){
                return owner.getOwnerId();
            }
        }
        return -1;
    }

    public int getAdminId(GymUser gymUser) {
        for(GymAdmin admin : GymAdminDAOImpl.adminMap.values()){
            if(gymUser.getUserId() == admin.getUserId()){
                return admin.getAdminId();
            }
        }

        return -1;
    }

    public List<Booking> getBookingsByCustomerId(int customerId) {
        List<Booking> bookings = new ArrayList<>();
        for (Booking booking : bookingMap.values()) {
            if (booking.getCustomerId() == customerId) {
                bookings.add(booking);
            }
        }
        return bookings;
    }

    public Booking getBookingByBookingId(int bookingId) {
        return bookingMap.get(bookingId);
    }

    public int getUserIdFromOwnerId(int ownerId) {
        GymOwner owner =  ownerMap.get(ownerId);
        return owner.getUserId();
    }

    public boolean approvePayment(int bookingId) {
        Booking booking = bookingMap.get(bookingId);
        Slot bookingSlot = slotMap.get(booking.getSlotId());
        booking.setStatus(1); // 1 for confirmed
        bookingMap.put(bookingId, booking);
        Payment payment = new Payment();
        payment.setBookingId(bookingId);
        payment.setAmount(100); // Assuming a fixed amount for simplicity
        addPayment(payment);
        int currSeats = bookingSlot.getBookedSeats();
        int capacity = centerMap.get(bookingSlot.getCenterId()).getCapacity();
        if  (currSeats < capacity) {
            bookingSlot.setBookedSeats(currSeats + 1);
            slotMap.put(bookingSlot.getSlotId(), bookingSlot);
            Notification bookingNotification = new Notification();
            bookingNotification.setMessage("Customer " + booking.getCustomerId() + " just booked slot " + bookingSlot.getSlotId());
            GymCenter bookingCenter = centerMap.get(bookingSlot.getCenterId());
            GymOwner bookingOwner = ownerMap.get(bookingCenter.getOwnerId());
            bookingNotification.setUserId(bookingOwner.getUserId());
            addNotification(bookingNotification);
            return true;
        } else {
            return false;
        }
    }

    public void addNotification(Notification notification) {
        int newNotificationId = notificationMap.isEmpty() ? 1 : Collections.max(notificationMap.keySet()) + 1;
        notification.setNotifId(newNotificationId);
        notificationMap.put(newNotificationId, notification);
    }

    public void addPayment(Payment payment) {
        int newPaymentId = Collections.max(paymentMap.keySet()) + 1;
        payment.setPaymentId(newPaymentId);
        paymentMap.put(newPaymentId, payment);
    }

    public List<Payment> getAllPayments() {
        return new ArrayList<>(paymentMap.values());
    }

    public void cancelBookingById(int bookingId) {
        bookingMap.remove(bookingId);
    }

    public List<Slot> getSlotByCenterId(int gymCenterId) {
        List<Slot> slots = new ArrayList<>();
        for (Slot slot : slotMap.values()) {
            if (slot.getCenterId() == gymCenterId) {
                slots.add(slot);
            }
        }
        return slots;
    }

    public Slot getSlotByBookingId(int bookingId) {
        Booking booking = bookingMap.get(bookingId);
        for (Slot slot : slotMap.values()) {
            if (slot.getSlotId() == booking.getSlotId()) {
                return slot;
            }
        }
        return null;
    }

    public String getCenterNameByCenterId(int gymCenterId) {
        for (GymCenter center : centerMap.values()) {
            if (center.getCenterId() == gymCenterId) {
                return center.getName();
            }
        }
        return null;
    }

    public int addBooking(Booking booking) {
        int newBookingId = Collections.max(bookingMap.keySet()) + 1;
        booking.setBookingId(newBookingId);
        bookingMap.put(booking.getBookingId(), booking);
        return newBookingId;
    }

    @Override
    public void addUser(GymUser user) {
        int newUserId = Collections.max(userMap.keySet()) + 1;
        user.setUserId(newUserId);
        userMap.put(newUserId, user);

        try (Connection db = DBConnection.getConnection();
             PreparedStatement preparedStatement = db.prepareStatement("INSERT INTO Flipfit.GymUser (userId, userName, roleId, password, name) VALUES (?, ?, ?, ?, ?);")) {

            preparedStatement.setInt(1, user.getUserId());
            preparedStatement.setString(2, user.getUserName());
            preparedStatement.setInt(3, user.getRole().getRoleId()+1);
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getName());

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
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


    public List<GymCenter> getAllCenters(){
        return new ArrayList<>(centerMap.values());
    }
    public List<GymCenter> getAllValidCenters() {
        List<GymCenter> centers = new ArrayList<>();
        for (GymCenter center : centerMap.values()) {
            if (center.getApprovalStatus() == 1) {
                centers.add(center);
            }
        }
        return centers;
    }
    public List<GymCenter> getAllCentersByOwnerId(int ownerId) {
        List<GymCenter> gymCenters = new ArrayList<>();
        for (GymCenter gymCenter : centerMap.values()) {
            if (gymCenter.getOwnerId() == ownerId) {
                gymCenters.add(gymCenter);
            }
        }
        return gymCenters;
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
    public Payment getPaymentByBookingId(int bookingId) {
        for(Payment payment: GymUserDAOImpl.paymentMap.values()) {
            if(payment.getBookingId() == bookingId) {
                return payment;
            }
        }
        return null;
    }
    @Override
    public List<GymUser> getAllUsers() {
        return new ArrayList<>(userMap.values());
    }
    public List<GymOwner> getAllOwners() {
        return new ArrayList<>(GymOwnerDAOImpl.ownerMap.values());
    }

    public List<GymCustomer> getAllCustomers(){
        return new ArrayList<>(GymCustomerDAOImpl.customerMap.values());
    }
    @Override
    public void removeUser(int userId) {
        userMap.remove(userId);
    }

    public Role getRole(int role) {
        return roleMap.get(role);
    }

}