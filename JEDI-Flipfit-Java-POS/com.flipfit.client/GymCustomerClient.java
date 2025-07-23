package com.flipflit.client;

import java.util.Scanner;

import com.flipfit.business.*;

public class GymCustomerClient {

    public static void customerMenu(int customerId) {

        GymCustomerBusiness customer = new GymCustomerBusiness();
        System.out.println("Welcome cutomer!");


        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome Customer!");
        System.out.println("Enter your choice");
        System.out.println("\t1\tView gym centers near you");
        System.out.println("\t2\tView your bookings");
        System.out.println("\t5\tLogout");

        int choice = scanner.nextInt();
        scanner.nextLine();  // consume newline

        switch (choice) {
            case 1:
                System.out.println("Here are the centers near you!");
        }

        scanner.close();


    }
}
