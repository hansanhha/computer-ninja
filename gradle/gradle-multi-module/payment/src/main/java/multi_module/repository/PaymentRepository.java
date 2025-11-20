package multi_module.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import multi_module.domain.Payment;

public interface PaymentRepository extends CrudRepository<Payment, Long>{

    Optional<Payment> findByOrderId(Long orderId);
    
}
