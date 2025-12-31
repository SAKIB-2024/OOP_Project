import java.time.LocalDate;

public class Job implements Displayable {
    private int jobId;
    private String title;
    private String description;
    private Employer employer;
    private String requiredSkills;
    private String salaryRange;
    private boolean isActive;
    private LocalDate postDate;
    private LocalDate applicationDeadline;

    public Job(int jobId, String title, String description, Employer employer,
               String requiredSkills, String salaryRange, LocalDate applicationDeadline) {
        this.jobId = jobId;
        this.title = title;
        this.description = description;
        this.employer = employer;
        this.requiredSkills = requiredSkills;
        this.salaryRange = salaryRange;
        this.isActive = true;
        this.postDate = LocalDate.now();
        this.applicationDeadline = applicationDeadline;
    }

    // Getters and setters
    public int getJobId() { return jobId; }
    public void setJobId(int jobId) { this.jobId = jobId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Employer getEmployer() { return employer; }
    public void setEmployer(Employer employer) { this.employer = employer; }

    public String getRequiredSkills() { return requiredSkills; }
    public void setRequiredSkills(String requiredSkills) { this.requiredSkills = requiredSkills; }

    public String getSalaryRange() { return salaryRange; }
    public void setSalaryRange(String salaryRange) { this.salaryRange = salaryRange; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public LocalDate getPostDate() { return postDate; }
    public void setPostDate(LocalDate postDate) { this.postDate = postDate; }

    public LocalDate getApplicationDeadline() { return applicationDeadline; }
    public void setApplicationDeadline(LocalDate applicationDeadline) { this.applicationDeadline = applicationDeadline; }

    // Check if job is still accepting applications
    public boolean isAcceptingApplications() {
        return isActive && (LocalDate.now().isBefore(applicationDeadline) ||
                LocalDate.now().isEqual(applicationDeadline));
    }

    @Override
    public String toString() {
        return getDisplayText();
    }

    @Override
    public String getDisplayText() {
        return "Job ID: " + jobId + ", Title: " + title + ", Company: " + employer.getCompanyName() +
                ", Deadline: " + applicationDeadline + ", Status: " +
                (isAcceptingApplications() ? "Open" : "Closed");
    }
}