package com.flipfit.client;

import com.flipfit.bean.GymCustomer;
import com.flipfit.bean.GymOwner;
import com.flipfit.bean.GymUser;
import com.flipfit.business.GymCustomerBusinessService;
import com.flipfit.business.GymOwnerBusinessService;
import com.flipfit.business.GymUserBusinessService;
import com.flipfit.dao.GymUserDAOImpl;
import com.flipfit.exception.InvalidCredentialsException;
import com.flipfit.exception.UsernameAlreadyExistsException;

import java.util.InputMismatchException;
import java.util.Scanner;


/*
 *@author: Aryan, Ihita, Eshwar
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

            try {
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
                        try {
                            int roleSpecificId = userBusiness.loginUser(username, password, role);
                            System.out.println("\n[SUCCESS] Login successful. Welcome, " + username + "!");
                            switch (role) {
                                case 0: GymCustomerMenu.customerMenu(roleSpecificId); break;
                                case 1: GymOwnerMenu.ownerMenu(roleSpecificId); break;
                                case 2: GymAdminMenu.adminMenu(roleSpecificId); break;
                            }
                        } catch (InvalidCredentialsException e) {
                            System.out.println("\n[ERROR] " + e.getMessage() + "\n");
                        }
                        break;

                    case 2:
                        System.out.printf("%n****************** NEW CUSTOMER REGISTRATION ******************%n");

                        // --- Step 1: Gather details that don't need immediate validation ---
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

                        // --- Step 2: Loop until a unique username is provided and registration is successful ---
                        while (true) {
                            try {
                                System.out.print("> Enter a unique Username: ");
                                String userName = scanner.nextLine();

                                // --- Step 3: Attempt to create the user and customer in the system ---
                                GymUser newUser = userBusiness.createUserBean(name, customerPassword, 0, userName);
                                userBusiness.addUser(newUser); // This is the line that will throw the exception

                                GymCustomer newCustomer = customerBusiness.createCustomerBean(name, customerPassword, 0, userName, age, location, gender, email, newUser.getUserId());
                                customerBusiness.registerCustomer(newCustomer);

                                System.out.printf("\n[SUCCESS] Welcome, %s! Your customer account has been created.\n", name);
                                System.out.println("           You can now log in using your new credentials.\n");

                                break; // IMPORTANT: Exit the while loop on successful registration

                            } catch (UsernameAlreadyExistsException e) {
                                System.out.println("\n[ERROR] " + e.getMessage());
                                // The loop will now automatically continue, prompting the user for a username again.
                            }
                        }
                        break;
                    case 3:
                        System.out.printf("%n****************** NEW GYM OWNER REGISTRATION ******************%n");

                        // --- Step 1: Gather details that don't need immediate validation ---
                        System.out.print("> Enter your Full Name: ");
                        String ownerName = scanner.nextLine();

                        System.out.print("> Enter a Password: ");
                        String ownerPassword = scanner.nextLine();

                        System.out.print("> Select your Gender (0 for Male, 1 for Female): ");
                        int ownerGender = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        System.out.print("> Enter your Email Address: ");
                        String ownerEmail = scanner.nextLine();

                        // --- Step 2: Loop until a unique username is provided and registration is successful ---
                        while (true) {
                            try {
                                System.out.print("> Enter a unique Username: ");
                                String ownerUserName = scanner.nextLine();

                                // --- Step 3: Attempt to create the user and owner in the system ---
                                GymUser newOwnerUser = userBusiness.createUserBean(ownerName, ownerPassword, 1, ownerUserName);
                                userBusiness.addUser(newOwnerUser); // This line will throw the exception

                                GymOwner newOwner = ownerBusiness.createOwnerBean(ownerName, ownerPassword, 1, ownerUserName, ownerGender, ownerEmail, newOwnerUser.getUserId());
                                ownerBusiness.registerOwner(newOwner);

                                System.out.printf("\n[SUCCESS] Welcome, %s! Your gym owner account has been created.\n", ownerName);
                                System.out.println("           You can now log in to register your gym centers.\n");

                                break; // IMPORTANT: Exit the while loop on successful registration

                            } catch (UsernameAlreadyExistsException e) {
                                System.out.println("\n[ERROR] " + e.getMessage());
                                // The loop will now automatically continue, prompting the user for a username again.
                            }
                        }
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
            }catch (InputMismatchException e) {
                System.out.println("\n[ERROR] Invalid input. Please enter a number corresponding to a menu option.\n");
                scanner.nextLine(); // IMPORTANT: Clear the bad input from the scanner
            }
        }

        scanner.close();
    }

}