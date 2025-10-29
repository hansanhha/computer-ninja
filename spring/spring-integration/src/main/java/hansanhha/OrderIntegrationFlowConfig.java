package hansanhha;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpMethod;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.http.dsl.Http;
import org.springframework.integration.jdbc.JdbcMessageHandler;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Configuration
public class OrderIntegrationFlowConfig {

    private final IntegrationFlow basicFlow;

    private final MessageChannel inputChannel;


    OrderIntegrationFlowConfig(MessageChannel inputChannel, IntegrationFlow basicFlow) {
        this.inputChannel = inputChannel;
        this.basicFlow = basicFlow;
    }

    @Bean
    IntegrationFlow orderFlow(KafkaTemplate<String, String> kafkaTemplate,
                                TaskExecutor taskExecutor,
                                OrderService orderService,
                                JdbcMessageHandler jdbcHandler) {

        return IntegrationFlow
        .from(Http.inboundGateway("/orders")
            .requestMapping(m -> m.methods(HttpMethod.POST)))
        .transform(Transformers.fromJson(Order.class))
        .split(Order::getItems)
        .channel(c -> c.executor(taskExecutor))
        .handle("externalApiService", "call")
        .aggregate(a -> a.correlationStrategy(m -> m.getHeaders().get("orderId"))
                         .releaseStrategy(g -> g.size() == (int)g.getOne().getHeaders().get("expectedCount")))
        .handle(orderService, "summarize")
        .handle(jdbcHandler)
        .handle(Http.outboundGateway("http://notify/ack"))
        .get();
    }
    
    @Service
    public static class OrderService {
        public void summarize() {

        }
    }

    public static class Order {
        public List<Object> getItems() {
            return null;
        }
    }


}
