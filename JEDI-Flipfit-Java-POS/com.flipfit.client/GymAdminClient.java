package com.flipflit.client;

import java.util.Scanner;

import com.flipfit.business.*;

public class GymAdminClient {
        public static void adminMenu(int adminId) {
        
        GymAdminBusiness admin = new GymAdminBusiness();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome Admin!");
        System.out.println("Enter your choice");
        System.out.println("\t1\tView all registered gym centers");
        System.out.println("\t2\tView pending gym center approvals");
        System.out.println("\t3\tView all payments");
        System.out.println("\t4\tLogout");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch(choice)
        {
            case 1:
                admin.viewRegisteredGyms();
                break;
            case 2:
                admin.viewPendingGymCenters();
                System.out.println("Select a gym for approval or rejection!");
                int gymChoice = scanner.nextInt();
                scanner.nextLine();
                boolean status = admin.verifyGymCenter(gymChoice);
                if(status==true)
                {System.out.println("Gym center with id "+ gymChoice+ " approved!");}
                else
                System.out.println("Gym center with id" + gymChoice+"rejected!");
                break;
            case 3:
                admin.viewPayments();
                break;
            case 4:
                System.out.println("See you again!");
                break;
            default:
                System.out.println("Invalid choice.");
        }
        scanner.close();
    }
}