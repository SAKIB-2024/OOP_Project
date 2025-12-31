import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Database.initialize();
        Scanner scanner = new Scanner(System.in);
        AuthenticationService authService = new AuthenticationService();

        // Start the deadline checker thread
        Thread deadlineThread = new Thread(new JobDeadlineChecker());
        deadlineThread.setDaemon(true);
        deadlineThread.start();

        while (true) {
            System.out.println("\n===== RED HAWKS JobConnect Portal =====");
            System.out.println("Who are you?");
            System.out.println("1. Job Hunter");
            System.out.println("2. Employer");
            System.out.println("3. Admin");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int roleChoice = getIntInput(scanner);
            if (roleChoice == 4) {
                System.out.println("Thank you for using JobConnect. Goodbye!");
                scanner.close();
                return;
            }
            if (roleChoice < 1 || roleChoice > 3) {
                System.out.println("Invalid option. Please try again.");
                continue;
            }
            handleRoleAuthentication(roleChoice, scanner, authService);
        }
    }

    private static void handleRoleAuthentication(int roleChoice, Scanner scanner, AuthenticationService authService) {
        String roleName = "";
        switch (roleChoice) {
            case 1: roleName = "Job Hunter"; break;
            case 2: roleName = "Employer"; break;
            case 3: roleName = "Admin"; break;
        }
        while (true) {
            System.out.println("\n===== " + roleName + " Portal =====");
            System.out.println("1. Sign Up");
            System.out.println("2. Sign In");
            System.out.println("3. Back to Main Menu");
            System.out.print("Choose an option: ");

            int authChoice = getIntInput(scanner);

            switch (authChoice) {
                case 1:
                    authService.signUp(scanner, roleChoice);
                    break;
                case 2:
                    try {
                        User loggedInUser = authService.signIn(scanner, roleChoice);
                        if (loggedInUser != null) {
                            loggedInUser.displayDashboard(scanner);
                        }
                    } catch (AuthenticationException e) {
                        System.out.println("Login failed: " + e.getMessage());
                    }
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static int getIntInput(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid number.");
            scanner.next();
        }
        int input = scanner.nextInt();
        scanner.nextLine();
        return input;
    }
}
// hr@techsolutions.com tech0123
//