package hansanhha.entity_relationship;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository("relationshipPostRepository")
public interface PostRepository extends CrudRepository<Post, Long> {


}
