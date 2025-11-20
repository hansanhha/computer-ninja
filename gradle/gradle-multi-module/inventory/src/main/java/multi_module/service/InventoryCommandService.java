package multi_module.service;

import java.util.Random;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import multi_module.event.StockReservationFailedEvent;
import multi_module.event.StockReservedEvent;

@Service
public class InventoryCommandService {
    
    private final ApplicationEventPublisher publisher;

    public InventoryCommandService(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void reserveStock(Long orderId) {
        boolean ok = new Random().nextBoolean();

        if (ok) {
            publisher.publishEvent(new StockReservedEvent(orderId));
        } else {
            publisher.publishEvent(new StockReservationFailedEvent(orderId));
        }
    }
}
