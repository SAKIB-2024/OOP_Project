import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Employer extends User {
    private String companyName;
    private String companyDescription;
    private List<Job> postedJobs;

    public Employer(int id, String name, String email, String password,
                    String companyName, String companyDescription) {
        super(id, name, email, password);
        this.companyName = companyName;
        this.companyDescription = companyDescription;
        this.postedJobs = new ArrayList<>();
    }

    // Getters and setters
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getCompanyDescription() { return companyDescription; }
    public void setCompanyDescription(String companyDescription) { this.companyDescription = companyDescription; }

    public List<Job> getPostedJobs() { return postedJobs; }

    @Override
    public void displayDashboard(Scanner scanner) {
        while (true) {
            System.out.println("\n===== Employer Dashboard =====");
            System.out.println("Welcome, " + getName() + " from " + companyName + "!");
            System.out.println("1. View Profile");
            System.out.println("2. Edit Profile");
            System.out.println("3. Post a New Job");
            System.out.println("4. View My Jobs");
            System.out.println("5. View Applications");
            System.out.println("6. Logout");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewProfile();
                    break;
                case 2:
                    editProfile(scanner);
                    break;
                case 3:
                    postNewJob(scanner);
                    break;
                case 4:
                    viewMyJobs();
                    break;
                case 5:
                    viewApplications(scanner);
                    break;
                case 6:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void viewProfile() {
        System.out.println("\n===== Company Profile =====");
        System.out.println("Company Name: " + companyName);
        System.out.println("Contact Person: " + getName());
        System.out.println("Email: " + getEmail());
        System.out.println("Description: " + companyDescription);
        System.out.println("Jobs Posted: " + postedJobs.size());
    }

    private void editProfile(Scanner scanner) {
        System.out.println("\n===== Edit Profile =====");
        System.out.print("Enter new contact name (current: " + getName() + "): ");
        String newName = scanner.nextLine();
        if (!newName.isEmpty()) setName(newName);

        System.out.print("Enter new company name (current: " + companyName + "): ");
        String newCompanyName = scanner.nextLine();
        if (!newCompanyName.isEmpty()) companyName = newCompanyName;

        System.out.print("Enter new company description (current: " + companyDescription + "): ");
        String newDescription = scanner.nextLine();
        if (!newDescription.isEmpty()) companyDescription = newDescription;

        System.out.println("Profile updated successfully!");
    }

    private void postNewJob(Scanner scanner) {
        System.out.println("\n===== Post a New Job =====");
        System.out.print("Enter job title: ");
        String title = scanner.nextLine();

        System.out.print("Enter job description: ");
        String description = scanner.nextLine();

        System.out.print("Enter required skills: ");
        String skills = scanner.nextLine();

        System.out.print("Enter salary range: ");
        String salary = scanner.nextLine();

        // Get application deadline with validation
        LocalDate deadline = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        while (deadline == null) {
            System.out.print("Enter application deadline (YYYY-MM-DD): ");
            String deadlineStr = scanner.nextLine();

            try {
                deadline = parseDeadline(deadlineStr);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format: " + e.getMessage());
            }
        }

        Job newJob = new Job(
                Database.getNextJobId(),
                title,
                description,
                this,
                skills,
                salary,
                deadline
        );

        postedJobs.add(newJob);
        Database.allJobs.put(newJob.getJobId(), newJob);
        System.out.println("Job posted successfully with ID: " + newJob.getJobId());
    }

    private LocalDate parseDeadline(String deadlineStr) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate deadline = LocalDate.parse(deadlineStr, formatter);

        if (deadline.isBefore(LocalDate.now())) {
            throw new DateTimeParseException("Deadline must be in the future.", deadlineStr, 0);
        }

        return deadline;
    }

    private void viewMyJobs() {
        System.out.println("\n===== Your Posted Jobs =====");
        if (postedJobs.isEmpty()) {
            System.out.println("You haven't posted any jobs yet.");
            return;
        }

        postedJobs.forEach(job -> {
            System.out.println("----------------------------------------");
            System.out.println("Job ID: " + job.getJobId());
            System.out.println("Title: " + job.getTitle());
            System.out.println("Description: " + job.getDescription());
            System.out.println("Required Skills: " + job.getRequiredSkills());
            System.out.println("Salary: " + job.getSalaryRange());
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

    private void viewApplications(Scanner scanner) {
        System.out.println("\n===== Applications for Your Jobs =====");
        if (postedJobs.isEmpty()) {
            System.out.println("You haven't posted any jobs yet.");
            return;
        }

        // Show jobs with applications
        postedJobs.forEach(job -> {
            long appCount = Database.allApplications.stream()
                    .filter(app -> app.getJob().getJobId() == job.getJobId())
                    .count();

            if (appCount > 0) {
                System.out.println("Job ID: " + job.getJobId() + " - " + job.getTitle() +
                        " (" + appCount + " applications)");
            }
        });

        System.out.print("Enter Job ID to view applications (or 0 to go back): ");
        int jobId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (jobId == 0) return;

        // Check if the job belongs to this employer
        Job selectedJob = postedJobs.stream()
                .filter(job -> job.getJobId() == jobId)
                .findFirst()
                .orElse(null);

        if (selectedJob == null) {
            System.out.println("Invalid Job ID or you don't have permission to view applications for this job.");
            return;
        }

        System.out.println("\nApplications for Job: " + selectedJob.getTitle());
        Database.allApplications.stream()
                .filter(app -> app.getJob().getJobId() == jobId)
                .forEach(app -> {
                    System.out.println("----------------------------------------");
                    System.out.println("Application ID: " + app.getApplicationId());
                    System.out.println("Applicant: " + app.getJobHunter().getName());
                    System.out.println("Email: " + app.getJobHunter().getEmail());
                    System.out.println("Skills: " + app.getJobHunter().getSkills());
                    System.out.println("Status: " + app.getStatus());
                    System.out.println("Applied On: " + app.getApplicationDate());
                    System.out.println("----------------------------------------");
                });

        // Allow employer to update application status
        System.out.print("Enter Application ID to update status (or 0 to go back): ");
        int appId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (appId == 0) return;

        Application selectedApp = Database.allApplications.stream()
                .filter(app -> app.getApplicationId() == appId && app.getJob().getJobId() == jobId)
                .findFirst()
                .orElse(null);

        if (selectedApp == null) {
            System.out.println("Invalid Application ID.");
            return;
        }

        System.out.println("Current status: " + selectedApp.getStatus());
        System.out.print("Enter new status (Under Review, Shortlisted, Rejected, Hired): ");
        String newStatus = scanner.nextLine();

        selectedApp.setStatus(newStatus);
        System.out.println("Application status updated successfully!");
    }
}