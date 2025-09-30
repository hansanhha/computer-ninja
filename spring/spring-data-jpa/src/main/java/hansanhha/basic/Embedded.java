package hansanhha.basic;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Embedded {

    @Id
    @GeneratedValue
    private Long id;

//    @jakarta.persistence.Embedded
//    @AttributeOverrides({
//            @AttributeOverride(name = "name", column = @Column(name = "a_name")),
//            @AttributeOverride(name = "age", column = @Column(name = "a_age"))
//    })
//    private EmbeddableVO embeddableEntity;
//
//    @jakarta.persistence.Embedded
//    @AttributeOverrides({
//            @AttributeOverride(name = "name", column = @Column(name = "b_name")),
//            @AttributeOverride(name = "age", column = @Column(name = "b_age"))
//    })
//    private EmbeddableVO embeddableEntity2;
//
//    @Embeddable
//    record EmbeddableVO(String name, int age) {}

}
