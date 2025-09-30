package hansanhha.entity_relationship;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity(name = "relationshipPost")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id @GeneratedValue
    private Long id;

    private String title;

    private String content;

    @JoinColumn(name = "post_image_id")
    @OneToOne
    private Image image;

    @JoinColumn(name = "author_id") // user 필드에 대한 데이터베이스 컬럼 이름 지정 author_id
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
