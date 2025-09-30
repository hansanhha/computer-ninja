package hansanhha.ddd;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service("dddOrderService")
@RequiredArgsConstructor
public class OrderService {

    private final ProductService productService;
    private final OrderRepository orderRepository;

    @Transactional
    public Long createOrder(CreateOrderCommand cmd) {
        Order order = Order.create();
        for (var item : cmd.items()) {
            Product product = productService.getProduct(item.productId());
            order.addItem(product, item.quantity());
        }
        Order saved = orderRepository.save(order);
        return saved.getId();
    }
}
