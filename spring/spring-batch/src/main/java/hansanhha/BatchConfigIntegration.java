package hansanhha;

import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.Partitioner;
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
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;

@Configuration
public class BatchConfigIntegration {

    private final MessageChannel requestsChannel;
    private final MessageChannel repliesChannel;
    private final EntityManagerFactory emf;

    public BatchConfigIntegration(
        MessageChannel requestsChannel, 
        MessageChannel repliesChannel,
        EntityManagerFactory emf) {
        this.requestsChannel = requestsChannel;
        this.repliesChannel = repliesChannel;
        this.emf = emf;
    }

    @Bean
    ItemProcessor<Person, ProcessedPerson> IntegrationPersonProcessor() {
        return new PersonProcessor();
    }

    @Bean
    ColumnRangePartitioner integrationColumnRangePartitioner() {
        return new ColumnRangePartitioner();
    }
    
    @Bean
    PartitionHandler integrationPartitionHandler() {
        MessagingTemplate messagingTemplate = new MessagingTemplate(requestsChannel);

        return (stepExeuctionSplitter, stepExecution) -> {
            var partitions = stepExeuctionSplitter.split(stepExecution, 3);

            for (var partition : partitions) {
                var start = partition.getExecutionContext().getInt("start");
                var end = partition.getExecutionContext().getInt("end");
                var metadata = Map.of("start", start, "end", end);

                messagingTemplate.send(MessageBuilder.withPayload(metadata).build());
                System.out.println("Master send metadata to requestsChannel: " + metadata);
            }

            return partitions;
        };
    }

    @Bean
    @StepScope
    JpaPagingItemReader<Person> integrationPartitioningReader(
        @Value("#{stepExecutionContext['start']}") Integer start,
        @Value("#{stepExecutionContext['end']}") Integer end) {

        return new JpaPagingItemReaderBuilder<Person>()
            .name("integrationPartitioningReader")
            .entityManagerFactory(emf)
            .queryString("SELECT p FROM Person p WHERE p.id BETWEEN :start AND :end")
            .parameterValues(Map.of("start", start, "end", end))
            .pageSize(20)
            .build();
    }

    @Bean
    JpaItemWriter<ProcessedPerson> integrationPartitioningWriter() {
        JpaItemWriter<ProcessedPerson> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(emf);
        return writer;
    }

    @Bean
    Step integrationWorkerStep(
        JobRepository jobRepository, 
        PlatformTransactionManager txManager,
        @Qualifier("IntegrationPersonProcessor") ItemProcessor<Person, ProcessedPerson> processor,
        @Qualifier("integrationPartitioningReader") JpaPagingItemReader<Person> reader,
        @Qualifier("integrationPartitioningWriter") JpaItemWriter<ProcessedPerson> writer) {

        return new StepBuilder("integrationWorkerStep", jobRepository)
            .<Person, ProcessedPerson>chunk(50, txManager)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .build();
    }

    @Bean
    Step integrationMasterStep(
        JobRepository jobRepository,
        @Qualifier("integrationColumnRangePartitioner") Partitioner partitioner,
        @Qualifier("integrationPartitionHandler") PartitionHandler partitionHandler) {

        return new StepBuilder("integartionMasterStep", jobRepository)
                .partitioner("integrationWorkerStep", partitioner)
                .partitionHandler(partitionHandler)
                .build();
    }

    // ServiceActivator: 메시지를 수신하고 비즈니스 메서드를 실행하는 핸들러로 스프링 통합 프레임워크의 데이터 처리 플로우에 비즈니스 로직을 접목시키는 역할을 한다
    // 메시지가 requestsChannel 채널에 도착하면 자동으로 이 메서드가 실행된다
    // 메서드를 실행한 후 결과값을 다른 채널로 전파할 수 있다
    @ServiceActivator(inputChannel = "requestsChannel")
    void requestChannelServiceActivator(String partitionData) {
        System.out.println("Worker received partition data: " + partitionData);

        String processedData = "Processed partition: " + partitionData;
        repliesChannel.send(MessageBuilder.withPayload(processedData).build());
        System.out.println("Worker send processed data to repliesChannel: " + processedData);
    }

    @ServiceActivator(inputChannel = "repliesChannel")
    void repliesChannelServiceActivator(String processedData) {
        System.out.println("Worker received processed data: " + processedData);
    }

    @Bean
    Job integrationPartitioningPersonJob(
        JobRepository jobRepository,
        @Qualifier("integrationMasterStep") Step step) {
            
        return new JobBuilder("integrationPartitioningJob", jobRepository)
            .start(step)
            .build();
    }

}
