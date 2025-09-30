package hansanhha.basic;


import jakarta.persistence.*;
import lombok.Getter;


public class Inheritance {

    // 부모 테이블에 모든 자식 엔티티의 컬럼을 포함시키고 DTYPE 컬럼으로 특정 자식 엔티티를 구분한다
    @Entity
    @Getter
    @jakarta.persistence.Inheritance
    public static class SingleTableEntity {
        @Id
        @GeneratedValue
        private Long id;

        private String name;
    }

    @Entity
    @Getter
    public static class SingleTableChildEntity extends SingleTableEntity {

    }

    // 부모 테이블과 각 자식 테이블을 따로 관리하고 조인을 이용한다
    @Entity
    @jakarta.persistence.Inheritance(strategy = InheritanceType.JOINED)
    public static class JoinedTableEntity {
        @Id
        @GeneratedValue
        private Long id;
    }

    // 자식 테이블에 부모의 컬럼을 포함시킨다
    @Entity
    @jakarta.persistence.Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
    public static class TablePerClassEntity {
        @Id
        @GeneratedValue
        private Long id;
    }
}
