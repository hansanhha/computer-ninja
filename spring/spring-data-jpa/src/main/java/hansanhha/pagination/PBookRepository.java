package hansanhha.pagination;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Window;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("paginationBookRepository")
public interface PBookRepository extends JpaRepository<PBook, Long> {
    
    @Query("SELECT b FROM paginationBook b")
    List<PBook> findAllToList(Pageable pageable);


    @Query("SELECT b FROM paginationBook b")
    Page<PBook> findAllToPage(Pageable pageable);


    @Query("SELECT b FROM paginationBook b")
    Slice<PBook> findAllToSlice(Pageable pageable);


    Window<PBook> findAllBy(Pageable pageable, ScrollPosition position);

}
