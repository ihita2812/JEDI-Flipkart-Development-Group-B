package com.flipfit.client;

import com.flipfit.bean.*;
import com.flipfit.business.*;
import java.util.*;

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
                System.out.println("Notifications:");
                // Assuming viewNotifications returns a list of notifications
                List <Notificaton> notifications = ownerBusiness.viewNotifications();
                for (Notificaton notification : notifications) {
                    System.out.println(notification.getMessage());
                }
                if (notifications.isEmpty()) {
                    System.out.println("No notifications available.");
                }
                System.out.println("---------------------------------------------");
                break;
            case 6:
                ownerBusiness.viewPayment(new GymCenter());
                break;
            case 7:
                ownerBusiness.editSlot("23", "HSR");
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
