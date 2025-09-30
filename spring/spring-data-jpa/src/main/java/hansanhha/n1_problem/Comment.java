package hansanhha.n1_problem;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity(name = "n1ProblemComment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    @JoinColumn(name = "author_id")
    @ManyToOne
    @Setter(AccessLevel.PACKAGE)
    private User user;

    private String content;

    public Comment(String content) {
        this.content = content;
    }

}
