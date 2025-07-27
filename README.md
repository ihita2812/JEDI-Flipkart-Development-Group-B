# JEDI-Flipfit
# Flipfit: A Gym Booking Platform

Welcome to Flipfit! This project is a comprehensive gym and slot booking application that has evolved from a command-line interface to a modern, RESTful web service.

This guide provides all the necessary steps to set up, run, and thoroughly test both the legacy console application and the new Dropwizard REST API.

## 1. Prerequisites

Before you begin, ensure you have the following software installed and configured on your system:

*   **Java Development Kit (JDK):** Version 11 or higher (the project is compiled with Java 14).
*   **Apache Maven:** The standard Java build and dependency management tool.
*   **MySQL Server & MySQL Workbench:** To host and manage the application's database.
*   **IntelliJ IDEA:** The recommended Integrated Development Environment (IDE) for this project.
*   **Postman:** An API platform for building and using APIs. We will use this to test our REST endpoints.

## 2. Setup Instructions

Follow these steps to get the project running on your local machine.

### Step 2.1: Clone the Repository

Open your terminal or command prompt and clone the repository to a location of your choice:

```bash
git clone <your-repository-url>
```

### Step 2.2: Set Up the Database

The application requires a MySQL database named `Flipfit`.

1.  **Create the Database:** Open MySQL Workbench and run the following command to create the database schema:
    ```sql
    CREATE SCHEMA Flipfit;
    ```

2.  **Run the Setup Script:** This repository includes a master SQL script that will create all the necessary tables and populate them with a rich set of dummy data for testing.
    *   Locate the file named `schema.sql` in the root of the repository.
    *   Open `schema.sql` in MySQL Workbench.
    *   Execute the entire script. This will drop any old tables, create new ones, and insert all the test data.

### Step 2.3: Configure the Database Connection

The application's database password needs to be configured to match your local MySQL setup.

1.  Navigate to the following file in the project:
    `JEDI-Flipkart-Development-Group-B/src/main/java/com/flipfit/dao/DBConnection.java`

2.  In this file, find the following line:
    ```java
    return DriverManager.getConnection("jdbc:mysql://localhost:3306/Flipfit","root","aXath2oo!@#%");
    ```

3.  Change the password (`"aXath2oo!@#%"`) to your own local MySQL root password.

### Step 2.4: Build the Project with Maven

1.  Open the project in IntelliJ IDEA.
2.  Allow IntelliJ to import the project as a Maven project. It will read the `pom.xml` file and automatically download all the required libraries (like Dropwizard). This may take a few minutes.
3.  If you encounter any dependency errors, open the Maven tool window (`View -> Tool Windows -> Maven`) and click the "Reload All Maven Projects" button.

## 3. Running the Application

This project has two different entry points: the original console application and the new Dropwizard web service.

### Part A: Running the Console Application (Legacy)

1.  In the IntelliJ Project Explorer, navigate to:
    `src/main/java/com/flipfit/client/GymFlipfitApplicationClient.java`
2.  Right-click on the `GymFlipfitApplicationClient.java` file.
3.  Select "Run 'GymFlipfitApplicationClient.main()'".
4.  The console application will start in the "Run" window at the bottom of IntelliJ.

### Part B: Running the Dropwizard REST API

1.  In the top-right corner of IntelliJ, click on "**Add Configuration...**".
2.  In the window that opens, click the **+** button and select **Application**.
3.  Configure the runner as follows:
    *   **Name:** `Run Flipfit Server`
    *   **Main class:** Click the `...` button and select `FlipfitApplication`.
    *   **Program arguments:** Type `server`.
4.  Click **Apply** and then **OK**.
5.  Click the green "Run" (**▶**) button to start the server. The console will show a success message when the API is live at `http://localhost:8080`.

## 4. Comprehensive Testing Guide

This guide covers all functionalities of the application. For API testing, use Postman.

### Part A: REST API Testing

#### Test Suite 1: Authentication & Registration (`/login`, `/register`)

| Test Case | Method | URL | Body (raw, JSON) | Expected Status |
| :--- | :--- | :--- | :--- | :--- |
| 1.1 Register New Customer | `POST` | `http://localhost:8080/customer` | `{"name":"Diana","userName":"diana",...}` | `201 Created` |
| 1.2 Register New Owner | `POST` | `http://localhost:8080/owner/register` | `{"name":"Clark","userName":"clark",...}` | `201 Created` |
| 1.3 Register Duplicate User| `POST` | `http://localhost:8080/customer` | (Same as 1.1) | `409 Conflict` |
| 1.4 Successful Login | `POST` | `http://localhost:8080/login` | `{"userName":"customer1","password":"pass","role":{"roleId":0}}` | `200 OK` |
| 1.5 Failed Login | `POST` | `http://localhost:8080/login` | `{"userName":"customer1","password":"wrong",...}` | `401 Unauthorized`|
| 1.6 Change Password | `PUT` | `http://localhost:8080/login/customer1/password` | `{"oldPassword":"pass","newPassword":"newpass"}` | `200 OK` |

