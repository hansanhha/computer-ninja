package hansanhha.ddd.compare_to_jpa;


public class Order {

    private OrderId id;
    private Address shippingAddress;

    public void changeShippingAddress(Address newShippingAddress) {
        this.shippingAddress = newShippingAddress;
    }

    public record OrderId(Long value) { }
}
