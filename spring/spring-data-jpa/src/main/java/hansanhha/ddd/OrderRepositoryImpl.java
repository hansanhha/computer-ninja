package hansanhha.ddd;


import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository{

    private final OrderJpaRepository repository;

    @Override
    public Optional<Order> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Order save(Order order) {
        return repository.save(order);
    }

}
