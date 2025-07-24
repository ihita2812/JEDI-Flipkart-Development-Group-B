package com.flipfit.client;

import java.util.Scanner;
import com.flipfit.bean.*;
import com.flipfit.business.*;
import com.flipfit.DAO.*;
import java.util.List;

public class GymOwnerMenu {
    public static void ownerMenu() {
        GymOwnerBusinessServiceInterface ownerBusiness = new GymOwnerBusinessService();
        Boolean isRunning = true;

        Scanner scanner = new Scanner(System.in);

        while(isRunning){
        System.out.println("---------------------------------------------");
        System.out.println("Welcome Gym Owner!");
        System.out.println("Enter your choice");
        System.out.println("\t1\tRegister Gym Center");
        System.out.println("\t2\tView Gym Centers");
        System.out.println("\t3\tAdd Slots and Capacity");
        System.out.println("\t4\tView Slot");
        System.out.println("\t5\tView Notifications");
        System.out.println("\t6\tView Payment");
        System.out.println("\t7\tEdit Slot");
        System.out.println("\t7\tLogout");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.println("---------------------------------------------");
                System.out.println("Enter Center Name:");
                String centerName = scanner.nextLine();
                System.out.println("Enter Center Location:");
                String centerLocation = scanner.nextLine();
                ownerBusiness.registerGymCenter("CultFit", "Kormangla");
                break;
            case 2:
                List<GymCenter> gymCenters= ownerBusiness.viewGymCenters(new GymOwner());
                if (gymCenters.isEmpty()) {
                    System.out.println("No gym centers found for this owner.");
                } else {
                    System.out.println("Gym Centers for you:");
                    for (GymCenter center : gymCenters) {
                        System.out.println("Center Name: " + center.getName() + ", Location: " + center.getLocation());
                    }
                }
                break;
            case 3:
                ownerBusiness.addSlotsAndCapacity(new GymCenter(), 5, 20);
                break;
            case 4:
                ownerBusiness.viewSlot(new GymCenter());
                break;
            case 5:
                ownerBusiness.viewNotifications();
                break;
            case 6:
                ownerBusiness.viewPayment(new GymCenter());
                break;
            case 7:
                ownerBusiness.editSlot(23, "HSR");
                break;
            case 8:
                System.out.println("Logging out...");
                isRunning = false; // Exit the owner menu
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
    scanner.close();
    }
}
