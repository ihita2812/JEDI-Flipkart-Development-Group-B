package com.flipfit.client;

import java.util.Scanner;
import com.flipfit.bean.*;
import com.flipfit.business.*;

public class GymOwnerClient {
    public static void ownerMenu() {
        GymOwnerBusiness ownerBusiness = new GymOwnerBusiness();

        while(true)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome Gym Owner!");
        System.out.println("Enter your choice");
        System.out.println("\t1\tRegister Gym Center");
        System.out.println("\t2\tView Gym Centers");
        System.out.println("\t3\tAdd Slots and Capacity");
        System.out.println("\t4\tView Slot");
        System.out.println("\t5\tView Notifications");
        System.out.println("\t6\tView Payment");
        System.out.println("\t7\tLogout");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                ownerBusiness.registerGymCenter("CultFit", "Kormangla");
                break;
            case 2:
                ownerBusiness.viewGymCenters(new GymOwner());
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
                System.out.println("Logging out...");
                return; // Exit the owner menu
            default:
                System.out.println("Invalid choice.");
        }
        scanner.close();
    }

    }
}
