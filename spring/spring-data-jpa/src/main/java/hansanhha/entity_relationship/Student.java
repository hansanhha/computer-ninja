package hansanhha.entity_relationship;


import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity(name = "relationship_student")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Student {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(
            name = "student_course", // 연결 테이블 이름
            joinColumns = @JoinColumn(name = "student_id"), // Student FK
            inverseJoinColumns = @JoinColumn(name = "course_id") // Course FK
    )
    private List<Course> courses = new ArrayList<>();
}
