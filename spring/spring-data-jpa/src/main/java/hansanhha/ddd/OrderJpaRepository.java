package hansanhha.ddd;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface OrderJpaRepository extends JpaRepository<Order, Long> {

    @Query(
        """
        SELECT o
        FROM dddOrders o
        JOIN FETCH o.items
        WHERE o.id = :id 
        """)
    Optional<Order> findByIdWithItems(@Param("id") Long id);
}
