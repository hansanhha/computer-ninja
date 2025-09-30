package hansanhha.basic;


import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ElementCollection {

    @Id
    @GeneratedValue
    private Long id;

    // JPA는 coffees라는 테이블을 아래의 컬럼과 함께 생성한다
    // element_collection_entity_id (외래키)
    // coffee_name, coffee_amount
    @jakarta.persistence.ElementCollection
    @CollectionTable(name = "coffees", joinColumns = @JoinColumn(name = "element_collection_entity_id"))
    @AttributeOverrides({
        @AttributeOverride(name = "name", column = @Column(name = "coffee_name")),
        @AttributeOverride(name = "amount", column = @Column(name = "coffee_amount")),
    })
    private List<Coffee> coffees = new ArrayList<>();

    @Embeddable
    record Coffee(String name, int amount) { }

}
