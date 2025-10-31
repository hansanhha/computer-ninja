package hansanhha;

import java.time.LocalDateTime;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class BatchJobScheduler {
    
    private final JobLauncher jobLauncher;
    private final Job job;

    public BatchJobScheduler(JobLauncher jobLauncher, @Qualifier("personJob") Job job) {
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    // 매일 새벽 3시 실행
    @Scheduled(cron = "0 0 3 * * *")
    public void runJob() throws Exception {
        System.out.println("executing job automatically");

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("requestTime", LocalDateTime.now().toString())
                .toJobParameters();

        JobExecution exec = jobLauncher.run(job, jobParameters);
        System.out.println("JobExecution status: " + exec.getStatus());
    }
    
}
