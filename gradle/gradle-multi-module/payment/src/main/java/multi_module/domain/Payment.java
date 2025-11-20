package multi_module.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Payment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    private PaymentStatus status = PaymentStatus.PENDING;

    protected Payment() {
    }

    public Payment(Long orderId) {
        this.orderId = orderId;
    }

    public void complete() {
        this.status = PaymentStatus.COMPLETED;
    }

    public void refund() {
        this.status = PaymentStatus.REFUNDED;
    }

    public Long getId() {
        return id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public PaymentStatus getStatus() {
        return status;
    }
}
