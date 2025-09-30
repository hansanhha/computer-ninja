package hansanhha.basic;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;


public class MappedSuperclass {

    @jakarta.persistence.MappedSuperclass
    @Getter
    public static class BaseEntity {
        private String name;
        private Integer age;
    }

    @Entity
    public static class ChildEntity extends BaseEntity {
        @Id
        @GeneratedValue
        private Long id;
    }



}
