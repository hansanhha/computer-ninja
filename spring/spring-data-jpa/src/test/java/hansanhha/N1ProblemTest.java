package hansanhha;


import hansanhha.n1_problem.*;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.loader.MultipleBagFetchException;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@DataJpaTest
public class N1ProblemTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    void setUp () {
        for (int i = 1; i <= 100; i++) {
            User user = new User("user " + i);
            em.persist(user);

            Post post = new Post("post " + i, user);
            em.persist(post);
        }

        em.flush();
        em.clear();
    }

    @AfterEach
    void tearDown() {
        var stats = getStatistics();
        stats.clear();
    }

    @Test
    void 게시글목록_조회쿼리에_연관엔티티_조회쿼리가_N번_발생한다() {
        setUp();
        var stats = getStatistics();
        stats.setStatisticsEnabled(true);

        List<Post> posts = (List<Post>) postRepository.findAllNoEntityGraph();

        // 각 Post 엔티티에 대한 User 연관 엔티티 조회 100번 발생
        for (Post post : posts) {
            var a = post.getUser().getUsername();
        }

        long postQueryCount = stats.getQueryExecutionCount();
        long userFetchCount = stats.getEntityFetchCount();
        long totaQueryCount = postQueryCount + userFetchCount;

        // 총 101번의 쿼리 실행
        assertThat(totaQueryCount).isEqualTo(101);
    }

    @Test
    void 엔티티그래프를_사용하여_해결() {
        setUp();
        var stats = getStatistics();
        stats.setStatisticsEnabled(true);

        List<Post> posts = postRepository.findAll();

        for (Post post : posts) {
            var a = post.getUser().getUsername();
        }

        long postQueryCount = stats.getQueryExecutionCount();
        long userFetchCount = stats.getEntityFetchCount();
        long totalQueryCount = postQueryCount + userFetchCount;

        assertThat(totalQueryCount).isEqualTo(1);
    }

    @Test
    void 페치조인을_사용하여_해결() {
        setUp();
        var stats = getStatistics();
        stats.setStatisticsEnabled(true);

        List<Post> posts = postRepository.findAllFetchJoin();

        for (Post post : posts) {
            System.out.println(post.getUser().getUsername());
        }

        long postQueryCount = stats.getQueryExecutionCount();
        long userFetchCount = stats.getEntityFetchCount();

        assertThat(postQueryCount + userFetchCount).isEqualTo(1);
    }

    @Test
    void DTO프로젝션을_사용하여_해결() {
        setUp();
        var stats = getStatistics();
        stats.setStatisticsEnabled(true);

        List<PostViewDto> posts = postRepository.findAllProjection();

        for (PostViewDto post : posts) {
            System.out.println(post.username());
        }

        long postQueryCount = stats.getQueryExecutionCount();
        long userFetchCount = stats.getEntityFetchCount();

        assertThat(postQueryCount + userFetchCount).isEqualTo(1);
    }

    @Test
    void 여러컬렉션을_페치조인하면_QueryException이_발생한다() {
        User user = new User("a");
        user.addPost(new Post("a's post 1"));
        user.addPost(new Post("a's post 2"));
        user.addComment(new Comment("a's comment 1"));
        user.addComment(new Comment("a's comment 2"));

        em.persist(user);
        em.flush();
        em.clear();

        // assertThatThrownBy(() -> userRepository.findByIdFetchJoin(1L)).isInstanceOf(InvalidDataAccessApiUsageException.class);
        // assertThatThrownBy(() -> userRepository.findByIdFetchJoin(1L)).hasRootCauseInstanceOf(MultipleBagFetchException.class);
    }

    private Statistics getStatistics() {
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory().unwrap(SessionFactory.class);
        return sessionFactory.getStatistics();
    }

}
