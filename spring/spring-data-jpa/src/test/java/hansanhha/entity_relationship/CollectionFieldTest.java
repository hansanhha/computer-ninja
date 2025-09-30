package hansanhha.entity_relationship;


import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static hansanhha.entity_relationship.CollectionFieldTest.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@EnableJpaRepositories
@EntityScan(basePackageClasses = {A_Team.class, B_Team.class, A_Member.class, B_Member.class})
@DataJpaTest
public class CollectionFieldTest {

    @Autowired
    TestEntityManager em;

    // A_Team: 클래스 내부에서 컬렉션 필드 초기화 진행
    // B_Team: 클래스 내부에서 컬렉션 필드 초기화 진행 X
    @Test
    void NoExceptionUnlessNoCollectionInitial() {
        A_Team aTeam = new A_Team();
        em.persist(aTeam);
        em.flush();
        em.clear();

        A_Member aMember = new A_Member();
        A_Team saved = em.find(A_Team.class, 1L);

        saved.add(aMember);
        em.persist(saved);
        em.flush();
    }

    // A_Team: 클래스 내부에서 컬렉션 필드 초기화 진행
    // B_Team: 클래스 내부에서 컬렉션 필드 초기화 진행 X
    @Test
    void NullPointerExceptionWhenNoCollectionInitial() {
        B_Team bTeam = new B_Team();
        
        bTeam.add(new B_Member());
        bTeam.add(new B_Member());
        bTeam.add(new B_Member());
        
        em.persist(bTeam);
        em.flush();
        em.clear();

        bTeam = em.find(B_Team.class, 1L);
        assertThat(bTeam.getMembers()).hasSize(3);
    }

    @Entity
    @Getter
    @NoArgsConstructor
    static class A_Team {
        @Id
        @GeneratedValue
        private Long id;

        @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
        private Set<A_Member> members = new HashSet<>();

        void add(A_Member member) {
            members.add(member);
            member.setTeam(this);
        }
    }

    @Entity
    @Getter
    @NoArgsConstructor
    static class B_Team {
        @Id
        @GeneratedValue
        private Long id;

        @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
        private Set<B_Member> members;

        void add(B_Member member) {
            members.add(member);
            member.setTeam(this);
        }
    }

    @Entity
    @Getter
    @Setter
    @NoArgsConstructor
    static class A_Member {
        @Id
        @GeneratedValue
        private Long id;

        @JoinColumn(name = "team_id")
        @ManyToOne(fetch = FetchType.LAZY)
        private A_Team team;

    }

    @Entity
    @Getter
    @Setter
    @NoArgsConstructor
    static class B_Member {
        @Id
        @GeneratedValue
        private Long id;

        @JoinColumn(name = "team_id")
        @ManyToOne(fetch = FetchType.LAZY)
        private B_Team team;

    }

}
