package com.flipfit.client;

import java.util.Scanner;

import com.flipfit.bean.*;
import com.flipfit.business.GymCustomerBusinessService;
import com.flipfit.business.GymOwnerBusinessService;
import com.flipfit.business.GymUserBusinessService;

public class GymFlipfitApplicationClient {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        GymCustomerBusinessService customerBusiness = new GymCustomerBusinessService();
        GymOwnerBusinessService ownerBusiness = new GymOwnerBusinessService();
        GymUserBusinessService userBusiness = new GymUserBusinessService();
        boolean more = true;

        while (more) {
            System.out.println("---------------------------------------------");
            System.out.println("Welcome to the Flipfit application");
            System.out.println("Enter your choice");
            System.out.println("\t1\tLogin");
            System.out.println("\t2\tReg of Gym Customer");
            System.out.println("\t3\tReg of Gym Owner");
            System.out.println("\t4\tSave Password");
            System.out.println("\t5\tExit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    String password = scanner.nextLine();
                    System.out.print("Enter Role (0: Customer, 1: Owner, 2: Admin): ");
                    int role = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    // check the implementation of loginUser
                
                    int roleSpecificId = userBusiness.loginUser(username, password,role);
                    
                    if (roleSpecificId != -1 && roleSpecificId != -2) {
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
                        System.out.println("role is invalid. Please enter correct role.");
                    }
                    else{
                        System.out.println("Invalid credentials. Try again.");
                    }
                    break;

                // ROLL OBJECT CREATION
                case 2:
                    // ---------------------------------------------------------------------------
                    boolean exists = true;
                    String userName = "";
                    while (exists) {
                        System.out.println("Enter UNIQUE username:");
                        userName = scanner.nextLine();
                        scanner.nextLine();
                        exists = userBusiness.userNameExists(userName);
                    }
                    System.out.println("Enter name:");
                    String name = scanner.nextLine();
                    scanner.nextLine();
                    System.out.println("Enter password:");
                    String pasword = scanner.nextLine();
                    scanner.nextLine();
                    GymUser newUser = userBusiness.createUserBean(name, pasword, 0, userName);
                    userBusiness.addUser(newUser);
                    System.out.println("User registered successfully with ID: " + newUser.getUserId());
                    // ---------------------------------------------------------------------------
                    System.out.println("Enter age:");
                    int age = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter location:");
                    String loca = scanner.nextLine();
                    scanner.nextLine();
                    System.out.println("Enter gender:");
                    int gender = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter email:");
                    String email = scanner.nextLine();
                    scanner.nextLine();
                    GymCustomer newCustomer = customerBusiness.createCustomerBean(name, pasword, 0, userName, age, loca, gender, email);
                    customerBusiness.registerCustomer(newCustomer);
                    System.out.println("Customer registered successfully with ID: " + newCustomer.getCustomerId());

                    break;

                case 3:
                    // ---------------------------------------------------------------------------
                    boolean exists1 = true;
                    String userName1 = "";
                    while (exists1) {
                        System.out.println("Enter UNIQUE username:");
                        userName1 = scanner.nextLine();
                        scanner.nextLine();
                        exists1 = userBusiness.userNameExists(userName1);
                    }
                    System.out.println("Enter name:");
                    String name1 = scanner.nextLine();
                    scanner.nextLine();
                    System.out.println("Enter password:");
                    String password1 = scanner.nextLine();
                    scanner.nextLine();
                    GymUser newUser1 = userBusiness.createUserBean(name1, password1, 1, userName1);
                    userBusiness.addUser(newUser1);
                    System.out.println("User registered successfully with ID: " + newUser1.getUserId());
                    // ---------------------------------------------------------------------------
                    System.out.println("Enter gender:");
                    int gende = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter email:");
                    String emai = scanner.nextLine();
                    scanner.nextLine();

                    GymOwner newOwner = ownerBusiness.createOwnerBean(name1, password1, 1, userName1, gende, emai);
                    ownerBusiness.registerOwner(newOwner);
                    System.out.println("Owner registered successfully with ID: " + newOwner.getOwnerId());
                    break;

                case 4:
                    System.out.println("Password Saved. (dummy)");
                    break;

                case 5:
                    System.out.println("Thank you for using Flipfit. Exiting...");
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