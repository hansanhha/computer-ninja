package hansanhha;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.messaging.MessageChannel;

@Configuration
public class BasicIntegrationFlowConfig {
    
    @Bean
    MessageChannel inputChannel() {
        return new DirectChannel();
    }

    @Bean
    MessageChannel outputChannel() {
        return new DirectChannel();
    }

    @Bean
    IntegrationFlow basicFlow() {
        return IntegrationFlow
            .from(this.inputChannel()) // 시작점 정의, 두 번째 인자로 Poller(주기) 설정 가능
            .transform(String.class, String::toUpperCase) // payload 변환 또는 header 조작
            .channel(this.outputChannel()) // 명시적 채널 지정
            .get();
    }
}
