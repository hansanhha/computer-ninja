package hansanhha.basic;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity(name = "basicEntityExample")
@Table(name = "basic_entity_example")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BasicEntity {

    @Id
    @GeneratedValue
    @Column(name =  "basic_entity_id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(name = "email_address")
    private String email;

    public BasicEntity(String username, String email) {
        this.username = username;
        this.email = email;
    }

}
