import java.util.Scanner;

public class AuthenticationService {

    public User signIn(Scanner scanner, int roleType) throws AuthenticationException {
        System.out.println("\n===== Sign In =====");
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        // Find user with matching email, password, and role type
        for (User user : Database.allUsers) {
            boolean roleMatches = false;

            switch (roleType) {
                case 1: // Job Hunter
                    roleMatches = (user instanceof JobHunter);
                    break;
                case 2: // Employer
                    roleMatches = (user instanceof Employer);
                    break;
                case 3: // Admin
                    roleMatches = (user instanceof Admin);
                    break;
            }

            if (roleMatches && user.getEmail().equals(email) && user.getPassword().equals(password)) {
                System.out.println("Login successful! Welcome, " + user.getName() + "!");
                return user;
            }
        }

        throw new AuthenticationException("Invalid email or password, or incorrect role selection.");
    }

    public void signUp(Scanner scanner, int roleType) {
        System.out.println("\n===== Sign Up =====");

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        // Check if email already exists
        for (User user : Database.allUsers) {
            if (user.getEmail().equals(email)) {
                System.out.println("Email already registered. Please use a different email.");
                return;
            }
        }

        // Password validation
        String password;
        while (true) {
            System.out.print("Enter password (minimum 8 characters): ");
            password = scanner.nextLine();

            if (password.length() >= 8) {
                break;
            } else {
                System.out.println("Password must be at least 8 characters long. Please try again.");
            }
        }

        int newId = Database.getNextUserId();
        User newUser = null;

        switch (roleType) {
            case 1: // Job Hunter
                System.out.print("Enter your skills: ");
                String skills = scanner.nextLine();

                System.out.print("Enter your resume summary: ");
                String resume = scanner.nextLine();

                newUser = new JobHunter(newId, name, email, password, resume, skills);
                break;

            case 2: // Employer
                System.out.print("Enter company name: ");
                String companyName = scanner.nextLine();

                System.out.print("Enter company description: ");
                String companyDesc = scanner.nextLine();

                newUser = new Employer(newId, name, email, password, companyName, companyDesc);
                break;

            case 3: // Admin
                // In a real system, admin registration would be restricted
                // For this demo, we'll allow it but with a warning
                System.out.println("Warning: Admin registration is typically restricted in production systems.");
                newUser = new Admin(newId, name, email, password);
                break;
        }

        Database.allUsers.add(newUser);
        System.out.println("Registration successful! You can now sign in.");
    }
}