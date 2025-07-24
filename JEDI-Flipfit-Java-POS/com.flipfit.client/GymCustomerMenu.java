package com.flipfit.client;

import java.util.*;
import com.flipfit.business.*;
import com.flipfit.bean.*;
import java.time.LocalTime;
import java.time.LocalDate;

public class GymCustomerMenu {

    public static void customerMenu(int customerId) {

        GymCustomerBusinessServiceInterface customerBusiness = new GymCustomerBusinessService();

        Scanner scanner = new Scanner(System.in);
        boolean more = true;

        while (more) {
            System.out.println("---------------------------------------------");
            System.out.println("Welcome Customer!");
            System.out.println("Enter your choice");
            System.out.println("\t1\tView gym centers near you");
            System.out.println("\t2\tView your bookings");
            System.out.println("\t3\tEdit Profile");
            System.out.println("\t4\tView Notifications");
            System.out.println("\t5\tLogout");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {

            case 1:
                System.out.println("---------------------------------------------");
                System.out.println("Here are the centers near you!");
                List<GymCenter> centerList = customerBusiness.viewGymCenter();
                List<Integer> centerIds = new ArrayList<>();
                for (GymCenter gymCenter : centerList) {
                    centerIds.add(gymCenter.getCenterId());
                    System.out.println("Center Id: " + gymCenter.getCenterId());
                    System.out.println("Name: " + gymCenter.getName());
                    System.out.println("Location: " + gymCenter.getLocation());
                    System.out.println("---------------------------------------------");
                }
                System.out.println("---------------------------------------------");
                System.out.println("You can enter the gym center number to view available slots!");
                System.out.println("Enter 0 to go to home page!");

                int choice2 = scanner.nextInt();
                scanner.nextLine();

                if (choice2 == 0) {
                    break;
                } else {
                    if (centerIds.contains(choice2)) {
                        System.out.println("---------------------------------------------");
                        List<Slot> slotResultsList = customerBusiness.viewSlotsFromCenter(choice2);
                        List<Integer> slotIds = new ArrayList<>();
                        if (slotResultsList.isEmpty()) {
                            System.out.println("No slots available for the selected gym center.");
                        } else {
                            System.out.println("Here are the available slots!");
                            for (Slot slot : slotResultsList) {
                                slotIds.add(slot.getSlotId());
                            }
                            for (int i = 0; i < slotResultsList.size(); i++) {
                                System.out.println("Slot ID: " + slotIds.get(i));
                                LocalTime slotStartTime = slotResultsList.get(i).getStartTime();
                                LocalTime slotEndTime = slotStartTime.plusHours(1);
                                LocalDate slotDate = slotResultsList.get(i).getDate();
                                System.out.println("Slot: " + slotStartTime + " - " + slotEndTime + " on " + slotDate);
                                System.out.println("---------------------------------------------");
                            }
                        }
                        System.out.println("---------------------------------------------");
                        System.out.println("You can enter the slot number to book it!");
                        System.out.println("Enter 0 to go to home page!");

                        int choice3 = scanner.nextInt();
                        scanner.nextLine();

                        if (choice3 == 0) {
                            break;
                        } else {
                            if (slotIds.contains(choice3)) {
                                System.out.println("---------------------------------------------");
                                int bookingId = customerBusiness.bookSlot(customerId, choice3);
                                System.out.println("Slot booked successfully! Your booking ID is: " + bookingId);
                                System.out.println("You can make payment now or later.");
                                System.out.println("Make payment at least 6 hours before the slot timing to confirm!");
                                System.out.println("Enter 1 to pay now, 0 to pay later and go to home page!");

                                int choice4 = scanner.nextInt();
                                scanner.nextLine();

                                if (choice4 == 1) {
                                    System.out.println("---------------------------------------------");
                                    customerBusiness.makePayment(bookingId);
                                    System.out.println("Payment made successfully! Your booking (Booking ID:" + bookingId + ") is confirmed.");
                                } else {
                                    more = false;
                                }
                            } else {
                                System.out.println("Invalid Slot ID. Breaking to main menu.");
                                break;
                            }
                        }
                    } else {
                        System.out.println("Invalid Center ID. Breaking to main menu.");
                    }
                }
            break;

            case 2:
                    System.out.println("---------------------------------------------");
                    System.out.println("Here are your bookings!");
                    List<Booking> bookingResultsList = customerBusiness.viewBookings(customerId);
                    List<Integer> bookingIds = new ArrayList<>();
                    if (bookingResultsList.isEmpty()) {
                        System.out.println("No bookings found.");
                    } else {
                        for (Booking booking : bookingResultsList) {
                            int bookingId = booking.getBookingId();
                            bookingIds.add(bookingId);
                            System.out.println("Booking ID: " + bookingId);
                            Slot bookingSlot = customerBusiness.viewSlotFromBooking(bookingId);
                            LocalTime slotStartTime = bookingSlot.getStartTime();
                            LocalTime slotEndTime = slotStartTime.plusHours(1);
                            LocalDate slotDate = bookingSlot.getDate();
                            System.out.println("Slot: " + slotStartTime + " - " + slotEndTime + " on " + slotDate);
                            System.out.println("Status: " + booking.getStatus());
                            System.out.println("Gym Center: " + customerBusiness.viewCenterName(bookingSlot.getCenterId()));
                            System.out.println("---------------------------------------------");
                        }
                    }
                    System.out.println("---------------------------------------------");
                    System.out.println("You can enter the booking number number to CANCEL or EDIT it!");
                    System.out.println("Enter 0 to go back!");

                        int choice5 = scanner.nextInt();
                        scanner.nextLine();

                        if (choice5 == 0) {
                            break;
                        } else {
                            if (bookingIds.contains(choice5)) {
                            System.out.println("---------------------------------------------");
                                System.out.println("Enter C for CANCEL or E for EDIT (anything else to quit to home page)!");
                                char choice6 = scanner.next().charAt(0);
                                switch (choice6) {
                                    case 'C':
                                        customerBusiness.cancelBooking(choice5);
                                        System.out.println("Booking " + choice5 + " cancelled successfully.");
                                        break;
                                    case 'E':
                                        customerBusiness.editBooking(choice5);
                                    default:
                                        break;
                                }
                            } else {
                            System.out.println("Invalid Booking ID. Breaking to main menu.");
                        }

                    }
                        break;

            case 3:
            System.out.println("---------------------------------------------");
            customerBusiness.editProfile(null);
            break;

            case 4:
                System.out.println("Here are your notifications!");
                List<Notification> notifications = customerBusiness.viewNotificationsByCustomerId(customerId);
                if (notifications.isEmpty()) {
                    System.out.println("No notifications available.");
                }
                for (Notification notification : notifications) {
                    System.out.println(notification.getMessage());
                }
                System.out.println("---------------------------------------------");
                break;
            case 5:
                more = false;
                break;

            default:
                System.out.println("---------------------------------------------");
                System.out.println("Bad choice :/");

            }
        }

        scanner.close();

 }

}
