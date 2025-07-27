-- =================================================================
-- MASTER TESTING SCRIPT FOR FLIPFIT (FINAL VERIFICATION)
-- =================================================================

-- Step 1: Clean the Database for a fresh start.
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS Payment, Booking, Slot, Notification, GymCenter, GymCustomer, GymOwner, GymAdmin, GymUser, Role;
SET FOREIGN_KEY_CHECKS = 1;
SELECT 'All previous tables dropped successfully.' AS 'Status';

-- Step 2: Recreate all tables with the correct, final schema.
CREATE TABLE Role (roleId INT PRIMARY KEY, roleName VARCHAR(50), roleDesc VARCHAR(255));
CREATE TABLE GymUser (userId INT PRIMARY KEY AUTO_INCREMENT, userName VARCHAR(50) UNIQUE NOT NULL, roleId INT, password VARCHAR(50), name VARCHAR(100), FOREIGN KEY (roleId) REFERENCES Role(roleId));
CREATE TABLE GymAdmin (adminId INT PRIMARY KEY AUTO_INCREMENT, userId INT, FOREIGN KEY (userId) REFERENCES GymUser(userId) ON DELETE CASCADE);
CREATE TABLE GymOwner (ownerId INT PRIMARY KEY AUTO_INCREMENT, gender INT, email VARCHAR(100), userId INT, FOREIGN KEY (userId) REFERENCES GymUser(userId) ON DELETE CASCADE);
CREATE TABLE GymCustomer (customerId INT PRIMARY KEY AUTO_INCREMENT, age INT, location VARCHAR(100), gender INT, email VARCHAR(100), userId INT, FOREIGN KEY (userId) REFERENCES GymUser(userId) ON DELETE CASCADE);
CREATE TABLE GymCenter (centerId INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(100), location VARCHAR(100), capacity INT, numSlots INT, ownerId INT, approvalStatus INT, FOREIGN KEY (ownerId) REFERENCES GymOwner(ownerId) ON DELETE CASCADE);
CREATE TABLE Slot (slotId INT PRIMARY KEY AUTO_INCREMENT, startTime TIME, date DATE, bookedSeats INT, centerId INT, FOREIGN KEY (centerId) REFERENCES GymCenter(centerId) ON DELETE CASCADE);
CREATE TABLE Booking (bookingId INT PRIMARY KEY AUTO_INCREMENT, customerID INT, slotId INT, status INT, FOREIGN KEY (customerID) REFERENCES GymCustomer(customerId) ON DELETE CASCADE, FOREIGN KEY (slotId) REFERENCES Slot(slotId) ON DELETE CASCADE);
CREATE TABLE Payment (paymentId INT PRIMARY KEY AUTO_INCREMENT, bookingId INT, customerId INT, amount FLOAT, paymentDateTime DATETIME, FOREIGN KEY (bookingId) REFERENCES Booking(bookingId) ON DELETE CASCADE, FOREIGN KEY (customerId) REFERENCES GymCustomer(customerId) ON DELETE CASCADE);
CREATE TABLE Notification (notifiId INT PRIMARY KEY AUTO_INCREMENT, message VARCHAR(255), userId INT, FOREIGN KEY (userId) REFERENCES GymUser(userId) ON DELETE CASCADE);
SELECT 'All tables recreated successfully.' AS 'Status';

-- Step 3: Populate the database with comprehensive test data.
INSERT INTO Role (roleId, roleName, roleDesc) VALUES (1, 'Customer','A customer of the gym'), (2, 'Owner','An owner of the gym'), (3, 'Admin','An admin of the gym');
-- Users: 1 Admin, 2 Owners, 3 Customers
INSERT INTO GymUser (userId, userName, roleId, password, name) VALUES
(1, 'admin', 3, 'pass', 'The Administrator'),
(101, 'owner1', 2, 'pass', 'Jane Smith'),
(102, 'owner2', 2, 'pass', 'David Lee'),
(201, 'customer1', 1, 'pass', 'Alice'),
(202, 'customer2', 1, 'pass', 'Bob'),
(203, 'customer3', 1, 'pass', 'Charlie'); -- User for deletion tests

-- Role-Specific Tables
INSERT INTO GymAdmin (adminId, userId) VALUES (1, 1);
INSERT INTO GymOwner (ownerId, gender, email, userId) VALUES (1, 1, 'jane.smith@gym.com', 101), (2, 0, 'david.lee@gym.com', 102);
INSERT INTO GymCustomer (customerId, age, location, gender, email, userId) VALUES (1, 25, 'Koramangala', 1, 'alice@email.com', 201), (2, 30, 'Jayanagar', 0, 'bob@email.com', 202), (3, 22, 'Whitefield', 0, 'charlie@email.com', 203);

-- Gym Centers
INSERT INTO GymCenter (centerId, name, location, capacity, ownerId, approvalStatus) VALUES
(1, 'Flex Fitness', 'Koramangala', 10, 1, 1), -- Approved
(2, 'Iron Paradise', 'Indiranagar', 5, 1, 2),  -- Pending
(3, 'Cardio Central', 'HSR Layout', 15, 1, 0), -- Rejected
(4, 'Yoga Harmony', 'Jayanagar', 20, 2, 1),   -- Approved
(5, 'Power Zone', 'MG Road', 12, 2, 1),       -- Approved
(6, 'Cardio King', 'Whitefield', 25, 2, 1);  -- Approved, but has NO slots

-- Slots
INSERT INTO Slot (slotId, startTime, date, bookedSeats, centerId) VALUES
(101, '07:00:00', CURDATE(), 0, 1), -- Empty
(102, '08:00:00', CURDATE(), 1, 1), -- One booking, available
(103, '09:00:00', CURDATE(), 10, 1),-- FULL slot
(201, '06:00:00', CURDATE(), 5, 4),
(202, '07:00:00', CURDATE(), 2, 4);

-- Bookings
INSERT INTO Booking (bookingId, customerID, slotId, status) VALUES
(1001, 1, 102, 1), -- Alice, Confirmed in slot 102
(1002, 2, 202, 0), -- Bob, PENDING in slot 202
(1003, 1, 103, 0); -- Alice, PENDING in the FULL slot 103

-- Payments
INSERT INTO Payment (paymentId, bookingId, customerId, amount, paymentDateTime) VALUES (1, 1001, 1, 150.00, NOW() - INTERVAL 1 DAY);

-- Notifications
INSERT INTO Notification (notifiId, message, userId) VALUES
(1, 'Welcome to Flipfit, Alice!', 201),
(2, 'Your gym Flex Fitness has been approved.', 101),
(3, 'A new gym, Iron Paradise, is awaiting your approval.', 1);

SELECT 'Database has been reset and populated with new test data successfully.' AS 'Final Status';