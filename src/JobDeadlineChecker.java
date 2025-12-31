public class JobDeadlineChecker implements Runnable {
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(3600000);
                checkDeadlines();
            } catch (InterruptedException e) {
                System.out.println("Job deadline checker interrupted.");
                break;
            }
        }
    }

    private void checkDeadlines() {
        for (Job job : Database.allJobs.values()) {
            if (job.isActive() && !job.isAcceptingApplications()) {
                job.setActive(false);
                System.out.println("Job ID " + job.getJobId() + " has been automatically closed due to deadline passing.");
            }
        }
    }
}