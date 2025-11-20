package multi_module.repository;

import org.springframework.data.repository.CrudRepository;

import multi_module.domain.Order;

public interface OrderRepository extends CrudRepository<Order, Long>{
    
}
