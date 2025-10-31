package hansanhha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication

// 스프링의 TaskScheduler를 활성화시켜 @Scheduled 메서드를 일정 주기로 실행할 수 있게 한다
@EnableScheduling
public class SpringBatchApplication {

    void main() {
        SpringApplication.run(SpringBatchApplication.class);
    }
    
}
