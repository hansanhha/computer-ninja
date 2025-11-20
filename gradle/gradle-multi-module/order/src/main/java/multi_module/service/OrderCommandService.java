package multi_module.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import multi_module.domain.Order;
import multi_module.event.OrderCompletedEvent;
import multi_module.event.OrderCreatedEvent;
import multi_module.repository.OrderRepository;

@Service
@Transactional
public class OrderCommandService {

    private final OrderRepository repository;
    private final ApplicationEventPublisher publisher;

    public OrderCommandService(OrderRepository repository, ApplicationEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    public void createOrder(Long productId, Integer quantity) {
        Order order = repository.save(new Order(productId, quantity));
        publisher.publishEvent(new OrderCreatedEvent(order.getId()));
    }

    public void completeOrder(Long orderId) {
        Order order = repository.findById(orderId).orElseThrow();
        order.complete();
        publisher.publishEvent(new OrderCompletedEvent(order.getId()));
    }

    public void cancelOrder(Long orderId) {
        Order order = repository.findById(orderId).orElseThrow();
        order.cancel();
    }
}
