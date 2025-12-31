import java.time.LocalDate;

public class Application {
    private int applicationId;
    private JobHunter jobHunter;
    private Job job;
    private String status;
    private LocalDate applicationDate;

    public Application(int applicationId, JobHunter jobHunter, Job job, String status) {
        this.applicationId = applicationId;
        this.jobHunter = jobHunter;
        this.job = job;
        this.status = status;
        this.applicationDate = LocalDate.now();
    }

    // Getters and setters
    public int getApplicationId() { return applicationId; }
    public void setApplicationId(int applicationId) { this.applicationId = applicationId; }

    public JobHunter getJobHunter() { return jobHunter; }
    public void setJobHunter(JobHunter jobHunter) { this.jobHunter = jobHunter; }

    public Job getJob() { return job; }
    public void setJob(Job job) { this.job = job; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getApplicationDate() { return applicationDate; }
    public void setApplicationDate(LocalDate applicationDate) { this.applicationDate = applicationDate; }

    @Override
    public String toString() {
        return "Application ID: " + applicationId + ", Job: " + job.getTitle() +
                ", Applicant: " + jobHunter.getName() + ", Status: " + status;
    }
}