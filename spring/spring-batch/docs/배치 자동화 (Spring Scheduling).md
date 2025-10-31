#### 인덱스
- [스프링 스케줄링 + JobLauncher](#스프링-스케줄링--joblauncher)


자동화 방법
- Spring Scheduler + JobLauncher: 간단한 단일 서버 배치
- Quartz Scheduler + JobOperator: 다중 Job 관리, 실패 알람, 재시작 가능
- Spring Cloud Data Flow: Kubernetes 기반 분산 배치 관리
- Jenkins / Airflow 연동: CI/CD 파이프라인 또는 워크플로우 관리 툴에서 Job 호출


## 스프링 스케줄링 + JobLauncher

스프링 프레임워크에서 제공하는 스케줄링 기능을 이용하면 배치 작업을 자동화로 돌릴 수 있다

가장 간단한 방법은 스프링 스케줄링과 JobLauncher를 통해 단일 서버의 배치 작업을 자동화하는 것이다

```java
@SpringBootApplication

// 스프링의 TaskScheduler를 활성화시켜 @Scheduled 메서드를 일정 주기로 실행할 수 있게 한다
@EnableScheduling
public class SpringBatchApplication {

    void main() {
        SpringApplication.run(SpringBatchApplication.class);
    }
    
}
```

```java
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
```
