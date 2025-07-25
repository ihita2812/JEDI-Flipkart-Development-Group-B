package com.flipfit.dao;

import com.flipfit.bean.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

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
        try (Connection db = DBConnection.getConnection();
             PreparedStatement preparedStatement = db.prepareStatement("SELECT customerId FROM Flipfit.GymCustomer WHERE userId = ?")) {

            // Set the userId from the GymUser object as the parameter for the query
            preparedStatement.setInt(1, gymUser.getUserId());

            ResultSet rs = preparedStatement.executeQuery();

            // Check if a result was returned
            if (rs.next()) {
                // If a match is found, return the customerId
                return rs.getInt("customerId");
            }

        } catch (SQLException e) {
            System.err.println("Error fetching customerId for userId: " + gymUser.getUserId());
            e.printStackTrace();
        }
        return -1;
    }

    public int getOwnerId(GymUser gymUser) {
        // The query must find the owner record by matching the 'userId'
        String sql = "SELECT ownerId FROM Flipfit.GymOwner WHERE userId = ?";

        try (Connection db = DBConnection.getConnection();
             PreparedStatement preparedStatement = db.prepareStatement(sql)) {

            // Set the userId from the GymUser object as the parameter for the query
            preparedStatement.setInt(1, gymUser.getUserId());

            ResultSet rs = preparedStatement.executeQuery();

            // Check if a result was returned
            if (rs.next()) {
                // If a match is found, return the ownerId
                return rs.getInt("ownerId");
            }

        } catch (SQLException e) {
            // Corrected the error message to be specific to this method
            System.err.println("Error fetching ownerId for userId: " + gymUser.getUserId());
            e.printStackTrace();
        }

        // If no owner is found for that userId or an error occurs, return -1
        return -1;
    }

    public int getAdminId(GymUser gymUser) {
        // SQL query to find the adminId by matching the userId
        String sql = "SELECT adminId FROM Flipfit.GymAdmin WHERE userId = ?";

        // Use a try-with-resources block for automatic resource management
        try (Connection db = DBConnection.getConnection();
             PreparedStatement preparedStatement = db.prepareStatement(sql)) {

            // Set the userId from the input GymUser object as the query parameter
            preparedStatement.setInt(1, gymUser.getUserId());

            // Execute the query and get the result set
            ResultSet rs = preparedStatement.executeQuery();

            // Check if the result set contains any rows
            if (rs.next()) {
                // If a match is found, retrieve the adminId from the result and return it
                return rs.getInt("adminId");
            }

        } catch (SQLException e) {
            System.err.println("Error fetching adminId for userId: " + gymUser.getUserId());
            e.printStackTrace();
        }

        // If no admin record is found for the given userId or if a database error occurs, return -1
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
        try (Connection db = DBConnection.getConnection();
             PreparedStatement getUserIdStatement = db.prepareStatement("SELECT USERID FROM Flipfit.GymOwner WHERE OWNERID = ?")) {
            getUserIdStatement.setInt(1, ownerId);
            ResultSet userIdResult = getUserIdStatement.executeQuery();
            if(userIdResult.next()) {
                return userIdResult.getInt("userId");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
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
        notificationMap.put(newNotificationId, notification);

        try (Connection db = DBConnection.getConnection();
                PreparedStatement insertStatement = db.prepareStatement("INSERT INTO Flipfit.Notification (notifiId, message, userId) VALUES (?,?,?)")) {
            // Set the parameters for the INSERT query from the notification object
            insertStatement.setInt(1, notification.getNotifId());
            insertStatement.setString(2, notification.getMessage());
            insertStatement.setInt(3, notification.getUserId());

            // Execute the insert operation
            int rowsAffected = insertStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Notification was successfully added to the database.");
            } else {
                System.err.println("Failed to add the notification to the database.");
            }
        } // insertStatement is auto-closed here.
        catch (SQLException e) {
            System.err.println("A database error occurred while adding the notification.");
            e.printStackTrace();
        }
    }

    public void addPayment(Payment payment) {
        int newPaymentId = -1;
        try (Connection db = DBConnection.getConnection();
             PreparedStatement preparedStatement = db.prepareStatement("SELECT IFNULL(MAX(paymentId), 0) + 1 FROM Flipfit.Payment")) {
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                newPaymentId = rs.getInt(1);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        payment.setPaymentId(newPaymentId);
        paymentMap.put(newPaymentId, payment);

        try (Connection db = DBConnection.getConnection();
             PreparedStatement preparedStatement = db.prepareStatement("INSERT INTO Flipfit.Payment (bookingId, customerId, amount, paymentDateTime, paymentId) VALUES (?, ?, ?, ?, ?);")) {

            preparedStatement.setInt(1, payment.getBookingId());
            preparedStatement.setInt(2, payment.getCustomerId());
            preparedStatement.setFloat(3, payment.getAmount());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(payment.getPaymentDateTime()));
            preparedStatement.setInt(5, payment.getPaymentId());

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Payment> getAllPayments() {
        return new ArrayList<>(paymentMap.values());
    }

    public void cancelBookingById(int bookingId) {
        bookingMap.remove(bookingId);
        try (Connection db = DBConnection.getConnection();
             PreparedStatement preparedStatement = db.prepareStatement("DELETE FROM Flipfit.Booking WHERE bookingId = ?")) {

            preparedStatement.setInt(1, bookingId);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        int newBookingId = -1;
        try (Connection db = DBConnection.getConnection();
             PreparedStatement preparedStatement = db.prepareStatement("SELECT IFNULL(MAX(bookingId), 0) + 1 FROM Flipfit.Booking")) {
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                newBookingId = rs.getInt(1);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        booking.setBookingId(newBookingId);
        bookingMap.put(booking.getBookingId(), booking);


        try (Connection db = DBConnection.getConnection();
             PreparedStatement preparedStatement = db.prepareStatement("INSERT INTO Flipfit.Booking (bookingId, customerID, slotId, status) VALUES (?, ?, ?, ?);")) {

            preparedStatement.setInt(1, booking.getBookingId());
            preparedStatement.setInt(2, booking.getCustomerId());
            preparedStatement.setInt(3, booking.getSlotId());
            preparedStatement.setInt(4, booking.getStatus());

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newBookingId;
    }

    @Override
    public void addUser(GymUser user){
        int newUserId = -1;
        try (Connection db = DBConnection.getConnection();
             PreparedStatement preparedStatement = db.prepareStatement("SELECT IFNULL(MAX(userId), 0) + 1 FROM Flipfit.GymUser")) {
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                newUserId = rs.getInt(1);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }


        user.setUserId(newUserId);
        userMap.put(newUserId, user);

        System.out.println("User " + newUserId + " has been added.");

        try (Connection db = DBConnection.getConnection();
             PreparedStatement preparedStatement = db.prepareStatement("INSERT INTO Flipfit.GymUser (userId, userName, roleId, password, name) VALUES (?, ?, ?, ?, ?);")) {

            preparedStatement.setInt(1, newUserId);
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

    public GymUser getUserById(int userId) {
        return userMap.get(userId);
    }

    public GymUser getUserByUsername(String username) {
//        for (GymUser user : userMap.values()) {
//            if (user.getUserName().equals(username)) {
//                return user;
//            }
//        }
        try (Connection db = DBConnection.getConnection();
             PreparedStatement getUserStatement = db.prepareStatement("SELECT * FROM Flipfit.GymUser WHERE userName = ?")) {

            getUserStatement.setString(1, username);
            ResultSet userResult = getUserStatement.executeQuery();
            if(userResult.next()) {
//                System.out.println(userResult.getString("userName"));

                GymUser user = new GymUser();
                user.setUserId(userResult.getInt("userId"));
                user.setUserName(userResult.getString("userName"));
                user.setPassword(userResult.getString("password"));
                user.setName(userResult.getString("name"));

                //            System.out.println("UserId" + user.getUserId());

                int roleId = userResult.getInt("roleId") - 1;
                user.setRole(getRole(roleId));

                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<GymCenter> getAllCenters(){
        List<GymCenter> gymCenters = new ArrayList<>();

        // The SQL query to select all records from the GymCenter table
        String sql = "SELECT * FROM Flipfit.GymCenter";

        // Use a try-with-resources block to ensure database resources are closed automatically
        try (Connection db = DBConnection.getConnection();
             PreparedStatement preparedStatement = db.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {

            // Iterate through each row of the result set
            while (rs.next()) {
                // For each row, create a new GymCenter object
                GymCenter center = new GymCenter();

                // Populate the object's fields using data from the current row
                center.setCenterId(rs.getInt("centerId"));
                center.setName(rs.getString("name"));
                center.setLocation(rs.getString("location"));
                center.setCapacity(rs.getInt("capacity"));
                center.setNumSlots(rs.getInt("numSlots"));
                center.setOwnerId(rs.getInt("ownerId"));
                center.setApprovalStatus(rs.getInt("approvalStatus"));

                // Add the fully populated object to the list
                gymCenters.add(center);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching gym centers from the database.");
            e.printStackTrace();
        }

        // Return the list of gym centers. It will be empty if no records were found or if an error occurred.
        return gymCenters;
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
    public List<GymUser> getAllUsers() {
        List<GymUser> userList = new ArrayList<>();
        String sql = "SELECT * FROM Flipfit.GymUser";

        try (Connection db = DBConnection.getConnection();
             PreparedStatement preparedStatement = db.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                GymUser user = new GymUser();
                user.setUserId(rs.getInt("userId"));
                user.setUserName(rs.getString("userName"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));

                // Assuming roleId in the database is 1-based (1, 2, 3)
                // and getRole() expects 0-based (0, 1, 2)
                int roleId = rs.getInt("roleId") - 1;
                user.setRole(getRole(roleId));

                userList.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all users from database.");
            e.printStackTrace();
        }
        return userList;
    }
    public List<GymOwner> getAllOwners() {
        List<GymOwner> ownerList = new ArrayList<>();
        // SQL JOIN to combine data from GymUser and GymOwner tables into a single result
        String sql = "SELECT u.userId, u.userName, u.password, u.name, u.roleId, o.ownerId, o.gender, o.email " +
                "FROM Flipfit.GymUser u JOIN Flipfit.GymOwner o ON u.userId = o.userId";

        try (Connection db = DBConnection.getConnection();
             PreparedStatement preparedStatement = db.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                GymOwner owner = new GymOwner();

                // Populate fields from the GymUser part of the result
                owner.setUserId(rs.getInt("userId"));
                owner.setUserName(rs.getString("userName"));
                owner.setPassword(rs.getString("password"));
                owner.setName(rs.getString("name"));

                // Populate fields from the GymOwner part of the result
                owner.setOwnerId(rs.getInt("ownerId"));
                owner.setGender(rs.getInt("gender"));
                owner.setEmail(rs.getString("email"));

                // Set the role
                int roleId = rs.getInt("roleId") - 1;
                owner.setRole(getRole(roleId));

                ownerList.add(owner);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all owners from database.");
            e.printStackTrace();
        }
        return ownerList;
    }

    public List<GymCustomer> getAllCustomers(){
        List<GymCustomer> customerList = new ArrayList<>();
        // SQL JOIN to combine data from GymUser and GymCustomer tables
        String sql = "SELECT u.userId, u.userName, u.password, u.name, u.roleId, c.customerId, c.age, c.location, c.gender, c.email " +
                "FROM Flipfit.GymUser u JOIN Flipfit.GymCustomer c ON u.userId = c.userId";

        try (Connection db = DBConnection.getConnection();
             PreparedStatement preparedStatement = db.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                GymCustomer customer = new GymCustomer();

                // Populate fields from the GymUser part of the result
                customer.setUserId(rs.getInt("userId"));
                customer.setUserName(rs.getString("userName"));
                customer.setPassword(rs.getString("password"));
                customer.setName(rs.getString("name"));

                // Populate fields from the GymCustomer part of the result
                customer.setCustomerId(rs.getInt("customerId"));
                customer.setAge(rs.getInt("age"));
                customer.setLocation(rs.getString("location"));
                customer.setGender(rs.getInt("gender"));
                customer.setEmail(rs.getString("email"));

                // Set the role
                int roleId = rs.getInt("roleId") - 1;
                customer.setRole(getRole(roleId));

                customerList.add(customer);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all customers from database.");
            e.printStackTrace();
        }
        return customerList;
    }
    public void removeUser(int userId) {
        userMap.remove(userId);

        try (Connection db = DBConnection.getConnection();
             PreparedStatement preparedStatement = db.prepareStatement("DELETE FROM Flipfit.GymUser WHERE userId = ?")) {

            preparedStatement.setInt(1, userId);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Role getRole(int role) {
        if (roleMap.containsKey(role)) {
            return roleMap.get(role);
        }
        return null;
    }

    public void updateUserPassword(String username, String newPassword) {
        GymUser userToUpdate = getUserByUsername(username);
        if (userToUpdate != null) {
            userToUpdate.setPassword(newPassword);
        }
    }

    public boolean updateGymCenterApprovalStatus(int gymCenterId, int approvalStatus) {
        String sql = "UPDATE Flipfit.GymCenter SET approvalStatus = ? WHERE centerId = ?";

        try (Connection db = DBConnection.getConnection();
             PreparedStatement preparedStatement = db.prepareStatement(sql)) {

            preparedStatement.setInt(1, approvalStatus);
            preparedStatement.setInt(2, gymCenterId);

            // executeUpdate() returns the number of rows affected.
            int rowsAffected = preparedStatement.executeUpdate();

            // Return true if the update changed at least one row, false otherwise.
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating approval status for Gym Center ID: " + gymCenterId);
            e.printStackTrace();
            return false;
        }
    }

    public GymCenter getCenterById(int gymCenterId) {
        String sql = "SELECT * FROM Flipfit.GymCenter WHERE centerId = ?";
        GymCenter center = null;

        try (Connection db = DBConnection.getConnection();
             PreparedStatement preparedStatement = db.prepareStatement(sql)) {

            preparedStatement.setInt(1, gymCenterId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                center = new GymCenter();
                center.setCenterId(rs.getInt("centerId"));
                center.setName(rs.getString("name"));
                center.setLocation(rs.getString("location"));
                center.setCapacity(rs.getInt("capacity"));
                center.setNumSlots(rs.getInt("numSlots"));
                center.setOwnerId(rs.getInt("ownerId"));
                center.setApprovalStatus(rs.getInt("approvalStatus"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching Gym Center by ID: " + gymCenterId);
            e.printStackTrace();
        }
        return center;
    }

}