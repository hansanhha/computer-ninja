package hansanhha.n1_problem;


import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository("n1ProblemPostRepository")
public interface PostRepository extends CrudRepository<Post, Long> {

    // 일반 findAll()과 동일하다
    @Query("SELECT p FROM n1ProblemPost p")
    List<Post> findAllNoEntityGraph();

    // 네임드 엔티티 그래프
    @EntityGraph(value = "post.author")
    List<Post> findAll();

    // 엔티티 그래프 (페치조인할 연관 엔티티의 필드 이름 명시)
    // @EntityGraph(attributePaths = "user")
    // List<PostEntityGraph> findAll();

    // 페치 조인
    @Query(
            """
            SELECT p
            FROM n1ProblemPost p
            JOIN FETCH p.user
            """
    )
    List<Post> findAllFetchJoin();

    // DTO 프로젝션
    @Query(
            """
            SELECT new hansanhha.n1_problem.PostViewDto(p.title, u.username)
            FROM n1ProblemPost p
            JOIN p.user u
            """
    )
    List<PostViewDto> findAllProjection();
}
