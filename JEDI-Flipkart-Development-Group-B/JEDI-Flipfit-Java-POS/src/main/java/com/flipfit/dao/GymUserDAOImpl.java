package com.flipfit.dao;

import com.flipfit.bean.*;
import com.flipfit.exception.SlotFullException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 *@author:Zaid, Ihita, Kashif
 *@ClassName:GymUserDAOImpl
 *@Exceptions:SQL Exception
 *@version:1.0
 *@See :GymUserDAO
 */
public class GymUserDAOImpl implements GymUserDAO {

    public static Map<Integer, Role> roleMap = new HashMap<>();
    public GymUserDAOImpl() {
        // This ensures that the roleMap cache is populated when the DAO is created.
        initializeAdmin();
    }
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
        // SQL query to select all bookings for a specific customerId.
        String sql = "SELECT * FROM Flipfit.Booking WHERE customerID = ?";

        try (Connection db = DBConnection.getConnection();
             PreparedStatement preparedStatement = db.prepareStatement(sql)) {
            // Set the customerId parameter for the query
            preparedStatement.setInt(1, customerId);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Booking booking = new Booking();
                    booking.setBookingId(rs.getInt("bookingId"));
                    booking.setCustomerId(rs.getInt("customerID"));
                    booking.setSlotId(rs.getInt("slotId"));
                    booking.setStatus(rs.getInt("status"));

                    bookings.add(booking);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching bookings for customer ID: " + customerId);
            e.printStackTrace();
        }
        return bookings;
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
        Connection db = null;
        try {
            db = DBConnection.getConnection();
            // --- Step 1: Start a Transaction ---
            // We are performing multiple updates. They must all succeed or none should.
            db.setAutoCommit(false);

            // --- Step 2: Get all necessary info in one go ---
            String infoSql = "SELECT s.slotId, s.bookedSeats, c.capacity, b.customerId, c.ownerId " +
                    "FROM Flipfit.Booking b " +
                    "JOIN Flipfit.Slot s ON b.slotId = s.slotId " +
                    "JOIN Flipfit.GymCenter c ON s.centerId = c.centerId " +
                    "WHERE b.bookingId = ?";

            PreparedStatement infoStmt = db.prepareStatement(infoSql);
            infoStmt.setInt(1, bookingId);
            ResultSet rs = infoStmt.executeQuery();

            if (!rs.next()) {
                System.err.println("No booking or slot found for bookingId: " + bookingId);
                db.rollback(); // Rollback the transaction
                return false;
            }

            int slotId = rs.getInt("slotId");
            int bookedSeats = rs.getInt("bookedSeats");
            int capacity = rs.getInt("capacity");
            int customerId = rs.getInt("customerId");
            int ownerId = rs.getInt("ownerId");

            // --- Step 3: The Core Business Logic ---
            if (bookedSeats >= capacity) {
                System.out.println("Payment failed: Slot is already full.");
                db.rollback(); // Rollback the transaction
                throw new SlotFullException(slotId); // Throw the specific exception
            }

            // --- Step 4: Perform the Database Updates ---
            // Increment booked seats for the slot
            PreparedStatement updateSlotStmt = db.prepareStatement("UPDATE Flipfit.Slot SET bookedSeats = bookedSeats + 1 WHERE slotId = ?");
            updateSlotStmt.setInt(1, slotId);
            updateSlotStmt.executeUpdate();

            // Confirm the booking status
            PreparedStatement updateBookingStmt = db.prepareStatement("UPDATE Flipfit.Booking SET status = 1 WHERE bookingId = ?");
            updateBookingStmt.setInt(1, bookingId);
            updateBookingStmt.executeUpdate();

            // --- Step 5: Create Payment and Notification ---
            Payment payment = new Payment();
            payment.setBookingId(bookingId);
            payment.setCustomerId(customerId);
            payment.setAmount(100); // Assuming a fixed amount
            payment.setPaymentDateTime(LocalDateTime.now());
            addPayment(payment, db);

            Notification notification = new Notification();
            notification.setMessage("A customer has confirmed their booking for slot " + slotId + " at your gym.");
            int ownerUserId = getUserIdFromOwnerId(ownerId);
            notification.setUserId(ownerUserId);
            addNotification(notification, db);

            // --- Step 6: Commit the Transaction ---
            // If we reach here, all updates were successful
            db.commit();
            return true;

        } catch (SQLException e) {
            System.err.println("Database error during payment approval. Rolling back transaction.");
            e.printStackTrace();
            if (db != null) {
                try {
                    db.rollback();
                } catch (SQLException ex) {
                    e.printStackTrace();
                }
            }
            return false;
        } finally {
            if (db != null) {
                try {
                    db.setAutoCommit(true); // Always restore default behavior
                    db.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void addNotification(Notification notification, Connection db) throws SQLException {
        int newNotificationId = -1;
        try (PreparedStatement idStmt = db.prepareStatement("SELECT IFNULL(MAX(notifiId), 0) + 1 FROM Flipfit.Notification")) {
            ResultSet rs = idStmt.executeQuery();
            if (rs.next()) {
                newNotificationId = rs.getInt(1);
            }
        }
        notification.setNotifId(newNotificationId);

        try (PreparedStatement insertStmt = db.prepareStatement("INSERT INTO Flipfit.Notification (notifiId, message, userId) VALUES (?,?,?)")) {
            insertStmt.setInt(1, notification.getNotifId());
            insertStmt.setString(2, notification.getMessage());
            insertStmt.setInt(3, notification.getUserId());
            insertStmt.executeUpdate();
        }
    }

    public void addNotification(Notification notification) {
        try (Connection db = DBConnection.getConnection()) {
            addNotification(notification, db);
        } catch (SQLException e) {
            System.err.println("A database error occurred while adding the notification.");
            e.printStackTrace();
        }
    }

    // Add this NEW method
    public void addPayment(Payment payment, Connection db) throws SQLException {
        int newPaymentId = -1;
        try (PreparedStatement preparedStatement = db.prepareStatement("SELECT IFNULL(MAX(paymentId), 0) + 1 FROM Flipfit.Payment")) {
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                newPaymentId = rs.getInt(1);
            }
        }
        payment.setPaymentId(newPaymentId);

        try (PreparedStatement preparedStatement = db.prepareStatement("INSERT INTO Flipfit.Payment (bookingId, customerId, amount, paymentDateTime, paymentId) VALUES (?, ?, ?, ?, ?);")) {
            preparedStatement.setInt(1, payment.getBookingId());
            preparedStatement.setInt(2, payment.getCustomerId());
            preparedStatement.setFloat(3, payment.getAmount());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(payment.getPaymentDateTime()));
            preparedStatement.setInt(5, payment.getPaymentId());
            preparedStatement.executeUpdate();
        }
    }

    public void addPayment(Payment payment) {
        try (Connection db = DBConnection.getConnection()) {
            // It then calls the transactional worker method, passing the connection.
            addPayment(payment, db);
        } catch (SQLException e) {
            System.err.println("A database error occurred while adding the payment.");
            e.printStackTrace();
        }
    }


    public List<Payment> getAllPayments() {
        List<Payment> payments = new ArrayList<>();

        String sql = "SELECT * FROM Flipfit.Payment";

        try (Connection db = DBConnection.getConnection();
             PreparedStatement preparedStatement = db.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                Payment payment = new Payment();
                payment.setPaymentId(rs.getInt("paymentId"));
                payment.setBookingId(rs.getInt("bookingId"));
                payment.setCustomerId(rs.getInt("customerId"));
                payment.setAmount(rs.getFloat("amount"));
                payment.setPaymentDateTime(rs.getTimestamp("paymentDateTime").toLocalDateTime());

                payments.add(payment);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all payments from the database.");
            e.printStackTrace();
        }
        return payments;
    }

    public void cancelBookingById(int bookingId) {
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
        // SQL query to select all slots for a specific centerId
        String sql = "SELECT * FROM Flipfit.Slot WHERE centerId = ?";

        try (Connection db = DBConnection.getConnection();
             PreparedStatement preparedStatement = db.prepareStatement(sql)) {

            // Set the gymCenterId parameter for the query
            preparedStatement.setInt(1, gymCenterId);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Slot slot = new Slot();
                    slot.setSlotId(rs.getInt("slotId"));
                    // Convert java.sql.Time to java.time.LocalTime
                    slot.setStartTime(rs.getTime("startTime").toLocalTime());
                    // Convert java.sql.Date to java.time.LocalDate
                    slot.setDate(rs.getDate("date").toLocalDate());
                    slot.setBookedSeats(rs.getInt("bookedSeats"));
                    slot.setCenterId(rs.getInt("centerId"));

                    slots.add(slot);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching slots for center ID: " + gymCenterId);
            e.printStackTrace();
        }
        return slots;
    }

    public Slot getSlotByBookingId(int bookingId) {
        // This SQL query joins the Booking and Slot tables to find the slot
        // that corresponds to a given bookingId.
        String sql = "SELECT s.* FROM Flipfit.Slot s JOIN Flipfit.Booking b ON s.slotId = b.slotId WHERE b.bookingId = ?";
        Slot slot = null;

        try (Connection db = DBConnection.getConnection();
             PreparedStatement preparedStatement = db.prepareStatement(sql)) {

            preparedStatement.setInt(1, bookingId);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    slot = new Slot();
                    slot.setSlotId(rs.getInt("slotId"));
                    slot.setStartTime(rs.getTime("startTime").toLocalTime());
                    slot.setDate(rs.getDate("date").toLocalDate());
                    slot.setBookedSeats(rs.getInt("bookedSeats"));
                    slot.setCenterId(rs.getInt("centerId"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching slot for booking ID: " + bookingId);
            e.printStackTrace();
        }
        return slot;
    }

    public String getCenterNameByCenterId(int gymCenterId) {
        // This query selects only the 'name' column for a specific centerId.
        String sql = "SELECT name FROM Flipfit.GymCenter WHERE centerId = ?";
        String centerName = null;

        try (Connection db = DBConnection.getConnection();
             PreparedStatement preparedStatement = db.prepareStatement(sql)) {
            preparedStatement.setInt(1, gymCenterId);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    centerName = rs.getString("name");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching center name for center ID: " + gymCenterId);
            e.printStackTrace();
        }
        return centerName;
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

    public GymUser getUserByUsername(String username) {
        try (Connection db = DBConnection.getConnection();
             PreparedStatement getUserStatement = db.prepareStatement("SELECT * FROM Flipfit.GymUser WHERE userName = ?")) {

            getUserStatement.setString(1, username);
            ResultSet userResult = getUserStatement.executeQuery();
            if(userResult.next()) {

                GymUser user = new GymUser();
                user.setUserId(userResult.getInt("userId"));
                user.setUserName(userResult.getString("userName"));
                user.setPassword(userResult.getString("password"));
                user.setName(userResult.getString("name"));

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
        List<GymCenter> allCenters = getAllCenters();
        for (GymCenter center : allCenters) {
            if (center.getApprovalStatus() == 1) {
                centers.add(center);
            }
        }
        return centers;
    }
    public List<GymCenter> getAllCentersByOwnerId(int ownerId) {
        List<GymCenter> gymCenters = new ArrayList<>();
        List<GymCenter> allCenters = getAllCenters();
        for (GymCenter gymCenter : allCenters) {
            if (gymCenter.getOwnerId() == ownerId) {
                gymCenters.add(gymCenter);
            }
        }
        return gymCenters;
    }

    public List<Booking> getBookingsBySlotId(int slotId) {
            List<Booking> bookings = new ArrayList<>();
            String sql = "SELECT * FROM Flipfit.Booking WHERE slotId = ?";
            try (Connection db = DBConnection.getConnection();
                 PreparedStatement preparedStatement = db.prepareStatement(sql)) {
                preparedStatement.setInt(1, slotId);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        Booking booking = new Booking();
                        booking.setBookingId(rs.getInt("bookingId"));
                        booking.setCustomerId(rs.getInt("customerID"));
                        booking.setSlotId(rs.getInt("slotId"));
                        booking.setStatus(rs.getInt("status"));
                        bookings.add(booking);
                    }
                }
            } catch (SQLException e) {
                System.err.println("Error fetching bookings for slot ID: " + slotId);
                e.printStackTrace();
            }
        return bookings;
    }
    public Payment getPaymentByBookingId(int bookingId) {

        String sql = "SELECT * FROM Flipfit.Payment WHERE bookingId = ?";
        Payment payment = null;
        try (Connection db = DBConnection.getConnection();
             PreparedStatement preparedStatement = db.prepareStatement(sql)) {
            preparedStatement.setInt(1, bookingId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    payment = new Payment();
                    payment.setPaymentId(rs.getInt("paymentId"));
                    payment.setBookingId(rs.getInt("bookingId"));
                    payment.setCustomerId(rs.getInt("customerId"));
                    payment.setAmount(rs.getFloat("amount"));
                    payment.setPaymentDateTime(rs.getTimestamp("paymentDateTime").toLocalDateTime());
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching payment for booking ID: " + bookingId);
            e.printStackTrace();
        }
        return payment;
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
        // The SQL query to update the password for a specific username.
        String sql = "UPDATE Flipfit.GymUser SET password = ? WHERE userName = ?";

        // Use a try-with-resources block for automatic resource management.
        try (Connection db = DBConnection.getConnection();
             PreparedStatement preparedStatement = db.prepareStatement(sql)) {

            // Set the parameters for the query.
            preparedStatement.setString(1, newPassword); // The new password
            preparedStatement.setString(2, username);    // The username to identify the row

            // Execute the update. executeUpdate() returns the number of rows affected.
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Database successfully updated for user: " + username);
            } else {
                System.out.println("No user found with that username in the database to update.");
            }

        } catch (SQLException e) {
            System.err.println("Database error during password update for user: " + username);
            e.printStackTrace();
        }
        // --- End of New JDBC Logic ---
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