package hansanhha.entity_relationship;


import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity(name = "relationshipUsers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickname;

    // 양방향 관계의 주인 명시
    // 주인의 필드 이름으로 매핑한다
    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    /*
        @OneToMany(orphanRemoval = true) 주의사항

        하이버네이트는 컬렉션 엔티티의 참조를 이용하여 변경 사항을 추적한다
        단순히 컬렉션을 새로운 리스트로 교체하면 orphanRemoval을 삭제해야 되는데 기존 참조가 없어져서 추적이 불가능해지므로 예외가 발생한다
        예외 메시지: A collection with orphan deletion was no longer referenced by the owning <entity> instance
        따라서 기존 리스트를 비우고 새로운 요소를 추가하는 방식으로 구현하는 것이 안전하다
     */
}
