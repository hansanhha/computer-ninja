package hansanhha.entity_relationship;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity(name = "relationship_image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Image {

    @Id
    @GeneratedValue
    private Long id;

    private String url;

    @OneToOne(mappedBy = "image")
    private Post post;
}
