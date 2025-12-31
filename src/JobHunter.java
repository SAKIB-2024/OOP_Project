import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JobHunter extends User {
    private String resume;
    private String skills;
    private List<Application> appliedJobs;

    public JobHunter(int id, String name, String email, String password, String resume, String skills) {
        super(id, name, email, password);
        this.resume = resume;
        this.skills = skills;
        this.appliedJobs = new ArrayList<>();
    }

    // Getters and setters
    public String getResume() { return resume; }
    public void setResume(String resume) { this.resume = resume; }

    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }

    public List<Application> getAppliedJobs() { return appliedJobs; }

    @Override
    public void displayDashboard(Scanner scanner) {
        while (true) {
            System.out.println("\n===== Job Hunter Dashboard =====");
            System.out.println("Welcome, " + getName() + "!");
            System.out.println("1. View Profile");
            System.out.println("2. Edit Profile");
            System.out.println("3. Search Jobs");
            System.out.println("4. View Applied Jobs");
            System.out.println("5. Logout");
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
                    searchJobs(scanner);
                    break;
                case 4:
                    viewAppliedJobs();
                    break;
                case 5:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void viewProfile() {
        System.out.println("\n===== Your Profile =====");
        System.out.println("Name: " + getName());
        System.out.println("Email: " + getEmail());
        System.out.println("Skills: " + skills);
        System.out.println("Resume: " + resume);
    }

    private void editProfile(Scanner scanner) {
        System.out.println("\n===== Edit Profile =====");
        System.out.print("Enter new name (current: " + getName() + "): ");
        String newName = scanner.nextLine();
        if (!newName.isEmpty()) setName(newName);

        System.out.print("Enter new skills (current: " + skills + "): ");
        String newSkills = scanner.nextLine();
        if (!newSkills.isEmpty()) skills = newSkills;

        System.out.print("Enter new resume text (current: " + resume + "): ");
        String newResume = scanner.nextLine();
        if (!newResume.isEmpty()) resume = newResume;

        System.out.println("Profile updated successfully!");
    }

    private void searchJobs(Scanner scanner) {
        System.out.println("\n===== Available Jobs =====");
        if (Database.allJobs.isEmpty()) {
            System.out.println("No jobs available at the moment.");
            return;
        }

        Database.allJobs.values().forEach(job -> {
            if (job.isAcceptingApplications()) {
                System.out.println("----------------------------------------");
                System.out.println("Job ID: " + job.getJobId());
                System.out.println("Title: " + job.getTitle());
                System.out.println("Company: " + job.getEmployer().getName());
                System.out.println("Description: " + job.getDescription());
                System.out.println("Required Skills: " + job.getRequiredSkills());
                System.out.println("Salary: " + job.getSalaryRange());
                System.out.println("Application Deadline: " + job.getApplicationDeadline());
                System.out.println("Status: " + (job.isAcceptingApplications() ? "Accepting Applications" : "Closed"));
                System.out.println("----------------------------------------");
            }
        });

        System.out.print("Enter Job ID to apply (or 0 to go back): ");
        int jobId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (jobId == 0) return;

        Job selectedJob = Database.allJobs.get(jobId);
        if (selectedJob != null) {
            // Check if job is still accepting applications
            if (!selectedJob.isAcceptingApplications()) {
                System.out.println("This job is no longer accepting applications. The deadline has passed.");
                return;
            }

            // Check if already applied
            boolean alreadyApplied = appliedJobs.stream()
                    .anyMatch(app -> app.getJob().getJobId() == jobId);

            if (alreadyApplied) {
                System.out.println("You have already applied for this job.");
            } else {
                Application newApplication = new Application(
                        Database.getNextApplicationId(),
                        this,
                        selectedJob,
                        "Applied"
                );

                appliedJobs.add(newApplication);
                Database.allApplications.add(newApplication);
                System.out.println("Application submitted successfully!");
            }
        } else {
            System.out.println("Invalid Job ID.");
        }
    }

    private void viewAppliedJobs() {
        System.out.println("\n===== Your Applications =====");
        if (appliedJobs.isEmpty()) {
            System.out.println("You haven't applied to any jobs yet.");
            return;
        }

        appliedJobs.forEach(app -> {
            System.out.println("----------------------------------------");
            System.out.println("Application ID: " + app.getApplicationId());
            System.out.println("Job Title: " + app.getJob().getTitle());
            System.out.println("Company: " + app.getJob().getEmployer().getName());
            System.out.println("Status: " + app.getStatus());
            System.out.println("Applied On: " + app.getApplicationDate());
            System.out.println("Job Deadline: " + app.getJob().getApplicationDeadline());
            System.out.println("Job Status: " + (app.getJob().isAcceptingApplications() ? "Open" : "Closed"));
            System.out.println("----------------------------------------");
        });
    }
}