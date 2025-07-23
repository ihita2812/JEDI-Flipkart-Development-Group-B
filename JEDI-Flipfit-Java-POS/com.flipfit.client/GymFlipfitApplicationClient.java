import java.util.Scanner;
public class FlipFitApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcom To Flipfit Application");
        while (true) {
            System.out.println("Enter your choice:");
            System.out.println("1.Login");
            System.out.println("2.Registration of Gym Customer");
            System.out.println("3.Registration of Gym Owner");
            System.out.println("4.Change Password");
            System.out.println("5.Exit");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Enter username:");
                    String username = scanner.nextLine();
                    System.out.println("Enter password:");
                    String password = scanner.nextLine();
                    System.out.println("Enter role:");
                    System.out.println("1. Gym Customer");
                    System.out.println("2. Gym Owner");
                    System.out.println("3. Gym Admin");
                    int role = scanner.nextInt();
                    switch (role) {
                        case 1:
                            showCustomerMenu();
                            break;
                        case 2:
                            showOwnerMenu();
                            break;
                        case 3:
                            showAdminMenu();
                            break;
                        default:
                            System.out.println("Invalid Role selected");
                            break;
                    }
                    break;
                case 2:
            }
        }
    }
}