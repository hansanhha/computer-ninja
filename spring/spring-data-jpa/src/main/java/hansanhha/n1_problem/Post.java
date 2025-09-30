package hansanhha.n1_problem;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity(name = "n1ProblemPost")
@NamedEntityGraph(
        name = "post.author",
        attributeNodes = { @NamedAttributeNode("user") }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    @Setter(AccessLevel.PACKAGE)
    private User user;

    public Post(String title, User user) {
        this.title = title;
        this.user = user;
    }

    public Post(String title) {
        this.title = title;
    }

}
