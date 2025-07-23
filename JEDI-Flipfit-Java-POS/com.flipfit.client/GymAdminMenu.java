package com.flipfit.client;

import java.util.Scanner;
import com.flipfit.business.*;

public class GymAdminMenu {
        public static void adminMenu(int adminId) {
        
        GymAdminBusinessServiceInterface admin = new GymAdminBusinessService();

        Scanner scanner = new Scanner(System.in);
        boolean valid = true;    

         while(valid)
        {
        
        System.out.println("---------------------------------------------");
        System.out.println("Welcome Admin!");
        System.out.println("Enter your choice");
        System.out.println("\t1\tView all registered gym centers");
        System.out.println("\t2\tView pending gym center approvals");
        System.out.println("\t3\tView all payments");
        System.out.println("\t4\tAdd Roles");
        System.out.println("\t5\tLogout");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch(choice)
        {
            case 1:
                admin.viewRegisteredGyms();
                break;
            case 2:
                System.out.println("Select a gym for approval or rejection!");
                admin.viewPendingGymCenters();
                int gymChoice = scanner.nextInt();
                scanner.nextLine();
                boolean status = admin.verifyGymCenter(gymChoice);
                if(status==true)
                {System.out.println("Gym center with id "+ gymChoice+ " approved!");}
                else
                System.out.println("Gym center with id" + gymChoice+"rejected!");
                break;
            case 3:
                System.out.println("Here are the payments!");
                admin.viewPayments();
                break;
            case 4:
                System.out.println("Enter Role name to be added");
                String roleName = scanner.nextLine();
                scanner.nextLine();

                System.out.println("Enter Role Description to be added");
                String roleDesc = scanner.nextLine();
                scanner.nextLine();

                admin.addNewRole(roleName,roleDesc);
                break;
            case 5:
                System.out.println("See you again!");
                return;
            default:
                System.out.println("Invalid choice.");
        }
        }
        scanner.close();
    }
}