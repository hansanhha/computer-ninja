package hansanhha;

import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;

@Configuration
public class BatchConfigPartitioning {

    private final EntityManagerFactory emf;

    public BatchConfigPartitioning(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Bean
    ItemProcessor<Person, ProcessedPerson> partitioningPersonProcessor() {
        return new PersonProcessor();
    }

    @Bean
    ColumnRangePartitioner partitioningColumnRangePartitioner() {
        return new ColumnRangePartitioner();
    }

    @Bean
    PartitionHandler partitioningHandler(@Qualifier("partitioningWorkerStep") Step step) {
        TaskExecutorPartitionHandler handler = new TaskExecutorPartitionHandler();
        handler.setTaskExecutor(new SimpleAsyncTaskExecutor()); // 병렬 실행
        handler.setStep(step); // Worker Step
        handler.setGridSize(3); // 파티션 개수
        return handler;
    }

    @Bean
    @StepScope
    JpaPagingItemReader<Person> partitioningReader(
        @Value("#{stepExecutionContext['start']}") Integer start,
        @Value("#{stepExecutionContext['end']}") Integer end) {

        return new JpaPagingItemReaderBuilder<Person>()
            .name("partitioningReader")
            .entityManagerFactory(emf)
            .queryString("SELECT p FROM Person p WHERE p.id BETWEEN :start AND :end")
            .parameterValues(Map.of("start", start, "end", end))
            .pageSize(20)
            .build();
    }

    @Bean
    JpaItemWriter<ProcessedPerson> partitioningWriter() {
        JpaItemWriter<ProcessedPerson> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(emf);
        return writer;
    }

    @Bean
    Step partitioningWorkerStep(
        JobRepository jobRepository, 
        PlatformTransactionManager txManager, 
        @Qualifier("partitioningPersonProcessor") ItemProcessor<Person, ProcessedPerson> processor,
        @Qualifier("partitioningReader") JpaPagingItemReader<Person> reader,
        @Qualifier("partitioningWriter") JpaItemWriter<ProcessedPerson> writer) {

        return new StepBuilder("partitioningWorkerStep", jobRepository)
            .<Person, ProcessedPerson>chunk(50, txManager)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .build();
    }

    @Bean
    Step partitioningMasterStep(
        JobRepository jobRepository, 
        @Qualifier("partitioningWorkerStep") Step step, 
        @Qualifier("partitioningColumnRangePartitioner") Partitioner partitioner,
        @Qualifier("partitioningHandler") PartitionHandler partitionHandler) {

        return new StepBuilder("partitioningMasterStep", jobRepository)
            .partitioner("partitioningWorkerStep", partitioner)
            .partitionHandler(partitionHandler)
            .build();
    }

    @Bean
    Job partitioningPersonJob(
        JobRepository jobRepository,
        @Qualifier("partitioningMasterStep") Step step) {

        return new JobBuilder("partitioningPersonJob", jobRepository)
            .start(step)
            .build();
    }

}
