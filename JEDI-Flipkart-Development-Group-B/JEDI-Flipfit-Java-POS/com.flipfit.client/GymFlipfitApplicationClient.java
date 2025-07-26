package com.flipfit.client;

import java.util.Scanner;

import com.flipfit.bean.*;
import com.flipfit.business.GymCustomerBusinessService;
import com.flipfit.business.GymOwnerBusinessService;
import com.flipfit.business.GymUserBusinessService;
import com.flipfit.dao.*;


/*
 *@author: Aryan, Ihita, Kashif
 *@ClassName:GymFlipfitApplicationClient
 *@version:1.0
 *@See :GymCustomerBusinessService, GymOwnerBusinessService, GymUserBusinessService
 */

public class GymFlipfitApplicationClient {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        GymCustomerBusinessService customerBusiness = new GymCustomerBusinessService();
        GymOwnerBusinessService ownerBusiness = new GymOwnerBusinessService();
        GymUserBusinessService userBusiness = new GymUserBusinessService();
        boolean more = true;

        GymUserDAOImpl gymUserDAO = new GymUserDAOImpl();
        gymUserDAO.initializeAdmin(); // Initialize admin, owner, and customer roles

        while (more) {

            System.out.printf("%n*********************************************%n");
            System.out.printf("      WELCOME TO THE FLIPFIT PLATFORM%n");
            System.out.printf("*********************************************%n");

            System.out.println("\n--- Main Menu ---");
            System.out.println("  1. Login to Your Account");
            System.out.println("  2. Register as a New Customer");
            System.out.println("  3. Register as a New Gym Owner");

            System.out.println("\n--- System ---");
            System.out.println("  4. Change My Password");
            System.out.println("  5. Exit Application");

            System.out.print("\nEnter your choice -> ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.printf("%n********************* ACCOUNT LOGIN *********************%n");
                    System.out.print("> Enter Username: ");
                    String username = scanner.nextLine();

                    System.out.print("> Enter Password: ");
                    String password = scanner.nextLine();

                    System.out.print("> Select Role (0: Customer, 1: Owner, 2: Admin): ");
                    int role = scanner.nextInt();
                    scanner.nextLine(); // consume newline

                    int roleSpecificId = userBusiness.loginUser(username, password, role);

                    if (roleSpecificId >= 0) {
                        // Successful login returns a positive ID
                        System.out.println("\n[SUCCESS] Login successful. Welcome, " + username + "!");
                        switch (role) {
                            case 0:
                                GymCustomerMenu.customerMenu(roleSpecificId);
                                break;
                            case 1:
                                GymOwnerMenu.ownerMenu(roleSpecificId);
                                break;
                            case 2:
                                GymAdminMenu.adminMenu(roleSpecificId);
                                break;
                        }
                    } else if (roleSpecificId == -2) {
                        System.out.println("\n[ERROR] Invalid role selected. Please ensure you are logging in with your correct role.\n");
                    } else { // Handles -1 and any other errors
                        System.out.println("\n[ERROR] Invalid username or password. Please try again.\n");
                    }
                    break;

//                // ROLL OBJECT CREATION
//                case 2:
//                    // ---------------------------------------------------------------------------
//                    boolean exists = true;
//                    String userName = "";
//                    while (exists) {
//                        System.out.println("Enter UNIQUE username:");
//                        userName = scanner.nextLine();
//                        exists = userBusiness.userNameExists(userName);
//                    }
//                    System.out.println("Enter name:");
//                    String name = scanner.nextLine();
//                    System.out.println("Enter password:");
//                    String pasword = scanner.nextLine();
//                    GymUser newUser = userBusiness.createUserBean(name, pasword, 0, userName);
//                    userBusiness.addUser(newUser);
//                    System.out.println("User registered successfully with ID: " + newUser.getUserId());
//                    // ---------------------------------------------------------------------------
//                    System.out.println("Enter age:");
//                    int age = scanner.nextInt();
//                    scanner.nextLine();
//                    System.out.println("Enter location:");
//                    String loca = scanner.nextLine();
//                    System.out.println("Enter gender:");
//                    System.out.println("\t\t0 for Male");
//                    System.out.println("\t\t1 for Female");
//                    int gender = scanner.nextInt();
//                    System.out.println("Enter email:");
//                    String email = scanner.nextLine();
//                    scanner.nextLine();
//                    int userId = newUser.getUserId();
//                    GymCustomer newCustomer = customerBusiness.createCustomerBean(name, pasword, 0, userName, age, loca, gender, email, userId);
//                    customerBusiness.registerCustomer(newCustomer);
//                    System.out.println("Customer registered successfully with ID: " + newCustomer.getCustomerId());
//
//                    break;
                case 2:
                    System.out.printf("%n****************** NEW CUSTOMER REGISTRATION ******************%n");

                    // --- Step 1: Get a unique username ---
                    String userName;
                    while (true) {
                        System.out.print("> Enter a unique Username: ");
                        userName = scanner.nextLine();
                        if (userBusiness.userNameExists(userName)) {
                            System.out.println("\n[ERROR] Username '" + userName + "' is already taken. Please try another.\n");
                        } else {
                            break; // Exit loop if username is unique
                        }
                    }

                    // --- Step 2: Gather all other details ---
                    System.out.print("> Enter your Full Name: ");
                    String name = scanner.nextLine();

                    System.out.print("> Enter a Password: ");
                    String customerPassword = scanner.nextLine();

                    System.out.print("> Enter your Age: ");
                    int age = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    System.out.print("> Enter your Location: ");
                    String location = scanner.nextLine();

                    System.out.print("> Select your Gender (0 for Male, 1 for Female): ");
                    int gender = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    System.out.print("> Enter your Email Address: ");
                    String email = scanner.nextLine();

                    // --- Step 3: Create the user and customer in the system ---
                    GymUser newUser = userBusiness.createUserBean(name, customerPassword, 0, userName);
                    userBusiness.addUser(newUser);

                    GymCustomer newCustomer = customerBusiness.createCustomerBean(name, customerPassword, 0, userName, age, location, gender, email, newUser.getUserId());
                    customerBusiness.registerCustomer(newCustomer);

                    System.out.printf("\n[SUCCESS] Welcome, %s! Your customer account has been created.\n", name);
                    System.out.println("           You can now log in using your new credentials.\n");
                    break;

//                case 3:
//                    // ---------------------------------------------------------------------------
//                    boolean exists1 = true;
//                    String userName1 = "";
//                    while (exists1) {
//                        System.out.println("Enter UNIQUE username:");
//                        userName1 = scanner.nextLine();
//                        exists1 = userBusiness.userNameExists(userName1);
//                    }
//                    System.out.println("Enter name:");
//                    String name1 = scanner.nextLine();
//                    System.out.println("Enter password:");
//                    String password1 = scanner.nextLine();
//                    GymUser newUser1 = userBusiness.createUserBean(name1, password1, 1, userName1);
//                    userBusiness.addUser(newUser1);
//                    System.out.println("User registered successfully with ID: " + newUser1.getUserId());
//                    // ---------------------------------------------------------------------------
//                    System.out.println("Enter gender:");
//                    System.out.println("\t\t0 for Male");
//                    System.out.println("\t\t1 for Female");
//                    int gende = scanner.nextInt();
//                    System.out.println("Enter email:");
//                    String emai = scanner.nextLine();
//                    scanner.nextLine();
//                    int userId1 = newUser1.getUserId();
//                    GymOwner newOwner = ownerBusiness.createOwnerBean(name1, password1, 1, userName1, gende, emai, userId1);
//                    ownerBusiness.registerOwner(newOwner);
//                    System.out.println("Owner registered successfully with ID: " + newOwner.getOwnerId());
//                    break;

                case 3:
                    System.out.printf("%n****************** NEW GYM OWNER REGISTRATION ******************%n");

                    // --- Step 1: Get a unique username ---
                    String ownerUserName;
                    while (true) {
                        System.out.print("> Enter a unique Username: ");
                        ownerUserName = scanner.nextLine();
                        if (userBusiness.userNameExists(ownerUserName)) {
                            System.out.println("\n[ERROR] Username '" + ownerUserName + "' is already taken. Please try another.\n");
                        } else {
                            break; // Exit loop if username is unique
                        }
                    }

                    // --- Step 2: Gather all other details ---
                    System.out.print("> Enter your Full Name: ");
                    String ownerName = scanner.nextLine();

                    System.out.print("> Enter a Password: ");
                    String ownerPassword = scanner.nextLine();

                    System.out.print("> Select your Gender (0 for Male, 1 for Female): ");
                    int ownerGender = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    System.out.print("> Enter your Email Address: ");
                    String ownerEmail = scanner.nextLine();

                    // --- Step 3: Create the user and owner in the system ---
                    GymUser newOwnerUser = userBusiness.createUserBean(ownerName, ownerPassword, 1, ownerUserName);
                    userBusiness.addUser(newOwnerUser);

                    GymOwner newOwner = ownerBusiness.createOwnerBean(ownerName, ownerPassword, 1, ownerUserName, ownerGender, ownerEmail, newOwnerUser.getUserId());
                    ownerBusiness.registerOwner(newOwner);

                    System.out.printf("\n[SUCCESS] Welcome, %s! Your gym owner account has been created.\n", ownerName);
                    System.out.println("           You can now log in to register your gym centers.\n");
                    break;

                case 4:
                    System.out.printf("%n********************* CHANGE PASSWORD *********************%n");
                    System.out.print("> To continue, please confirm your Username: ");
                    String adminUsername = scanner.nextLine();

                    System.out.print("> Enter your CURRENT password: ");
                    String oldPassword = scanner.nextLine();

                    System.out.print("> Enter your NEW password: ");
                    String newPassword1 = scanner.nextLine();

                    System.out.print("> Confirm your NEW password: ");
                    String newPassword2 = scanner.nextLine();

                    if (!newPassword1.equals(newPassword2)) {
                        System.out.println("\n[ERROR] The new passwords do not match. Please try again.\n");
                        break;
                    }

                    if (userBusiness.changePassword(adminUsername, oldPassword, newPassword1)) {
                        System.out.println("\n[SUCCESS] Your password has been changed successfully.\n");
                    } else {
                        System.out.println("\n[ERROR] Password change failed. Please check your username and current password.\n");
                    }
                    break;

                case 5:
                    System.out.printf("%n*********************************************%n");
                    System.out.println("  Thank you for using the Flipfit Platform!");
                    System.out.println("              Exiting now...");
                    System.out.printf("*********************************************%n\n");

                    scanner.close();
                    more = false;
                    break;

                default:
                    System.out.println("Invalid choice! Please select again.");

            }
        }

        scanner.close();
    }

}