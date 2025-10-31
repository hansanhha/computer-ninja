package hansanhha;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class FailureJobExecutionListener implements JobExecutionListener {

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus().isUnsuccessful()) {
            // logging, sending alert ...
            var jobName = jobExecution.getJobInstance().getJobName();
            var jobId = jobExecution.getJobId();
            var parameters = jobExecution.getJobParameters().getParameters();
            var exceptions = jobExecution.getAllFailureExceptions();

            System.out.println(
                """
                Job failed with the following states
                - name: %s
                - id: %s
                - parameters: %s
                - exceptions %s 
                """.formatted(jobName, jobId, parameters, exceptions)
            );
        }
    }
    
}