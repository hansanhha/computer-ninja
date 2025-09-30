package hansanhha.transaction.spring_rollback;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository("springRollbackUserRepository")
public interface UserRepository extends JpaRepository<User, Long> {

}
