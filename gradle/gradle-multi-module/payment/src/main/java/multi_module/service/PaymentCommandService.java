package multi_module.service;

import java.util.Random;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import multi_module.domain.Payment;
import multi_module.event.PaymentCompletedEvent;
import multi_module.event.PaymentFailedEvent;
import multi_module.repository.PaymentRepository;

@Service
@Transactional
public class PaymentCommandService {
    
    private final PaymentRepository repository;
    private final ApplicationEventPublisher publisher;

    public PaymentCommandService(PaymentRepository repository, ApplicationEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    public void requestPayment(Long orderId) {
        Payment payment = repository.save(new Payment(orderId));

        boolean result = new Random().nextBoolean();
        if (result) {
            payment.complete();
            publisher.publishEvent(new PaymentCompletedEvent(orderId));
        } else {
            publisher.publishEvent(new PaymentFailedEvent(orderId));
        }
    }

    public void refund(Long orderId) {
        repository.findByOrderId(orderId).ifPresent(Payment::refund);
    }
}
