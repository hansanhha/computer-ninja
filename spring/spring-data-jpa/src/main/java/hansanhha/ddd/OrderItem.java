package hansanhha.ddd;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@Entity(name = "dddOrderItems")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Embedded
    private Money purchaseAmount;

    private int quantity;

    public OrderItem(Order order, Long productId, Money amount, int quantity) {
        this.order = order;
        this.productId = productId;
        this.purchaseAmount = amount;
        this.quantity = quantity;
    }

    public Money getPurchaseAmount() {
        return purchaseAmount.multiply(quantity);
    }
}
