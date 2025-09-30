package hansanhha.ddd.compare_to_jpa;


import jakarta.persistence.*;
import lombok.NoArgsConstructor;


@Entity(name = "orders")
@NoArgsConstructor
public class OrderJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private AddressJPA shippingAddress;

    public OrderJPA(AddressJPA shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public void changeShippingAddress(AddressJPA newShippingAddress) {
        this.shippingAddress = newShippingAddress;
    }

}
