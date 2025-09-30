package hansanhha.entity_relationship;


import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity(name = "relationship_course")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Course {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    // 관계 주인 명시
    @ManyToMany(mappedBy = "courses")
    private List<Student> students =  new ArrayList<>();
}
