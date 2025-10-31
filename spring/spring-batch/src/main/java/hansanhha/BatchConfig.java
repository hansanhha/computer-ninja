package hansanhha;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;

@Configuration
public class BatchConfig {
    
    private final EntityManagerFactory emf;
    private final LogTasklet logTasklet;

    public BatchConfig(
            EntityManagerFactory emf, 
            LogTasklet logTasklet) {

        this.emf = emf;
        this.logTasklet = logTasklet;
    }

    
    @Bean
    ItemProcessor<Person, ProcessedPerson> personProcessor() {
        return new PersonProcessor();
    }
    
    @Bean
    JpaPagingItemReader<Person> reader() {
        return new JpaPagingItemReaderBuilder<Person>()
                .name("personItemReader")
                .entityManagerFactory(emf)
                .queryString("SELECT p FROM Person p")
                .pageSize(10)
                .build();
    }
    
    @Bean
    JpaItemWriter<ProcessedPerson> writer() {
        JpaItemWriter<ProcessedPerson> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(emf);
        return writer;
    }

    @Bean
    Step personProcessStep(
        JobRepository jobRepository, 
        PlatformTransactionManager txManager, 
        @Qualifier("personProcessor") ItemProcessor<Person, ProcessedPerson> processor) {

        return new StepBuilder("stepPersonChunk", jobRepository)
            .<Person, ProcessedPerson>chunk(10, txManager)
            .reader(reader())
            .processor(processor)
            .writer(writer())
            .build();
    }

    @Bean
    Step logStep(JobRepository jobRepository, PlatformTransactionManager txManager) {
        return new StepBuilder("taskletStep", jobRepository)
            .tasklet(logTasklet, txManager)
            .build();
    }

    @Bean
    Job personJob(
        JobRepository jobRepository, 
        @Qualifier("personProcessStep") Step personProcessStep, 
        @Qualifier("logStep") Step logStep) {

        return new JobBuilder("personJob", jobRepository)
                .start(personProcessStep)
                .next(logStep)
                .build();
    }

}
