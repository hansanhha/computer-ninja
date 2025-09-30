package hansanhha.bulk;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, Long> {
    
    @Modifying
    @Query("UPDATE bulkAccount a SET a.activated = FALSE WHERE a.lastLogin < :limit ")
    int bulkUpdateInactivated(@Param("limit") LocalDateTime limit);
}
