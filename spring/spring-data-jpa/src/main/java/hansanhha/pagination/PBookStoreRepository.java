package hansanhha.pagination;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("paginationBookStoreRepository")
public interface PBookStoreRepository extends JpaRepository<PBookStore, Long> {
    
    @Query("SELECT bs FROM paginationBookStore bs JOIN FETCH bs.books")
    List<PBookStore> findStores(Pageable pageable);
}
