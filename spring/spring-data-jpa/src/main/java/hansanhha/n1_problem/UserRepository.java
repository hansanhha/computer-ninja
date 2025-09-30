package hansanhha.n1_problem;


import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository("n1ProblemUserRepository")
public interface UserRepository extends CrudRepository<User, Long> {

    @Query(
        """
        SELECT u
        FROM n1ProblemUser u
        JOIN FETCH u.posts
        """
    )
    List<User> findAllWithPosts();

    // InvalidDataAccessApiUsageException (MultipleBagFetchException - hibernate) 발생 JPQL
    @Query("""
    SELECT u
    FROM n1ProblemUser u
    JOIN FETCH u.posts
    JOIN FETCH u.comments
    WHERE u.id = :id
    """)
    User findByIdFetchJoin(@Param("id") Long id);

}
