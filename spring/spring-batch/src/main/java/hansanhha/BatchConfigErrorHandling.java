package hansanhha;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.skip.SkipException;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.RetryListener;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;

@Configuration
public class BatchConfigErrorHandling {

    private final EntityManagerFactory emf;

    public BatchConfigErrorHandling(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Bean
    JpaItemWriter<Object> writerStub() {
        JpaItemWriter<Object> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(emf);
        return writer;
    }

    // 배치 처리 중 예외가 발생하는 것을 가정한 Step 정의
    @Bean
    Step failureStep(JobRepository jobRepository, PlatformTransactionManager txManager) {
        return new StepBuilder("failureStep", jobRepository)
            .<Object, Object>chunk(10, txManager)
            .reader(() -> {throw new RuntimeException();})
            .processor(i -> new Object())
            .writer(writerStub())
            .build();
    }

    // 실패가 발생하는 Job
    // 리스너를 통해 실패 정보를 로깅한다
    @Bean
    Job failureLoggingJob(
        JobRepository jobRepository,
        @Qualifier("failureJobExecutionListener") JobExecutionListener listener,
        @Qualifier("failureStep") Step step) {

        return new JobBuilder("failureJob", jobRepository)
                .start(step)
                .listener(listener)
                .build();
    }

    @Bean
    Step retryStep(
        JobRepository jobRepository, 
        PlatformTransactionManager txManager,
        @Qualifier("failureJobRetryListener") RetryListener listener) {

        return new StepBuilder("retryStep", jobRepository)
            .<Object, Object>chunk(10, txManager)
            .reader(() -> {throw new RuntimeException();})
            .processor(i -> new Object())
            .writer(writerStub())
            .faultTolerant() 
            .retry(RuntimeException.class) // RuntimeException에 대해 retry 시도
            .retryLimit(3) // 최대 3번 retry 시도
            .skip(SkipException.class) // 스킵할 특정 예외 지정
            .skipLimit(5) // 스킵할 예외에 대한 최대 누적 횟수 (설정한 수 이상으로 스킵 예외 발생 시 Step FAILED 처리)
            .listener(listener) // retry 리스너 등록
            .build();
    }

    @Bean
    Job retryJob(
        JobRepository jobRepository,
        @Qualifier("retryStep") Step step) {

        return new JobBuilder("retryJob", jobRepository)
                .start(step)
                .build();
    }
    
    
}