#### Test Suite 2: Administrator Endpoints (`/admin`)

| Test Case | Method | URL | Body (raw, JSON) | Expected Status |
| :--- | :--- | :--- | :--- | :--- |
| 2.1 View All Gyms | `GET` | `http://localhost:8080/admin/gyms` | (None) | `200 OK` |
| 2.2 View All Owners | `GET` | `http://localhost:8080/admin/owners` | (None) | `200 OK` |
| 2.3 View All Customers | `GET` | `http://localhost:8080/admin/customers` | (None) | `200 OK` |
| 2.4 Approve a Gym | `PUT` | `http://localhost:8080/admin/gyms/2` | `{"approvalStatus": 1}` | `200 OK` |
| 2.5 Approve Invalid Gym | `PUT` | `http://localhost:8080/admin/gyms/99` | `{"approvalStatus": 1}` | `404 Not Found` |
| 2.6 Admin Adds Customer | `POST`| `http://localhost:8080/admin/customers`| `{"name":"Bruce Wayne","userName":"bruce",...}`| `201 Created` |
| 2.7 Admin Adds Owner | `POST`| `http://localhost:8080/admin/owners` | `{"name":"Tony Stark","userName":"tony",...}`| `201 Created` |
| 2.8 Remove a Customer | `DELETE`| `http://localhost:8080/admin/customers/3`| (None) | `200 OK` |
| 2.9 Remove Owner (Twice)| `DELETE`| `http://localhost:8080/admin/owners/2` | (None) | `200 OK` (1st), `404 Not Found` (2nd) |

#### Test Suite 3: Customer Endpoints (`/customer`)

| Test Case | Method | URL | Body (raw, JSON) | Expected Status |
| :--- | :--- | :--- | :--- | :--- |
| 3.1 View Approved Gyms | `GET` | `http://localhost:8080/customer/gyms` | (None) | `200 OK` |
| 3.2 View Slots for Gym | `GET` | `http://localhost:8080/customer/gyms/1/slots`| (None) | `200 OK` |
| 3.3 View My Bookings | `GET` | `http://localhost:8080/customer/1/bookings` | (None) | `200 OK` |
| 3.4 Book a Slot | `POST` | `http://localhost:8080/customer/bookings` | `{"customerId": 1, "slotId": 101}` | `201 Created` |
| 3.5 Pay for Booking | `POST` | `http://localhost:8080/customer/payments` | `{"bookingId": 1002}` | `200 OK` |
| 3.6 Pay for Full Slot | `POST` | `http://localhost:8080/customer/payments` | `{"bookingId": 1003}` | `409 Conflict` |
| 3.7 Cancel Booking | `DELETE`| `http://localhost:8080/customer/bookings/1001`| (None) | `200 OK` |
| 3.8 View My Notifications| `GET` | `http://localhost:8080/customer/1/notifications`| (None) | `200 OK` |

#### Test Suite 4: Gym Owner Endpoints (`/owner`)

| Test Case | Method | URL | Body (raw, JSON) | Expected Status |
| :--- | :--- | :--- | :--- | :--- |
| 4.1 Register New Gym | `POST` | `http://localhost:8080/owner/gyms` | `{"name":"Wayne Fitness","ownerId":1,...}`| `201 Created` |
| 4.2 View My Gyms | `GET` | `http://localhost:8080/owner/1/gyms` | (None) | `200 OK` |
| 4.3 View Slots for My Gym| `GET` | `http://localhost:8080/owner/gyms/1/slots`| (None) | `200 OK` |
| 4.4 View Bookings for Slot| `GET` | `http://localhost:8080/owner/slots/102/bookings`| (None) | `200 OK` |
| 4.5 Add a New Slot | `POST` | `http://localhost:8080/owner/gyms/1/slots` | `{"startTime":"11:00:00","date":"2025-12-25"}`| `201 Created` |
| 4.6 View Payments for Gym| `GET` | `http://localhost:8080/owner/gyms/1/payments` | (None) | `200 OK` |
| 4.7 View My Notifications| `GET` | `http://localhost:8080/owner/1/notifications` | (None) | `200 OK` |

### Part B: Console Application Smoke Test

This is to verify that the legacy application still functions as expected.

1.  Run the Console App as described in section 3A.
2.  **Test Admin Flow:**
    *   Log in as `admin` / `pass` (role 2).
    *   Choose option `2` to view pending gyms. Verify 'Iron Paradise' is listed.
    *   Approve 'Iron Paradise' (ID 2).
3.  **Test Customer Flow:**
    *   Log in as `customer1` / `pass` (role 0).
    *   Choose option `1` to view gyms. Verify that the newly approved 'Iron Paradise' is now visible.
    *   Proceed to book an available slot.
4.  **Test Owner Flow:**
    *   Log in as `owner1` / `pass` (role 1).
    *   Choose option `2` to view your gyms. Verify the status of 'Iron Paradise' is now "Approved".
    *   Choose option `4` to add a new slot to one of your approved gyms.
