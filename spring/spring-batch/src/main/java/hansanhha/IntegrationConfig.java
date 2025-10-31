package hansanhha;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

@Configuration
public class IntegrationConfig {
    
    // 파티션 데이터 전송 채널
    @Bean
    MessageChannel requestsChannel() {
        return new DirectChannel();
    }

    // 결과 수신 채널
    @Bean
    MessageChannel repliesChannel() {
        return new DirectChannel();
    }

}
