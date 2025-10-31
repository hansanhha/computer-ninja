package hansanhha;

import java.util.Collection;
import java.util.stream.Collectors;

import org.apache.logging.log4j.util.Strings;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobLauncher jobLauncher;
    private final JobRepository jobRepository;
    private final Job personJob;
    private final Job failureLoggingJob;
    private final Job retryJob;
    private final Job partitioningPersonJob;
    private final Job integrationPartitioningPersonJob;

    public JobController(
        JobLauncher jobLauncher, 
        JobRepository jobRepository, 
        @Qualifier("personJob") Job personJob,
        @Qualifier("failureLoggingJob") Job failureLoggingJob,
        @Qualifier("retryJob") Job retryJob,
        @Qualifier("partitioningPersonJob") Job partitioningPersonJob,
        @Qualifier("integrationPartitioningPersonJob") Job integrationPartitioningPersonJob) {

        this.jobLauncher = jobLauncher;
        this.jobRepository = jobRepository;
        this.personJob = personJob;
        this.failureLoggingJob = failureLoggingJob;
        this.retryJob = retryJob;
        this.partitioningPersonJob = partitioningPersonJob;
        this.integrationPartitioningPersonJob = integrationPartitioningPersonJob;
    }

    @PostMapping("/simple")
    public ResponseEntity<String> runJob() {
        return executeJob(personJob);
    }

    @PostMapping("/failure")
    public ResponseEntity<String> runFailureJob() {
        return executeJob(failureLoggingJob);
    }

    @PostMapping("/retry")
    public ResponseEntity<String> runRetryJob() {
        return executeJob(retryJob);
    }

    @PostMapping("/restrat")
    public ResponseEntity<String> runRestartJob() {
        return null;
    }

    @PostMapping("/partitioning")
    public ResponseEntity<String> runPartitioningJob() {
        return executeJob(partitioningPersonJob);
    }

    @PostMapping("/integration")
    public ResponseEntity<String> runIntegrationJob() {
        return executeJob(integrationPartitioningPersonJob);
    }

    private ResponseEntity<String> executeJob(Job job) {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters();
            
            JobExecution jobExecution = jobLauncher.run(job, jobParameters);
            JobInstance jobInstance = jobExecution.getJobInstance();

            return ResponseEntity.ok(
            """
            Job started successfully
            name: %s
            status: %s
            timestamp: %s
            """.formatted(
                jobInstance.getJobName(),
                jobExecution.getStatus(),
                jobParameters.getLong("timestamp")
            ));

        } catch (Exception e) {
            return ResponseEntity.status(500).body(job.getName() + " job failed to start concurrently: " + e.getMessage());
        }
    }

    @GetMapping("/{name}/{timestamp}")
    public ResponseEntity<String> getJob(@PathVariable String name, @PathVariable Long timestamp) {
        JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("timestamp", timestamp)
                    .toJobParameters();

        JobInstance jobInstance = jobRepository.getJobInstance(name, jobParameters);
        JobExecution jobExecution = jobRepository.getLastJobExecution(name, jobParameters);
        Collection<StepExecution> stepExecutions = jobExecution.getStepExecutions();

        return ResponseEntity.ok(
            """
            JOB INSTANCE DETAILS
            - id: %d
            - version: %d

            JOB EXECUTION DETAILS
            - create time: %s
            - start time: %s
            - end time: %s
            - elasped time: %s
            - status: %s

            STEP EXECUTIONS
            %s
            """.formatted(
                jobInstance.getInstanceId(), 
                jobInstance.getVersion(),
                jobExecution.getCreateTime(),
                jobExecution.getStartTime(),
                jobExecution.getEndTime(),
                jobExecution.getEndTime() != null && jobExecution.getStartTime() != null ?
                    Math.abs(jobExecution.getEndTime().getNano() - jobExecution.getStartTime().getNano()) / 1_000_000 + "ms" : "N/A",
                jobExecution.getExitStatus(),
                stepExecutions.stream().map(StepExecution::getSummary).map(s -> Strings.concat("- ", s)).collect(Collectors.joining("\n\n"))
                )
        );
    }
    
}