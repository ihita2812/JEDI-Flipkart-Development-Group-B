package com.flipfit.client;

import java.util.*;
import com.flipfit.business.*;
import com.flipfit.bean.*;
import java.time.LocalTime;
import java.time.LocalDate;
public class GymCustomerMenu {

    public static void customerMenu(int customerId) {

//        GymCustomerBusinessServiceInterface customerBusiness = new GymCustomerBusinessService();
//
//        Scanner scanner = new Scanner(System.in);
//        boolean more = true;
//
//        while (more) {
//            System.out.println("---------------------------------------------");
//            System.out.println("Welcome Customer!");
//            System.out.println("Enter your choice");
//            System.out.println("\t1\tView gym centers near you");
//            System.out.println("\t2\tView your bookings");
//            System.out.println("\t3\tEdit Profile");
//            System.out.println("\t4\tView Notifications");
//            System.out.println("\t5\tLogout");
//
//            int choice = scanner.nextInt();
//            scanner.nextLine(); // consume newline
//
//            switch (choice) {
//
//            case 1:
//                System.out.println("---------------------------------------------");
//                System.out.println("Here are the centers near you!");
//                customerBusiness.viewGymCenter(null);
//                for (int i = 1; i < 5; i++) {
//                    System.out.println("GYM CENTER " + i);
//                }
//                System.out.println("---------------------------------------------");
//                System.out.println("You can enter the gym center number to view available slots!");
//                System.out.println("Enter 0 to go to home page!");
//
//                int choice2 = scanner.nextInt();
//                scanner.nextLine();
//
//                if (choice2 == 0) {
//                    break;
//                } else {
//                    System.out.println("---------------------------------------------");
//                    List<> slotResultsList = customer.viewSlots(choice2);
//                    if (slotResultsList.isEmpty()) {
//                        System.out.println("No slots available for the selected gym center.");
//                    } else {
//                        System.out.println("Here are the available slots!");
//                        List<Integer> slotIds = (List<Integer>) slotResultsList.get(0);
//                        List<Slot> slots = (List<Slot>) slotResultsList.get(1);
//                        for (int i = 0; i < slotIds.size(); i++) {
//                            System.out.println("Slot ID: " + slotIds.get(i));
//                            LocalTime slotStartTime = slots.get(i).getStartTime();
//                            LocalTime slotEndTime = slotStartTime.plusHours(1);
//                            LocalDate slotDate = slots.get(i).getDate();
//                            System.out.println("Slot: " + slotStartTime + " - " + slotEndTime + " on " + slotDate);
//                            System.out.println("---------------------------------------------");
//                        }
//                    }
//                    System.out.println("---------------------------------------------");
//                    System.out.println("You can enter the slot number to book it!");
//                    System.out.println("Enter 0 to go to home page!");
//
//                    int choice3 = scanner.nextInt();
//                    scanner.nextLine();
//
//                    if (choice3 == 0) {
//                        break;
//                    } else {
//                        if (slotIds.contains(choice3)) {
//                            System.out.println("---------------------------------------------");
//                            int bookingId = customer.bookSlot(customerId, choice3);
//                            System.out.println("Slot booked successfully! Your booking ID is: " + bookingId);
//                            System.out.println("You can make payment now or later.");
//                            System.out.println("Make payment atleast 6 hours before the slot timing to confirm!");
//                            System.out.println("Enter 1 to pay now, 0 to pay later and go to home page!");
//
//                            int choice4 = scanner.nextInt();
//                            scanner.nextLine();
//
//                            if (choice4 == 1) {
//                                System.out.println("---------------------------------------------");
//                                customer.makePayment(bookingId);
//                                System.out.println("Payment made successfully! Your booking (Booking ID:" + bookingId + ") is confirmed.");
//                            } else {
//                                more = false;
//                            }
//                        } else {
//                            System.out.println("Invalid Slot ID. Breaking to main menu.");
//                            break;
//                        }
//                    }
//                }
//            break;
//
//            case 2:
//                    System.out.println("---------------------------------------------");
//                    System.out.println("Here are your bookings!");
//                    List<> bookingResultsList = customer.viewBookings();
//                    if (bookingResultsList.isEmpty()) {
//                        System.out.println("No bookings found.");
//                    } else {
//                        List<Integer> bookingIds = (List<Integer>) bookingResultsList.get(0);
//                        List<Booking> bookings = (List<Booking>) bookingResultsList.get(1);
//                        List<Slot> slots = (List<Slot>) bookingResultsList.get(2);
//
//                        for (int i = 0; i < bookingIds.size(); i++) {
//                            System.out.println("Booking ID: " + bookingIds.get(i));
//                            LocalTime slotStartTime = slots.get(i).getStartTime();
//                            LocalTime slotEndTime = slotStartTime.plusHours(1);
//                            LocalDate slotDate = slots.get(i).getDate();
//                            System.out.println("Slot: " + slotStartTime + " - " + slotEndTime + " on " + slotDate);
//                            System.out.println("Status: " + bookings.get(i).getStatus());
//                            System.out.println("Gym Center: " + centerMap.get(slots.get(i).getGymCenterId()).getName());
//                            System.out.println("---------------------------------------------");
//                        }
//                    }
//                    System.out.println("---------------------------------------------");
//                    System.out.println("You can enter the booking number number to CANCEL or EDIT it!");
//                    System.out.println("Enter 0 to go back!");
//
//                        int choice5 = scanner.nextInt();
//                        scanner.nextLine();
//
//                        if (choice5 == 0) {
//                            break;
//                        } else {
//                            if (bookingIds.contains(choice5)) {
//                            System.out.println("---------------------------------------------");
//                                System.out.println("Enter C for CANCEL or E for EDIT (anything else to quit to home page)!");
//                                char choice6 = scanner.next().charAt(0);
//                                switch (choice6) {
//                                    case 'C':
//                                        customer.cancelBooking(choice5);
//                                        System.out.println("Booking " + choice5 + " cancelled successfully.");
//                                        break;
//                                    case 'E':
//                                        customer.editBooking(choice5);
//                                    default:
//                                        break;
//                                }
//                            } else {
//                            System.out.println("Invalid Booking ID. Breaking to main menu.");
//                        }
//
//                    }
//                        break;
//
//            case 3:
//            System.out.println("---------------------------------------------");
//            customer.editProfile(null);
//            break;
//
//            case 4:
//                System.out.println("Here are your notifications!");
//                List <Notificaton> notifications = customerBusiness.viewNotificationsByCustomerId(customerId);
//                for (Notificaton notification : notifications) {
//                    System.out.println(notification.getMessage());
//                }
//                if (notifications.isEmpty()) {
//                    System.out.println("No notifications available.");
//                }
//                System.out.println("---------------------------------------------");
//                break;
//            case 5:
//                more = false;
//                break;
//
//            default:
//                System.out.println("---------------------------------------------");
//                System.out.println("Bad choice :/");
//
//            }
//        }
//
//        scanner.close();
//
 }

}
