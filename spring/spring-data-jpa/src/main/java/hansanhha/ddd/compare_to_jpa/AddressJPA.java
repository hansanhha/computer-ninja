package hansanhha.ddd.compare_to_jpa;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class AddressJPA {

    private String street;
    private String city;

}
