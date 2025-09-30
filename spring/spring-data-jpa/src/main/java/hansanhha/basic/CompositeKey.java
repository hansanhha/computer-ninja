package hansanhha.basic;


import jakarta.persistence.*;
import java.io.Serializable;


public class CompositeKey {


    // 1. @IdClass
    public record OrderItemId(Long orderId, Long productId) implements Serializable {}

    @Entity
    @IdClass(OrderItemId.class)
    public static class OrderItem {
        @Id
        private Long orderId;

        @Id
        private Long productId;
    }


    // 2. @EmbeddedId
    @Embeddable
    public record ReviewId(Long orderId, Long productId) {}

    @Entity
    public static class Review {

        @EmbeddedId
        private ReviewId id;
    }

    // 3. @MapsId
    @Embeddable
    public record CourseId(Long studentId, Long professorId) {}

    @Entity
    public static class Course {
        @EmbeddedId
        private CourseId id;

        @MapsId("studentId")
        @ManyToOne
        @JoinColumn(name = "student_id")
        private Student student;

        @MapsId("professorId")
        @ManyToOne
        @JoinColumn(name = "professor_id")
        private Professor professor;
    }

    @Entity
    public static class Student {
        @Id
        @GeneratedValue
        private Long id;
    }

    @Entity
    public static class Professor {
        @Id
        @GeneratedValue
        private Long id;
    }

}
