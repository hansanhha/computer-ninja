package hansanhha.ddd;


import java.util.Optional;

// 도메인 순수 리포지토리 인터페이스
public interface OrderRepository {

    Optional<Order> findById(Long id);
    Order save(Order order);
}
