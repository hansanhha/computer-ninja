package hansanhha;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

@SpringBootTest
public class BasicIntegrationFlowTest {
    
    @Autowired
    private MessageChannel inputChannel;

    @Autowired
    private MessageChannel outputChannel;

    @Test
    void basicTest() {
        ((DirectChannel) this.outputChannel).subscribe(message -> System.out.println("Transformed message: " + message.getPayload()));
        this.inputChannel.send(new GenericMessage<>("hello, integration flow"));
    }
    
}
