package hansanhha.n1_problem;


import com.zaxxer.hikari.util.FastList;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity(name = "n1ProblemUser")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    public User(String username) {
        this.username = username;
    }

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    public void addPost(Post post) {
        posts.add(post);
        post.setUser(this);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setUser(this);
    }

}
