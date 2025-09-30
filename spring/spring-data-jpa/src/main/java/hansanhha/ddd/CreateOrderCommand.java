package hansanhha.ddd;


import java.util.List;


public record CreateOrderCommand(List<CreateOrderItem> items) {

    public record CreateOrderItem(Long productId, Integer quantity) {}
}
