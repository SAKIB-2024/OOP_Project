import java.util.Scanner;

public class Admin extends User {

    public Admin(int id, String name, String email, String password) {
        super(id, name, email, password);
    }

    @Override
    public void displayDashboard(Scanner scanner) {
        while (true) {
            System.out.println("\n===== Admin Dashboard =====");
            System.out.println("Welcome, " + getName() + "!");
            System.out.println("1. View All Users");
            System.out.println("2. View All Jobs");
            System.out.println("3. View All Applications");
            System.out.println("4. Deactivate Job");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewAllUsers();
                    break;
                case 2:
                    viewAllJobs();
                    break;
                case 3:
                    viewAllApplications();
                    break;
                case 4:
                    deactivateJob(scanner);
                    break;
                case 5:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void viewAllUsers() {
        System.out.println("\n===== All Users =====");
        System.out.println("Total Users: " + Database.allUsers.size());

        System.out.println("\n--- Job Hunters ---");
        Database.allUsers.stream()
                .filter(user -> user instanceof JobHunter)
                .forEach(user -> System.out.println(user));

        System.out.println("\n--- Employers ---");
        Database.allUsers.stream()
                .filter(user -> user instanceof Employer)
                .forEach(user -> System.out.println(user));

        System.out.println("\n--- Admins ---");
        Database.allUsers.stream()
                .filter(user -> user instanceof Admin)
                .forEach(user -> System.out.println(user));
    }

    private void viewAllJobs() {
        System.out.println("\n===== All Jobs =====");
        System.out.println("Total Jobs: " + Database.allJobs.size());

        Database.allJobs.values().forEach(job -> {
            System.out.println("----------------------------------------");
            System.out.println("Job ID: " + job.getJobId());
            System.out.println("Title: " + job.getTitle());
            System.out.println("Company: " + job.getEmployer().getCompanyName());
            System.out.println("Description: " + job.getDescription());
            System.out.println("Status: " + (job.isActive() ? "Active" : "Inactive"));
            System.out.println("Deadline: " + job.getApplicationDeadline());
            System.out.println("Accepting Applications: " + (job.isAcceptingApplications() ? "Yes" : "No"));
            System.out.println("Applications: " +
                    Database.allApplications.stream()
                            .filter(app -> app.getJob().getJobId() == job.getJobId())
                            .count());
            System.out.println("----------------------------------------");
        });
    }

    private void viewAllApplications() {
        System.out.println("\n===== All Applications =====");
        System.out.println("Total Applications: " + Database.allApplications.size());

        Database.allApplications.forEach(app -> {
            System.out.println("----------------------------------------");
            System.out.println("Application ID: " + app.getApplicationId());
            System.out.println("Job Title: " + app.getJob().getTitle());
            System.out.println("Company: " + app.getJob().getEmployer().getCompanyName());
            System.out.println("Applicant: " + app.getJobHunter().getName());
            System.out.println("Status: " + app.getStatus());
            System.out.println("Applied On: " + app.getApplicationDate());
            System.out.println("----------------------------------------");
        });
    }

    private void deactivateJob(Scanner scanner) {
        System.out.println("\n===== Deactivate Job =====");
        viewAllJobs();

        System.out.print("Enter Job ID to deactivate (or 0 to cancel): ");
        int jobId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (jobId == 0) return;

        Job job = Database.allJobs.get(jobId);
        if (job != null) {
            job.setActive(false);
            System.out.println("Job deactivated successfully!");
        } else {
            System.out.println("Invalid Job ID.");
        }
    }
}