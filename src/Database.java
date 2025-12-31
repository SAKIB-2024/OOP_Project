import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;

public class Database {
    // Static collections to simulate a database
    public static List<User> allUsers = new ArrayList<>();
    public static Map<Integer, Job> allJobs = new HashMap<>();
    public static List<Application> allApplications = new ArrayList<>();

    // ID counters
    public static int userIdCounter = 1000;
    public static int jobIdCounter = 2000;
    public static int applicationIdCounter = 3000;

    public static void initialize() {

        Admin admin1 = new Admin(userIdCounter++, "System Admin", "admin@jobconnect.com", "admin123");
        Admin admin2 = new Admin(userIdCounter++, "Support Admin", "support@jobconnect.com", "support123");
        allUsers.add(admin1);
        allUsers.add(admin2);

        // Create sample employers
        Employer employer1 = new Employer(
                userIdCounter++,
                "Tech Solutions Inc.",
                "hr@techsolutions.com",
                "tech0123",
                "Tech Solutions Inc.",
                "A leading technology company specializing in software development"
        );

        Employer employer2 = new Employer(
                userIdCounter++,
                "Data Analytics Corp",
                "careers@datacorp.com",
                "data0123",
                "Data Analytics Corp",
                "We transform data into insights for business growth"
        );

        allUsers.add(employer1);
        allUsers.add(employer2);

        // Create sample job hunters
        JobHunter hunter1 = new JobHunter(
                userIdCounter++,
                "John Doe",
                "john.doe@email.com",
                "john0123",
                "Experienced software developer with 5 years in Java development",
                "Java, Spring Boot, SQL, JavaScript"
        );

        JobHunter hunter2 = new JobHunter(
                userIdCounter++,
                "Alice Smith",
                "alice.smith@email.com",
                "alice123",
                "Data scientist with expertise in machine learning",
                "Python, R, Machine Learning, Data Visualization"
        );

        allUsers.add(hunter1);
        allUsers.add(hunter2);

        // Create sample jobs with deadlines
        Job job1 = new Job(
                jobIdCounter++,
                "Senior Java Developer",
                "Looking for an experienced Java developer to join our team.",
                employer1,
                "Java, Spring Boot, Microservices",
                "$90,000 - $120,000",
                LocalDate.now().plusDays(30)  // 30 days from now
        );

        Job job2 = new Job(
                jobIdCounter++,
                "Data Scientist",
                "Seeking a data scientist to analyze and interpret complex data.",
                employer2,
                "Python, Machine Learning, Statistics",
                "$85,000 - $110,000",
                LocalDate.now().plusDays(45)  // 45 days from now
        );

        allJobs.put(job1.getJobId(), job1);
        allJobs.put(job2.getJobId(), job2);
        employer1.getPostedJobs().add(job1);
        employer2.getPostedJobs().add(job2);

        // Create sample applications
        Application app1 = new Application(
                applicationIdCounter++,
                hunter1,
                job1,
                "Applied"
        );

        Application app2 = new Application(
                applicationIdCounter++,
                hunter2,
                job2,
                "Under Review"
        );

        allApplications.add(app1);
        allApplications.add(app2);
        hunter1.getAppliedJobs().add(app1);
        hunter2.getAppliedJobs().add(app2);
    }

    public static int getNextUserId() {
        return userIdCounter++;
    }

    public static int getNextJobId() {
        return jobIdCounter++;
    }

    public static int getNextApplicationId() {
        return applicationIdCounter++;
    }
}