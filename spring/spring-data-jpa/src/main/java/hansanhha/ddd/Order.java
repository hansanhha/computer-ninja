package hansanhha.ddd;


import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


// JPA 엔티티에 도메인 행위 포함
@Entity(name = "dddOrders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    @Embedded
    @Getter
    private Money totalPurchaseAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.CREATED;

    public static Order create() {
        return new Order();
    }

    public void addItem(Product product, int quantity) {
        if (status != OrderStatus.CREATED) {
            throw new IllegalStateException("Cannot add an item to the order");
        }

        OrderItem item = new OrderItem(this, product.getId(), product.getAmount(), quantity);
        items.add(item);
        totalPurchaseAmount = totalPurchaseAmount.add(item.getPurchaseAmount());
    }

    public void cancel() {
        if (status == OrderStatus.SHIPPED) {
            throw new IllegalStateException("Cannot cancel an item to the order");
        }

        this.status = OrderStatus.CANCELED;
    }
}
