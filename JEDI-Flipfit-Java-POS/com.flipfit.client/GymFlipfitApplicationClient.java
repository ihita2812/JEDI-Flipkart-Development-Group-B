package com.flipfit.client;

import java.util.Scanner;

import com.flipfit.business.*;

public class MainApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LoginService loginService = new LoginService();
        RegistrationService registrationService = new RegistrationService();

        while (true) {
            System.out.println("Welcome to the Flipfit application");
            System.out.println("Enter your choice");
            System.out.println("\t1\tLogin");
            System.out.println("\t2\tReg of Gym Cus");
            System.out.println("\t3\tReg of Gym Own");
            System.out.println("\t4\tSave Password");
            System.out.println("\t5\tExit");

            int choice = scanner.nextInt();
            scanner.nextLine();  // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    String password = scanner.nextLine();
                    System.out.print("Enter Role (0: Customer, 1: Owner, 2: Admin): ");
                    int role = scanner.nextInt();
                    scanner.nextLine(); // consume newline

                    boolean isValid = loginService.authenticate(username, password, role);
                    if (isValid) {
                        switch (role) {
                            case 0:
                                customerMenu();
                                break;
                            case 1:
                                ownerMenu();
                                break;
                            case 2:
                                adminMenu();
                                break;
                        }
                    } else {
                        System.out.println("Invalid credentials. Try again.");
                    }
                    break;

                case 2:
                    registrationService.registerCustomer();
                    break;

                case 3:
                    registrationService.registerOwner();
                    break;

                case 4:
                    registrationService.savePassword();
                    break;

                case 5:
                    System.out.println("Thank you for using Flipfit. Exiting...");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice! Please select again.");
            }
        }
    }

    public static void customerMenu() {
        System.out.println("Welcome Customer! [Add your menu options here]");
    }

    public static void ownerMenu() {
        System.out.println("Welcome Owner! [Add your menu options here]");
    }

    public static void adminMenu() {
        System.out.println("Welcome Admin! [Add your menu options here]");
    }
}